package com.example.skeletune.data.model;

public class ComentarioResponseDto {
    private String nombreUsuario;
    private String avatarUrl;
    private String comentario;
    private String fechaRelativa; //

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFechaRelativa() {
        return fechaRelativa;
    }

    public void setFechaRelativa(String fechaRelativa) {
        this.fechaRelativa = fechaRelativa;
    }
}
