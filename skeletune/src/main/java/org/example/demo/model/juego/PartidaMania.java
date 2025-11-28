package org.example.demo.model.juego;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal; // Importar BigDecimal
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PartidaMania")
public class PartidaMania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partida_mania")
    private Integer idPartidaMania;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chart_mania", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChartMania chartMania;

    @CreationTimestamp
    @Column(name = "fecha", updatable = false)
    private LocalDateTime fecha;

    @Column(name = "puntaje", nullable = false)
    private Integer puntaje;

    @Column(name = "accuracy", nullable = false, precision = 5, scale = 2)
    private BigDecimal accuracy; // Cambiado de Double a BigDecimal

    @Column(name = "combo_max", nullable = false)
    private Integer comboMax;

    @Column(name = "perfects")
    private Integer perfects = 0;

    @Column(name = "greats")
    private Integer greats = 0;

    @Column(name = "goods")
    private Integer goods = 0;

    @Column(name = "misses")
    private Integer misses = 0;

    @Column(name = "detalles", columnDefinition = "JSON")
    private String detalles; // JSON NULL
}
