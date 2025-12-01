package org.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProgresoDto implements Serializable {

    private Integer idProgreso;
    private Integer idUsuario;
    private Integer idLeccion;
    private LocalDate fecha;
    private Integer duracionMinutos;
    private String comentario;
}
