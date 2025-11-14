package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Novedad;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NovedadDto {

    private Integer idNovedad;
    private Integer idAdmin;
    private String titulo;
    private String contenido;
    private String imagenUrl;
    private LocalDateTime fechaPublicacion;
    private Novedad.Importancia importancia;

    public NovedadDto(Novedad novedad) {
        this.idNovedad = novedad.getIdNovedad();
        this.idAdmin = novedad.getAdmin().getId();
        this.titulo = novedad.getTitulo();
        this.contenido = novedad.getContenido();
        this.imagenUrl = novedad.getImagenUrl();
        this.fechaPublicacion = novedad.getFechaPublicacion();
        this.importancia = novedad.getImportancia();
    }
}
