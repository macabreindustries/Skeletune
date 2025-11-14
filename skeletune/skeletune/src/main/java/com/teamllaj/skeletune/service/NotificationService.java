package com.teamllaj.skeletune.service;

import com.teamllaj.skeletune.model.entity.NotificationType;
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

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NotificationDTO createNotification(NotificationEntity notification) {
        // CORRECCIÓN 1: Usar setFecha() en lugar de setCreatedAt()
        if (notification.getFecha() == null) {
            notification.setFecha(LocalDateTime.now());
        }

        // CORRECCIÓN 2: Usar el valor por defecto 'SISTEMA' si el tipo no está establecido.
        if (notification.getTipo() == null) {
            notification.setTipo(NotificationType.SISTEMA);
        }

        NotificationEntity savedEntity = notificationRepository.save(notification);
        return convertToDTO(savedEntity);
    }

    // El método PUT para marcar como leído (si lo tenías, también debes usar el nuevo ID)
    /*
    public NotificationDTO markAsRead(Long idNotificacion) {
        NotificationEntity notification = notificationRepository.findById(idNotificacion)
            .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        notification.setLeido(true);
        return convertToDTO(notificationRepository.save(notification));
    }
    */


    // --- Mapper (Conversión Entity a DTO) ---

    private NotificationDTO convertToDTO(NotificationEntity entity) {
        NotificationDTO dto = new NotificationDTO();

        // CORRECCIÓN 3: Usar los nuevos getters
        dto.setIdNotificacion(entity.getIdNotificacion());
        dto.setIdUsuario(entity.getIdUsuario()); // Corregido: antes era getUsername()
        dto.setTipo(entity.getTipo());          // Corregido: antes era getType()
        dto.setTitulo(entity.getTitulo());
        dto.setMensaje(entity.getMensaje());    // Corregido: antes era getMessage()
        dto.setIdReferencia(entity.getIdReferencia());
        dto.setTablaReferencia(entity.getTablaReferencia());
        dto.setLeido(entity.getLeido());

        // CORRECCIÓN 4: Usar getFecha() en el cálculo de duración
        dto.setDuration(calculateDuration(entity.getFecha()));

        return dto;
    }

    // El método calculateDuration sigue igual...
    private String calculateDuration(LocalDateTime pastTime) {
        if (pastTime == null) {
            return "hace un momento";
        }
        Duration duration = Duration.between(pastTime, LocalDateTime.now());

        if (duration.toDays() > 0) {
            return "hace " + duration.toDays() + (duration.toDays() == 1 ? " día" : " días");
        } else if (duration.toHours() > 0) {
            return "hace " + duration.toHours() + (duration.toHours() == 1 ? " hora" : " horas");
        } else if (duration.toMinutes() > 0) {
            return "hace " + duration.toMinutes() + (duration.toMinutes() == 1 ? " minuto" : " minutos");
        } else {
            return "hace un momento";
        }
    }
}