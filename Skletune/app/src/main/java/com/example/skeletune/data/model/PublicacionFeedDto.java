package com.example.skeletune.data.model;

public class PublicacionFeedDto {
    private Integer idPublicacion;
    private String nombreUsuario;
    private String avatarUrl;
    private String tiempoPublicacion;
    private String textoBody;
    private String imageUrlContent;
    private int likesCount;
    private int commentsCount;
    private boolean isLikedByMe;

    public PublicacionFeedDto(Integer idPublicacion, String nombreUsuario, String avatarUrl, String tiempoPublicacion, String textoBody, String imageUrlContent, int likesCount, int commentsCount, boolean isLikedByMe) {
        this.idPublicacion = idPublicacion;
        this.nombreUsuario = nombreUsuario;
        this.avatarUrl = avatarUrl;
        this.tiempoPublicacion = tiempoPublicacion;
        this.textoBody = textoBody;
        this.imageUrlContent = imageUrlContent;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.isLikedByMe = isLikedByMe;
    }

    public Integer getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

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

    public String getTiempoPublicacion() {
        return tiempoPublicacion;
    }

    public void setTiempoPublicacion(String tiempoPublicacion) {
        this.tiempoPublicacion = tiempoPublicacion;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getImageUrlContent() {
        return imageUrlContent;
    }

    public void setImageUrlContent(String imageUrlContent) {
        this.imageUrlContent = imageUrlContent;
    }

    public String getTextoBody() {
        return textoBody;
    }

    public void setTextoBody(String textoBody) {
        this.textoBody = textoBody;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public boolean isLikedByMe() {
        return isLikedByMe;
    }

    public void setLikedByMe(boolean likedByMe) {
        isLikedByMe = likedByMe;
    }


    // Getters y Setters
    // ...
}