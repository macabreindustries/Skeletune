package com.teamllaj.skeletune.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

// HE QUITADO las siguientes líneas, ya que las clases deberían estar
// disponibles si están en el mismo paquete:
// import com.teamllaj.skeletune.model.entity.Usuario;
// import com.teamllaj.skeletune.model.entity.Media;


@Entity
@Table(name = "Mensaje") // 1. Coincide con el nombre de la tabla SQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje") // Mapea al nombre de la columna SQL
    private Long idMensaje;

    // 2. Relación con el emisor (Foreign Key: id_emisor)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emisor", nullable = false)
    private Usuario emisor;

    // 3. Relación con el receptor (Foreign Key: id_receptor)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receptor", nullable = false)
    private Usuario receptor;

    // 4. Contenido del mensaje
    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String mensaje;

    // 5. Media opcional (Foreign Key: id_media). ON DELETE SET NULL permite que sea nullable.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_media", nullable = true) // id_media puede ser NULL
    private Media media;

    // 6. Fecha de envío (coincide con fecha_envio)
    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio = LocalDateTime.now();

    // 7. Estado de lectura (visto BOOLEAN)
    @Column(name = "visto", nullable = false)
    private Boolean visto = false;


    // Constructor conveniente para la creación básica (sin media)
    public MessageEntity(Usuario emisor, Usuario receptor, String mensaje) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.mensaje = mensaje;
        this.fechaEnvio = LocalDateTime.now();
        this.visto = false;
    }
}