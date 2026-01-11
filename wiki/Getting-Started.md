# Getting Started

This guide will walk you through creating your first Hyperledger Fabric network with EasyCC and deploying a chaincode.

## Quick Start

```bash
# 1. Create a workspace directory
mkdir my-fabric-network
cd my-fabric-network

# 2. Initialize the network
easycc init

# 3. Create network artifacts (crypto, genesis block, etc.)
easycc create

# 4. Start the network
easycc start

# 5. Deploy your chaincode
easycc deploy --chaincode-path /path/to/your/chaincode --name chaincode-name
# --chaincode-path is optional if your chaincode is located under workspace/chaincode
# --name is optional (default chaincode name is: mychaincode)

# 6. Export connection profiles and wallets
easycc export

# 7. Stopping the network
easycc stop
```

That's it! You now have a running Fabric network with your chaincode deployed.

## Detailed Walkthrough

### Step 1: Initialize Your Workspace

Navigate to an empty directory where you want to set up your Fabric network:

```bash
mkdir my-fabric-network
cd my-fabric-network
easycc init
```

This creates a `easycc/config.json` file based on information inputed by user (requested interactively in the command line):
- Name of the network
- Number of organizations
- Organization names (comma-separated, no spaces)

Example of content of this file:
```json
{
  "network_name": "rea",
  "organizations": [
    "org1",
    "org2",
    "org3"
  ]
}
```

### Step 2: Create Network Artifacts

Generate all necessary cryptographic materials and configuration:

```bash
easycc create
```

This creates:
- `crypto-config/`: Certificates and keys for all network components
- `channel-artifacts/`: Genesis block and channel configuration
- `configtx.yaml`: Channel configuration template
- `docker-compose.yaml`: Container orchestration file

**What's happening:**
- Certificate Authority (CA) generates identity certificates
- MSP (Membership Service Provider) structures are created
- Channel genesis block is generated
- Anchor peer configurations are prepared

### Step 3: Start the Network

Launch all Docker containers:

```bash
easycc start
```

This starts:
- Orderer node (consensus and ordering service)
- Peer nodes (one per organization)
- CouchDB instances (state database for each peer)

**Output includes:**
- Container status for each component
- CouchDB URLs for database access
- Next step instructions

**Access CouchDB:**
Open http://localhost:5984/_utils (for first organization) in your browser:
- Username: `admin`
- Password: `adminpw`

### Step 4: Deploy Your Chaincode

#### Option A: Deploy from Workspace

If your chaincode is in the `chaincode/` subdirectory:

```bash
easycc deploy
```

#### Option B: Deploy from Custom Path

```bash
easycc deploy --chaincode-path /path/to/your/chaincode
```

#### Option C: Specify Name and Version

```bash
easycc deploy \
  --chaincode-path /path/to/your/chaincode \
  --name mycontract \
  --version 1.0
```

**What's happening:**
1. **Package**: Chaincode is packaged with metadata
2. **Install**: Package is installed on all peer nodes
3. **Approve**: Each organization approves the chaincode definition
4. **Commit**: Chaincode definition is committed to the channel

**Note**: Chaincode is built by Fabric during installation (no pre-compilation needed).

### Step 5: Export Connection Materials

Generate connection profiles and identity wallets for application development:

```bash
easycc export
```

This creates:
- `exports/connection-profiles/` - Connection profiles for each organization
- `exports/wallets/` - Identity wallets with admin credentials
- Displays extracted public keys (for testing)

**Output structure:**
In this example structure, we created a network with two organizations: one "client" and one "supplier".

```
exports/
├── connection-profiles/
│   ├── connection-client.json
│   └── connection-supplier.json
└── wallets/
    ├── client/
    │   └── admin.id
    └── supplier/
        └── admin.id
```

## Common Workflows

### Restart Network (Preserving Data)

```bash
easycc stop
easycc start
```

### Complete Reset

```bash
easycc clean --all
easycc create
easycc start
easycc deploy
```

### Update Chaincode

```bash
# Make changes to your chaincode
easycc deploy --version 1.1
```

### View Running Containers
```bash
docker ps
```

### Stop Network

```bash
easycc stop
```

## Next Steps

* Learn about [Automated Testing with ChaincoderTestr](Testing-Guide.md)
* Review the [Command Reference](Command-Reference.md) for all available options
* Customize your network with the [Configuration Guide](Configuration.md)
