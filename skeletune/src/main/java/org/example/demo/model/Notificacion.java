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
@Table(name = "Notificacion")
public class Notificacion {

    public enum Tipo {
        sistema,
        validacion_rol,
        novedad,
        progreso,
        logro,
        mensaje
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, columnDefinition = "ENUM('sistema','validacion_rol','novedad','progreso','logro','mensaje') DEFAULT 'sistema'")
    private Tipo tipo;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "id_referencia")
    private Integer idReferencia;

    @Column(name = "tabla_referencia", length = 50)
    private String tablaReferencia;

    @CreationTimestamp
    @Column(name = "fecha", nullable = false, updatable = false)
    private LocalDateTime fecha;

    @Column(name = "leido", nullable = false)
    private boolean leido = false;
}
