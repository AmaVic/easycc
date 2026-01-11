# Testing with ChaincoderTestr

Complete guide to automated chaincode testing using EasyCC and ChaincoderTestr.

## Overview

ChaincoderTestr is an external tool for automated chaincode testing. EasyCC provides seamless integration through the `easycc test` command, which executes your test suite against an already-running network:

1. Verifies network prerequisites (crypto materials, wallets, connection profiles)
2. Runs ChaincoderTestr with the appropriate credentials
3. Logs all test results

**Note:** The `easycc test` command assumes your network is already set up with crypto materials and wallets exported. The test suite should contain public keys that were generated during the initial network setup.

## Prerequisites

### 1. Install ChaincoderTestr

Download ChaincoderTestr from the official repository or website.
For download and installation instructions, check out the [repository's documentation](https://github.com/AmaVic/ChaincodeTestr/).

Currently, EasyCC only works with v0.3 of ChaincodeTestr.

**Verify installation**
```
./ChaincoderTestr-0.3/bin/ChaincoderTestr -h
```
Running the program this way should display help information in the terminal.

### 2. Prepare Test Suite

Create a JSON test suite file that defines your test scenarios.

**Example: `test-suite.json`**
```json
{
    "name": "Happy Path REA",
    "testsCases": [
        {
            "name": "Create First EconomicAgent (Elmo)",
            "businessEventName": "EVcrEconomicAgent",
            "payload": {
                "publicKey": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEOqHVWeIlraubwG+CsmobuigvO0TeaXJbmS8QkkwTOTtBtogKFVjj9PyBt0hekRKy8wtVllQpfu+UgfrITok5BA==",
                "Name": "Elmo"
            },
            "expectedToSucceed": true,
            "thenMarkAsReady": true,
            "expectedAttributeValues": [
                {
                    "boId": "EconomicAgent#0",
                    "attributeName": "name",
                    "expectedValue": "Elmo"
                }
            ],
            "expectedBOStates": [
                {
                    "boId": "EconomicAgent#0",
                    "expectedStateName": "exists"
                }
            ]
        },
        {
            "name": "Create Second EconomicAgent(Cookie)",
            "businessEventName": "EVcrEconomicAgent",
            "payload": {
                "publicKey": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEMkHRF1L6deb2FCwQkZbwNeGtp00kc/sxd2R5XLfiGCM6yA9F4NJXPg1DdNZ45x2YqcDn5lBJKkzHvNMVADMBIw==",
                "Name": "Cookie"
            },
            "expectedToSucceed": true,
            "thenMarkAsReady": false,
            "expectedAttributeValues": [
                {
                    "boId": "EconomicAgent#1",
                    "attributeName": "name",
                    "expectedValue": "Cookie"
                }
            ],
            "expectedBOStates": [
                {
                    "boId": "EconomicAgent#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicAgent#1",
                    "expectedStateName": "exists"
                }
            ]
        },
        {
            "name": "Create Economic Resource Resource (Cookie)",
            "businessEventName": "EVcrEconomicResource",
            "payload": {
                "Name": "Cookie"
            },
            "expectedToSucceed": true,
            "thenMarkAsReady": false,
            "expectedAttributeValues": [
                {
                    "boId": "EconomicResource#0",
                    "attributeName": "name",
                    "expectedValue": "Cookie"
                }
            ],
            "expectedBOStates": [
                {
                    "boId": "EconomicAgent#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicAgent#1",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#0",
                    "expectedStateName": "exists"
                }
            ]
        },
        {
            "name": "Create Economic Resource Resource (Cash)",
            "businessEventName": "EVcrEconomicResource",
            "payload": {
                "Name": "Cash"
            },
            "expectedToSucceed": true,
            "thenMarkAsReady": false,
            "expectedAttributeValues": [
                {
                    "boId": "EconomicResource#1",
                    "attributeName": "name",
                    "expectedValue": "Cash"
                }
            ],
            "expectedBOStates": [
                {
                    "boId": "EconomicAgent#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicAgent#1",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#1",
                    "expectedStateName": "exists"
                }
            ]
        },
        {
            "name": "Register Ownership of Cash by Cookie",
            "businessEventName": "EVcrOwnership",
            "payload": {
                "Name": "CashOwnedByCookie",
                "economicResourceId_EconomicResource_Ownership": "EconomicResource#1",
                "economicAgentId_EconomicAgent_Ownership": "EconomicAgent#1"
            },
            "expectedToSucceed": true,
            "thenMarkAsReady": false,
            "expectedAttributeValues": [
                {
                    "boId": "Ownership#0",
                    "attributeName": "name",
                    "expectedValue": "CashOwnedByCookie"
                }
            ],
            "expectedBOStates": [
                {
                    "boId": "EconomicAgent#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicAgent#1",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#1",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "Ownership#0",
                    "expectedStateName": "image"
                }
            ]
        },
        {
            "name": "Register Ownership of Cookie by Elmo",
            "businessEventName": "EVcrOwnership",
            "payload": {
                "Name": "CookieOwnedByElmo",
                "economicResourceId_EconomicResource_Ownership": "EconomicResource#0",
                "economicAgentId_EconomicAgent_Ownership": "EconomicAgent#0"
            },
            "expectedToSucceed": true,
            "thenMarkAsReady": false,
            "expectedAttributeValues": [
                {
                    "boId": "Ownership#1",
                    "attributeName": "name",
                    "expectedValue": "CookieOwnedByElmo"
                }
            ],
            "expectedBOStates": [
                {
                    "boId": "EconomicAgent#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicAgent#1",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#1",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "Ownership#0",
                    "expectedStateName": "image"
                },
                {
                    "boId": "Ownership#1",
                    "expectedStateName": "image"
                }
            ]
        },
        {
            "name": "Create Economic Event (delivery)",
            "businessEventName": "EVcrEconomicEvent",
            "payload": {
                "Name": "delivery",
                "TimeStamp": null,
                "CountDependentViews": 0
            },
            "expectedToSucceed": true,
            "thenMarkAsReady": false,
            "expectedAttributeValues": [
                {
                    "boId": "EconomicEvent#0",
                    "attributeName": "name",
                    "expectedValue": "delivery"
                }
            ],
            "expectedBOStates": [
                {
                    "boId": "EconomicAgent#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicAgent#1",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#1",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "Ownership#0",
                    "expectedStateName": "image"
                },
                {
                    "boId": "Ownership#1",
                    "expectedStateName": "image"
                },
                {
                    "boId": "EconomicEvent#0",
                    "expectedStateName": "business event"
                }
            ]
        },
        {
            "name": "Create Economic Event (payment)",
            "businessEventName": "EVcrEconomicEvent",
            "payload": {
                "Name": "payment",
                "TimeStamp": null,
                "CountDependentViews": 0
            },
            "expectedToSucceed": true,
            "thenMarkAsReady": false,
            "expectedAttributeValues": [
                {
                    "boId": "EconomicEvent#1",
                    "attributeName": "name",
                    "expectedValue": "payment"
                }
            ],
            "expectedBOStates": [
                {
                    "boId": "EconomicAgent#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicAgent#1",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#0",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "EconomicResource#1",
                    "expectedStateName": "exists"
                },
                {
                    "boId": "Ownership#0",
                    "expectedStateName": "image"
                },
                {
                    "boId": "Ownership#1",
                    "expectedStateName": "image"
                },
                {
                    "boId": "EconomicEvent#0",
                    "expectedStateName": "business event"
                },
                {
                    "boId": "EconomicEvent#1",
                    "expectedStateName": "business event"
                }
            ]
        }
    ]
}
```

### 3. Setup Network and Export Credentials

Before running tests, you need to set up your network:

```bash
# Initialize workspace
mkdir my-test-workspace
cd my-test-workspace
easycc init --orgs client,supplier

# Create network artifacts and crypto materials
easycc create

# Start the network
easycc start

# Export credentials and get public keys
easycc export

# Deploy your chaincode
easycc deploy --chaincode-name mychaincode-path
```

After running `easycc export`, you'll see public keys displayed for each organization. **Copy these public keys into your test suite JSON file**, they need to match the identities generated during network creation.

## Running Tests

Once your network is set up and your test suite contains the correct public keys, run your tests:

```bash
easycc test \
  --tester-path /path/to/ChaincoderTestr \
  --test-suite-path ./test-suite.json \
  --org client \
  --chaincode-name mychaincode
```

**Workflow:**
1. Verifies crypto materials exist
2. Verifies wallet exists for the specified organization
3. Verifies connection profile exists
4. Runs ChaincoderTestr with appropriate credentials
5. Saves results to `logs/`

**Example output:**
```
🧪 Running Chaincode Test Suite

Organization: client
Chaincode: mychaincode v1.0
Test Suite: ./test-suite.json
Log file: /Users/user/workspace/logs/test-suite-20260111-143025.log

Verifying prerequisites...
  ✓ Crypto material found
  ✓ Wallet found for client
  ✓ Connection profile found

All prerequisites met. Running tests...

Running ChaincoderTestr...

Executing: /path/to/ChaincoderTestr
  --wallet-path ./exports/wallets/client/
  --identity-name admin
  --connection-profile-path ./exports/connection-profiles/connection-client.json
  --chaincode-name mychaincode
  --test-suite-path ./test-suite.json

⏳ Running tests... This may take a while.

[ChaincoderTestr output follows...]
```

### Error Handling

If prerequisites are missing, you'll see helpful error messages:

```
Error: Crypto material not found. Please run 'easycc create' first to set up the network.
```

```
Error: Wallet for organization 'client' not found at /path/to/exports/wallets/client. 
Please run 'easycc export' first.
```

```
Error: Connection profile not found at /path/to/connection-client.json. 
Please run 'easycc export' first.
```

## Complete Testing Workflow

Here's a typical end-to-end workflow for testing:

### 1. Initial Setup (Once)

```bash
# Initialize workspace
easycc init --orgs client,supplier

# Create network artifacts
easycc create

# Start network
easycc start

# Export credentials and note the public keys
easycc export

# Deploy chaincode
easycc deploy --chaincode-name mychaincode
```

### 2. Prepare Test Suite (Once)

Update your test suite JSON file with the public keys displayed by `easycc export`:

```json
{
    "name": "My Test Suite",
    "testsCases": [
        {
            "name": "Create User",
            "businessEventName": "EVcrUser",
            "payload": {
                "publicKey": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE...",  // ← Copy from easycc export
                "Name": "Alice"
            },
            ...
        }
    ]
}
```

### 3. Run Tests (Repeatedly)

```bash
# Run your test suite
easycc test \
  --tester-path ~/ChaincoderTestr/bin/ChaincoderTestr \
  --test-suite-path ./tests/my-test-suite.json \
  --org client

# Modify chaincode or test suite, redeploy if needed
easycc deploy --chaincode-name mychaincode

# Run tests again
easycc test \
  --tester-path ~/ChaincoderTestr/bin/ChaincoderTestr \
  --test-suite-path ./tests/my-test-suite.json \
  --org client
```

### 4. Clean Up (When Needed)

If you need to completely reset the network:

```bash
# Stop and remove everything (including crypto)
easycc clean --all

# Start over from step 1
easycc create
easycc start
easycc export  # Note: This will generate NEW public keys!
# Update test suite with new public keys
easycc deploy
easycc test ...
```

## Test Options

### Basic Test
```bash
easycc test \
  --tester-path /opt/ChaincoderTestr/bin/ChaincoderTestr \
  --test-suite-path ./tests/basic-test.json \
  --org client
```

### Custom Chaincode
```bash
easycc test \
  --tester-path ~/ChaincoderTestr \
  --test-suite-path ./tests/advanced-test.json \
  --org supplier \
  --chaincode-name inventory \
  --chaincode-version 2.0
```

### Multiple Test Suites

Run different test suites sequentially:

```bash
# Test 1: Happy path
easycc test \
  --tester-path /opt/ChaincoderTestr \
  --test-suite-path ./tests/happy-path.json \
  --org client

# Test 2: Error handling (reuses same network and keys)
easycc test \
  --tester-path /opt/ChaincoderTestr \
  --test-suite-path ./tests/error-handling.json \
  --org client

# Test 3: Edge cases
easycc test \
  --tester-path /opt/ChaincoderTestr \
  --test-suite-path ./tests/edge-cases.json \
  --org supplier
```

## Test Logging

### Log File Location

All test output is saved to:
```
logs/<test-suite-name>-<timestamp>.log
```

Example:
```
logs/happy-path-20251214-153045.log
```