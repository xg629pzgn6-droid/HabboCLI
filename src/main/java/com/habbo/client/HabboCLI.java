package com.habbo.client;

import com.habbo.client.cli.CommandInterpreter;
import com.habbo.client.network.HabboConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main entry point for Habbo CLI application
 */
public class HabboCLI {
    private static final Logger logger = LoggerFactory.getLogger(HabboCLI.class);
    private static HabboConnection connection;
    private static CommandInterpreter commandInterpreter;

    public static void main(String[] args) {
        logger.info("Starting Habbo CLI...");

        // Initialize the CLI
        commandInterpreter = new CommandInterpreter();

        try {
            // Display welcome message
            displayWelcome();

            // Start interactive mode
            startInteractiveMode();

        } catch (IOException e) {
            logger.error("Fatal error in CLI", e);
            System.exit(1);
        }
    }

    /**
     * Display welcome message
     */
    private static void displayWelcome() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║      Habbo CLI - Independent Client    ║");
        System.out.println("║           Version 1.0.0               ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Type 'help' for available commands.");
        System.out.println();
    }

    /**
     * Start interactive command mode
     */
    private static void startInteractiveMode() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while (true) {
            System.out.print("> ");
            line = reader.readLine();

            if (line == null || line.isEmpty()) {
                continue;
            }

            if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) {
                shutdown();
                break;
            }

            try {
                commandInterpreter.executeCommand(line);
            } catch (Exception e) {
                logger.error("Error executing command", e);
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Shutdown the CLI
     */
    private static void shutdown() {
        logger.info("Shutting down Habbo CLI...");

        if (connection != null && connection.isConnected()) {
            connection.disconnect();
        }

        System.out.println("Goodbye!");
        System.exit(0);
    }

    public static HabboConnection getConnection() {
        return connection;
    }

    public static void setConnection(HabboConnection conn) {
        connection = conn;
    }

    public static CommandInterpreter getCommandInterpreter() {
        return commandInterpreter;
    }
}
