package com.teamllaj.skeletune.controller;
import com.teamllaj.skeletune.model.entity.MessageEntity; // Importación de la entidad renombrada
import com.teamllaj.skeletune.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    // Endpoint para obtener TODOS los mensajes
    @GetMapping
    public List<MessageEntity> getAllMessages() {
        return messageRepository.findAll();
    }

    // Endpoint para obtener mensajes filtrados por nombre de usuario
    @GetMapping("/user/{username}")
    public List<MessageEntity> getMessagesByUsername(@PathVariable String username) {
        return messageRepository.findByUsernameOrderBySentAtDesc(username);
    }

    // Endpoint para CREAR un nuevo mensaje
    @PostMapping
    public ResponseEntity<MessageEntity> createMessage(@RequestBody MessageEntity message) {
        // Asegurarse de que la fecha se establezca si no viene en el cuerpo
        if (message.getSentAt() == null) {
            message.setSentAt(java.time.LocalDateTime.now());
        }
        MessageEntity savedMessage = messageRepository.save(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }
}
