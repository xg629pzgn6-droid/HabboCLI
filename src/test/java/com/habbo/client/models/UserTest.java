package com.habbo.client.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User(1, "TestUser");
    }

    @Test
    public void testUserCreation() {
        assertEquals(1, user.getUserId());
        assertEquals("TestUser", user.getUsername());
        assertEquals(1, user.getLevel());
        assertFalse(user.isAdmin());
    }

    @Test
    public void testUserProperties() {
        user.setMotto("Hello World");
        user.setLevel(10);
        user.setAdmin(true);

        assertEquals("Hello World", user.getMotto());
        assertEquals(10, user.getLevel());
        assertTrue(user.isAdmin());
    }

    @Test
    public void testUserToString() {
        user.setMotto("Test");
        String result = user.toString();
        assertTrue(result.contains("TestUser"));
        assertTrue(result.contains("Test"));
    }
}
