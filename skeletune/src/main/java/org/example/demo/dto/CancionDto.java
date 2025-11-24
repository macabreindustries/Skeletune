package org.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Cancion;

import java.io.Serializable; // Añadir Serializable

@Data
@NoArgsConstructor
public class CancionDto implements Serializable { // Implementar Serializable

    private Integer idCancion;
    private String titulo;
    private String artista;
    private Cancion.Dificultad dificultad;
    private String urlAudio;
    private String urlPartitura;
    private String imagenUrl; // NUEVO CAMPO
    private Integer idAdmin;

}
