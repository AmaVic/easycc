//! Workspace initialization command.
//!
//! Creates a new EasyCC workspace with network configuration and optional chaincode.

use crate::config::{NetworkConfig, NetworkState};
use crate::workspace;
use anyhow::{Context, Result, bail};
use colored::Colorize;
use dialoguer::{Input, theme::ColorfulTheme};
use std::fs;
use std::path::{Path, PathBuf};
use walkdir::WalkDir;

/// Validates that a directory contains a valid JVM chaincode.
///
/// Checks for the presence of build.gradle and src directory.
fn validate_jvm_chaincode(path: &Path) -> Result<()> {
    // Check for build.gradle or build.gradle.kts
    let has_gradle = path.join("build.gradle").exists() || path.join("build.gradle.kts").exists();

    if !has_gradle {
        bail!(
            "Chaincode directory does not contain a Gradle build file (build.gradle or build.gradle.kts).\nOnly JVM-based chaincodes are supported."
        );
    }

    // Check for src directory (typical Java/Kotlin structure)
    if !path.join("src").exists() {
        bail!(
            "Chaincode directory does not contain a 'src' directory.\nExpected standard Gradle project structure."
        );
    }

    Ok(())
}

/// Executes the init command.
///
/// Creates a new workspace with network configuration, state file, and optionally
/// copies provided chaincode.
///
/// # Arguments
///
/// * `name` - Network name (prompts if not provided)
/// * `orgs` - Comma-separated organization names (prompts if not provided)
/// * `path` - Workspace path (defaults to current directory)
/// * `chaincode` - Path to existing chaincode to copy
pub async fn execute(
    name: Option<String>,
    orgs: Option<String>,
    path: Option<String>,
    chaincode: Option<String>,
) -> Result<()> {
    println!(
        "{}",
        "🚀 Initializing Hyperledger Fabric workspace..."
            .bright_blue()
            .bold()
    );
    println!();

    // Determine workspace path
    let workspace_path = if let Some(p) = path {
        PathBuf::from(&p)
    } else {
        workspace::get_workspace_root_or_current()?
    };

    // Check if already initialized
    if workspace::is_workspace(&workspace_path) {
        anyhow::bail!("Directory is already an easycc workspace");
    }

    // Get network name (from arg or prompt)
    let network_name = if let Some(n) = name {
        n
    } else {
        Input::with_theme(&ColorfulTheme::default())
            .with_prompt("Network name")
            .interact_text()?
    };

    // Get organizations (from arg or prompt)
    let organizations = if let Some(orgs_str) = orgs {
        orgs_str
            .split(',')
            .map(|s| s.trim().to_string())
            .filter(|s| !s.is_empty())
            .collect::<Vec<String>>()
    } else {
        let num_orgs: usize = Input::with_theme(&ColorfulTheme::default())
            .with_prompt("Number of organizations (1-10)")
            .validate_with(|input: &usize| {
                if *input >= 1 && *input <= 10 {
                    Ok(())
                } else {
                    Err("Please enter a number between 1 and 10")
                }
            })
            .interact()?;

        let orgs_input: String = Input::with_theme(&ColorfulTheme::default())
            .with_prompt(format!(
                "Organization names (comma-separated, {} orgs)",
                num_orgs
            ))
            .interact_text()?;

        let orgs: Vec<String> = orgs_input
            .split(',')
            .map(|s| s.trim().to_string())
            .filter(|s| !s.is_empty())
            .collect();

        if orgs.len() != num_orgs {
            anyhow::bail!("Expected {} organizations, got {}", num_orgs, orgs.len());
        }

        orgs
    };

    // Create config
    let config = NetworkConfig::new(network_name.clone(), organizations);
    config.validate()?;

    // Validate chaincode if provided
    let chaincode_provided = if let Some(ref cc_path) = chaincode {
        let source = Path::new(cc_path);
        validate_jvm_chaincode(source).context("Chaincode validation failed")?;
        true
    } else {
        false
    };

    // Create workspace structure
    create_workspace_structure(
        &workspace_path,
        &config,
        chaincode.as_deref(),
        chaincode_provided,
    )?;

    println!();
    println!(
        "{} Workspace initialized successfully!",
        "✓".bright_green().bold()
    );
    println!();
    println!(
        "Configuration saved to: {}",
        workspace::get_config_path(&workspace_path).display()
    );
    println!();
    println!("{}", "Next steps:".bright_yellow().bold());
    if chaincode_provided {
        println!("  1. Run 'easycc create' to generate network artifacts");
        println!("  2. Run 'easycc start' to launch the network");
    } else {
        println!("  1. Place your JVM chaincode in ./chaincode directory");
        println!("  2. Run 'easycc create' to generate network artifacts");
        println!("  3. Run 'easycc start' to launch the network");
    }

    Ok(())
}

fn create_workspace_structure(
    workspace_path: &PathBuf,
    config: &NetworkConfig,
    chaincode_source: Option<&str>,
    _chaincode_provided: bool,
) -> Result<()> {
    // Create easycc directory
    let easycc_dir = workspace::get_easycc_dir(workspace_path);
    fs::create_dir_all(&easycc_dir).context("Failed to create easycc directory")?;

    // Save config
    let config_path = workspace::get_config_path(workspace_path);
    config.save(&config_path)?;

    // Save initial state
    let state = NetworkState::new();
    let state_path = workspace::get_state_path(workspace_path);
    state.save(&state_path)?;

    // Create chaincode directory
    let chaincode_dir = workspace::get_chaincode_dir(workspace_path);

    if let Some(source_path) = chaincode_source {
        // Copy chaincode from source path
        let source = Path::new(source_path);
        if !source.exists() {
            anyhow::bail!("Chaincode directory '{}' does not exist", source_path);
        }
        if !source.is_dir() {
            anyhow::bail!("Chaincode path '{}' is not a directory", source_path);
        }

        copy_directory(source, &chaincode_dir)?;
        println!("{} Copied chaincode from {}", "✓".green(), source_path);
    } else {
        // Create empty chaincode directory with README
        if !chaincode_dir.exists() {
            fs::create_dir_all(&chaincode_dir).context("Failed to create chaincode directory")?;

            // Create a README in chaincode directory
            let readme_path = chaincode_dir.join("README.md");
            fs::write(
                &readme_path,
                "# Chaincode\n\nPlace your Hyperledger Fabric chaincode here.\n",
            )
            .context("Failed to create chaincode README")?;
        }
        println!("{} Created chaincode directory", "✓".green());
    }

    println!("{} Created easycc/config.json", "✓".green());
    println!("{} Created easycc/state.json", "✓".green());

    Ok(())
}

fn copy_directory(src: &Path, dst: &Path) -> Result<()> {
    fs::create_dir_all(dst).context("Failed to create destination directory")?;

    for entry in WalkDir::new(src).min_depth(1) {
        let entry = entry.context("Failed to read directory entry")?;
        let path = entry.path();
        let relative_path = path
            .strip_prefix(src)
            .context("Failed to get relative path")?;
        let target_path = dst.join(relative_path);

        if entry.file_type().is_dir() {
            fs::create_dir_all(&target_path).context("Failed to create directory")?;
        } else {
            if let Some(parent) = target_path.parent() {
                fs::create_dir_all(parent).context("Failed to create parent directory")?;
            }
            fs::copy(path, &target_path).context("Failed to copy file")?;
        }
    }

    Ok(())
}
