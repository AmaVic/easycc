//! Network startup command.
//!
//! Starts the Fabric network, creates the channel, and joins peers.

use crate::{
    config::{NetworkConfig, NetworkState, NetworkStatus},
    network, utils, workspace,
};
use anyhow::{Context, Result, bail};
use colored::Colorize;
use dialoguer::Confirm;
use std::fs;
use std::process::Command;
use std::thread;
use std::time::Duration;

/// Executes the start command.
///
/// Performs the following:
/// 1. Updates /etc/hosts with peer hostnames
/// 2. Starts Docker containers
/// 3. Creates the channel
/// 4. Joins all peers to the channel
/// 5. Updates anchor peers
/// 6. Displays CouchDB access information
pub async fn execute() -> Result<()> {
    println!(
        "{}",
        "🚀 Starting Hyperledger Fabric network..."
            .bright_blue()
            .bold()
    );
    println!();

    // Find workspace root
    let workspace_root = workspace::find_workspace_root()?;
    println!("Workspace: {}", workspace_root.display());
    println!();

    // Load config
    let config_path = workspace::get_config_path(&workspace_root);
    let config = NetworkConfig::load(&config_path)?;

    // Load state
    let state_path = workspace::get_state_path(&workspace_root);
    let mut state = NetworkState::load(&state_path)?;

    // Check if crypto is generated
    if !state.crypto_generated {
        bail!("Network artifacts not created. Run 'easycc create' first.");
    }

    // Check for existing running containers
    check_existing_networks(&config)?;

    println!("Network: {}", config.network_name);
    println!("Organizations: {}", config.organizations.join(", "));
    println!();

    // Update /etc/hosts with dynamic hostnames
    update_etc_hosts(&config.organizations)?;

    // Generate docker-compose.yaml
    println!("🐳 Generating Docker Compose configuration...");
    let docker_dir = workspace::get_docker_dir(&workspace_root);
    let docker_compose_path = docker_dir.join("docker-compose.yaml");
    network::generate_docker_compose(
        &config.network_name,
        &config.organizations,
        &docker_compose_path,
    )?;
    println!("{} docker-compose.yaml created", "✓".green());

    // Start Docker containers
    println!("🐳 Starting Docker containers...");
    let status = Command::new("docker-compose")
        .args(&["-f", docker_compose_path.to_str().unwrap(), "up", "-d"])
        .current_dir(&workspace_root)
        .status()
        .context("Failed to run docker-compose")?;

    if !status.success() {
        bail!("Failed to start Docker containers. Make sure Docker is running.");
    }

    println!("{} Containers started", "✓".green());

    // Wait for containers to be ready
    println!("⏳ Waiting for containers to be ready...");
    thread::sleep(Duration::from_secs(10));

    // Create channel using osnadmin
    println!("📺 Creating channel 'mychannel'...");
    create_channel(&workspace_root)?;
    println!("{} Channel created", "✓".green());
    state.channel_created = true;

    // Join peers to channel
    println!("🔗 Joining peers to channel...");
    join_peers_to_channel(&workspace_root, &config)?;
    println!("{} Peers joined to channel", "✓".green());

    // Update anchor peers
    println!("⚓ Updating anchor peers...");
    update_anchor_peers(&workspace_root, &config)?;
    println!("{} Anchor peers updated", "✓".green());

    // Update state
    state.network_status = NetworkStatus::Running;
    state.save(&state_path)?;

    println!();
    println!(
        "{} Network started successfully!",
        "✓".bright_green().bold()
    );
    println!();

    // Print CouchDB URLs
    println!("{}", "🗄️  CouchDB Instances:".bright_cyan().bold());
    for (i, org) in config.organizations.iter().enumerate() {
        let port = 5984 + i;
        println!(
            "  {} - {}",
            org.bright_yellow(),
            format!("http://localhost:{}", port)
                .bright_blue()
                .underline()
        );
        println!("     Credentials: admin / adminpw");
        println!("     Logs: docker logs couchdb{}", i);
    }
    println!();

    println!("{}", "Next steps:".bright_yellow().bold());
    println!("  Run 'easycc deploy' to deploy your chaincode");

    Ok(())
}

