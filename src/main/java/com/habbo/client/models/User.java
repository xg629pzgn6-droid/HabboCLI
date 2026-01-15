package com.habbo.client.models;

/**
 * Represents a Habbo user/player
 */
public class User {
    private int userId;
    private String username;
    private String motto;
    private int level;
    private boolean isAdmin;

    public User(int userId, String username) {
        this.userId = userId;
        this.username = username;
        this.level = 1;
        this.isAdmin = false;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", motto='" + motto + '\'' +
                ", level=" + level +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
