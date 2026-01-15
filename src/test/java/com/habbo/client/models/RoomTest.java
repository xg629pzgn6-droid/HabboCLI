package com.habbo.client.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {
    private Room room;

    @Before
    public void setUp() {
        room = new Room(1, "Test Room");
    }

    @Test
    public void testRoomCreation() {
        assertEquals(1, room.getRoomId());
        assertEquals("Test Room", room.getRoomName());
        assertEquals(25, room.getMaxUsers());
    }

    @Test
    public void testRoomProperties() {
        room.setRoomDescription("A test room");
        room.setOwner("TestUser");
        room.setCurrentUsers(5);
        room.setPublic(false);

        assertEquals("A test room", room.getRoomDescription());
        assertEquals("TestUser", room.getOwner());
        assertEquals(5, room.getCurrentUsers());
        assertFalse(room.isPublic());
    }

    @Test
    public void testRoomToString() {
        room.setOwner("TestUser");
        room.setCurrentUsers(3);
        String result = room.toString();
        assertTrue(result.contains("Test Room"));
        assertTrue(result.contains("TestUser"));
    }
}