fn create_channel(workspace_root: &std::path::Path) -> Result<()> {
    let bin_dir = utils::get_fabric_bin_dir();
    let osnadmin = bin_dir.join("osnadmin");

    let genesis_block = workspace_root.join("channel-artifacts/genesis.block");

    // Use orderer TLS certificates for client authentication
    let ca_cert = format!(
        "{}/crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/ca.crt",
        workspace_root.display()
    );
    let client_cert = format!(
        "{}/crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/server.crt",
        workspace_root.display()
    );
    let client_key = format!(
        "{}/crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/server.key",
        workspace_root.display()
    );

    // Retry logic with exponential backoff
    let max_retries = 3;
    let mut retry_delay = std::time::Duration::from_secs(3);

    for attempt in 1..=max_retries {
        let output = Command::new(&osnadmin)
            .args(&[
                "channel",
                "join",
                "--channelID",
                "mychannel",
                "--config-block",
                genesis_block.to_str().unwrap(),
                "-o",
                "orderer.example.com:7053",
                "--ca-file",
                &ca_cert,
                "--client-cert",
                &client_cert,
                "--client-key",
                &client_key,
            ])
            .output();

        match output {
            Ok(out) if out.status.success() => {
                println!(
                    "{} Channel 'mychannel' created successfully",
                    "✓".bright_green().bold()
                );
                return Ok(());
            }
            Ok(out) => {
                if attempt < max_retries {
                    // Show error on first attempt to help debug
                    if attempt == 1 {
                        let stderr = String::from_utf8_lossy(&out.stderr);
                        println!("\n  Debug: {}", stderr.trim());
                    }
                    print!(
                        "\r⏳ Waiting for orderer to be ready... (attempt {}/{})",
                        attempt, max_retries
                    );
                    std::io::Write::flush(&mut std::io::stdout()).ok();
                    std::thread::sleep(retry_delay);
                    retry_delay =
                        std::cmp::min(retry_delay * 2, std::time::Duration::from_secs(10));
                } else {
                    println!();
                    let stderr = String::from_utf8_lossy(&out.stderr);
                    eprintln!("Error: {}", stderr);
                    bail!("Failed to create channel after {} attempts", max_retries);
                }
            }
            Err(e) => {
                if attempt < max_retries {
                    print!(
                        "\r⏳ Waiting for orderer to be ready... (attempt {}/{})",
                        attempt, max_retries
                    );
                    std::io::Write::flush(&mut std::io::stdout()).ok();
                    std::thread::sleep(retry_delay);
                    retry_delay =
                        std::cmp::min(retry_delay * 2, std::time::Duration::from_secs(10));
                } else {
                    println!();
                    bail!("Failed to run osnadmin: {}", e);
                }
            }
        }
    }

    Ok(())
}

fn join_peers_to_channel(workspace_root: &std::path::Path, config: &NetworkConfig) -> Result<()> {
    let bin_dir = utils::get_fabric_bin_dir();
    let peer = bin_dir.join("peer");
    let fabric_cfg_path = bin_dir.join("config");

    // Use the genesis block we already created
    let block_path = workspace_root.join("channel-artifacts/genesis.block");

    for (i, org) in config.organizations.iter().enumerate() {
        let domain = format!("{}.example.com", org.to_lowercase());
        let port = 7051 + (i * 1000);
        // Use Org1MSP, Org2MSP format to match crypto-config generation
        let msp_id = format!("Org{}MSP", i + 1);

        // Set environment variables for this peer
        let msp_config_path = format!(
            "{}/crypto-config/peerOrganizations/{}/users/Admin@{}/msp",
            workspace_root.display(),
            domain,
            domain
        );
        let tls_root_cert = format!(
            "{}/crypto-config/peerOrganizations/{}/peers/peer0.{}/tls/ca.crt",
            workspace_root.display(),
            domain,
            domain
        );

        // Join peer to channel
        let status = Command::new(&peer)
            .args(&["channel", "join", "-b", block_path.to_str().unwrap()])
            .env("FABRIC_CFG_PATH", fabric_cfg_path.to_str().unwrap())
            .env("CORE_PEER_TLS_ENABLED", "true")
            .env("CORE_PEER_LOCALMSPID", &msp_id)
            .env("CORE_PEER_TLS_ROOTCERT_FILE", &tls_root_cert)
            .env("CORE_PEER_MSPCONFIGPATH", &msp_config_path)
            .env("CORE_PEER_ADDRESS", format!("peer0.{}:{}", domain, port))
            .status()
            .context("Failed to join peer to channel")?;

        if !status.success() {
            bail!("Failed to join peer {} to channel", domain);
        }
    }

    Ok(())
}

