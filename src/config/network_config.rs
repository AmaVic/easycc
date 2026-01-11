//! Network configuration data structures and validation.

use anyhow::{Context, Result};
use serde::{Deserialize, Serialize};
use std::fs;
use std::path::Path;

/// Network configuration containing network name and organization list.
#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct NetworkConfig {
    pub network_name: String,
    pub organizations: Vec<String>,
}

impl NetworkConfig {
    /// Creates a new network configuration.
    pub fn new(network_name: String, organizations: Vec<String>) -> Self {
        Self {
            network_name,
            organizations,
        }
    }

    /// Loads and validates a network configuration from a JSON file.
    ///
    /// # Errors
    ///
    /// Returns an error if the file cannot be read, parsed, or fails validation.
    pub fn load(path: &Path) -> Result<Self> {
        let content = fs::read_to_string(path).context("Failed to read config file")?;
        let config: NetworkConfig =
            serde_json::from_str(&content).context("Failed to parse config file")?;
        config.validate()?;
        Ok(config)
    }

    /// Saves the network configuration to a JSON file.
    ///
    /// # Errors
    ///
    /// Returns an error if serialization or file writing fails.
    pub fn save(&self, path: &Path) -> Result<()> {
        let content = serde_json::to_string_pretty(self).context("Failed to serialize config")?;
        fs::write(path, content).context("Failed to write config file")?;
        Ok(())
    }

    /// Validates the network configuration.
    ///
    /// Ensures the network name is not empty, at least one organization exists,
    /// no more than 10 organizations, and all organization names are valid.
    ///
    /// # Errors
    ///
    /// Returns an error if any validation rule is violated.
    pub fn validate(&self) -> Result<()> {
        if self.network_name.is_empty() {
            anyhow::bail!("Network name cannot be empty");
        }

        if self.organizations.is_empty() {
            anyhow::bail!("At least one organization is required");
        }

        if self.organizations.len() > 10 {
            anyhow::bail!("Maximum 10 organizations supported");
        }

        for org in &self.organizations {
            if org.is_empty() {
                anyhow::bail!("Organization name cannot be empty");
            }
            if !org
                .chars()
                .all(|c| c.is_alphanumeric() || c == '-' || c == '_')
            {
                anyhow::bail!(
                    "Organization name '{}' contains invalid characters. Use only alphanumeric, '-', or '_'",
                    org
                );
            }
        }

        Ok(())
    }
}
