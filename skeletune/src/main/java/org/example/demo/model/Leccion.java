package org.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Leccion")
public class Leccion {

    public enum Tipo {
        teoria, practica
    }

    public enum Nivel {
        principiante, intermedio, avanzado
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leccion")
    private Integer idLeccion;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo = Tipo.practica;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel", nullable = false)
    private Nivel nivel = Nivel.principiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cancion", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Cancion cancion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_video", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private VideoEducativo video;
}
