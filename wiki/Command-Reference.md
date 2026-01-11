# Command Reference

Complete reference for all EasyCC commands and their options.

## Global Options

These options work with all commands:

```bash
--help, -h     Show help information
--version, -V  Show version information
```

---

## `easycc init`

Initialize a new Fabric network workspace.

### Usage

```bash
easycc init [OPTIONS]
```

### Options

| Option | Description | Default |
|--------|-------------|---------|
| `--orgs <ORGS>` | Comma-separated list of organization names | `Client,Supplier` |

### Examples

```bash
# Initialize with default organizations
easycc init

# Initialize with custom organizations
easycc init --orgs Org1,Org2,Org3,Org4

# Initialize with different org names
easycc init --orgs Manufacturer,Distributor,Retailer
```

### Output

Creates `network-config.json` with your configuration:

```json
{
  "version": "1.0",
  "organizations": ["Client", "Supplier"],
  "channel_name": "mychannel",
  "created_at": "2025-12-14T10:30:00Z"
}
```

---

## `easycc create`

Generate all network artifacts (cryptographic materials, genesis block, channel configuration).

### Usage

```bash
easycc create
```

### Prerequisites

- Workspace must be initialized (`easycc init`)
- Fabric binaries must be available

### What It Creates

- `crypto-config/` - All certificates and keys
  - `peerOrganizations/` - Peer and user identities
  - `ordererOrganizations/` - Orderer identities
- `channel-artifacts/` - Genesis block and channel tx files
- `configtx.yaml` - Channel configuration
- `docker-compose.yaml` - Container definitions

### Examples

```bash
# Create artifacts in current directory
easycc create
```

### Notes

- ⚠️ **Existing crypto-config will be removed** and regenerated
- This is a prerequisite for `easycc start`
- Generated keys are used for all subsequent operations

---

## `easycc start`

Start the Fabric network (Docker containers).

### Usage

```bash
easycc start
```

### Prerequisites

- Network artifacts must exist (`easycc create`)
- Docker must be running

### What It Does

1. Checks for existing Fabric containers (prompts to clean if found)
2. Starts Docker containers via docker-compose
3. Waits for containers to be healthy
4. Creates the channel
5. Joins all peers to the channel
6. Updates anchor peer configuration

### Containers Started

- **Orderer**: `orderer.example.com` (port 7050)
- **Peers**: `peer0.<org>.example.com` (ports 7051, 8051, ...)
- **CouchDB**: `couchdb0`, `couchdb1`, ... (ports 5984, 5985, ...)

### Examples

```bash
easycc start
```

### CouchDB Access

After starting, access CouchDB web interface:

- Organization 1: http://localhost:5984/_utils
- Organization 2: http://localhost:5985/_utils
- Credentials: `admin` / `adminpw`

View logs:
```bash
docker logs peer0.client.example.com
docker logs orderer.example.com
docker logs couchdb0
```

---

## `easycc stop`

Stop all running Fabric containers.

### Usage

```bash
easycc stop
```

### What It Does

- Stops all Docker containers
- Preserves data volumes
- Network can be restarted with `easycc start`

### Examples

```bash
# Stop the network
easycc stop

# Restart later
easycc start
```

### Notes

- Data is **preserved** (ledger and state database remain intact)
- To remove data, use `easycc clean`

---

## `easycc deploy`

Deploy chaincode to the network.

### Usage

```bash
easycc deploy [OPTIONS]
```

### Options

| Option | Description | Default |
|--------|-------------|---------|
| `--chaincode-path <PATH>` | Path to chaincode directory | `./chaincode` |
| `--name <NAME>` | Chaincode name | `mychaincode` |
| `--version <VERSION>` | Chaincode version | `1.0` |

### Prerequisites

- Network must be running (`easycc start`)
- Chaincode must be valid Java/Gradle project

### Deployment Steps

1. **Package**: Creates chaincode package
2. **Install**: Installs on all peer nodes
3. **Approve**: Each org approves the definition
4. **Commit**: Commits definition to channel

### Examples

```bash
# Deploy chaincode from ./chaincode directory
easycc deploy

# Deploy from custom location
easycc deploy --chaincode-path /path/to/my-chaincode

# Deploy with custom name and version
easycc deploy --name inventory --version 2.0

# Full custom deployment
easycc deploy \
  --chaincode-path ~/projects/supply-chain-cc \
  --name supplychain \
  --version 1.5
```

### Endorsement Policy

Default policy: `OR('Org1MSP.member','Org2MSP.member')`

Transactions require endorsement from **any one** organization.

### Notes

