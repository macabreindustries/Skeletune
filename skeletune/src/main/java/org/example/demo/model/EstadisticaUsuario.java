package org.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EstadisticaUsuario")
public class EstadisticaUsuario {

    public enum Nivel {
        principiante, intermedio, avanzado
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estadistica")
    private Integer idEstadistica;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "total_minutos_practica", columnDefinition = "INT DEFAULT 0")
    private int totalMinutosPractica = 0;

    @Column(name = "lecciones_completadas", columnDefinition = "INT DEFAULT 0")
    private int leccionesCompletadas = 0;

    @Column(name = "canciones_aprendidas", columnDefinition = "INT DEFAULT 0")
    private int cancionesAprendidas = 0;

    @Column(name = "racha_dias", columnDefinition = "INT DEFAULT 0")
    private int rachaDias = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_general", columnDefinition = "ENUM('principiante','intermedio','avanzado') DEFAULT 'principiante'")
    private Nivel nivelGeneral = Nivel.principiante;
}
