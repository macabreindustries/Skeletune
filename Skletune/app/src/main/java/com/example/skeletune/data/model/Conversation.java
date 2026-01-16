package com.example.skeletune.data.model;

public class Conversation {

    private final User otherUser;
    private final Mensaje lastMessage;

    public Conversation(User otherUser, Mensaje lastMessage) {
        this.otherUser = otherUser;
        this.lastMessage = lastMessage;
    }

    public User getOtherUser() {
        return otherUser;
    }

    public Mensaje getLastMessage() {
        return lastMessage;
    }
}
    