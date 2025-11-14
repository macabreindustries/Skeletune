package com.teamllaj.skeletune.service;

import com.teamllaj.skeletune.model.entity.MessageEntity;
import com.teamllaj.skeletune.model.dto.MessageDTO;
import com.teamllaj.skeletune.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    // Inyección de dependencias por constructor (recomendado)
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Obtiene todos los mensajes y los convierte a DTOs.
     */
    public List<MessageDTO> getAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(this::convertToDTO) // Usa el mapeador
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los mensajes de un usuario específico.
     */
    public List<MessageDTO> getMessagesByUsername(String username) {
        return messageRepository.findByUsernameOrderBySentAtDesc(username)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Guarda una nueva entidad de mensaje y devuelve el DTO.
     */
    public MessageDTO createMessage(MessageEntity message) {
        // Asegura que la fecha esté establecida (aunque la entidad ya lo hace por defecto)
        if (message.getSentAt() == null) {
            message.setSentAt(LocalDateTime.now());
        }
        MessageEntity savedEntity = messageRepository.save(message);
        return convertToDTO(savedEntity);
    }

    // --- Métodos Privados de Ayuda ---

    /**
     * Convierte una Entidad (de BD) a un DTO (para el cliente).
     */
    private MessageDTO convertToDTO(MessageEntity entity) {
        MessageDTO dto = new MessageDTO();
        dto.setUsername(entity.getUsername());
        dto.setMessageContent(entity.getMessageContent());
        dto.setDuration(calculateDuration(entity.getSentAt())); // Calcula el tiempo transcurrido
        return dto;
    }

    /**
     * Calcula la cadena de tiempo transcurrido (ej. "hace 5 minutos").
     * (Copiado de NotificationService para mantener consistencia)
     */
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