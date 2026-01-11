# EasyCC - Easy Chaincode CLI

[![Build](https://github.com/AmaVic/easycc/workflows/Build/badge.svg)](https://github.com/AmaVic/easycc/actions/workflows/build.yml)
[![Documentation](https://github.com/AmaVic/easycc/workflows/Documentation/badge.svg)](https://github.com/AmaVic/easycc/actions/workflows/docs.yml)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

**Simplify Hyperledger Fabric development with an intuitive CLI**

EasyCC is a Rust-based command-line tool that automates the setup, management, and testing of Hyperledger Fabric networks and java chaincodes for local development. Get a blockchain environment running in seconds.

## Key Features

- **Zero-config network setup** - Running network in under 30 seconds
- **Complete chaincode lifecycle** - Build, deploy, and test with single commands
- **CouchDB state database** - Rich queries out of the box
- **Testing integration** - Built-in support for ChaincoderTestr
- **Cross-platform** - Works on macOS, Linux, and Windows

## Quick Start

```bash
# Install EasyCC
cargo install easycc

# Create and start a network (you will be prompted for the configuration)
easycc init
easycc create
easycc start

# Deploy your chaincode
easycc deploy --chaincode-path ./chaincode

# Access CouchDB
open http://localhost:5984/_utils  # credentials: admin/adminpw
```

**That's it!** You now have a Fabric network with CouchDB running locally.

## 📚 Documentation

**Comprehensive documentation is available in the [Wiki](wiki/):**

- **[Installation Guide](wiki/Installation.md)** - Platform-specific setup instructions
- **[Getting Started](wiki/Getting-Started.md)** - Detailed tutorial for first-time users
- **[Command Reference](wiki/Command-Reference.md)** - Complete command documentation
- **[Testing Guide](wiki/Testing-Guide.md)** - ChaincoderTestr integration

The code documentation is available [here](https://amavic.github.io/easycc/).

## Prerequisites

- **Docker** (Desktop or Engine) - Container runtime
- **Hyperledger Fabric binaries** (2.5.0+) - Tools like `peer`, `configtxgen`

See [Installation Guide](wiki/Installation.md) for detailed setup instructions

## Common Commands

```bash
# Initialize workspace
easycc init

# Create network artifacts
easycc create

# Start network
easycc start

# Deploy chaincode
easycc deploy --chaincode-path ./chaincode

# Run tests
easycc test --tester-path ./ChaincoderTestr \
            --test-suite-path ./tests.json \
            --org client

# Stop network
easycc stop

# Clean up
easycc clean --all
```

See [Command Reference](wiki/Command-Reference.md) for all available commands and options.

## Contributors and Funding
This project is developed and maintained by Victor Amaral de Sousa. It has been requested and funded by Wim Laurier and Satoshi Horiuchi, respectively from the Université Catholique de Louvain (Belgium) and the University of Chuo (Japan).