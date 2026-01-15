package com.habbo.client.models;

/**
 * Represents a Habbo room
 */
public class Room {
    private int roomId;
    private String roomName;
    private String roomDescription;
    private int maxUsers;
    private int currentUsers;
    private String owner;
    private boolean isPublic;

    public Room(int roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxUsers = 25;
        this.currentUsers = 0;
        this.isPublic = true;
    }

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public int getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(int currentUsers) {
        this.currentUsers = currentUsers;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", owner='" + owner + '\'' +
                ", currentUsers=" + currentUsers +
                "/" + maxUsers +
                ", isPublic=" + isPublic +
                '}';
    }
}
