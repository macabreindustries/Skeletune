package com.example.skeletune.ui.common;

public class ChatMessage {
    private String message;
    private String timestamp;
    private boolean isSentByUser;

    public ChatMessage(String message, String timestamp, boolean isSentByUser) {
        this.message = message;
        this.timestamp = timestamp;
        this.isSentByUser = isSentByUser;
    }

    // Getters
    public String getMessage() { return message; }
    public String getTimestamp() { return timestamp; }
    public boolean isSentByUser() { return isSentByUser; }
}
