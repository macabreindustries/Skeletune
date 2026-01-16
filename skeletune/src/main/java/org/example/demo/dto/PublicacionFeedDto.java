package org.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class PublicacionFeedDto {
    private Integer idPublicacion;
    private String nombreUsuario;
    private String avatarUrl;
    private String tiempoPublicacion; // "Hace 5 min"
    private String textoBody;
    private String imageUrlContent;
    private int likesCount;
    private int commentsCount;
    private boolean isLikedByMe;
}