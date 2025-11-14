package org.example.demo.controller;

import org.example.demo.dto.MensajeDto;
import org.example.demo.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skeletune/api/mensajes")
public class MensajeController {

    private final MensajeService mensajeService;

    @Autowired
    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping
    public ResponseEntity<List<MensajeDto>> getAllMensajes() {
        return ResponseEntity.ok(mensajeService.findAll());
    }

    @GetMapping("/conversacion/{idUsuario1}/{idUsuario2}")
    public ResponseEntity<List<MensajeDto>> getConversation(
            @PathVariable Integer idUsuario1,
            @PathVariable Integer idUsuario2) {
        return ResponseEntity.ok(mensajeService.getConversation(idUsuario1, idUsuario2));
    }

    @PostMapping
    public ResponseEntity<MensajeDto> sendMessage(@RequestBody MensajeDto mensajeDto) {
        MensajeDto sentMessage = mensajeService.sendMessage(mensajeDto);
        return new ResponseEntity<>(sentMessage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDto> updateMessage(@PathVariable Integer id, @RequestBody MensajeDto mensajeDto) {
        return ResponseEntity.ok(mensajeService.updateMessage(id, mensajeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer id) {
        mensajeService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