fn check_existing_networks(_config: &NetworkConfig) -> Result<()> {
    // Check for both running and stopped Fabric containers
    let output = Command::new("docker")
        .args(&["ps", "-a", "--format", "{{.Names}}"])
        .output()
        .context("Failed to check Docker containers")?;

    let containers = String::from_utf8_lossy(&output.stdout);
    let fabric_containers: Vec<&str> = containers
        .lines()
        .filter(|line| line.contains("peer0.") || line.contains("orderer.") || line.contains("ca."))
        .collect();

    if !fabric_containers.is_empty() {
        // Check if any are running
        let running_output = Command::new("docker")
            .args(&["ps", "--format", "{{.Names}}"])
            .output()
            .context("Failed to check running containers")?;

        let running_containers = String::from_utf8_lossy(&running_output.stdout);
        let running_fabric: Vec<&str> = running_containers
            .lines()
            .filter(|line| {
                line.contains("peer0.") || line.contains("orderer.") || line.contains("ca.")
            })
            .collect();

        println!(
            "{}",
            "⚠️  Warning: Detected existing Fabric containers:"
                .yellow()
                .bold()
        );
        for container in &fabric_containers {
            let status = if running_fabric.contains(container) {
                "running".bright_green()
            } else {
                "stopped".bright_black()
            };
            println!("  - {} ({})", container, status);
        }
        println!();
        println!(
            "{}",
            "These containers may have existing ledger data that will cause conflicts.".yellow()
        );
        println!();

        let should_clean = Confirm::new()
            .with_prompt("Remove these containers and their volumes?")
            .default(true)
            .interact()
            .context("Failed to get user input")?;

        if should_clean {
            println!("🛑 Removing existing containers and volumes...");

            // Remove containers
            for container in fabric_containers {
                let _ = Command::new("docker")
                    .args(&["rm", "-f", "-v", container])
                    .output();
            }

            // Also remove any named volumes that might contain ledger data
            let volume_output = Command::new("docker")
                .args(&["volume", "ls", "-q"])
                .output()
                .context("Failed to list volumes")?;

            let volumes = String::from_utf8_lossy(&volume_output.stdout);
            let fabric_volumes: Vec<&str> = volumes
                .lines()
                .filter(|line| {
                    line.contains("peer0.") || line.contains("orderer.") || line.contains("ca.")
                })
                .collect();

            for volume in fabric_volumes {
                let _ = Command::new("docker")
                    .args(&["volume", "rm", "-f", volume])
                    .output();
            }

            println!("{} Containers and volumes removed", "✓".green());
            println!();
            // Wait a moment for cleanup
            thread::sleep(Duration::from_secs(2));
        } else {
            bail!(
                "Cannot start network with existing Fabric containers. Please remove them first with 'easycc clean'."
            );
        }
    }

    Ok(())
}

