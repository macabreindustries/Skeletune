package org.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VideoEducativoDto implements Serializable {

    private Integer idVideo;
    private Integer idProfesor; // Solo el ID para la relación
    private String titulo;
    private String descripcion;
    private String urlVideo;
    private Integer idThumbnailMedia; // Solo el ID para la relación
    private LocalDateTime fechaSubida;
}