- Chaincode is built by Fabric during installation
- Use different versions for updates
- All peers must have the chaincode installed

---

## `easycc export`

Export connection profiles, wallets, and display public keys.

### Usage

```bash
easycc export [OPTIONS]
```

### Options

| Option | Description | Default |
|--------|-------------|---------|
| `--output <PATH>` | Output directory | `./exports` |

### Prerequisites

- Network must be running (`easycc start`)
- Crypto materials must exist

### What It Exports

#### Connection Profiles
JSON files for Fabric Gateway connections:
- `exports/connection-profiles/connection-<org>.json`

#### Wallets
Admin identity files for each organization:
- `exports/wallets/<org>/admin.id`

#### Public Keys (Display Only)
Extracted and displayed in terminal for test configuration.

### Examples

```bash
# Export to default ./exports directory
easycc export

# Export to custom directory
easycc export --output ./my-exports
```

### Using Exported Files

**Java SDK:**
```java
Path networkConfigPath = Paths.get("exports/connection-profiles/connection-client.json");
Path walletPath = Paths.get("exports/wallets/client");
```

---

## `easycc test`

Run automated chaincode tests using ChaincoderTestr.

### Usage

```bash
easycc test [OPTIONS]
```

### Options

| Option | Description | Required |
|--------|-------------|----------|
| `--tester-path <PATH>` | Path to ChaincoderTestr binary | Yes |
| `--test-suite-path <PATH>` | Path to test suite JSON | Yes |
| `--org <ORG>` | Organization name (lowercase) | Yes |
| `--chaincode-name <NAME>` | Chaincode name | No (default: `mychaincode`) |
| `--chaincode-version <VERSION>` | Chaincode version | No (default: `1.0`) |

### Prerequisites

- ChaincoderTestr installed
- Test suite JSON file prepared
- Network configuration initialized

### Examples

```bash
# Full test run with ChaincoderTestr
easycc test \
  --tester-path /opt/ChaincoderTestr/bin/ChaincoderTestr \
  --test-suite-path ./tests/happy-path.json \
  --org client

# Test with custom chaincode
easycc test \
  --tester-path ~/tools/ChaincoderTestr \
  --test-suite-path ./tests/error-handling.json \
  --org supplier \
  --chaincode-name inventory \
  --chaincode-version 2.0
```

### Test Logging

All output is logged to: `logs/<test-suite-name>-<timestamp>.log`

Example: `logs/happy-path-20251214-101506.log`

Log includes:
- All easycc command output
- ChaincoderTestr output
- Timestamps and execution details

### Public Key Management

On first run, public keys are displayed. Copy them into your test suite:

```json
{
  "participants": [
    {
      "name": "Alice",
      "organization": "client",
      "publicKey": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE..."
    }
  ]
}
```

**Subsequent test runs automatically reuse the same keys** (no manual update needed).

## `easycc clean`

Clean up network resources.

### Usage

```bash
easycc clean [OPTIONS]
```

### Options

| Option | Description |
|--------|-------------|
| `--all` | Remove all resources including crypto materials |

### What It Cleans

**Without `--all`:**
- Stops and removes Docker containers
- Removes Docker volumes
- **Preserves** crypto-config and artifacts

**With `--all`:**
- Everything from above, plus:
- Removes crypto-config/
- Removes channel-artifacts/
- Removes docker-compose.yaml
- Removes configtx.yaml
- Removes exports/

### Examples

```bash
# Clean containers and volumes (preserve crypto)
easycc clean

# Complete cleanup (fresh start)
easycc clean --all
```

### Use Cases

- **Regular cleanup**: `easycc clean` (faster restarts, consistent keys)
- **Fresh start**: `easycc clean --all` (new keys, clean state)
- **Before tests**: `easycc test` handles this automatically

## Environment Variables

### `FABRIC_BIN_PATH`

Override the default path to Fabric binaries.

```bash
export FABRIC_BIN_PATH=/opt/fabric/bin
easycc create
```

Default locations checked:
1. `$FABRIC_BIN_PATH`
2. `~/fabric-samples/bin`
3. System PATH

---

## Common Command Sequences

### First-Time Setup
```bash
easycc init
easycc create
easycc start
easycc deploy
easycc export
```

### Quick Restart
```bash
easycc stop
easycc start
```

### Update Chaincode
```bash
# Make code changes
easycc deploy --version 1.1
```

### Full Reset
```bash
easycc clean --all
easycc create
easycc start
easycc deploy
```

### Automated Testing
```bash
easycc test \
  --tester-path /path/to/ChaincoderTestr \
  --test-suite-path ./tests/suite.json \
  --org client
```
