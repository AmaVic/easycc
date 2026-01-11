//! ChaincoderTestr integration command.
//!
//! Runs automated chaincode tests using the ChaincoderTestr tool.

use crate::{config::NetworkConfig, workspace};
use anyhow::{Context, Result, bail};
use chrono::Local;
use colored::Colorize;
use std::fs::{self, File};
use std::io::{BufRead, BufReader, Write};
use std::path::Path;
use std::process::{Command, Stdio};
use std::sync::{Arc, Mutex};

// Macro to log both to console and file
macro_rules! log_both {
    ($log_file:expr, $($arg:tt)*) => {{
        let msg = format!($($arg)*);
        println!("{}", msg);
        if let Ok(mut file) = $log_file.lock() {
            let stripped = String::from_utf8_lossy(&strip_ansi_escapes::strip(&msg)).to_string();
            let _ = writeln!(file, "{}", stripped);
            let _ = file.flush();
        }
    }};
}

/// Executes the test command.
///
/// Runs chaincode tests using the ChaincoderTestr tool.
///
/// This command assumes the network is already set up with:
/// - Crypto materials generated
/// - Network running
/// - Wallets and connection profiles exported
/// - Chaincode deployed
///
/// The test suite file should contain public keys that were generated
/// during the initial network setup.
///
/// # Arguments
///
/// * `tester_path` - Path to ChaincoderTestr binary
/// * `test_suite_path` - Path to test suite JSON file
/// * `org` - Organization to use for testing
/// * `chaincode_name` - Optional chaincode name (defaults to "mychaincode")
/// * `chaincode_version` - Optional chaincode version (defaults to "1.0")
pub async fn execute(
    tester_path: String,
    test_suite_path: String,
    org: String,
    chaincode_name: Option<String>,
    chaincode_version: Option<String>,
) -> Result<()> {
    // Find workspace root
    let workspace_root = workspace::find_workspace_root()?;

    // Create logs directory and log file early
    let logs_dir = workspace_root.join("logs");
    fs::create_dir_all(&logs_dir)?;

    let test_suite_name = Path::new(&test_suite_path)
        .file_stem()
        .and_then(|s| s.to_str())
        .unwrap_or("test-suite");
    let timestamp = Local::now().format("%Y%m%d-%H%M%S");
    let log_filename = format!("{}-{}.log", test_suite_name, timestamp);
    let log_path = logs_dir.join(&log_filename);

    let log_file = Arc::new(Mutex::new(
        File::create(&log_path).context("Failed to create log file")?,
    ));

    // Write log header
    {
        let mut file = log_file.lock().unwrap();
        writeln!(file, "=== easycc Test Run ===")?;
        writeln!(
            file,
            "Timestamp: {}",
            Local::now().format("%Y-%m-%d %H:%M:%S")
        )?;
        writeln!(file, "Test Suite: {}", test_suite_path)?;
        writeln!(file)?;
        file.flush()?;
    }

    log_both!(
        log_file,
        "{}",
        "🧪 Running Chaincode Test Suite".bright_blue().bold()
    );
    log_both!(log_file, "");

    // Load config to check if initialized
    let config_path = workspace::get_config_path(&workspace_root);
    if !config_path.exists() {
        bail!("Workspace not initialized. Run 'easycc init' first.");
    }

    let config = NetworkConfig::load(&config_path)?;

    // Verify the organization exists
    if !config.organizations.contains(&org) {
        bail!(
            "Organization '{}' not found in network config. Available: {}",
            org,
            config.organizations.join(", ")
        );
    }

    // Determine chaincode name (from parameter or default)
    let cc_name = chaincode_name.unwrap_or_else(|| "mychaincode".to_string());
    let cc_version = chaincode_version.unwrap_or_else(|| "1.0".to_string());

    log_both!(log_file, "Organization: {}", org.bright_cyan());
    log_both!(
        log_file,
        "Chaincode: {} v{}",
        cc_name.bright_cyan(),
        cc_version.bright_cyan()
    );
    log_both!(log_file, "Test Suite: {}", test_suite_path.bright_cyan());
    log_both!(
        log_file,
        "Log file: {}",
        log_path.display().to_string().bright_cyan()
    );
    log_both!(log_file, "");

    // Verify prerequisites exist
    log_both!(log_file, "{}", "Verifying prerequisites...".bright_blue());

    // Check crypto material
    let crypto_dir = workspace_root.join("crypto-config");
    if !crypto_dir.exists() || !crypto_dir.is_dir() {
        bail!("Crypto material not found. Please run 'easycc create' first to set up the network.");
    }
    log_both!(log_file, "  ✓ Crypto material found");

    // Check wallet exists for the organization
    let wallet_path = workspace_root.join(format!("exports/wallets/{}", org.to_lowercase()));
    if !wallet_path.exists() {
        bail!(
            "Wallet for organization '{}' not found at {}. Please run 'easycc export' first.",
            org,
            wallet_path.display()
        );
    }
    log_both!(log_file, "  ✓ Wallet found for {}", org);

    // Check connection profile exists
    let connection_profile = workspace_root.join(format!(
        "exports/connection-profiles/connection-{}.json",
        org.to_lowercase()
    ));
    if !connection_profile.exists() {
        bail!(
            "Connection profile not found at {}. Please run 'easycc export' first.",
            connection_profile.display()
        );
    }
    log_both!(log_file, "  ✓ Connection profile found");

    log_both!(log_file, "");
    log_both!(
        log_file,
        "{}",
        "All prerequisites met. Running tests...".bright_green()
    );
    log_both!(log_file, "");

    // Run ChaincoderTestr
    log_both!(
        log_file,
        "{}",
        "Running ChaincoderTestr...".bright_yellow().bold()
    );
    log_both!(log_file, "");

    let wallet_path_arg = format!("./exports/wallets/{}/", org.to_lowercase());
    let connection_profile_arg = format!(
        "./exports/connection-profiles/connection-{}.json",
        org.to_lowercase()
    );

    log_both!(log_file, "Executing: {}", tester_path.bright_cyan());
    log_both!(log_file, "  --wallet-path {}", wallet_path_arg);
    log_both!(log_file, "  --identity-name admin");
    log_both!(
        log_file,
        "  --connection-profile-path {}",
        connection_profile_arg
    );
    log_both!(log_file, "  --chaincode-name {}", cc_name);
    log_both!(log_file, "  --test-suite-path {}", test_suite_path);
    log_both!(log_file, "");
    log_both!(
        log_file,
        "{}",
        "⏳ Running tests... This may take a while.".bright_blue()
    );
    log_both!(log_file, "");

    // Write section header to log
    {
        let mut file = log_file.lock().unwrap();
        writeln!(file, "=== ChaincoderTestr Output ===")?;
        writeln!(file)?;
        file.flush()?;
    }

    // Run ChaincoderTestr with output captured to both terminal and log
    let mut child = Command::new(&tester_path)
        .args([
            "--wallet-path",
            &wallet_path_arg,
            "--identity-name",
            "admin",
            "--connection-profile-path",
            &connection_profile_arg,
            "--chaincode-name",
            &cc_name,
            "--test-suite-path",
            &test_suite_path,
        ])
        .current_dir(&workspace_root)
        .stdout(Stdio::piped())
        .stderr(Stdio::piped())
        .spawn()
        .context("Failed to execute ChaincoderTestr")?;

    // Capture and display output while writing to log
    let log_file_clone = Arc::clone(&log_file);
    if let Some(stdout) = child.stdout.take() {
        let reader = BufReader::new(stdout);
        for line in reader.lines().map_while(Result::ok) {
            println!("{}", line);
            if let Ok(mut file) = log_file_clone.lock() {
                let _ = writeln!(file, "{}", line);
            }
        }
    }

    let log_file_clone2 = Arc::clone(&log_file);
    if let Some(stderr) = child.stderr.take() {
        let reader = BufReader::new(stderr);
        for line in reader.lines().map_while(Result::ok) {
            eprintln!("{}", line);
            if let Ok(mut file) = log_file_clone2.lock() {
                let _ = writeln!(file, "STDERR: {}", line);
            }
        }
    }

    let status = child.wait().context("Failed to wait for ChaincoderTestr")?;

    log_both!(log_file, "");
    log_both!(
        log_file,
        "{}",
        format!("📝 Full test log saved to: {}", log_path.display()).bright_cyan()
    );
    log_both!(log_file, "");

    if status.success() {
        log_both!(
            log_file,
            "{}",
            "✅ Test suite completed successfully!"
                .bright_green()
                .bold()
        );
    } else {
        let msg = format!(
            "Test suite failed with exit code: {}",
            status.code().unwrap_or(-1)
        );
        log_both!(log_file, "{}", msg);
        bail!(msg);
    }

    Ok(())
}
