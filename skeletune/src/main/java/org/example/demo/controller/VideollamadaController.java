package org.example.demo.controller;

import org.example.demo.dto.VideollamadaDto;
import org.example.demo.service.VideollamadaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/videollamadas")
public class VideollamadaController {

    private final VideollamadaService videollamadaService;

    public VideollamadaController(VideollamadaService videollamadaService) {
        this.videollamadaService = videollamadaService;
    }

    @GetMapping
    public List<VideollamadaDto> getAllVideollamadas() {
        return videollamadaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideollamadaDto> getVideollamadaById(@PathVariable Integer id) {
        VideollamadaDto videollamadaDto = videollamadaService.findById(id);
        return ResponseEntity.ok(videollamadaDto);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<VideollamadaDto> getVideollamadasByUsuario(@PathVariable Integer idUsuario) {
        return videollamadaService.findByUsuario(idUsuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VideollamadaDto createVideollamada(@RequestBody VideollamadaDto videollamadaDto) {
        return videollamadaService.save(videollamadaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideollamadaDto> updateVideollamada(@PathVariable Integer id, @RequestBody VideollamadaDto videollamadaDto) {
        VideollamadaDto updatedVideollamada = videollamadaService.update(id, videollamadaDto);
        return ResponseEntity.ok(updatedVideollamada);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VideollamadaDto> patchVideollamada(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        VideollamadaDto patchedVideollamada = videollamadaService.patch(id, updates);
        return ResponseEntity.ok(patchedVideollamada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideollamada(@PathVariable Integer id) {
        videollamadaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}