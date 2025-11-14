package com.teamllaj.skeletune.service;

import com.teamllaj.skeletune.model.entity.MessageEntity;
import com.teamllaj.skeletune.model.entity.Usuario;
import com.teamllaj.skeletune.model.dto.MessageDTO;
import com.teamllaj.skeletune.repository.MessageRepository;
import com.teamllaj.skeletune.repository.UsuarioRepository; // Nueva dependencia

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UsuarioRepository usuarioRepository; // Inyección de UsuarioRepository

    public MessageService(MessageRepository messageRepository, UsuarioRepository usuarioRepository) {
        this.messageRepository = messageRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene todos los mensajes y los convierte a DTOs.
     */
    public List<MessageDTO> getAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los mensajes de un usuario específico.
     * * NOTA: Este método requiere que tu repositorio defina un método para buscar
     * por el objeto 'emisor' o 'receptor', o que se use una consulta JPQL/nativa.
     * * Asumo que el método en el repositorio ahora busca por ID de usuario.
     */
    public List<MessageDTO> getMessagesByUserId(Long userId) {
        // En un chat real, buscarías mensajes donde userId sea emisor O receptor.
        // Aquí usamos findAll() y filtramos por simplicidad, pero se debe usar una consulta optimizada.
        return messageRepository.findAll().stream()
                .filter(m -> m.getEmisor().getIdUsuario().equals(userId) || m.getReceptor().getIdUsuario().equals(userId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    /**
     * Guarda una nueva entidad de mensaje y devuelve el DTO.
     */
    public MessageDTO createMessage(MessageEntity message) {
        // 1. Asignación de fecha corregida: Usar getFechaEnvio
        if (message.getFechaEnvio() == null) {
            message.setFechaEnvio(LocalDateTime.now());
        }

        // 2. Aquí deberías validar/obtener los objetos Usuario (emisor y receptor)
        // Se asume que el DTO o el MessageEntity entrante ya tiene los objetos Usuario
        // completos asignados. Si solo trae los IDs, se necesita una lógica de búsqueda:
        /*
        Usuario emisor = usuarioRepository.findById(message.getEmisor().getIdUsuario())
            .orElseThrow(() -> new ResourceNotFoundException("Emisor no encontrado"));
        message.setEmisor(emisor);

        Usuario receptor = usuarioRepository.findById(message.getReceptor().getIdUsuario())
            .orElseThrow(() -> new ResourceNotFoundException("Receptor no encontrado"));
        message.setReceptor(receptor);
        */


        MessageEntity savedEntity = messageRepository.save(message);
        return convertToDTO(savedEntity);
    }

    // --- Métodos Privados de Ayuda ---

    /**
     * Convierte una Entidad (de BD) a un DTO (para el cliente).
     */
    private MessageDTO convertToDTO(MessageEntity entity) {
        MessageDTO dto = new MessageDTO();

        // CORRECCIÓN 1: Asignar el ID o nombre de usuario del emisor.
        // No puedes asignar un objeto Usuario a un campo String (username).
        // Si tu DTO tiene un campo 'username' (String), obtén el nombre del objeto Usuario.
        // Si tu DTO tiene un campo 'idEmisor' (Long), usa entity.getEmisor().getIdUsuario().

        // ASUMO que el DTO ahora tiene un campo para el ID del Emisor (Long idEmisor)
        // y un campo para el contenido del mensaje (String mensaje).

        // **IMPORTANTE**: Si tu DTO sigue llamando al campo 'username', debes cambiarlo a 'idEmisor' o 'nombreEmisor'.
        // Aquí uso getEmisor().getIdUsuario().toString() como placeholder para 'username'.
        dto.setUsername(entity.getEmisor().getIdUsuario().toString());

        // CORRECCIÓN 2: Usar getMensaje()
        dto.setMessageContent(entity.getMensaje());

        // CORRECCIÓN 3: Pasar la fecha correcta (fechaEnvio) a calculateDuration
        dto.setDuration(calculateDuration(entity.getFechaEnvio()));

        return dto;
    }

    /**
     * Calcula la cadena de tiempo transcurrido (ej. "hace 5 minutos").
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
} // ¡Faltaba esta llave de cierre!