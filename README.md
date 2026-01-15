# Habbo CLI - Independent Client

A Java-based independent client for connecting to Habbo servers with a complete protocol layer and command-line interface.

## Features

- **Protocol Layer**: Complete implementation for Habbo server communication
- **CLI Interface**: Interactive command-line interface for user interaction
- **Network Connection**: Direct TCP socket connection to Habbo servers
- **Authentication**: Framework for user login/authentication
- **Extensible Architecture**: Modular design for easy feature expansion
- **Comprehensive Logging**: SLF4J with Logback integration

## Project Structure

```
HabboCLI/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/habbo/client/
│   │   │       ├── HabboCLI.java           # Main entry point
│   │   │       ├── network/
│   │   │       │   └── HabboConnection.java
│   │   │       ├── protocol/
│   │   │       │   ├── ProtocolMessage.java
│   │   │       │   ├── ProtocolEncoder.java
│   │   │       │   └── ProtocolDecoder.java
│   │   │       ├── cli/
│   │   │       │   └── CommandInterpreter.java
│   │   │       └── models/
│   │   │           ├── User.java
│   │   │           └── Room.java
│   │   └── resources/
│   │       └── logback.xml
│   └── test/
│       └── java/
│           └── com/habbo/client/
│               └── models/
│                   ├── RoomTest.java
│                   └── UserTest.java
└── pom.xml                                 # Maven configuration

```

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Building

```bash
# Build the project
mvn clean install

# Build with tests
mvn clean test

# Create executable JAR
mvn clean package

# Create fat JAR with dependencies
mvn clean assembly:assembly
```

## Running

### Using Maven
```bash
mvn exec:java -Dexec.mainClass="com.habbo.client.HabboCLI"
```

### Using JAR
```bash
java -jar target/habbo-cli-1.0.0-SNAPSHOT.jar
```

## Available Commands

Once running, use these commands:

| Command | Description |
|---------|-------------|
| `help` | Show available commands |
| `connect <host:port>` | Connect to a Habbo server |
| `disconnect` | Disconnect from server |
| `login <username> <password>` | Login to account |
| `logout` | Logout from account |
| `status` | Show connection status |
| `exit`/`quit` | Exit the CLI |

### Example Usage

```
> connect localhost:30000
Connected to localhost:30000

> status
Connected to localhost:30000

> login testuser testpass
Attempting to login as: testuser

> disconnect
Disconnected from server

> exit
Goodbye!
```

## Dependencies

### Core
- SLF4J 2.0.9 - Logging API
- Logback 1.4.11 - Logging implementation
- GSON 2.10.1 - JSON processing
- Commons CLI 1.6.0 - Command-line interface
- Commons IO 2.13.0 - I/O utilities

### Testing
- JUnit 4.13.2 - Unit testing
- Mockito 5.5.1 - Mocking framework

## Architecture

### Protocol Layer (`com.habbo.client.protocol`)
- `ProtocolMessage`: Base class for all protocol messages
- `ProtocolEncoder`: Encodes messages to binary format
- `ProtocolDecoder`: Decodes binary data to messages

### Network Layer (`com.habbo.client.network`)
- `HabboConnection`: Manages TCP connection to servers
- `ConnectionListener`: Interface for connection events

### Models (`com.habbo.client.models`)
- `User`: Represents a player/user
- `Room`: Represents a Habbo room

### CLI (`com.habbo.client.cli`)
- `CommandInterpreter`: Interprets and executes user commands
- Built-in commands for connection, authentication, and status

## Logging

Logs are written to:
- **Console**: Real-time output at INFO level and above
- **File**: Detailed logs in `logs/habbo-cli.log`

Configure logging in `src/main/resources/logback.xml`

## Development

### Running Tests
```bash
mvn test
```

### Running Specific Test
```bash
mvn test -Dtest=RoomTest
```

### Check for Errors
```bash
mvn clean install -X
```

## Future Enhancements

- [ ] Implement Habbo protocol message structures
- [ ] Add user authentication flow
- [ ] Implement room navigation and chat
- [ ] Add avatar movements and interactions
- [ ] Support for item/furniture interactions
- [ ] Configuration file support
- [ ] Enhanced CLI with colors and formatting
- [ ] GUI alternative using JavaFX

## License

This project is provided as-is for educational purposes.

## Contributing

To contribute:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## Support

For issues or questions, please open an issue on the GitHub repository.
