# EasyCC - Easy Chaincode CLI

[![Build](https://github.com/AmaVic/easycc-dev/workflows/Build/badge.svg)](https://github.com/AmaVic/easycc-dev/actions/workflows/build.yml)
[![Documentation](https://github.com/AmaVic/easycc-dev/workflows/Documentation/badge.svg)](https://github.com/AmaVic/easycc-dev/actions/workflows/docs.yml)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

**Simplify Hyperledger Fabric development with an intuitive CLI**

EasyCC is a Rust-based command-line tool that automates the setup, management, and testing of Hyperledger Fabric networks and java chaincodes for local development. Get a blockchain environment running in seconds.

## ✨ Key Features

- **Zero-config network setup** - Running network in under 30 seconds
- **Complete chaincode lifecycle** - Build, deploy, and test with single commands
- **CouchDB state database** - Rich queries out of the box
- **Testing integration** - Built-in support for ChaincoderTestr
- **Cross-platform** - Works on macOS, Linux, and Windows

## 🚀 Quick Start

```bash
# Install EasyCC
cargo install easycc

# Create and start a network
easycc init
easycc create
easycc start

# Deploy your chaincode
easycc deploy --chaincode-path ./chaincode

# Access CouchDB
open http://localhost:5984/_utils  # credentials: admin/adminpw
```

**That's it!** You now have a 2-org Fabric network with CouchDB running locally.

## 📚 Documentation

**Comprehensive documentation is available in the [Wiki](wiki/):**

- **[Installation Guide](wiki/Installation.md)** - Platform-specific setup instructions
- **[Getting Started](wiki/Getting-Started.md)** - Detailed tutorial for first-time users
- **[Command Reference](wiki/Command-Reference.md)** - Complete command documentation
- **[Testing Guide](wiki/Testing-Guide.md)** - ChaincoderTestr integration
- **[Configuration](wiki/Configuration.md)** - Advanced network customization
- **[Troubleshooting](wiki/Troubleshooting.md)** - Solutions to common issues
- **[Architecture](wiki/Architecture.md)** - How EasyCC works internally

## 📋 Prerequisites

- **Docker** (Desktop or Engine) - Container runtime
- **Hyperledger Fabric binaries** (2.5.0+) - Tools like `peer`, `configtxgen`
- **Java 11+** (for Java chaincode) or **Go 1.20+** (for Go chaincode)

See [Installation Guide](wiki/Installation.md) for detailed setup instructions



## 🎯 Common Commands

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

# Check status
easycc status

# Stop network
easycc stop

# Clean up
easycc clean --all
```

See [Command Reference](wiki/Command-Reference.md) for all available commands and options.

## 🛠️ Development

### Building from Source

```bash
git clone https://github.com/AmaVic/easycc-dev.git
cd easycc-dev
cargo build --release
./target/release/easycc --version
```

### Running Tests

```bash
cargo test
```

## 📖 Example Workflow

Complete example from initialization to testing:

```bash
# 1. Set up workspace
easycc init

# 2. Generate network configuration
easycc create

# 3. Start network (includes channel creation)
easycc start

# 4. Deploy your chaincode
easycc deploy --chaincode-path ./my-chaincode

# 5. Run automated tests
easycc test --tester-path ./ChaincoderTestr \
            --test-suite-path ./test-suite.json \
            --org client

# 6. Access CouchDB to view state
open http://localhost:5984/_utils

# 7. Clean up when done
easycc stop
easycc clean --all
```

See [Getting Started Guide](wiki/Getting-Started.md) for detailed walkthrough.

## 🤝 Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

[Specify your license here]

## 🙏 Acknowledgments

Built with:
- [Hyperledger Fabric](https://www.hyperledger.org/use/fabric) - Enterprise blockchain platform
- [Rust](https://www.rust-lang.org/) - Performance and reliability
- [Docker](https://www.docker.com/) - Container orchestration

## 📞 Support

- **Documentation**: [Wiki](wiki/)
- **Issues**: [GitHub Issues](https://github.com/AmaVic/easycc-dev/issues)
- **Discussions**: [GitHub Discussions](https://github.com/AmaVic/easycc-dev/discussions)

---

**Ready to get started?** Check out the [Installation Guide](wiki/Installation.md)!
