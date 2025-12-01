package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Media;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDto {
    private Integer idMedia;
    private Integer idUsuario;
    private Media.Tipo tipo;
    private String urlArchivo;
    private LocalDateTime fechaSubida;
    private String descripcion;
}
