package com.habbo.client.protocol;

import java.io.*;

/**
 * Protocol decoder for parsing incoming Habbo protocol messages
 */
public class ProtocolDecoder {
    private ByteArrayInputStream buffer;
    private DataInputStream dataStream;

    public ProtocolDecoder(byte[] data) {
        this.buffer = new ByteArrayInputStream(data);
        this.dataStream = new DataInputStream(buffer);
    }

    public int readInt() throws IOException {
        return dataStream.readInt();
    }

    public short readShort() throws IOException {
        return dataStream.readShort();
    }

    public byte readByte() throws IOException {
        return dataStream.readByte();
    }

    public String readString() throws IOException {
        int length = dataStream.readShort();
        if (length == 0) {
            return "";
        }
        byte[] stringBytes = new byte[length];
        dataStream.readFully(stringBytes);
        return new String(stringBytes, "UTF-8");
    }

    public byte[] readBytes(int length) throws IOException {
        byte[] data = new byte[length];
        dataStream.readFully(data);
        return data;
    }

    public byte[] getRemainingBytes() throws IOException {
        return dataStream.readAllBytes();
    }

    public boolean hasMoreData() {
        try {
            return buffer.available() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
