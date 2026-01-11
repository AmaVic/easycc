//! Utility functions for Hyperledger Fabric binary management.
//!
//! This module handles downloading and managing Fabric binaries (cryptogen,
//! configtxgen, peer, etc.) and provides wrappers for executing them.

use anyhow::{Context, Result, bail};
use std::fs;
use std::path::{Path, PathBuf};
use std::process::Command;

const FABRIC_VERSION: &str = "2.5.0";

/// Returns the directory where Fabric binaries are stored.
///
/// Binaries are stored in `~/.easycc/fabric-bins/`.
pub fn get_fabric_bin_dir() -> PathBuf {
    dirs::home_dir()
        .unwrap_or_else(|| PathBuf::from("."))
        .join(".easycc")
        .join("fabric-bins")
}

/// Ensures Fabric binaries are available, downloading them if necessary.
///
/// Checks for required binaries (cryptogen, configtxgen, peer) and downloads
/// the complete Fabric toolset if any are missing.
///
/// # Errors
///
/// Returns an error if the download or extraction fails.
pub fn ensure_fabric_binaries() -> Result<PathBuf> {
    let bin_dir = get_fabric_bin_dir();

    // Check if binaries already exist
    if bin_dir.join("cryptogen").exists()
        && bin_dir.join("configtxgen").exists()
        && bin_dir.join("peer").exists()
    {
        return Ok(bin_dir);
    }

    println!("📥 Fabric binaries not found. Downloading...");
    download_fabric_binaries(&bin_dir)?;

    Ok(bin_dir)
}

fn download_fabric_binaries(bin_dir: &Path) -> Result<()> {
    fs::create_dir_all(bin_dir).context("Failed to create fabric binaries directory")?;

    // Determine OS and architecture
    let (os, arch) = get_platform_info()?;

    let url = format!(
        "https://github.com/hyperledger/fabric/releases/download/v{}/hyperledger-fabric-{}-{}-{}.tar.gz",
        FABRIC_VERSION, os, arch, FABRIC_VERSION
    );

    println!("Downloading from: {}", url);
    println!("This may take a few minutes...");

    // Download using curl
    let tar_path = bin_dir.join("fabric.tar.gz");
    let status = Command::new("curl")
        .args(["-L", &url, "-o", tar_path.to_str().unwrap()])
        .status()
        .context("Failed to execute curl")?;

    if !status.success() {
        bail!("Failed to download Fabric binaries");
    }

    // Extract the tar.gz (it contains a 'bin' directory)
    let status = Command::new("tar")
        .args([
            "-xzf",
            tar_path.to_str().unwrap(),
            "-C",
            bin_dir.to_str().unwrap(),
        ])
        .status()
        .context("Failed to extract tar.gz")?;

    if !status.success() {
        bail!("Failed to extract Fabric binaries");
    }

    // Move binaries from extracted 'bin' directory to our bin_dir
    let extracted_bin = bin_dir.join("bin");
    if extracted_bin.exists() {
        for entry in fs::read_dir(&extracted_bin)? {
            let entry = entry?;
            let src = entry.path();
            let dest = bin_dir.join(entry.file_name());
            fs::rename(&src, &dest)?;
        }
        fs::remove_dir(&extracted_bin)?;
    }

    // Clean up tar file
    fs::remove_file(&tar_path)?;

    // Make binaries executable
    #[cfg(unix)]
    {
        use std::os::unix::fs::PermissionsExt;
        for binary in &["cryptogen", "configtxgen", "peer", "orderer", "osnadmin"] {
            let path = bin_dir.join(binary);
            if path.exists() {
                let mut perms = fs::metadata(&path)?.permissions();
                perms.set_mode(0o755);
                fs::set_permissions(&path, perms)?;
            }
        }
    }

    println!("✓ Fabric binaries downloaded successfully");

    Ok(())
}

fn get_platform_info() -> Result<(&'static str, &'static str)> {
    let os = if cfg!(target_os = "macos") {
        "darwin"
    } else if cfg!(target_os = "linux") {
        "linux"
    } else if cfg!(target_os = "windows") {
        bail!("Windows is not yet supported. Please use WSL2 or Docker Desktop on Windows.");
    } else {
        bail!("Unsupported operating system");
    };

    let arch = if cfg!(target_arch = "x86_64") {
        "amd64"
    } else if cfg!(target_arch = "aarch64") {
        "arm64"
    } else {
        bail!("Unsupported architecture");
    };

    Ok((os, arch))
}

/// Runs the cryptogen tool to generate crypto materials.
///
/// # Arguments
///
/// * `config_path` - Path to the crypto-config.yaml file
/// * `output_path` - Directory where crypto materials will be generated
///
/// # Errors
///
/// Returns an error if cryptogen fails to execute or returns a non-zero exit code.
pub fn run_cryptogen(config_path: &Path, output_path: &Path) -> Result<()> {
    let bin_dir = ensure_fabric_binaries()?;
    let cryptogen = bin_dir.join("cryptogen");

    let status = Command::new(&cryptogen)
        .args([
            "generate",
            "--config",
            config_path.to_str().unwrap(),
            "--output",
            output_path.to_str().unwrap(),
        ])
        .status()
        .context("Failed to run cryptogen")?;

    if !status.success() {
        bail!("cryptogen failed");
    }

    Ok(())
}

/// Runs the configtxgen tool to generate channel configuration artifacts.
///
/// # Arguments
///
/// * `config_path` - Path to the configtx.yaml file
/// * `profile` - Profile name from configtx.yaml to use
/// * `channel_name` - Name of the channel
/// * `output_path` - Where to write the generated artifact
/// * `output_type` - Either "genesis" for genesis block or "channel" for channel transaction
///
/// # Errors
///
/// Returns an error if configtxgen fails to execute or returns a non-zero exit code.
pub fn run_configtxgen(
    config_path: &Path,
    profile: &str,
    channel_name: &str,
    output_path: &Path,
    output_type: &str, // "genesis" or "channel"
) -> Result<()> {
    let bin_dir = ensure_fabric_binaries()?;
    let configtxgen = bin_dir.join("configtxgen");

    let mut args = vec![
        "-profile".to_string(),
        profile.to_string(),
        "-channelID".to_string(),
        channel_name.to_string(),
        "-configPath".to_string(),
        config_path.parent().unwrap().to_str().unwrap().to_string(),
    ];

    if output_type == "genesis" {
        args.extend(vec![
            "-outputBlock".to_string(),
            output_path.to_str().unwrap().to_string(),
        ]);
    } else {
        args.extend(vec![
            "-outputCreateChannelTx".to_string(),
            output_path.to_str().unwrap().to_string(),
        ]);
    }

    let status = Command::new(&configtxgen)
        .args(&args)
        .env("FABRIC_CFG_PATH", config_path.parent().unwrap())
        .status()
        .context("Failed to run configtxgen")?;

    if !status.success() {
        bail!("configtxgen failed");
    }

    Ok(())
}
