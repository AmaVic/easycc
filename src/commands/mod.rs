//! CLI command implementations.
//!
//! Each submodule implements a specific EasyCC command (init, create, start, etc.).
//! Commands follow a consistent pattern: an async `execute()` function that takes
//! command-specific arguments and returns a `Result<()>`.

pub mod clean;
pub mod create;
pub mod deploy;
pub mod export;
pub mod init;
pub mod start;
pub mod stop;
pub mod test;
