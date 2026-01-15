package com.habbo.client.protocol;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Protocol encoder for converting messages to Habbo protocol format
 */
public class ProtocolEncoder {
    private ByteArrayOutputStream buffer;
    private DataOutputStream dataStream;

    public ProtocolEncoder() {
        this.buffer = new ByteArrayOutputStream();
        this.dataStream = new DataOutputStream(buffer);
    }

    public void writeInt(int value) throws IOException {
        dataStream.writeInt(value);
    }

    public void writeShort(short value) throws IOException {
        dataStream.writeShort(value);
    }

    public void writeByte(byte value) throws IOException {
        dataStream.writeByte(value);
    }

    public void writeString(String value) throws IOException {
        if (value == null) {
            dataStream.writeShort(0);
        } else {
            byte[] bytes = value.getBytes("UTF-8");
            dataStream.writeShort(bytes.length);
            dataStream.write(bytes);
        }
    }

    public void writeBytes(byte[] data) throws IOException {
        dataStream.write(data);
    }

    public byte[] getBytes() throws IOException {
        dataStream.flush();
        return buffer.toByteArray();
    }

    public void reset() {
        buffer = new ByteArrayOutputStream();
        dataStream = new DataOutputStream(buffer);
    }
}
