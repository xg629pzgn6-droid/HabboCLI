package com.habbo.client.auth;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthenticationTest {

    private AuthenticationManager authManager;

    @Before
    public void setUp() {
        authManager = new AuthenticationManager();
    }

    @Test
    public void testSuccessfulAuthentication() {
        assertTrue("Authentication should succeed", 
            authManager.authenticate("xiony", "password123"));
        assertTrue("Should be authenticated", authManager.isAuthenticated());
        assertEquals("Username should match", "xiony", authManager.getCurrentUsername());
    }

    @Test
    public void testTokenValidity() {
        authManager.authenticate("xiony", "password123");
        assertNotNull("Token should exist", authManager.getCurrentToken());
        assertTrue("Token should be valid", authManager.getCurrentToken().isValid());
    }

    @Test
    public void testLogout() {
        authManager.authenticate("testuser", "password");
        assertTrue("Should be authenticated before logout", authManager.isAuthenticated());
        
        authManager.logout();
        assertFalse("Should not be authenticated after logout", authManager.isAuthenticated());
        assertNull("Token should be null after logout", authManager.getCurrentToken());
    }

    @Test
    public void testInvalidCredentials() {
        assertFalse("Authentication should fail with empty username", 
            authManager.authenticate("", "password"));
        assertFalse("Authentication should fail with empty password", 
            authManager.authenticate("user", ""));
    }

    @Test
    public void testMaxLoginAttempts() {
        for (int i = 0; i < 3; i++) {
            authManager.authenticate("", "invalid");
        }
        assertFalse("Should fail after max attempts", 
            authManager.authenticate("user", "password"));
        assertEquals("Remaining attempts should be 0", 0, authManager.getRemainingLoginAttempts());
    }

    @Test
    public void testSSOToken() {
        authManager.authenticate("xiony", "password123");
        SSOToken token = authManager.getCurrentToken();
        
        assertNotNull("Token should exist", token);
        assertEquals("Username should match", "xiony", token.getUsername());
        assertTrue("Token should be valid", token.isValid());
        assertFalse("Token should not be expired", System.currentTimeMillis() > token.getExpiresAt());
    }

    @Test
    public void testTokenRefresh() {
        authManager.authenticate("xiony", "password123");
        long originalExpiry = authManager.getCurrentToken().getExpiresAt();
        
        try {
            Thread.sleep(100); // Small delay to ensure different timestamp
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue("Token refresh should succeed", authManager.refreshToken());
        long newExpiry = authManager.getCurrentToken().getExpiresAt();
        
        assertTrue("Expiry time should be updated after refresh", newExpiry > originalExpiry);
        assertTrue("Token should remain valid", authManager.getCurrentToken().isValid());
    }

    @Test
    public void testAuthenticationStatus() {
        String status = authManager.getAuthenticationStatus();
        assertEquals("Should show not authenticated", "Not authenticated", status);
        
        authManager.authenticate("xiony", "password123");
        String authStatus = authManager.getAuthenticationStatus();
        assertTrue("Should contain username", authStatus.contains("xiony"));
        assertTrue("Should show expiration", authStatus.contains("Token expires"));
    }
}
