package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Leccion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeccionDto {
    private Integer idLeccion;
    private String titulo;
    private String descripcion;
    private Leccion.Tipo tipo;
    private Leccion.Nivel nivel;
    private Integer idCancion;
    private Integer idVideo;
}
