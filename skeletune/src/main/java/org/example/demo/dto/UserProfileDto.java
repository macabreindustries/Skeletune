package org.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserProfileDto {
    private String nombre;
    private String urlAvatar;
    private int siguiendoCount;
    private int seguidoresCount;
    private int totalLikesRecibidos;
    private List<PublicacionFeedDto> misPublicaciones;
}