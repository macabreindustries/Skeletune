package org.example.demo.model.juego;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Media;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonBackReference; // IMPORTANTE

@Data
@NoArgsConstructor
@Entity
@Table(name = "NotaMania")
public class NotaMania {

    public enum Tipo {
        normal, hold, flick, rest
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota_mania")
    private Integer idNotaMania;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chart_mania", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference // <-- La otra mitad para evitar los bucles infinitos
    private ChartMania chartMania;

    @Column(name = "tiempo_ms", nullable = false)
    private Integer tiempoMs;

    @Column(name = "carril", nullable = false)
    private Byte carril; // 1..4

    @Column(name = "duracion_ms")
    private Integer duracionMs = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imagen_media_id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Media imagenMedia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo = Tipo.normal;
}
