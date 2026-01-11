//! Chaincode deployment command.
//!
//! Handles the complete Fabric 2.x chaincode lifecycle: package, install, approve, commit.

use crate::config::NetworkConfig;
use crate::utils;
use crate::workspace;
use anyhow::{Context, Result, bail};
use colored::Colorize;
use std::net::TcpStream;
use std::path::PathBuf;
use std::process::Command;
use std::thread;
use std::time::Duration;

/// Executes the deploy command.
///
/// Deploys chaincode through the Fabric lifecycle:
/// 1. Packages the chaincode
/// 2. Installs on all peers
/// 3. Approves for each organization
/// 4. Commits to the channel
/// 5. Initializes (if applicable)
///
/// # Arguments
///
/// * `chaincode_path` - Path to chaincode directory (defaults to ./chaincode)
/// * `name` - Chaincode name (defaults to "mychaincode")
/// * `version` - Chaincode version (defaults to "1.0")
pub async fn execute(
    chaincode_path: Option<String>,
    name: Option<String>,
    version: Option<String>,
) -> Result<()> {
    let workspace_root = workspace::find_workspace_root()?;

    println!();
    println!("{} Deploying chaincode...", "📦".bright_blue().bold());
    println!();

    // Load network config
    let config_path = workspace::get_config_path(&workspace_root);
    let config = NetworkConfig::load(&config_path)?;

    // Determine chaincode path
    let cc_path = if let Some(path) = chaincode_path {
        PathBuf::from(path)
    } else {
        workspace_root.join("chaincode")
    };

    if !cc_path.exists() {
        bail!("Chaincode directory not found: {}", cc_path.display());
    }

    // Determine chaincode name and version
    let cc_name = name.unwrap_or_else(|| "mychaincode".to_string());
    let cc_version = version.unwrap_or_else(|| "1.0".to_string());

    println!("Chaincode: {}", cc_name.bright_cyan());
    println!("Version: {}", cc_version.bright_cyan());
    println!("Path: {}", cc_path.display().to_string().bright_cyan());
    println!();

    // Check if chaincode is already built
    let build_libs = cc_path.join("build/libs");
    if !build_libs.exists() || std::fs::read_dir(&build_libs)?.count() == 0 {
        // Build chaincode is skipped - let Fabric build it during install
        println!(
            "{} Chaincode will be built during installation by Fabric",
            "ℹ️".bright_blue()
        );
        println!();
    } else {
        println!(
            "{} Found pre-built chaincode in build/libs",
            "✓".bright_green()
        );
        println!();
    }

    // Step 2: Package chaincode
    println!("{} Packaging chaincode...", "📦".bright_yellow());
    let package_file = package_chaincode(&workspace_root, &cc_path, &cc_name, &cc_version)?;
    println!(
        "{} Chaincode packaged: {}",
        "✓".bright_green().bold(),
        package_file.file_name().unwrap().to_str().unwrap()
    );
    println!();

    // Step 3: Install on all peers
    println!("{} Installing chaincode on peers...", "⚙️".bright_yellow());
    let package_id = install_chaincode(
        &workspace_root,
        &config,
        &package_file,
        &cc_name,
        &cc_version,
    )?;
    println!(
        "{} Chaincode installed with package ID:",
        "✓".bright_green().bold()
    );
    println!("  {}", package_id.bright_cyan());
    println!();

    // Step 3.5: Wait for orderer to be ready
    println!(
        "{} Waiting for orderer to be ready...",
        "⏳".bright_yellow()
    );
    wait_for_orderer(&workspace_root)?;
    println!("{} Orderer is ready", "✓".bright_green().bold());
    println!();

    // Step 4: Approve for each organization
    println!(
        "{} Approving chaincode for organizations...",
        "✅".bright_yellow()
    );
    approve_chaincode(&workspace_root, &config, &cc_name, &cc_version, &package_id)?;
    println!(
        "{} Chaincode approved for all organizations",
        "✓".bright_green().bold()
    );
    println!();

    // Step 5: Commit chaincode definition
    println!(
        "{} Committing chaincode to channel...",
        "🔗".bright_yellow()
    );
    commit_chaincode(&workspace_root, &config, &cc_name, &cc_version)?;
    println!(
        "{} Chaincode committed successfully",
        "✓".bright_green().bold()
    );
    println!();

    println!(
        "{} Chaincode deployment complete!",
        "✓".bright_green().bold()
    );
    println!();
    println!("{}", "Next steps:".bright_yellow().bold());
    println!("  Your chaincode is ready to use!");
    println!("  Run 'easycc export' to get connection profiles for client applications");
    println!();

    Ok(())
}

