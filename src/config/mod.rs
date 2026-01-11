//! Configuration management for network and state.
//!
//! This module provides data structures and operations for managing network
//! configuration and runtime state.

pub mod network_config;
pub mod network_state;

pub use network_config::NetworkConfig;
pub use network_state::{NetworkState, NetworkStatus};
