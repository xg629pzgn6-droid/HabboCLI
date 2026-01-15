package com.habbo.client.protocol;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Base protocol message class for Habbo server communication
 */
public abstract class ProtocolMessage {
    protected int messageId;
    protected byte[] messageData;

    public ProtocolMessage(int messageId) {
        this.messageId = messageId;
    }

    /**
     * Serialize the message to bytes
     */
    public abstract byte[] serialize() throws IOException;

    /**
     * Deserialize message from bytes
     */
    public abstract void deserialize(byte[] data) throws IOException;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageData(byte[] data) {
        this.messageData = data;
    }

    public byte[] getMessageData() {
        return messageData;
    }
}
