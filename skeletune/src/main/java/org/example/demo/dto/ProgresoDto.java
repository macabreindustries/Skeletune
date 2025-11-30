package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoDto {
    private Integer idProgreso;
    private Integer idUsuario;
    private Integer idLeccion;
    private LocalDate fecha;
    private int duracionMinutos;
    private String comentario;
}
