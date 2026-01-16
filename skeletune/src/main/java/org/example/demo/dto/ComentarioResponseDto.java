package org.example.demo.dto;

import lombok.Data;

@Data // Esto genera automáticamente los setComentario, setNombreUsuario, etc.
public class ComentarioResponseDto {
    private String nombreUsuario;
    private String avatarUrl;
    private String comentario;
    private String fechaRelativa;
}