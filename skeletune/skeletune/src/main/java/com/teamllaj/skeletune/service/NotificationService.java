// src/main/java/com/teamllaj/skeletune/service/NotificationService.java

package com.teamllaj.skeletune.service;

import com.teamllaj.skeletune.model.entity.NotificationEntity;
import com.teamllaj.skeletune.model.dto.NotificationDTO;
import com.teamllaj.skeletune.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // 1. MÉTODO FALTANTE #1: Obtener todas las notificaciones
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 2. MÉTODO FALTANTE #2: Crear una nueva notificación
    public NotificationDTO createNotification(NotificationEntity notification) {
        // Establece la hora de creación antes de guardar
        notification.setCreatedAt(LocalDateTime.now());
        NotificationEntity savedEntity = notificationRepository.save(notification);
        return convertToDTO(savedEntity);
    }

    // Método privado para convertir Entity a DTO y calcular la duración
    private NotificationDTO convertToDTO(NotificationEntity entity) {
        NotificationDTO dto = new NotificationDTO();
        dto.setUsername(entity.getUsername());
        dto.setNotificationType(entity.getType().name());
        dto.setMessage(entity.getMessage());

        // CÁLCULO DE DURACIÓN
        dto.setDuration(calculateDuration(entity.getCreatedAt()));

        return dto;
    }

    // Función para calcular y formatear la "duración" (tiempo transcurrido)
    private String calculateDuration(LocalDateTime pastTime) {
        Duration duration = Duration.between(pastTime, LocalDateTime.now());

        if (duration.toDays() > 0) {
            return "hace " + duration.toDays() + " días";
        } else if (duration.toHours() > 0) {
            return "hace " + duration.toHours() + " horas";
        } else if (duration.toMinutes() > 0) {
            return "hace " + duration.toMinutes() + " minutos";
        } else {
            return "hace un momento";
        }
    }
}