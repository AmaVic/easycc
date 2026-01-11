//! Network configuration and Docker Compose generation.
//!
//! This module handles the generation of configtx.yaml and docker-compose.yaml
//! files needed to configure and run the Hyperledger Fabric network.

use anyhow::{Context, Result};
use handlebars::Handlebars;
use serde_json::json;
use std::fs;
use std::path::Path;

const DOCKER_COMPOSE_TEMPLATE: &str = include_str!("../../templates/docker-compose.hbs");

const CONFIGTX_TEMPLATE: &str = r#"
Organizations:
  - &OrdererOrg
      Name: OrdererOrg
      ID: OrdererMSP
      MSPDir: crypto-config/ordererOrganizations/example.com/msp
      Policies:
          Readers:
              Type: Signature
              Rule: "OR('OrdererMSP.member')"
          Writers:
              Type: Signature
              Rule: "OR('OrdererMSP.member')"
          Admins:
              Type: Signature
              Rule: "OR('OrdererMSP.admin')"
      OrdererEndpoints:
          - orderer.example.com:7050

{{#each organizations}}
  - &{{this.name}}
      Name: {{this.name}}MSP
      ID: {{this.name}}MSP
      MSPDir: crypto-config/peerOrganizations/{{this.domain}}/msp
      Policies:
          Readers:
              Type: Signature
              Rule: "OR('{{this.name}}MSP.admin', '{{this.name}}MSP.peer', '{{this.name}}MSP.client')"
          Writers:
              Type: Signature
              Rule: "OR('{{this.name}}MSP.admin', '{{this.name}}MSP.client')"
          Admins:
              Type: Signature
              Rule: "OR('{{this.name}}MSP.admin')"
          Endorsement:
              Type: Signature
              Rule: "OR('{{this.name}}MSP.peer')"
      AnchorPeers:
          - Host: peer0.{{this.domain}}
            Port: {{this.port}}
{{/each}}

Capabilities:
    Channel: &ChannelCapabilities
        V2_0: true
    Orderer: &OrdererCapabilities
        V2_0: true
    Application: &ApplicationCapabilities
        V2_5: true

Application: &ApplicationDefaults
    Organizations:
    Policies:
        Readers:
            Type: ImplicitMeta
            Rule: "ANY Readers"
        Writers:
            Type: ImplicitMeta
            Rule: "ANY Writers"
        Admins:
            Type: ImplicitMeta
            Rule: "MAJORITY Admins"
        LifecycleEndorsement:
            Type: ImplicitMeta
            Rule: "MAJORITY Endorsement"
        Endorsement:
            Type: ImplicitMeta
            Rule: "MAJORITY Endorsement"
    Capabilities:
        <<: *ApplicationCapabilities

Orderer: &OrdererDefaults
    OrdererType: etcdraft
    Addresses:
        - orderer.example.com:7050
    EtcdRaft:
        Consenters:
        - Host: orderer.example.com
          Port: 7050
          ClientTLSCert: crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/server.crt
          ServerTLSCert: crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/server.crt
    BatchTimeout: 2s
    BatchSize:
        MaxMessageCount: 10
        AbsoluteMaxBytes: 99 MB
        PreferredMaxBytes: 512 KB
    Organizations:
    Policies:
        Readers:
            Type: ImplicitMeta
            Rule: "ANY Readers"
        Writers:
            Type: ImplicitMeta
            Rule: "ANY Writers"
        Admins:
            Type: ImplicitMeta
            Rule: "MAJORITY Admins"
        BlockValidation:
            Type: ImplicitMeta
            Rule: "ANY Writers"

Channel: &ChannelDefaults
    Policies:
        Readers:
            Type: ImplicitMeta
            Rule: "ANY Readers"
        Writers:
            Type: ImplicitMeta
            Rule: "ANY Writers"
        Admins:
            Type: ImplicitMeta
            Rule: "MAJORITY Admins"
    Capabilities:
        <<: *ChannelCapabilities

Profiles:
    TwoOrgsApplicationGenesis:
        <<: *ChannelDefaults
        Orderer:
            <<: *OrdererDefaults
            Organizations:
                - *OrdererOrg
            Capabilities: *OrdererCapabilities
        Application:
            <<: *ApplicationDefaults
            Organizations:
{{#each organizations}}
                - *{{this.name}}
{{/each}}
            Capabilities: *ApplicationCapabilities
"#;

/// Generates a configtx.yaml file for the given organizations.
///
/// The configtx.yaml defines the channel configuration including organizations,
/// policies, and capabilities.
///
/// # Arguments
///
/// * `orgs` - List of organization names
/// * `output_path` - Where to write the configtx.yaml file
///
/// # Errors
///
/// Returns an error if template rendering or file writing fails.
pub fn generate_configtx(orgs: &[String], output_path: &Path) -> Result<()> {
    let mut handlebars = Handlebars::new();
    handlebars
        .register_template_string("configtx", CONFIGTX_TEMPLATE)
        .context("Failed to register configtx template")?;

    let org_data: Vec<_> = orgs
        .iter()
        .enumerate()
        .map(|(i, name)| {
            json!({
                "name": format!("Org{}", i + 1),
                "domain": format!("{}.example.com", name.to_lowercase()),
                "port": 7051 + (i * 1000)
            })
        })
        .collect();

    let data = json!({
        "organizations": org_data
    });

    let rendered = handlebars
        .render("configtx", &data)
        .context("Failed to render configtx template")?;

    fs::write(output_path, rendered).context("Failed to write configtx.yaml")?;

    Ok(())
}

/// Generates a docker-compose.yaml file for the network.
///
/// Creates Docker Compose configuration for orderer, peers, and CouchDB instances.
///
/// # Arguments
///
/// * `network_name` - Name of the network (used for container names)
/// * `orgs` - List of organization names
/// * `output_path` - Where to write the docker-compose.yaml file
///
/// # Errors
///
/// Returns an error if template rendering or file writing fails.
pub fn generate_docker_compose(
    network_name: &str,
    orgs: &[String],
    output_path: &Path,
) -> Result<()> {
    let mut handlebars = Handlebars::new();
    handlebars
        .register_template_string("docker-compose", DOCKER_COMPOSE_TEMPLATE)
        .context("Failed to register docker-compose template")?;

    let org_data: Vec<_> = orgs
        .iter()
        .enumerate()
        .map(|(i, name)| {
            let base_port = 7051 + (i * 1000);
            json!({
                "index": i,
                "name": format!("Org{}", i + 1),
                "domain": format!("{}.example.com", name.to_lowercase()),
                "port": base_port,
                "cc_port": base_port + 2,  // chaincode port
                "ops_port": base_port + 3,  // operations/metrics port
                "couch_port": 5984 + i,     // CouchDB port
            })
        })
        .collect();

    let data = json!({
        "network_name": network_name,
        "organizations": org_data
    });

    let rendered = handlebars
        .render("docker-compose", &data)
        .context("Failed to render docker-compose template")?;

    fs::write(output_path, rendered).context("Failed to write docker-compose.yaml")?;

    Ok(())
}
