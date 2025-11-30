package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.EstadisticaUsuario;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaUsuarioDto {
    private Integer idEstadistica;
    private Integer idUsuario;
    private LocalDateTime fechaActualizacion;
    private int totalMinutosPractica;
    private int leccionesCompletadas;
    private int cancionesAprendidas;
    private int rachaDias;
    private EstadisticaUsuario.Nivel nivelGeneral;
}
