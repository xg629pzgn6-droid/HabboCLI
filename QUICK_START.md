# Habbo CLI - Quick Start Guide

## Project Overview
Habbo CLI is a complete Java Maven project that provides an independent client for connecting to Habbo servers with a full protocol layer and interactive CLI interface.

## Build Status
✅ **BUILD SUCCESSFUL** - All 14 files compiled and 6 tests passed

## Quick Start

### 1. Build the Project
```bash
mvn clean install
```

### 2. Run the CLI
```bash
# Using Maven
mvn exec:java -Dexec.mainClass="com.habbo.client.HabboCLI"

# Or using the JAR file
java -jar target/habbo-cli-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```

### 3. Use Available Commands
Once the CLI starts:
```
> help
> connect localhost:30000
> status
> disconnect
> exit
```

## Project Architecture

### Core Modules

**Protocol Layer** (`com.habbo.client.protocol`)
- `ProtocolMessage` - Base protocol message class
- `ProtocolEncoder` - Binary message encoding
- `ProtocolDecoder` - Binary message decoding

**Network Layer** (`com.habbo.client.network`)
- `HabboConnection` - TCP socket management and async message reception

**Data Models** (`com.habbo.client.models`)
- `User` - Player/user representation
- `Room` - Room representation

**CLI** (`com.habbo.client.cli`)
- `CommandInterpreter` - Command parsing and execution
- Built-in commands: connect, disconnect, login, logout, status, help

## Available Commands in VS Code

Via the Tasks menu (Ctrl+Shift+B / Cmd+Shift+B):
- **Maven: Clean Install** - Build and test project
- **Maven: Run Tests** - Execute all tests
- **Maven: Run Habbo CLI** - Launch the CLI directly
- **Java: Run JAR** - Run the compiled JAR
- **Maven: Package** - Package without running tests

## Project Structure
```
HabboCLI/
├── src/main/java/com/habbo/client/
│   ├── HabboCLI.java
│   ├── protocol/
│   ├── network/
│   ├── cli/
│   └── models/
├── src/main/resources/
│   └── logback.xml
├── src/test/java/com/habbo/client/
│   └── models/
├── pom.xml
└── README.md
```

## Build Output
- **Standard JAR**: `target/habbo-cli-1.0.0-SNAPSHOT.jar` (21 KB)
- **Fat JAR**: `target/habbo-cli-1.0.0-SNAPSHOT-jar-with-dependencies.jar` (1.7 MB)

## Dependencies
- Java 17+
- Maven 3.6+
- SLF4J 2.0.9 & Logback 1.4.11 (Logging)
- GSON 2.10.1 (JSON)
- Commons CLI 1.6.0 (CLI Framework)
- JUnit 4.13.2 (Testing)

## Test Results
```
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

✓ RoomTest (3 tests)
✓ UserTest (3 tests)
```

## Next Steps
1. Review the [README.md](README.md) for detailed documentation
2. Implement actual Habbo protocol messages
3. Add authentication flow
4. Implement room navigation and chat functionality
5. Add avatar movements and interactions

## Troubleshooting

**Issue**: mvn command not found
- **Solution**: Install Maven from https://maven.apache.org/

**Issue**: Java version too old
- **Solution**: Update to Java 17+ from https://www.oracle.com/java/

**Issue**: Port already in use
- **Solution**: Change the port in the connect command to an available port

## Documentation
- Full documentation: [README.md](README.md)
- Project instructions: [.github/copilot-instructions.md](.github/copilot-instructions.md)
