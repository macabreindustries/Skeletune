package org.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Cancion;

@Data
@NoArgsConstructor
public class CancionDto {

    private Integer idCancion;
    private String titulo;
    private String artista;
    private Cancion.Dificultad dificultad;
    private String urlAudio;
    private String urlPartitura;
    private Integer idAdmin;

}