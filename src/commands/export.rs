//! Connection profile and wallet export command.
//!
//! Exports connection profiles and admin wallets for use with Fabric SDKs.

use crate::config::NetworkConfig;
use crate::workspace;
use anyhow::{Context, Result, bail};
use colored::Colorize;
use serde_json::json;
use std::path::PathBuf;

/// Executes the export command.
///
/// Generates connection profiles and wallet credentials for each organization,
/// making them ready for use with Fabric SDKs (Node.js, Java, etc.).
///
/// # Arguments
///
/// * `output` - Optional output directory (defaults to ./exports)
pub async fn execute(output: Option<String>) -> Result<()> {
    let workspace_root = workspace::find_workspace_root()?;

    println!();
    println!(
        "{} Exporting connection profiles and wallets...",
        "📤".bright_blue().bold()
    );
    println!();

    // Load network config
    let config_path = workspace::get_config_path(&workspace_root);
    let config = NetworkConfig::load(&config_path)?;

    // Determine output directory
    let export_dir = if let Some(path) = output {
        PathBuf::from(path)
    } else {
        workspace_root.join("exports")
    };

    std::fs::create_dir_all(&export_dir)?;

    println!(
        "Export directory: {}",
        export_dir.display().to_string().bright_cyan()
    );
    println!();

    // Create subdirectories
    let profiles_dir = export_dir.join("connection-profiles");
    let wallets_dir = export_dir.join("wallets");
    std::fs::create_dir_all(&profiles_dir)?;
    std::fs::create_dir_all(&wallets_dir)?;

    // Export connection profile for each organization
    println!("{} Generating connection profiles...", "📝".bright_yellow());
    for org in &config.organizations {
        export_connection_profile(&workspace_root, &config, org, &profiles_dir)?;
        println!("  ✓ Generated connection profile for {}", org);
    }
    println!();

    // Export wallet credentials for each organization
    println!("{} Exporting wallet credentials...", "🔐".bright_yellow());
    for org in &config.organizations {
        export_wallet(&workspace_root, &config, org, &wallets_dir)?;
        println!("  ✓ Exported wallet for {}", org);
    }
    println!();

    // Display public keys
    println!("{}", "📋 Public Keys:".bright_cyan().bold());
    println!();
    for org in &config.organizations {
        let pubkey_file = wallets_dir.join(org.to_lowercase()).join("public_key.txt");
        if pubkey_file.exists() {
            let pubkey = std::fs::read_to_string(&pubkey_file)?;
            println!("  {} ({})", org.bright_yellow().bold(), "admin".dimmed());
            println!("  {}", pubkey.trim().bright_white());
            println!();
        }
    }

    println!("{} Export complete!", "✓".bright_green().bold());
    println!();
    println!("{}", "Files exported:".bright_yellow().bold());
    println!("  Connection Profiles: {}", profiles_dir.display());
    println!("  Wallets: {}", wallets_dir.display());
    println!();
    println!("{}", "Next steps:".bright_yellow().bold());
    println!("  Use these files in your client applications to connect to the network");
    println!("  Each organization has its own connection profile and wallet");
    println!();

    Ok(())
}

