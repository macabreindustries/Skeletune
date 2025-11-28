package org.example.demo.dto.juego;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.juego.NotaMania;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class NotaManiaDto implements Serializable {

    private Integer idNotaMania;
    private Integer idChartMania; // Solo el ID para la relación
    private Integer tiempoMs;
    private Byte carril;
    private Integer duracionMs;
    private Integer imagenMediaId; // Solo el ID para la relación
    private NotaMania.Tipo tipo;
}
