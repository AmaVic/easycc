# Installation Guide

This guide walks you through installing EasyCC and its prerequisites on macOS, Linux, and Windows.

## Prerequisites
### Rust and Cargo
Rust (version 1.88.0 or later) and cargo must be installed on your computer.

You can find detailed installation instructions on the official website: [https://rustup.rs/](https://rustup.rs/).

### Docker Desktop

Docker is required to run the Fabric network containers.

#### macOS
```bash
brew install --cask docker
# Or download from https://www.docker.com/products/docker-desktop
```

#### Linux (Ubuntu/Debian)
```bash
# Install Docker Engine
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh

# Add your user to docker group
sudo usermod -aG docker $USER
newgrp docker

# Install Docker Compose
sudo apt-get update
sudo apt-get install docker-compose-plugin
```

#### Windows
Download and install Docker Desktop from https://www.docker.com/products/docker-desktop

**Important for Windows:**
- [Enable WSL 2 backend](https://docs.docker.com/desktop/features/wsl/)
- Ensure Docker Desktop is running before using EasyCC

### Hyperledger Fabric Binaries

EasyCC requires Fabric binaries (peer, orderer, configtxgen, etc.) version 2.5.0.

#### All Platforms
```bash
# Download and install Fabric binaries
curl -sSL https://bit.ly/2ysbOFE | bash -s -- 2.5.9 1.5.12 -d -s

# This installs binaries to ./fabric-samples/bin
# Add to your PATH or use FABRIC_BIN_PATH environment variable
export FABRIC_BIN_PATH=~/fabric-samples/bin
```

**Alternative**: Download binaries from https://github.com/hyperledger/fabric/releases

### Java Development Kit (JDK) - Optional

**Not required for basic chaincode deployment**. 

Chaincode is compiled inside Docker containers using Java 1.8. You only need a local JDK if you want to:
- Build chaincode locally before deployment (optional)
- Develop chaincode with IDE features

## Installing EasyCC

### Option 1: Install from Crates.io

```bash
cargo install easycc
```

Once the command is successfully executed, the software should be available from anywhere on your computer.

### Option 2: Build from Source

#### Prerequisites
- Rust toolchain (1.88.0 or later)

#### Build and Install
```bash
# Clone the repository
git clone https://github.com/AmaVic/easycc
cd easycc

# Build and install
cargo install --path .
```

This installs `easycc` to `~/.cargo/bin/` (automatically in your PATH if Rust is properly configured).

## Verify Installation

Check that EasyCC is installed correctly:

```bash
easycc --version
```

You should see output like:
```
easycc 0.1.0
```

Check that Docker is running:
```bash
docker ps
```

Check that Fabric binaries are accessible:
```bash
peer version
```

## Environment Configuration

### FABRIC_BIN_PATH (Optional)

If Fabric binaries are not in your system PATH, set the `FABRIC_BIN_PATH` environment variable:

```bash
export FABRIC_BIN_PATH=/path/to/fabric-samples/bin
```

Add to your shell profile (~/.bashrc, ~/.zshrc, etc.) to make it permanent.

## Next Steps

Once installation is complete:

1. Read the [Getting Started Guide](Getting-Started.md) to create your first network
2. Review the [Command Reference](Command-Reference.md) for available commands
3. Read the [Testing Guide](Testing-Guide.md) to run automated test suites on the network with ChaincodeTestr