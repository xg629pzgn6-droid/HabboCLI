package com.habbo.client.protocol.messages;

import com.habbo.client.protocol.ProtocolMessage;
import com.habbo.client.protocol.ProtocolEncoder;
import com.habbo.client.protocol.ProtocolDecoder;
import java.io.IOException;

/**
 * Authentication request message for Habbo protocol
 */
public class AuthenticationMessage extends ProtocolMessage {
    private static final int MESSAGE_ID = 0x0001; // Login message ID
    private String username;
    private String ssoToken;
    private String clientVersion;
    private String clientIdentifier;

    public AuthenticationMessage() {
        super(MESSAGE_ID);
    }

    public AuthenticationMessage(String username, String ssoToken) {
        super(MESSAGE_ID);
        this.username = username;
        this.ssoToken = ssoToken;
        this.clientVersion = "1.0.0";
        this.clientIdentifier = "habbo-cli";
    }

    @Override
    public byte[] serialize() throws IOException {
        ProtocolEncoder encoder = new ProtocolEncoder();
        encoder.writeShort((short) MESSAGE_ID);
        encoder.writeString(username);
        encoder.writeString(ssoToken);
        encoder.writeString(clientVersion);
        encoder.writeString(clientIdentifier);
        return encoder.getBytes();
    }

    @Override
    public void deserialize(byte[] data) throws IOException {
        ProtocolDecoder decoder = new ProtocolDecoder(data);
        decoder.readShort(); // Skip message ID
        this.username = decoder.readString();
        this.ssoToken = decoder.readString();
        this.clientVersion = decoder.readString();
        this.clientIdentifier = decoder.readString();
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSsoToken() {
        return ssoToken;
    }

    public void setSsoToken(String ssoToken) {
        this.ssoToken = ssoToken;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    @Override
    public String toString() {
        return "AuthenticationMessage{" +
                "username='" + username + '\'' +
                ", messageId=" + MESSAGE_ID +
                ", clientVersion='" + clientVersion + '\'' +
                '}';
    }
}
