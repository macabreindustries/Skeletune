package org.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "Progreso")
@Data
@NoArgsConstructor
public class Progreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_progreso")
    private Integer idProgreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_leccion")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Leccion leccion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos = 0;

    @Lob
    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;
}
