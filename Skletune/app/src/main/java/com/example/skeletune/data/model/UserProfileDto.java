package com.example.skeletune.data.model;

import java.util.List;

public class UserProfileDto {

    private String nombre;
    private String urlAvatar;
    private int siguiendoCount;
    private int seguidoresCount;
    private int totalLikesRecibidos;
    private List<PublicacionFeedDto> misPublicaciones;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public int getSiguiendoCount() {
        return siguiendoCount;
    }

    public void setSiguiendoCount(int siguiendoCount) {
        this.siguiendoCount = siguiendoCount;
    }

    public int getSeguidoresCount() {
        return seguidoresCount;
    }

    public void setSeguidoresCount(int seguidoresCount) {
        this.seguidoresCount = seguidoresCount;
    }

    public int getTotalLikesRecibidos() {
        return totalLikesRecibidos;
    }

    public void setTotalLikesRecibidos(int totalLikesRecibidos) {
        this.totalLikesRecibidos = totalLikesRecibidos;
    }

    public List<PublicacionFeedDto> getMisPublicaciones() {
        return misPublicaciones;
    }

    public void setMisPublicaciones(List<PublicacionFeedDto> misPublicaciones) {
        this.misPublicaciones = misPublicaciones;
    }
}