fn update_etc_hosts(organizations: &[String]) -> Result<()> {
    println!("🔧 Checking hosts file configuration...");

    // Determine hosts file path based on OS
    let hosts_path = if cfg!(windows) {
        r"C:\Windows\System32\drivers\etc\hosts"
    } else {
        "/etc/hosts"
    };

    // Read current hosts file
    let hosts_content = match fs::read_to_string(hosts_path) {
        Ok(content) => content,
        Err(_) => {
            println!(
                "{} Could not read hosts file - you may need to add entries manually",
                "⚠️".yellow()
            );
            return Ok(());
        }
    };

    // Build list of required hostnames
    let mut required_hosts = vec!["orderer.example.com".to_string()];
    for org in organizations {
        required_hosts.push(format!("peer0.{}.example.com", org.to_lowercase()));
    }

    // Check which hosts are missing
    let missing_hosts: Vec<String> = required_hosts
        .iter()
        .filter(|host| !hosts_content.contains(host.as_str()))
        .cloned()
        .collect();

    if missing_hosts.is_empty() {
        println!("{} All required hostnames found in hosts file", "✓".green());
        return Ok(());
    }

    // Prompt to add missing hosts
    println!("{}", "⚠️  Missing hostnames in hosts file:".yellow());
    for host in &missing_hosts {
        println!("  - {}", host);
    }
    println!();

    let hosts_line = format!("127.0.0.1 {}", missing_hosts.join(" "));
    println!("The following line needs to be added to your hosts file:");
    println!("{}", hosts_line.bright_cyan());
    println!();

    let should_add = Confirm::new()
        .with_prompt("Add these entries to hosts file now? (requires admin privileges)")
        .default(true)
        .interact()
        .context("Failed to get user input")?;

    if should_add {
        // Platform-specific command to update hosts file
        let status = if cfg!(windows) {
            // Windows: use PowerShell with elevated privileges
            Command::new("powershell")
                .args(&[
                    "-Command",
                    &format!("Add-Content -Path '{}' -Value '{}'", hosts_path, hosts_line),
                ])
                .status()
                .context("Failed to update hosts file")?
        } else {
            // Unix/Mac: use sudo
            Command::new("sudo")
                .args(&[
                    "sh",
                    "-c",
                    &format!("echo '{}' >> {}", hosts_line, hosts_path),
                ])
                .status()
                .context("Failed to update hosts file")?
        };

        if status.success() {
            println!("{} Hosts file updated successfully", "✓".green());
        } else {
            bail!("Failed to update hosts file. Please add the entries manually.");
        }
    } else {
        println!(
            "{}",
            "⚠️  Warning: Network may fail to start without proper hosts file entries".yellow()
        );
        println!("You can add them later manually:");
        if cfg!(windows) {
            println!("  Open PowerShell as Administrator and run:");
            println!(
                "  Add-Content -Path '{}' -Value '{}'",
                hosts_path, hosts_line
            );
        } else {
            println!("  sudo sh -c 'echo \"{}\" >> {}'", hosts_line, hosts_path);
        }
    }

    Ok(())
}

fn update_anchor_peers(workspace_root: &std::path::Path, config: &NetworkConfig) -> Result<()> {
    let bin_dir = utils::get_fabric_bin_dir();
    let peer = bin_dir.join("peer");
    let configtxgen = bin_dir.join("configtxgen");
    let fabric_cfg_path = bin_dir.join("config");

    for (i, org) in config.organizations.iter().enumerate() {
        let org_num = i + 1;
        let domain = format!("{}.example.com", org.to_lowercase());
        let port = 7051 + (i * 1000);
        // Use Org1MSP, Org2MSP format to match crypto-config generation
        let msp_id = format!("Org{}MSP", org_num);

        let msp_config_path = format!(
            "{}/crypto-config/peerOrganizations/{}/users/Admin@{}/msp",
            workspace_root.display(),
            domain,
            domain
        );
        let tls_root_cert = format!(
            "{}/crypto-config/peerOrganizations/{}/peers/peer0.{}/tls/ca.crt",
            workspace_root.display(),
            domain,
            domain
        );

        // Generate anchor peer update transaction
        let anchor_tx = workspace_root.join(format!("channel-artifacts/{}anchors.tx", msp_id));
        let configtx_yaml = workspace_root.join("configtx.yaml");

        let status = Command::new(&configtxgen)
            .args(&[
                "-profile",
                "TwoOrgsApplicationGenesis",
                "-outputAnchorPeersUpdate",
                anchor_tx.to_str().unwrap(),
                "-channelID",
                "mychannel",
                "-asOrg",
                &msp_id,
            ])
            .env("FABRIC_CFG_PATH", configtx_yaml.parent().unwrap())
            .stderr(std::process::Stdio::null())
            .status()
            .context("Failed to generate anchor peer update")?;

        if !status.success() {
            // Anchor peer updates might fail in some versions, continue anyway
            continue;
        }

        // Update anchor peer
        let _ = Command::new(&peer)
            .args(&[
                "channel", "update",
                "-c", "mychannel",
                "-f", anchor_tx.to_str().unwrap(),
                "-o", "orderer.example.com:7050",
                "--tls",
                "--cafile", &format!("{}/crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem", workspace_root.display()),
            ])
            .env("FABRIC_CFG_PATH", fabric_cfg_path.to_str().unwrap())
            .env("CORE_PEER_TLS_ENABLED", "true")
            .env("CORE_PEER_LOCALMSPID", &msp_id)
            .env("CORE_PEER_TLS_ROOTCERT_FILE", &tls_root_cert)
            .env("CORE_PEER_MSPCONFIGPATH", &msp_config_path)
            .env("CORE_PEER_ADDRESS", format!("peer0.{}:{}", domain, port))
            .stderr(std::process::Stdio::null())
            .status();
    }

    Ok(())
}
