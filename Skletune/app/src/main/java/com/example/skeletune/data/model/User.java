package com.example.skeletune.data.model;

import java.io.Serializable;

// Se implementa Serializable para poder pasar objetos User entre actividades
public class User implements Serializable {

    private int id;
    private String username;
    private String avatarUrl;

    public User(int id, String username, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
