//! Network shutdown command.
//!
//! Stops all Docker containers and removes volumes.

use crate::{config::NetworkState, workspace};
use anyhow::{Context, Result, bail};
use colored::Colorize;
use std::process::Command;

/// Executes the stop command.
///
/// Stops all running containers and removes associated volumes using docker-compose.
pub async fn execute() -> Result<()> {
    println!(
        "{}",
        "🛑 Stopping Hyperledger Fabric network..."
            .bright_blue()
            .bold()
    );
    println!();

    // Find workspace root
    let workspace_root = workspace::find_workspace_root()?;
    println!("Workspace: {}", workspace_root.display());
    println!();

    // Check if docker-compose.yaml exists
    let docker_compose_path =
        workspace::get_docker_dir(&workspace_root).join("docker-compose.yaml");
    if !docker_compose_path.exists() {
        bail!("Docker Compose file not found. Network may not be started.");
    }

    // Stop containers and remove volumes using docker-compose
    println!("🐳 Stopping Docker containers and removing volumes...");
    let status = Command::new("docker-compose")
        .args(["-f", docker_compose_path.to_str().unwrap(), "down", "-v"])
        .current_dir(&workspace_root)
        .status()
        .context("Failed to run docker-compose down")?;

    if !status.success() {
        bail!("Failed to stop containers");
    }

    println!("{} Containers and volumes removed", "✓".green());
    println!();

    // Update state
    let state_path = workspace::get_state_path(&workspace_root);
    let mut state = NetworkState::load(&state_path)?;
    state.network_status = crate::config::NetworkStatus::Stopped;
    state.save(&state_path)?;

    println!(
        "{}",
        "✅ Network stopped successfully!".bright_green().bold()
    );
    println!();
    println!("Containers and volumes have been removed.");
    println!(
        "To start the network again, run: {}",
        "easycc start".bright_cyan()
    );
    println!(
        "To remove all artifacts, run: {}",
        "easycc clean --all".bright_cyan()
    );

    Ok(())
}
