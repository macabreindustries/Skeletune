package org.example.demo.dto.juego;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.juego.FalloMania;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FalloManiaDto implements Serializable {

    private Integer idFalloMania;
    private Integer idPartidaMania; // Solo el ID para la relación
    private Integer tiempoMs;
    private FalloMania.Tipo tipo;
    private Integer desviacionMs;
}
