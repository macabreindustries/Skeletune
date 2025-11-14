package com.teamllaj.skeletune.model.dto;

import com.teamllaj.skeletune.model.entity.NotificationType;
import lombok.Data;

@Data
public class NotificationDTO {
    // Renombramos el ID y el usuario
    private Long idNotificacion;
    private Integer idUsuario;

    private NotificationType tipo;
    private String titulo;
    private String mensaje;
    private Integer idReferencia;
    private String tablaReferencia;
    private String duration; // Para la fecha
    private Boolean leido;
}