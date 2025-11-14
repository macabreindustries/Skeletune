package com.teamllaj.skeletune.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Media") // Mapea a la tabla 'Media' en tu base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_media")
    private Long idMedia;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "tipo_contenido")
    private String tipoContenido; // Ejemplo: "image/jpeg", "video/mp4"

    // (Opcional) Si la tabla Media tiene una clave foránea al usuario que la subió
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "id_usuario_propietario")
    // private Usuario propietario;
}