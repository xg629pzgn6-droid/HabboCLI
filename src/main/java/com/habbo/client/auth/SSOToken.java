package com.habbo.client.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SSO Token manager for Habbo authentication
 * Handles Single Sign-On token generation and validation
 */
public class SSOToken {
    private static final Logger logger = LoggerFactory.getLogger(SSOToken.class);

    private String token;
    private String username;
    private int userId;
    private long issuedAt;
    private long expiresAt;
    private boolean isValid;

    public SSOToken(String username, String ticket) {
        this.username = username;
        this.issuedAt = System.currentTimeMillis();
        this.expiresAt = issuedAt + (24 * 60 * 60 * 1000); // 24 hours
        this.token = generateToken(username, ticket);
        this.isValid = true;
        logger.debug("SSO Token created for user: {}", username);
    }

    /**
     * Generate SSO token from username and ticket
     * In real scenario, this would be obtained from Habbo SSO server
     */
    private String generateToken(String username, String ticket) {
        // Simulated token generation
        // In production, this would call Habbo's SSO service
        String tokenBase = username + ":" + ticket + ":" + System.currentTimeMillis();
        String encodedToken = java.util.Base64.getEncoder().encodeToString(tokenBase.getBytes());
        logger.info("Generated SSO token for user: {}", username);
        return encodedToken;
    }

    /**
     * Validate token against server
     */
    public synchronized boolean validate() {
        if (!isValid) {
            logger.warn("Token validation failed: token is invalid");
            return false;
        }

        if (System.currentTimeMillis() > expiresAt) {
            logger.warn("Token expired for user: {}", username);
            isValid = false;
            return false;
        }

        logger.debug("Token validated successfully for user: {}", username);
        return true;
    }

    /**
     * Refresh the SSO token
     */
    public synchronized boolean refresh(String newTicket) {
        if (!isValid) {
            logger.warn("Cannot refresh invalid token");
            return false;
        }

        this.token = generateToken(username, newTicket);
        this.issuedAt = System.currentTimeMillis();
        this.expiresAt = issuedAt + (24 * 60 * 60 * 1000);
        logger.info("Token refreshed for user: {}", username);
        return true;
    }

    /**
     * Revoke the token
     */
    public synchronized void revoke() {
        this.isValid = false;
        logger.info("Token revoked for user: {}", username);
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public boolean isValid() {
        return isValid && System.currentTimeMillis() <= expiresAt;
    }

    public long getRemainingTime() {
        return Math.max(0, expiresAt - System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "SSOToken{" +
                "username='" + username + '\'' +
                ", userId=" + userId +
                ", isValid=" + isValid() +
                ", remainingTime=" + (getRemainingTime() / 1000) + "s" +
                '}';
    }
}