fn package_chaincode(
    workspace_root: &PathBuf,
    chaincode_path: &PathBuf,
    name: &str,
    version: &str,
) -> Result<PathBuf> {
    let bin_dir = utils::get_fabric_bin_dir();
    let peer = bin_dir.join("peer");

    // Create package output directory
    let package_dir = workspace_root.join("chaincode-packages");
    std::fs::create_dir_all(&package_dir)?;

    let package_file = package_dir.join(format!("{}-{}.tar.gz", name, version));

    // Package the chaincode source (peer will build it during install, but we already built it)
    // Use the chaincode directory directly
    let status = Command::new(&peer)
        .args(&[
            "lifecycle",
            "chaincode",
            "package",
            package_file.to_str().unwrap(),
            "--path",
            chaincode_path.to_str().unwrap(),
            "--lang",
            "java",
            "--label",
            &format!("{}_{}", name, version),
        ])
        .env("FABRIC_CFG_PATH", bin_dir.join("config").to_str().unwrap())
        .stdout(std::process::Stdio::null())
        .status()
        .context("Failed to package chaincode")?;

    if !status.success() {
        bail!("Failed to package chaincode");
    }

    Ok(package_file)
}

fn install_chaincode(
    workspace_root: &PathBuf,
    config: &NetworkConfig,
    package_file: &PathBuf,
    name: &str,
    version: &str,
) -> Result<String> {
    let bin_dir = utils::get_fabric_bin_dir();
    let peer = bin_dir.join("peer");
    let fabric_cfg_path = bin_dir.join("config");

    let mut package_id = String::new();

    for (i, org) in config.organizations.iter().enumerate() {
        let domain = format!("{}.example.com", org.to_lowercase());
        let port = 7051 + (i * 1000);
        let msp_id = format!("Org{}MSP", i + 1);

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

        let output = Command::new(&peer)
            .args(&[
                "lifecycle",
                "chaincode",
                "install",
                package_file.to_str().unwrap(),
            ])
            .env("FABRIC_CFG_PATH", fabric_cfg_path.to_str().unwrap())
            .env("CORE_PEER_TLS_ENABLED", "true")
            .env("CORE_PEER_LOCALMSPID", &msp_id)
            .env("CORE_PEER_TLS_ROOTCERT_FILE", &tls_root_cert)
            .env("CORE_PEER_MSPCONFIGPATH", &msp_config_path)
            .env("CORE_PEER_ADDRESS", format!("peer0.{}:{}", domain, port))
            .output()
            .context("Failed to install chaincode")?;

        if !output.status.success() {
            let stderr = String::from_utf8_lossy(&output.stderr);

            // Check if it's already installed
            if stderr.contains("chaincode already successfully installed") {
                // Extract package ID from error message
                // Format: "...package ID 'name_version:hash'..."
                if let Some(start) = stderr.find("package ID '") {
                    if let Some(end) = stderr[start + 12..].find('\'') {
                        let extracted_id = &stderr[start + 12..start + 12 + end];
                        if !extracted_id.is_empty() {
                            package_id = extracted_id.to_string();
                        }
                    }
                }
                println!("  ✓ Already installed on peer0.{}", domain);
                continue;
            }

            bail!("Failed to install chaincode on {}: {}", domain, stderr);
        }

        // Extract package ID from output (first installation)
        if package_id.is_empty() {
            let stdout = String::from_utf8_lossy(&output.stdout);
            let stderr = String::from_utf8_lossy(&output.stderr);

            // Try to find package ID in stdout or stderr
            let combined = format!("{}\n{}", stdout, stderr);

            for line in combined.lines() {
                if line.contains("Chaincode code package identifier:") {
                    // Format: "Chaincode code package identifier: name_version:hash"
                    if let Some(colon_pos) = line.find("identifier:") {
                        package_id = line[colon_pos + 11..].trim().to_string();
                        break;
                    }
                } else if line.contains("Package ID:") {
                    // Format: "Package ID: name_version:hash"
                    if let Some(colon_pos) = line.find("ID:") {
                        package_id = line[colon_pos + 3..].trim().to_string();
                        break;
                    }
                }
            }
        }

        println!("  ✓ Installed on peer0.{}", domain);
    }

    // Query installed chaincode to get package ID
    if package_id.is_empty() {
        let org = &config.organizations[0];
        let domain = format!("{}.example.com", org.to_lowercase());
        let port = 7051;
        let msp_id = "Org1MSP".to_string();

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

        let output = Command::new(&peer)
            .args(&["lifecycle", "chaincode", "queryinstalled"])
            .env("FABRIC_CFG_PATH", fabric_cfg_path.to_str().unwrap())
            .env("CORE_PEER_TLS_ENABLED", "true")
            .env("CORE_PEER_LOCALMSPID", &msp_id)
            .env("CORE_PEER_TLS_ROOTCERT_FILE", &tls_root_cert)
            .env("CORE_PEER_MSPCONFIGPATH", &msp_config_path)
            .env("CORE_PEER_ADDRESS", format!("peer0.{}:{}", domain, port))
            .output()
            .context("Failed to query installed chaincode")?;

        let stdout = String::from_utf8_lossy(&output.stdout);

        // Parse output to find package ID matching our label
        let mut found_label = false;
        for line in stdout.lines() {
            if line.contains(&format!("Label: {}_{}", name, version)) {
                found_label = true;
                continue;
            } else if found_label && line.starts_with("Package ID:") {
                // Format: "Package ID: name_version:hash"
                if let Some(colon_pos) = line.find("ID:") {
                    package_id = line[colon_pos + 3..].trim().to_string();
                    break;
                }
            }
        }
    }

    if package_id.is_empty() {
        bail!("Failed to get package ID from installed chaincode");
    }

    Ok(package_id)
}

