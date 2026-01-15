package com.habbo.client.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * Connection handler for Habbo server communication
 */
public class HabboConnection {
    private static final Logger logger = LoggerFactory.getLogger(HabboConnection.class);

    private String host;
    private int port;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private boolean connected;
    private ConnectionListener connectionListener;

    public HabboConnection(String host, int port) {
        this.host = host;
        this.port = port;
        this.connected = false;
    }

    /**
     * Connect to the Habbo server
     */
    public synchronized boolean connect() {
        try {
            socket = new Socket(host, port);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            connected = true;
            logger.info("Connected to {}:{}", host, port);

            // Start receiving thread
            startReceivingThread();

            if (connectionListener != null) {
                connectionListener.onConnected();
            }
            return true;
        } catch (IOException e) {
            logger.error("Failed to connect to {}:{}", host, port, e);
            connected = false;
            return false;
        }
    }

    /**
     * Disconnect from the server
     */
    public synchronized void disconnect() {
        try {
            connected = false;
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            logger.info("Disconnected from {}:{}", host, port);

            if (connectionListener != null) {
                connectionListener.onDisconnected();
            }
        } catch (IOException e) {
            logger.error("Error disconnecting from server", e);
        }
    }

    /**
     * Send data to the server
     */
    public synchronized boolean send(byte[] data) {
        if (!connected || outputStream == null) {
            logger.warn("Cannot send data: not connected");
            return false;
        }

        try {
            outputStream.writeInt(data.length);
            outputStream.write(data);
            outputStream.flush();
            return true;
        } catch (IOException e) {
            logger.error("Error sending data to server", e);
            connected = false;
            return false;
        }
    }

    /**
     * Start a thread to receive messages from the server
     */
    private void startReceivingThread() {
        Thread receiverThread = new Thread(() -> {
            try {
                while (connected) {
                    int messageLength = inputStream.readInt();
                    byte[] messageData = new byte[messageLength];
                    inputStream.readFully(messageData);

                    if (connectionListener != null) {
                        connectionListener.onMessageReceived(messageData);
                    }
                }
            } catch (IOException e) {
                if (connected) {
                    logger.error("Error receiving message from server", e);
                }
                connected = false;
                if (connectionListener != null) {
                    connectionListener.onDisconnected();
                }
            }
        });
        receiverThread.setName("HabboReceiver");
        receiverThread.setDaemon(true);
        receiverThread.start();
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnectionListener(ConnectionListener listener) {
        this.connectionListener = listener;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    /**
     * Interface for connection events
     */
    public interface ConnectionListener {
        void onConnected();
        void onDisconnected();
        void onMessageReceived(byte[] data);
        void onError(Exception e);
    }
}
