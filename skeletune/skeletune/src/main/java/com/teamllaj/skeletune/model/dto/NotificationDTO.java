package com.teamllaj.skeletune.model.dto;

import lombok.Data;          // Incluye Getter y Setter
import lombok.NoArgsConstructor; // Constructor sin argumentos (Soluciona el error en el Service)
import lombok.AllArgsConstructor; // Constructor con todos los argumentos

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private String username;
    // Usamos String para Like, Comentario, Favoritos
    private String notificationType;
    private String message;
    // Campo para mostrar el tiempo transcurrido (e.g., "hace 5 minutos")
    private String duration;
}