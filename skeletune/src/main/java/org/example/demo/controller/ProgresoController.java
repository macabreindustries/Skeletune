package org.example.demo.controller;

import org.example.demo.dto.ProgresoDto;
import org.example.demo.service.ProgresoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/progreso")
public class ProgresoController {

    private final ProgresoService progresoService;

    public ProgresoController(ProgresoService progresoService) {
        this.progresoService = progresoService;
    }

    @GetMapping
    public ResponseEntity<List<ProgresoDto>> getAllProgresos(
            @RequestParam(required = false) Integer idUsuario,
            @RequestParam(required = false) Integer idLeccion,
            @RequestParam(required = false) LocalDate fecha) {
        List<ProgresoDto> progresos = progresoService.findAll(idUsuario, idLeccion, fecha);
        return ResponseEntity.ok(progresos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgresoDto> getProgresoById(@PathVariable Integer id) {
        ProgresoDto progresoDto = progresoService.findById(id);
        return progresoDto != null ? ResponseEntity.ok(progresoDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProgresoDto> createProgreso(@RequestBody ProgresoDto progresoDto) {
        ProgresoDto savedProgreso = progresoService.save(progresoDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProgreso.getIdProgreso())
                .toUri();
        return ResponseEntity.created(location).body(savedProgreso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgresoDto> updateProgreso(@PathVariable Integer id, @RequestBody ProgresoDto progresoDto) {
        ProgresoDto updatedProgreso = progresoService.update(id, progresoDto);
        return updatedProgreso != null ? ResponseEntity.ok(updatedProgreso) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProgresoDto> patchProgreso(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        ProgresoDto patchedProgreso = progresoService.patch(id, updates);
        return patchedProgreso != null ? ResponseEntity.ok(patchedProgreso) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgreso(@PathVariable Integer id) {
        progresoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
