//! Cryptographic material generation.
//!
//! This module handles the generation of crypto-config.yaml files used by
//! Hyperledger Fabric's cryptogen tool to create certificates and keys.

use anyhow::{Context, Result};
use handlebars::Handlebars;
use serde_json::json;
use std::fs;
use std::path::Path;

const CRYPTO_CONFIG_TEMPLATE: &str = r#"
OrdererOrgs:
  - Name: Orderer
    Domain: example.com
    EnableNodeOUs: true
    Specs:
      - Hostname: orderer

PeerOrgs:
{{#each organizations}}
  - Name: {{this.name}}
    Domain: {{this.domain}}
    EnableNodeOUs: true
    Template:
      Count: 1
    Users:
      Count: 1
{{/each}}
"#;

/// Generates a crypto-config.yaml file for the given organizations.
///
/// The generated file defines the orderer organization and peer organizations,
/// which cryptogen uses to create the necessary cryptographic materials.
///
/// # Arguments
///
/// * `orgs` - List of organization names
/// * `output_path` - Where to write the crypto-config.yaml file
///
/// # Errors
///
/// Returns an error if template rendering or file writing fails.
pub fn generate_crypto_config(orgs: &[String], output_path: &Path) -> Result<()> {
    let mut handlebars = Handlebars::new();
    handlebars
        .register_template_string("crypto-config", CRYPTO_CONFIG_TEMPLATE)
        .context("Failed to register crypto-config template")?;

    let org_data: Vec<_> = orgs
        .iter()
        .enumerate()
        .map(|(i, name)| {
            json!({
                "name": format!("Org{}", i + 1),
                "domain": format!("{}.example.com", name.to_lowercase())
            })
        })
        .collect();

    let data = json!({
        "organizations": org_data
    });

    let rendered = handlebars
        .render("crypto-config", &data)
        .context("Failed to render crypto-config template")?;

    fs::write(output_path, rendered).context("Failed to write crypto-config.yaml")?;

    Ok(())
}
