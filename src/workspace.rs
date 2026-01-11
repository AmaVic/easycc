//! Workspace directory structure and path management.
//!
//! This module handles workspace discovery and provides paths to various
//! workspace components (config files, crypto materials, Docker configs, etc.).

use anyhow::{Context, Result, bail};
use std::env;
use std::path::{Path, PathBuf};

const WORKSPACE_DIR: &str = "easycc";
const CONFIG_FILE: &str = "config.json";
const STATE_FILE: &str = "state.json";

/// Finds the workspace root by searching for the `easycc/` directory.
///
/// Searches the current directory and all parent directories until it finds
/// a directory containing an `easycc/` subdirectory.
///
/// # Errors
///
/// Returns an error if no workspace is found in the directory tree.
pub fn find_workspace_root() -> Result<PathBuf> {
    let current_dir = env::current_dir().context("Failed to get current directory")?;

    // Check current directory and all parents
    let mut dir = current_dir.as_path();
    loop {
        let workspace_dir = dir.join(WORKSPACE_DIR);
        if workspace_dir.exists() && workspace_dir.is_dir() {
            return Ok(dir.to_path_buf());
        }

        match dir.parent() {
            Some(parent) => dir = parent,
            None => bail!("Not an easycc workspace. Run 'easycc init' first."),
        }
    }
}

/// Returns the current directory.
///
/// Used during workspace initialization when no workspace exists yet.
pub fn get_workspace_root_or_current() -> Result<PathBuf> {
    env::current_dir().context("Failed to get current directory")
}

/// Checks if the given path is an EasyCC workspace.
///
/// Returns `true` if the path contains an `easycc/` subdirectory.
pub fn is_workspace(path: &Path) -> bool {
    path.join(WORKSPACE_DIR).exists()
}

/// Returns the path to the `easycc/` configuration directory.
pub fn get_easycc_dir(workspace_root: &Path) -> PathBuf {
    workspace_root.join(WORKSPACE_DIR)
}

/// Returns the path to the network configuration file.
pub fn get_config_path(workspace_root: &Path) -> PathBuf {
    get_easycc_dir(workspace_root).join(CONFIG_FILE)
}

/// Returns the path to the network state file.
pub fn get_state_path(workspace_root: &Path) -> PathBuf {
    get_easycc_dir(workspace_root).join(STATE_FILE)
}

/// Returns the path to the crypto materials directory.
pub fn get_crypto_config_dir(workspace_root: &Path) -> PathBuf {
    workspace_root.join("crypto-config")
}

/// Returns the path to the channel artifacts directory.
pub fn get_channel_artifacts_dir(workspace_root: &Path) -> PathBuf {
    workspace_root.join("channel-artifacts")
}

/// Returns the path to the Docker configuration directory.
pub fn get_docker_dir(workspace_root: &Path) -> PathBuf {
    workspace_root.join("docker")
}

/// Returns the path to the chaincode directory.
pub fn get_chaincode_dir(workspace_root: &Path) -> PathBuf {
    workspace_root.join("chaincode")
}
