package org.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "Cancion")
@Data
@NoArgsConstructor
public class Cancion {

    public enum Dificultad {
        facil, media, dificil
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancion")
    private Integer idCancion;

    @Column(name = "titulo", nullable = false, length = 150, unique = true)
    private String titulo;

    @Column(name = "artista", length = 100)
    private String artista;

    @Enumerated(EnumType.STRING)
    @Column(name = "dificultad")
    private Dificultad dificultad = Dificultad.media;

    @Column(name = "url_audio", length = 255)
    private String urlAudio;

    @Column(name = "url_partitura", length = 255)
    private String urlPartitura;

    @CreationTimestamp
    @Column(name = "fecha_subida", updatable = false)
    private LocalDateTime fechaSubida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_admin", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario admin;

}