fn export_connection_profile(
    workspace_root: &PathBuf,
    config: &NetworkConfig,
    org: &str,
    output_dir: &PathBuf,
) -> Result<()> {
    let domain = format!("{}.example.com", org.to_lowercase());
    let org_index = config.organizations.iter().position(|o| o == org).unwrap();
    let peer_port = 7051 + (org_index * 1000);
    let msp_id = format!("Org{}MSP", org_index + 1);

    // Read certificate files
    let ca_cert_path = workspace_root.join(format!(
        "crypto-config/peerOrganizations/{}/ca/ca.{}-cert.pem",
        domain, domain
    ));
    let ca_cert =
        std::fs::read_to_string(&ca_cert_path).context("Failed to read CA certificate")?;

    let peer_cert_path = workspace_root.join(format!(
        "crypto-config/peerOrganizations/{}/peers/peer0.{}/tls/ca.crt",
        domain, domain
    ));
    let peer_cert =
        std::fs::read_to_string(&peer_cert_path).context("Failed to read peer TLS certificate")?;

    let orderer_cert_path = workspace_root.join(
        "crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/ca.crt",
    );
    let orderer_cert = std::fs::read_to_string(&orderer_cert_path)
        .context("Failed to read orderer TLS certificate")?;

    // Create connection profile JSON
    let profile = json!({
        "name": format!("{}-network", config.network_name),
        "version": "1.0.0",
        "client": {
            "organization": org,
            "connection": {
                "timeout": {
                    "peer": {
                        "endorser": "300"
                    },
                    "orderer": "300"
                }
            }
        },
        "organizations": {
            org: {
                "mspid": msp_id,
                "peers": [
                    format!("peer0.{}", domain)
                ],
                "certificateAuthorities": [
                    format!("ca.{}", domain)
                ]
            }
        },
        "peers": {
            format!("peer0.{}", domain): {
                "url": format!("grpcs://peer0.{}:{}", domain, peer_port),
                "tlsCACerts": {
                    "pem": peer_cert
                },
                "grpcOptions": {
                    "ssl-target-name-override": format!("peer0.{}", domain),
                    "hostnameOverride": format!("peer0.{}", domain)
                }
            }
        },
        "certificateAuthorities": {
            format!("ca.{}", domain): {
                "url": format!("https://ca.{}:7054", domain),
                "caName": format!("ca-{}", org.to_lowercase()),
                "tlsCACerts": {
                    "pem": ca_cert.clone()
                },
                "httpOptions": {
                    "verify": false
                }
            }
        },
        "orderers": {
            "orderer.example.com": {
                "url": "grpcs://orderer.example.com:7050",
                "tlsCACerts": {
                    "pem": orderer_cert
                },
                "grpcOptions": {
                    "ssl-target-name-override": "orderer.example.com",
                    "hostnameOverride": "orderer.example.com"
                }
            }
        },
        "channels": {
            "mychannel": {
                "orderers": ["orderer.example.com"],
                "peers": {
                    format!("peer0.{}", domain): {
                        "endorsingPeer": true,
                        "chaincodeQuery": true,
                        "ledgerQuery": true,
                        "eventSource": true
                    }
                }
            }
        }
    });

    let profile_file = output_dir.join(format!("connection-{}.json", org.to_lowercase()));
    std::fs::write(&profile_file, serde_json::to_string_pretty(&profile)?)?;

    Ok(())
}

