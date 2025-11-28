package org.example.demo.dto.juego;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.juego.ChartMania;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List; // Importar List

@Data
@NoArgsConstructor
public class ChartManiaDto implements Serializable {

    private Integer idChartMania;
    private Integer idCancion; // Solo el ID para la relación
    private ChartMania.Dificultad dificultad;
    private Float speedMultiplier;
    private Byte numPistas;
    private Integer createdBy; // Solo el ID para la relación
    private LocalDateTime fechaCreacion;
    private List<NotaManiaDto> notas; // NUEVO CAMPO: Lista de notas
}
