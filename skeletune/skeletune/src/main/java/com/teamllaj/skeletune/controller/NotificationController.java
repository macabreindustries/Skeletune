// src/main/java/com/teamllaj/skeletune/controller/NotificationController.java

package com.teamllaj.skeletune.controller;

import com.teamllaj.skeletune.model.dto.NotificationDTO;
import com.teamllaj.skeletune.model.entity.NotificationEntity;
import com.teamllaj.skeletune.service.NotificationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications") // Endpoint base
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Endpoint para obtener todas las notificaciones
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications); // Código 200 OK
    }

    // Endpoint para crear una nueva notificación
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationEntity notification) {
        NotificationDTO newNotification = notificationService.createNotification(notification);
        // Retorna 201 Created y el objeto creado
        return ResponseEntity.status(201).body(newNotification);
    }


    // Podrías añadir más endpoints para GET por usuario, etc.
}