package com.teamllaj.skeletune.model.entity;

import jakarta.persistence.*; // Usa javax.persistence.* si usas Spring Boot 2
import java.time.LocalDateTime;
import lombok.Data;          // Incluye Getter, Setter, ToString, etc.
import lombok.NoArgsConstructor; // Constructor sin argumentos
import lombok.AllArgsConstructor; // Constructor con todos los argumentos

@Entity
@Table(name = "Notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion") // <--- Coincide con el nombre de tu PK
    private Long idNotificacion;

    @Column(name = "id_usuario", nullable = false) // <--- Columna id_usuario
    private Integer idUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", columnDefinition = "ENUM('sistema', 'validacion_rol', 'novedad', 'progreso', 'logro', 'mensaje') DEFAULT 'sistema'")
    private NotificationType tipo = NotificationType.SISTEMA; // Valor por defecto

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "mensaje", columnDefinition = "TEXT", nullable = false)
    private String mensaje;

    @Column(name = "id_referencia", nullable = true)
    private Integer idReferencia;

    @Column(name = "tabla_referencia", length = 50, nullable = true)
    private String tablaReferencia;

    @Column(name = "fecha", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "leido", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean leido = false;

    // NOTA: El FOREIGN KEY se gestiona automáticamente si usas otra entidad de Usuario,
    // pero aquí lo dejamos como un simple Integer para no depender de la clase Usuario ahora mismo.

}