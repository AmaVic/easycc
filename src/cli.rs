//! CLI argument parsing and command definitions.
//!
//! This module defines the command-line interface using `clap`, including all
//! available commands and their arguments.

use clap::{Parser, Subcommand};

/// Main CLI structure for the EasyCC tool.
#[derive(Parser)]
#[command(name = "easycc")]
#[command(version = "0.1.0")]
/// Available CLI commands.
#[command(about = "Easy Hyperledger Fabric network setup for chaincode testing", long_about = None)]
pub struct Cli {
    #[command(subcommand)]
    pub command: Commands,
}

#[derive(Subcommand)]
pub enum Commands {
    /// Initialize a new Fabric workspace
    Init {
        /// Network name
        #[arg(short, long)]
        name: Option<String>,

        /// Comma-separated list of organization names
        #[arg(short, long)]
        orgs: Option<String>,

        /// Path to initialize workspace (defaults to current directory)
        path: Option<String>,

        /// Path to existing chaincode directory to copy into workspace
        #[arg(short, long)]
        chaincode: Option<String>,
    },

    /// Generate network artifacts (crypto material, genesis block, docker configs)
    Create,

    /// Start the Fabric network
    Start,

    /// Stop the Fabric network
    Stop,

    /// Deploy chaincode to the network
    Deploy {
        /// Path to chaincode directory
        #[arg(short, long)]
        path: Option<String>,

        /// Chaincode name
        #[arg(short, long)]
        name: Option<String>,

        /// Chaincode version
        #[arg(short, long)]
        version: Option<String>,
    },

    /// Export connection profiles and wallets
    Export {
        /// Output directory for exports
        #[arg(short, long)]
        output: Option<String>,
    },

    /// Clean up network resources
    Clean {
        /// Remove all generated artifacts including crypto material
        #[arg(long)]
        all: bool,
    },

    /// Run full test workflow (clean, create, start, export, deploy, test)
    Test {
        /// Path to ChaincoderTestr binary
        #[arg(short, long)]
        tester_path: String,

        /// Path to test suite JSON file
        #[arg(short = 's', long)]
        test_suite_path: String,

        /// Organization to use for testing
        #[arg(short, long)]
        org: String,

        /// Chaincode name (defaults to 'mychaincode')
        #[arg(short = 'n', long)]
        chaincode_name: Option<String>,

        /// Chaincode version (defaults to '1.0')
        #[arg(short = 'v', long)]
        chaincode_version: Option<String>,
    },
}