fn export_wallet(
    workspace_root: &PathBuf,
    config: &NetworkConfig,
    org: &str,
    output_dir: &PathBuf,
) -> Result<()> {
    let domain = format!("{}.example.com", org.to_lowercase());
    // Get org index to determine MSP ID (Org1MSP, Org2MSP, etc.)
    let org_index = config.organizations.iter().position(|o| o == org).unwrap();
    let msp_id = format!("Org{}MSP", org_index + 1);

    // Create wallet directory for this org
    let wallet_dir = output_dir.join(org.to_lowercase());
    std::fs::create_dir_all(&wallet_dir)?;

    // Read admin credentials
    let admin_cert_path = workspace_root.join(format!(
        "crypto-config/peerOrganizations/{}/users/Admin@{}/msp/signcerts/Admin@{}-cert.pem",
        domain, domain, domain
    ));
    let admin_cert =
        std::fs::read_to_string(&admin_cert_path).context("Failed to read admin certificate")?;

    let admin_key_dir = workspace_root.join(format!(
        "crypto-config/peerOrganizations/{}/users/Admin@{}/msp/keystore",
        domain, domain
    ));

    // Find the private key file
    let key_files: Vec<_> = std::fs::read_dir(&admin_key_dir)?
        .filter_map(|e| e.ok())
        .filter(|e| e.path().is_file())
        .collect();

    if key_files.is_empty() {
        bail!("No private key found for admin of {}", org);
    }

    let admin_key = std::fs::read_to_string(&key_files[0].path())
        .context("Failed to read admin private key")?;

    // Create wallet identity file (compatible with Fabric SDK)
    let identity = json!({
        "credentials": {
            "certificate": admin_cert,
            "privateKey": admin_key
        },
        "mspId": msp_id,
        "type": "X.509",
        "version": 1
    });

    let identity_file = wallet_dir.join("admin.id");
    std::fs::write(&identity_file, serde_json::to_string_pretty(&identity)?)?;

    // Also create separate cert and key files for easy access
    let cert_file = wallet_dir.join("admin-cert.pem");
    let key_file = wallet_dir.join("admin-key.pem");
    std::fs::write(&cert_file, &admin_cert)?;
    std::fs::write(&key_file, &admin_key)?;

    // Extract and save the public key from the certificate
    match extract_public_key_from_cert(&admin_cert) {
        Ok(public_key) => {
            let pubkey_file = wallet_dir.join("public_key.txt");
            std::fs::write(&pubkey_file, &public_key)?;
        }
        Err(e) => {
            eprintln!("Warning: Failed to extract public key for {}: {}", org, e);
        }
    }

    // Create a metadata file
    let metadata = json!({
        "organization": org,
        "mspId": msp_id,
        "identityLabel": "admin",
        "description": format!("Admin identity for {}", org)
    });

    let metadata_file = wallet_dir.join("metadata.json");
    std::fs::write(&metadata_file, serde_json::to_string_pretty(&metadata)?)?;

    Ok(())
}

fn extract_public_key_from_cert(cert_pem: &str) -> Result<String> {
    use std::io::Write;
    use std::process::Command;

    // The chaincode extracts the public key using:
    // ctx.getClientIdentity().getX509Certificate().getPublicKey().getEncoded()
    // This returns the SubjectPublicKeyInfo (SPKI) format in DER encoding, then Base64 encodes it.
    //
    // We need to extract the same format: the full encoded public key (not just the EC point)
    // Use openssl to get the public key in DER format, then base64 encode it

    let mut child = Command::new("openssl")
        .args(&["x509", "-pubkey", "-noout"])
        .stdin(std::process::Stdio::piped())
        .stdout(std::process::Stdio::piped())
        .stderr(std::process::Stdio::piped())
        .spawn()
        .context("Failed to run openssl x509 command")?;

    // Write cert to stdin
    if let Some(mut stdin) = child.stdin.take() {
        stdin.write_all(cert_pem.as_bytes())?;
    }

    let output = child.wait_with_output()?;

    if !output.status.success() {
        let stderr = String::from_utf8_lossy(&output.stderr);
        bail!("openssl x509 failed: {}", stderr);
    }

    let pubkey_pem = String::from_utf8_lossy(&output.stdout).to_string();

    // Now convert PEM to DER format (binary)
    let mut child2 = Command::new("openssl")
        .args(&["pkey", "-pubin", "-outform", "DER"])
        .stdin(std::process::Stdio::piped())
        .stdout(std::process::Stdio::piped())
        .stderr(std::process::Stdio::piped())
        .spawn()
        .context("Failed to run openssl pkey command")?;

    if let Some(mut stdin) = child2.stdin.take() {
        stdin.write_all(pubkey_pem.as_bytes())?;
    }

    let output2 = child2.wait_with_output()?;

    if !output2.status.success() {
        let stderr = String::from_utf8_lossy(&output2.stderr);
        bail!("openssl pkey failed: {}", stderr);
    }

    // Now we have the DER-encoded public key (SubjectPublicKeyInfo format)
    // Base64 encode it to match what the chaincode does
    use base64::{Engine as _, engine::general_purpose};
    let b64 = general_purpose::STANDARD.encode(&output2.stdout);

    Ok(b64)
}
