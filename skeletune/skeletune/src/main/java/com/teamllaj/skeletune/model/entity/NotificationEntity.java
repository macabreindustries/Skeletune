package com.teamllaj.skeletune.model.entity;

import jakarta.persistence.*; // Usa javax.persistence.* si usas Spring Boot 2
import java.time.LocalDateTime;
import lombok.Data;          // Incluye Getter, Setter, ToString, etc.
import lombok.NoArgsConstructor; // Constructor sin argumentos
import lombok.AllArgsConstructor; // Constructor con todos los argumentos

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del usuario que generó la notificación
    private String username;

    // Tipo de notificación: "Like", "Comentario", "Favoritos"
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    // Contenido/mensaje de la notificación
    private String message;

    // Fecha y hora de creación para calcular la 'duración'
    private LocalDateTime createdAt;

    public enum NotificationType {
        Like, Comentario, Favoritos
    }
}