package org.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Videollamada;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VideollamadaDto {

    private Integer idVideollamada;
    private Integer idEmisor;
    private Integer idReceptor;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Videollamada.Estado estado;
}