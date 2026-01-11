# EasyCC Wiki

Welcome to the **EasyCC** documentation.

EasyCC is a command-line tool that simplifies Hyperledger Fabric network setup and chaincode deployment and testing.

It handles:

- **Cryptographic material generation** (wallets and certificates)
- **Network artifacts creation** (genesis block, channel configuration). For now, only one channel.
- **Docker container orchestration** (peers, orderer, CouchDB)
- **Channel creation and peer joining**
- **Chaincode deployment** (install, approve, commit). Only Java chaincode supported for now.
- **Connection profile and wallet export** (for application development)
- **Automated testing integration** (with [ChaincoderTestr](https://github.com/AmaVic/ChaincodeTestr))

## Quick Links

- **[Installation Guide](Installation.md)**
- **[Getting Started](Getting-Started.md)**
- **[Command Reference](Command-Reference.md)**
- **[Testing with ChaincoderTestr](Testing-Guide.md)**