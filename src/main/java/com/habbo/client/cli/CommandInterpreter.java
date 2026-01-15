package com.habbo.client.cli;

import com.habbo.client.HabboCLI;
import com.habbo.client.network.HabboConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Interprets and executes CLI commands
 */
public class CommandInterpreter {
    private static final Logger logger = LoggerFactory.getLogger(CommandInterpreter.class);
    private Map<String, Command> commands;

    public CommandInterpreter() {
        commands = new HashMap<>();
        registerCommands();
    }

    /**
     * Register all available commands
     */
    private void registerCommands() {
        commands.put("help", new HelpCommand());
        commands.put("connect", new ConnectCommand());
        commands.put("disconnect", new DisconnectCommand());
        commands.put("status", new StatusCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
    }

    /**
     * Execute a command
     */
    public void executeCommand(String input) {
        String[] parts = input.trim().split("\\s+", 2);
        String commandName = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        Command command = commands.get(commandName);

        if (command == null) {
            System.out.println("Unknown command: " + commandName);
            System.out.println("Type 'help' for available commands.");
            return;
        }

        try {
            command.execute(args);
        } catch (Exception e) {
            logger.error("Error executing command: " + commandName, e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Base interface for commands
     */
    public interface Command {
        void execute(String args) throws Exception;
    }

    /**
     * Help command
     */
    private class HelpCommand implements Command {
        @Override
        public void execute(String args) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║        Available Commands              ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println();
            System.out.println("  connect <host:port>    - Connect to Habbo server");
            System.out.println("  status                 - Show connection status");
            System.out.println("  login <user> <pass>    - Login with SSO authentication");
            System.out.println("  logout                 - Logout from account");
            System.out.println("  disconnect             - Disconnect from server");
            System.out.println("  help                   - Show this help message");
            System.out.println("  exit/quit              - Exit the CLI");
            System.out.println();
            System.out.println("Example:");
            System.out.println("  > connect localhost:30000");
            System.out.println("  > login xiony mypassword");
            System.out.println("  > status");
            System.out.println("  > logout");
            System.out.println();
        }
    }

    /**
     * Connect command
     */
    private class ConnectCommand implements Command {
        @Override
        public void execute(String args) {
            if (args.isEmpty()) {
                System.out.println("Usage: connect <host:port>");
                return;
            }

            String[] parts = args.split(":");
            if (parts.length != 2) {
                System.out.println("Invalid format. Use: connect <host:port>");
                return;
            }

            String host = parts[0];
            int port;

            try {
                port = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number: " + parts[1]);
                return;
            }

            HabboConnection connection = new HabboConnection(host, port);
            if (connection.connect()) {
                HabboCLI.setConnection(connection);
                System.out.println("✅ Connected to " + host + ":" + port);
            } else {
                System.out.println("❌ Failed to connect to " + host + ":" + port);
            }
        }
    }

    /**
     * Disconnect command
     */
    private class DisconnectCommand implements Command {
        @Override
        public void execute(String args) {
            HabboConnection connection = HabboCLI.getConnection();
            if (connection != null && connection.isConnected()) {
                connection.disconnect();
                System.out.println("✅ Disconnected from server");
            } else {
                System.out.println("❌ Not connected to any server");
            }
        }
    }

    /**
     * Status command
     */
    private class StatusCommand implements Command {
        @Override
        public void execute(String args) {
            HabboConnection connection = HabboCLI.getConnection();
            
            if (connection == null || !connection.isConnected()) {
                System.out.println("❌ Not connected to any server");
                return;
            }

            System.out.println("✅ Connected to " + connection.getHost() + ":" + connection.getPort());
            
            if (connection.isAuthenticated()) {
                System.out.println("🔐 " + connection.getAuthManager().getAuthenticationStatus());
            } else {
                System.out.println("❌ Not authenticated");
            }
        }
    }

    /**
     * Login command
     */
    private class LoginCommand implements Command {
        @Override
        public void execute(String args) {
            HabboConnection connection = HabboCLI.getConnection();
            if (connection == null || !connection.isConnected()) {
                System.out.println("❌ Not connected to any server. Use 'connect' first.");
                return;
            }

            String[] parts = args.split("\\s+");
            if (parts.length < 2) {
                System.out.println("Usage: login <username> <password>");
                return;
            }

            String username = parts[0];
            String password = parts[1];

            System.out.println("🔐 Attempting to login as: " + username);
            
            if (connection.authenticate(username, password)) {
                System.out.println("✅ Login successful!");
                System.out.println("📊 " + connection.getAuthManager().getAuthenticationStatus());
            } else {
                System.out.println("❌ Login failed!");
                int remaining = connection.getAuthManager().getRemainingLoginAttempts();
                if (remaining == 0) {
                    System.out.println("⛔ Maximum login attempts exceeded. Connection will be closed.");
                    connection.disconnect();
                } else {
                    System.out.println("⚠️  Remaining attempts: " + remaining);
                }
            }
        }
    }

    /**
     * Logout command
     */
    private class LogoutCommand implements Command {
        @Override
        public void execute(String args) {
            HabboConnection connection = HabboCLI.getConnection();
            
            if (connection != null && connection.isAuthenticated()) {
                String username = connection.getAuthManager().getCurrentUsername();
                if (connection.logout()) {
                    System.out.println("✅ Logged out successfully from: " + username);
                } else {
                    System.out.println("❌ Error during logout");
                }
            } else {
                System.out.println("❌ Not authenticated");
            }
        }
    }
}
