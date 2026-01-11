//! Network artifacts generation command.
//!
//! Generates crypto materials, genesis block, channel transaction, and Docker configuration.

use crate::{
    config::{NetworkConfig, NetworkState},
    crypto, network, utils, workspace,
};
use anyhow::{Context, Result};
use colored::Colorize;
use std::fs;

/// Executes the create command.
///
/// Generates all required artifacts for the Fabric network:
/// - Crypto materials (certificates and keys)
/// - Genesis block for the orderer
/// - Channel transaction
/// - Docker Compose configuration
pub async fn execute() -> Result<()> {
    println!(
        "{}",
        "🔧 Creating network artifacts...".bright_blue().bold()
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

    println!("Network: {}", config.network_name);
    println!("Organizations: {}", config.organizations.join(", "));
    println!();

    // Create directories
    let crypto_config_dir = workspace::get_crypto_config_dir(&workspace_root);
    let channel_artifacts_dir = workspace::get_channel_artifacts_dir(&workspace_root);
    let docker_dir = workspace::get_docker_dir(&workspace_root);

    fs::create_dir_all(&crypto_config_dir).context("Failed to create crypto-config directory")?;
    fs::create_dir_all(&channel_artifacts_dir)
        .context("Failed to create channel-artifacts directory")?;
    fs::create_dir_all(&docker_dir).context("Failed to create docker directory")?;

    // Generate crypto-config.yaml
    println!("📝 Generating crypto-config.yaml...");
    let crypto_config_yaml = workspace_root.join("crypto-config.yaml");
    crypto::generate_crypto_config(&config.organizations, &crypto_config_yaml)?;
    println!("{} crypto-config.yaml created", "✓".green());

    // Generate cryptographic material
    println!("🔐 Generating certificates and keys...");
    utils::run_cryptogen(&crypto_config_yaml, &crypto_config_dir)?;
    println!("{} Cryptographic material generated", "✓".green());
    state.crypto_generated = true;
    state.save(&state_path)?;

    // Generate configtx.yaml
    println!("📝 Generating configtx.yaml...");
    let configtx_yaml = workspace_root.join("configtx.yaml");
    network::generate_configtx(&config.organizations, &configtx_yaml)?;
    println!("{} configtx.yaml created", "✓".green());

    // Generate genesis block
    println!("🧱 Generating genesis block...");
    let genesis_block = channel_artifacts_dir.join("genesis.block");
    utils::run_configtxgen(
        &configtx_yaml,
        "TwoOrgsApplicationGenesis",
        "mychannel",
        &genesis_block,
        "genesis",
    )?;
    println!("{} Genesis block created", "✓".green());

    println!();
    println!(
        "{} Network artifacts created successfully!",
        "✓".bright_green().bold()
    );
    println!();
    println!("{}", "Next steps:".bright_yellow().bold());
    println!("  Run 'easycc start' to launch the network");

    Ok(())
}
