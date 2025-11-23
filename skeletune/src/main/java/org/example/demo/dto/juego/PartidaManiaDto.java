package org.example.demo.dto.juego;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal; // Importar BigDecimal
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PartidaManiaDto implements Serializable {

    private Integer idPartidaMania;
    private Integer idUsuario; // Solo el ID para la relación
    private Integer idChartMania; // Solo el ID para la relación
    private LocalDateTime fecha;
    private Integer puntaje;
    private BigDecimal accuracy; // Cambiado de Double a BigDecimal
    private Integer comboMax;
    private Integer perfects;
    private Integer greats;
    private Integer goods;
    private Integer misses;
    private String detalles; // Representing JSON as String
}
