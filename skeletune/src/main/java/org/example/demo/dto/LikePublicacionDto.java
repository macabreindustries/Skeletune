package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikePublicacionDto {
    private Integer idLike;
    private Integer idUsuario;
    private Integer idPublicacion;
    private LocalDateTime fechaLike;
}
