package org.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "Videollamada")
@Data
@NoArgsConstructor
public class Videollamada {

    public enum Estado {
        perdida, contestada, cancelada
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_videollamada")
    private Integer idVideollamada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emisor", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario emisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receptor", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario receptor;

    @CreationTimestamp
    @Column(name = "fecha_inicio", updatable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado = Estado.perdida;
}