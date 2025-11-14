package org.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Novedad")
public class Novedad {

    public enum Importancia {
        alta,
        media,
        baja
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_novedad")
    private Integer idNovedad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_admin", nullable = false)
    private Usuario admin;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "contenido", nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    @CreationTimestamp
    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private LocalDateTime fechaPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "importancia", nullable = false, columnDefinition = "ENUM('alta','media','baja') DEFAULT 'media'")
    private Importancia importancia;
}