fn wait_for_orderer(_workspace_root: &PathBuf) -> Result<()> {
    // Try for up to 30 seconds
    let max_attempts = 15;
    let mut attempt = 0;

    while attempt < max_attempts {
        // Try to establish TCP connection to orderer port
        if TcpStream::connect_timeout(&"127.0.0.1:7050".parse().unwrap(), Duration::from_secs(1))
            .is_ok()
        {
            // Wait an additional 2 seconds for Raft leader election to complete
            thread::sleep(Duration::from_secs(2));
            return Ok(());
        }

        attempt += 1;
        if attempt < max_attempts {
            thread::sleep(Duration::from_secs(2));
        }
    }

    bail!("Orderer did not become ready within 30 seconds")
}

fn approve_chaincode(
    workspace_root: &PathBuf,
    config: &NetworkConfig,
    name: &str,
    version: &str,
    package_id: &str,
) -> Result<()> {
    let bin_dir = utils::get_fabric_bin_dir();
    let peer = bin_dir.join("peer");
    let fabric_cfg_path = bin_dir.join("config");

    // Build dynamic signature policy that includes all organizations
    let signature_policy = {
        let policies: Vec<String> = (1..=config.organizations.len())
            .map(|i| format!("'Org{}MSP.member'", i))
            .collect();
        format!("OR({})", policies.join(","))
    };

    for (i, org) in config.organizations.iter().enumerate() {
        let domain = format!("{}.example.com", org.to_lowercase());
        let port = 7051 + (i * 1000);
        let msp_id = format!("Org{}MSP", i + 1);

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
        let orderer_ca = format!(
            "{}/crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem",
            workspace_root.display()
        );

        let output = Command::new(&peer)
            .args(&[
                "lifecycle",
                "chaincode",
                "approveformyorg",
                "-o",
                "orderer.example.com:7050",
                "--channelID",
                "mychannel",
                "--name",
                name,
                "--version",
                version,
                "--package-id",
                package_id,
                "--sequence",
                "1",
                "--signature-policy",
                &signature_policy,
                "--tls",
                "--cafile",
                &orderer_ca,
            ])
            .env("FABRIC_CFG_PATH", fabric_cfg_path.to_str().unwrap())
            .env("CORE_PEER_TLS_ENABLED", "true")
            .env("CORE_PEER_LOCALMSPID", &msp_id)
            .env("CORE_PEER_TLS_ROOTCERT_FILE", &tls_root_cert)
            .env("CORE_PEER_MSPCONFIGPATH", &msp_config_path)
            .env("CORE_PEER_ADDRESS", format!("peer0.{}:{}", domain, port))
            .output()
            .context("Failed to approve chaincode")?;

        if !output.status.success() {
            let stderr = String::from_utf8_lossy(&output.stderr);
            bail!("Failed to approve chaincode for {}: {}", org, stderr);
        }

        println!("  ✓ Approved for {}", org);
    }

    Ok(())
}

