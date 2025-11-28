package org.example.demo.model.juego;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@NoArgsConstructor
@Entity
@Table(name = "FalloMania")
public class FalloMania {

    public enum Tipo {
        early, late, miss
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fallo_mania")
    private Integer idFalloMania;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_partida_mania", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PartidaMania partidaMania;

    @Column(name = "tiempo_ms", nullable = false)
    private Integer tiempoMs;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo = Tipo.miss;

    @Column(name = "desviacion_ms")
    private Integer desviacionMs;
}
