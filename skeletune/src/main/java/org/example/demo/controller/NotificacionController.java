package org.example.demo.controller;

import org.example.demo.dto.NotificacionDto;
import org.example.demo.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skeletune/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @Autowired
    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public ResponseEntity<List<NotificacionDto>> getAllNotificaciones() {
        return ResponseEntity.ok(notificacionService.findAll());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacionDto>> getNotificacionesByUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(notificacionService.findByUsuarioId(idUsuario));
    }

    @PutMapping("/{idNotificacion}/leido")
    public ResponseEntity<NotificacionDto> markAsRead(@PathVariable Integer idNotificacion) {
        return ResponseEntity.ok(notificacionService.markAsRead(idNotificacion));
    }

    @PostMapping
    public ResponseEntity<NotificacionDto> createNotificacion(@RequestBody NotificacionDto notificacionDto) {
        NotificacionDto createdNotificacion = notificacionService.save(notificacionDto);
        return new ResponseEntity<>(createdNotificacion, HttpStatus.CREATED);
    }
}
