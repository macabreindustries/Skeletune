package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDto {
    private Integer idComentario;
    private Integer idPublicacion;
    private Integer idUsuario;
    private String comentario;
    private LocalDateTime fechaComentario;
}
