package com.teamllaj.skeletune.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages") // La tabla se llamará 'messages'
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity { // Nombre de clase ajustado

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de usuario que envía el mensaje
    @Column(name = "username", nullable = false)
    private String username;

    // El contenido del mensaje (el mensaje en sí)
    @Column(name = "message_content", nullable = false, columnDefinition = "TEXT")
    private String messageContent;

    // Fecha y hora en que se creó el mensaje (el campo "fecha")
    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt = LocalDateTime.now();

    // Constructor para crear nuevos mensajes rápidamente
    public MessageEntity(String username, String messageContent) {
        this.username = username;
        this.messageContent = messageContent;
        this.sentAt = LocalDateTime.now();
    }
}