package com.habbo.client.protocol.messages;

import com.habbo.client.protocol.ProtocolMessage;
import com.habbo.client.protocol.ProtocolDecoder;
import com.habbo.client.protocol.ProtocolEncoder;
import java.io.IOException;

/**
 * Server response to authentication request
 */
public class AuthenticationResponseMessage extends ProtocolMessage {
    private static final int MESSAGE_ID = 0x0002;
    
    public enum AuthStatus {
        SUCCESS(0, "Authentication successful"),
        INVALID_CREDENTIALS(1, "Invalid username or password"),
        TOKEN_INVALID(2, "SSO token is invalid"),
        TOKEN_EXPIRED(3, "SSO token has expired"),
        USER_BANNED(4, "User account is banned"),
        USER_LOCKED(5, "User account is locked"),
        SERVER_ERROR(6, "Server authentication error");

        private final int code;
        private final String message;

        AuthStatus(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public static AuthStatus fromCode(int code) {
            for (AuthStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            return SERVER_ERROR;
        }
    }

    private AuthStatus status;
    private int userId;
    private String sessionToken;
    private String authMessage;

    public AuthenticationResponseMessage() {
        super(MESSAGE_ID);
    }

    @Override
    public byte[] serialize() throws IOException {
        ProtocolEncoder encoder = new ProtocolEncoder();
        encoder.writeShort((short) MESSAGE_ID);
        encoder.writeByte((byte) status.getCode());
        encoder.writeInt(userId);
        encoder.writeString(sessionToken);
        encoder.writeString(authMessage);
        return encoder.getBytes();
    }

    @Override
    public void deserialize(byte[] data) throws IOException {
        ProtocolDecoder decoder = new ProtocolDecoder(data);
        decoder.readShort(); // Skip message ID
        this.status = AuthStatus.fromCode(decoder.readByte());
        this.userId = decoder.readInt();
        this.sessionToken = decoder.readString();
        this.authMessage = decoder.readString();
    }

    // Getters and Setters
    public AuthStatus getStatus() {
        return status;
    }

    public void setStatus(AuthStatus status) {
        this.status = status;
    }

    public boolean isSuccessful() {
        return status == AuthStatus.SUCCESS;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getAuthMessage() {
        return authMessage;
    }

    public void setAuthMessage(String authMessage) {
        this.authMessage = authMessage;
    }

    @Override
    public String toString() {
        return "AuthenticationResponseMessage{" +
                "status=" + status.name() + " (" + status.getMessage() + ")" +
                ", userId=" + userId +
                '}';
    }
}
