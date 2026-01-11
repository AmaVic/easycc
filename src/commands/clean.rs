//! Cleanup command.
//!
//! Removes Docker containers, volumes, and optionally all generated artifacts.

use crate::{config::NetworkState, workspace};
use anyhow::{Context, Result};
use colored::Colorize;
use std::fs;
use std::process::Command;

/// Executes the clean command.
///
/// Removes Docker containers and volumes. If `all` is true, also removes
/// crypto materials, channel artifacts, and Docker configurations.
///
/// # Arguments
///
/// * `all` - If true, removes all generated artifacts
pub async fn execute(all: bool) -> Result<()> {
    println!(
        "{}",
        "🧹 Cleaning up Hyperledger Fabric network..."
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

    if docker_compose_path.exists() {
        // Stop and remove containers and volumes
        println!("🐳 Removing Docker containers and volumes...");
        let status = Command::new("docker-compose")
            .args(["-f", docker_compose_path.to_str().unwrap(), "down", "-v"])
            .current_dir(&workspace_root)
            .status()
            .context("Failed to run docker-compose down")?;

        if status.success() {
            println!("{} Containers and volumes removed", "✓".green());
        } else {
            println!("{} Failed to remove some containers/volumes", "⚠️".yellow());
        }
    } else {
        println!(
            "{} No Docker Compose file found, skipping container cleanup",
            "ℹ️".blue()
        );
    }

    // Remove generated artifacts if --all is specified
    if all {
        println!();
        println!("🗑️  Removing generated artifacts...");

        let dirs_to_remove = vec![
            ("crypto-config", workspace_root.join("crypto-config")),
            (
                "channel-artifacts",
                workspace_root.join("channel-artifacts"),
            ),
            ("docker", workspace::get_docker_dir(&workspace_root)),
            ("exports", workspace_root.join("exports")),
        ];

        let files_to_remove = vec![
            ("configtx.yaml", workspace_root.join("configtx.yaml")),
            (
                "crypto-config.yaml",
                workspace_root.join("crypto-config.yaml"),
            ),
        ];

        for (name, dir) in dirs_to_remove {
            if dir.exists() {
                fs::remove_dir_all(&dir).with_context(|| format!("Failed to remove {}", name))?;
                println!("  {} Removed {}", "✓".green(), name);
            }
        }

        for (name, file) in files_to_remove {
            if file.exists() {
                fs::remove_file(&file).with_context(|| format!("Failed to remove {}", name))?;
                println!("  {} Removed {}", "✓".green(), name);
            }
        }

        // Reset state to created
        let state_path = workspace::get_state_path(&workspace_root);
        let mut state = NetworkState::load(&state_path)?;
        state.network_status = crate::config::NetworkStatus::Created;
        state.crypto_generated = false;
        state.save(&state_path)?;

        println!();
        println!(
            "{}",
            "✅ Network cleaned successfully!".bright_green().bold()
        );
        println!();
        println!(
            "To recreate the network, run: {}",
            "easycc create".bright_cyan()
        );
    } else {
        // Update state to stopped
        let state_path = workspace::get_state_path(&workspace_root);
        if state_path.exists() {
            let mut state = NetworkState::load(&state_path)?;
            state.network_status = crate::config::NetworkStatus::Stopped;
            state.save(&state_path)?;
        }

        println!();
        println!(
            "{}",
            "✅ Containers and volumes cleaned!".bright_green().bold()
        );
        println!();
        println!("Network artifacts (crypto, configs) are preserved.");
        println!(
            "To remove everything, run: {}",
            "easycc clean --all".bright_cyan()
        );
        println!(
            "To start the network again, run: {}",
            "easycc start".bright_cyan()
        );
    }

    Ok(())
}
