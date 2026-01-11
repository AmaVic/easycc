//! EasyCC - Easy Chaincode CLI
//!
//! A command-line tool that simplifies Hyperledger Fabric network setup, management,
//! and chaincode deployment for local development and testing.
//!
//! # Quick Start
//!
//! ```bash
//! # Initialize a workspace
//! easycc init
//!
//! # Create network artifacts
//! easycc create
//!
//! # Start the network
//! easycc start
//!
//! # Deploy chaincode
//! easycc deploy --chaincode-path ./chaincode
//! ```

mod cli;
mod commands;
mod config;
mod crypto;
mod network;
mod utils;
mod workspace;

use anyhow::Result;
use clap::Parser;
use cli::{Cli, Commands};

#[tokio::main]
async fn main() -> Result<()> {
    let cli = Cli::parse();

    match cli.command {
        Commands::Init {
            name,
            orgs,
            path,
            chaincode,
        } => {
            commands::init::execute(name, orgs, path, chaincode).await?;
        }
        Commands::Create => {
            commands::create::execute().await?;
        }
        Commands::Start => {
            commands::start::execute().await?;
        }
        Commands::Stop => {
            commands::stop::execute().await?;
        }
        Commands::Deploy {
            path,
            name,
            version,
        } => {
            commands::deploy::execute(path, name, version).await?;
        }
        Commands::Export { output } => {
            commands::export::execute(output).await?;
        }
        Commands::Clean { all } => {
            commands::clean::execute(all).await?;
        }
        Commands::Test {
            tester_path,
            test_suite_path,
            org,
            chaincode_name,
            chaincode_version,
        } => {
            commands::test::execute(
                tester_path,
                test_suite_path,
                org,
                chaincode_name,
                chaincode_version,
            )
            .await?;
        }
    }

    Ok(())
}
