package org.example.demo.model.juego;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Cancion;
import org.example.demo.model.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonManagedReference; // IMPORTANTE

import java.time.LocalDateTime;
import java.util.ArrayList; // Importar ArrayList
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ChartMania")
public class ChartMania {

    public enum Dificultad {
        facil, media, dificil, experto
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chart_mania")
    private Integer idChartMania;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cancion", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cancion cancion;

    @Enumerated(EnumType.STRING)
    @Column(name = "dificultad", nullable = false)
    private Dificultad dificultad = Dificultad.media;

    @Column(name = "speed_multiplier", nullable = false)
    private Float speedMultiplier = 1.0f;

    @Column(name = "num_pistas", nullable = false)
    private Byte numPistas = 4; // 4K fijo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Usuario createdBy;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(
        mappedBy = "chartMania",
        cascade = CascadeType.ALL, // <-- LA LÍNEA MÁGICA
        orphanRemoval = true,      // <-- Si borras una nota de la lista, se borra de la BD
        fetch = FetchType.LAZY // Puedes ajustar a EAGER si siempre necesitas las notas
    )
    @JsonManagedReference // <-- Evita bucles infinitos cuando el servidor responde
    private List<NotaMania> notas = new ArrayList<>(); // Inicializar para evitar NullPointerException

    // Asegúrate de que tienes un método para "setear" las notas que también actualice la relación inversa
    public void setNotas(List<NotaMania> notas) {
        this.notas.clear(); // Limpiar la lista existente
        if (notas != null) {
            for (NotaMania nota : notas) {
                nota.setChartMania(this); // <-- Esto asegura la consistencia de la relación
                this.notas.add(nota);
            }
        }
    }
}