fn commit_chaincode(
    workspace_root: &PathBuf,
    config: &NetworkConfig,
    name: &str,
    version: &str,
) -> Result<()> {
    let bin_dir = utils::get_fabric_bin_dir();
    let peer = bin_dir.join("peer");
    let fabric_cfg_path = bin_dir.join("config");

    // Use first organization's credentials
    let org = &config.organizations[0];
    let domain = format!("{}.example.com", org.to_lowercase());
    let port = 7051;
    let msp_id = "Org1MSP".to_string();

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
    let orderer_ca = format!(
        "{}/crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem",
        workspace_root.display()
    );

    // Build peer addresses and TLS certs for all organizations
    let peer_addresses: Vec<String> = config
        .organizations
        .iter()
        .enumerate()
        .map(|(i, org)| {
            let domain = format!("{}.example.com", org.to_lowercase());
            let port = 7051 + (i * 1000);
            format!("peer0.{}:{}", domain, port)
        })
        .collect();

    let tls_certs: Vec<String> = config
        .organizations
        .iter()
        .map(|org_name| {
            let domain = format!("{}.example.com", org_name.to_lowercase());
            format!(
                "{}/crypto-config/peerOrganizations/{}/peers/peer0.{}/tls/ca.crt",
                workspace_root.display(),
                domain,
                domain
            )
        })
        .collect();

    // Build dynamic signature policy that includes all organizations
    let signature_policy = {
        let policies: Vec<String> = (1..=config.organizations.len())
            .map(|i| format!("'Org{}MSP.member'", i))
            .collect();
        format!("OR({})", policies.join(","))
    };

    // Build args with owned strings
    let mut args = vec![
        "lifecycle".to_string(),
        "chaincode".to_string(),
        "commit".to_string(),
        "-o".to_string(),
        "orderer.example.com:7050".to_string(),
        "--channelID".to_string(),
        "mychannel".to_string(),
        "--name".to_string(),
        name.to_string(),
        "--version".to_string(),
        version.to_string(),
        "--sequence".to_string(),
        "1".to_string(),
        "--signature-policy".to_string(),
        signature_policy,
        "--tls".to_string(),
        "--cafile".to_string(),
        orderer_ca.clone(),
    ];

    for addr in &peer_addresses {
        args.push("--peerAddresses".to_string());
        args.push(addr.clone());
    }

    for cert in &tls_certs {
        args.push("--tlsRootCertFiles".to_string());
        args.push(cert.clone());
    }

    let args_refs: Vec<&str> = args.iter().map(|s| s.as_str()).collect();

    let status = Command::new(&peer)
        .args(&args_refs)
        .env("FABRIC_CFG_PATH", fabric_cfg_path.to_str().unwrap())
        .env("CORE_PEER_TLS_ENABLED", "true")
        .env("CORE_PEER_LOCALMSPID", &msp_id)
        .env("CORE_PEER_TLS_ROOTCERT_FILE", &tls_root_cert)
        .env("CORE_PEER_MSPCONFIGPATH", &msp_config_path)
        .env("CORE_PEER_ADDRESS", format!("peer0.{}:{}", domain, port))
        .stderr(std::process::Stdio::null())
        .status()
        .context("Failed to commit chaincode")?;

    if !status.success() {
        bail!("Failed to commit chaincode to channel");
    }

    Ok(())
}
