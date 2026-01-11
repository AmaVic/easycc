//! Network runtime state tracking.

use anyhow::{Context, Result};
use chrono::{DateTime, Utc};
use serde::{Deserialize, Serialize};
use std::fs;
use std::path::Path;

/// Runtime state of the network, tracking lifecycle phases.
#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct NetworkState {
    pub initialized_at: DateTime<Utc>,
    pub network_status: NetworkStatus,
    pub crypto_generated: bool,
    pub channel_created: bool,
    pub chaincode_deployed: Option<ChaincodeInfo>,
}

/// Current status of the network.
#[derive(Debug, Clone, Serialize, Deserialize, PartialEq)]
#[serde(rename_all = "lowercase")]
pub enum NetworkStatus {
    /// Network artifacts created but not started
    Created,
    /// Network is running
    Running,
    /// Network has been stopped
    Stopped,
}

/// Information about deployed chaincode.
#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct ChaincodeInfo {
    /// Chaincode name
    pub name: String,
    /// Chaincode version
    pub version: String,
    /// Chaincode sequence number
    pub sequence: u32,
    /// When the chaincode was deployed
    pub deployed_at: DateTime<Utc>,
}

impl NetworkState {
    /// Creates a new network state with default values.
    pub fn new() -> Self {
        Self {
            initialized_at: Utc::now(),
            network_status: NetworkStatus::Created,
            crypto_generated: false,
            channel_created: false,
            chaincode_deployed: None,
        }
    }

    /// Loads network state from a JSON file.
    pub fn load(path: &Path) -> Result<Self> {
        let content = fs::read_to_string(path).context("Failed to read state file")?;
        serde_json::from_str(&content).context("Failed to parse state file")
    }

    /// Saves network state to a JSON file.
    pub fn save(&self, path: &Path) -> Result<()> {
        let content = serde_json::to_string_pretty(self).context("Failed to serialize state")?;
        fs::write(path, content).context("Failed to write state file")?;
        Ok(())
    }
}

impl Default for NetworkState {
    fn default() -> Self {
        Self::new()
    }
}
