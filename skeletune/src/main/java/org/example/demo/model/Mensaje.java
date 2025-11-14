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
@Table(name = "Mensaje")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Integer idMensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emisor", nullable = false)
    private Usuario emisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receptor", nullable = false)
    private Usuario receptor;

    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_media")
    private Media media;

    @CreationTimestamp
    @Column(name = "fecha_envio", nullable = false, updatable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "visto", nullable = false)
    private boolean visto = false;
}
