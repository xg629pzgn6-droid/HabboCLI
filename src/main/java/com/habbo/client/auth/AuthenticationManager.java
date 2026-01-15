package com.habbo.client.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Authentication manager for handling user authentication and sessions
 */
public class AuthenticationManager {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationManager.class);

    private SSOToken currentToken;
    private String currentUsername;
    private boolean isAuthenticated;
    private int maxLoginAttempts = 3;
    private int loginAttempts = 0;

    public AuthenticationManager() {
        this.isAuthenticated = false;
    }

    /**
     * Authenticate user with username and password
     * In production, this would validate against Habbo servers
     */
    public synchronized boolean authenticate(String username, String password) {
        if (isAuthenticated && currentToken != null && currentToken.isValid()) {
            logger.warn("User already authenticated: {}", currentUsername);
            return true;
        }

        if (loginAttempts >= maxLoginAttempts) {
            logger.error("Maximum login attempts exceeded");
            return false;
        }

        // Simulate ticket generation from credentials
        String ticket = generateTicket(username, password);

        if (ticket == null || ticket.isEmpty()) {
            loginAttempts++;
            logger.warn("Authentication failed for user: {} ({}/{})", username, loginAttempts, maxLoginAttempts);
            return false;
        }

        // Create SSO token
        SSOToken token = new SSOToken(username, ticket);

        if (token.validate()) {
            this.currentToken = token;
            this.currentUsername = username;
            this.isAuthenticated = true;
            this.loginAttempts = 0;
            logger.info("User authenticated successfully: {}", username);
            return true;
        }

        loginAttempts++;
        logger.error("Token validation failed for user: {}", username);
        return false;
    }

    /**
     * Generate authentication ticket
     * In production, this would call Habbo SSO service
     */
    private String generateTicket(String username, String password) {
        // Validate credentials
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            logger.warn("Invalid credentials provided");
            return null;
        }

        // Simulate ticket generation
        // In production: POST to https://sso.habbo.com/authenticate
        String ticketBase = username + ":" + System.currentTimeMillis() + ":verified";
        String ticket = java.util.Base64.getEncoder().encodeToString(ticketBase.getBytes());

        logger.debug("Ticket generated for user: {}", username);
        return ticket;
    }

    /**
     * Logout current user
     */
    public synchronized void logout() {
        if (currentToken != null) {
            currentToken.revoke();
        }
        currentToken = null;
        currentUsername = null;
        isAuthenticated = false;
        loginAttempts = 0;
        logger.info("User logged out");
    }

    /**
     * Refresh authentication token
     */
    public synchronized boolean refreshToken() {
        if (currentToken == null || !currentToken.isValid()) {
            logger.warn("Cannot refresh: no valid token");
            return false;
        }

        String newTicket = generateTicket(currentUsername, "refresh");
        if (currentToken.refresh(newTicket)) {
            logger.info("Token refreshed for user: {}", currentUsername);
            return true;
        }

        return false;
    }

    // Getters
    public boolean isAuthenticated() {
        return isAuthenticated && currentToken != null && currentToken.isValid();
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public SSOToken getCurrentToken() {
        return currentToken;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public int getRemainingLoginAttempts() {
        return Math.max(0, maxLoginAttempts - loginAttempts);
    }

    public String getAuthenticationStatus() {
        if (!isAuthenticated) {
            return "Not authenticated";
        }

        if (currentToken == null) {
            return "Invalid token";
        }

        if (!currentToken.isValid()) {
            return "Token expired";
        }

        long remaining = currentToken.getRemainingTime();
        long hours = remaining / (60 * 60 * 1000);
        long minutes = (remaining % (60 * 60 * 1000)) / (60 * 1000);

        return "Authenticated as: " + currentUsername + 
               " (Token expires in " + hours + "h " + minutes + "m)";
    }

    @Override
    public String toString() {
        return "AuthenticationManager{" +
                "authenticated=" + isAuthenticated +
                ", user='" + currentUsername + '\'' +
                ", attempts=" + loginAttempts + "/" + maxLoginAttempts +
                '}';
    }
}
