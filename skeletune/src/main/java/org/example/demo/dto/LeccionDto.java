package org.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Leccion;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LeccionDto implements Serializable {

    private Integer idLeccion;
    private String titulo;
    private String descripcion;
    private Leccion.Tipo tipo;
    private Leccion.Nivel nivel;
    private Integer idCancion; // Solo el ID para la relación
    private Integer idVideo; // Solo el ID para la relación
}
