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
@Table(name = "Media")
public class Media {

    public enum Tipo {
        foto,
        video,
        audio
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_media")
    private Integer idMedia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, columnDefinition = "ENUM('foto','video','audio')")
    private Tipo tipo;

    @Column(name = "url_archivo", nullable = false, length = 255)
    private String urlArchivo;

    @CreationTimestamp
    @Column(name = "fecha_subida", nullable = false, updatable = false)
    private LocalDateTime fechaSubida;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
}
