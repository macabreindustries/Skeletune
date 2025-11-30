package org.example.demo.controller;

import org.example.demo.dto.ProgresoDto;
import org.example.demo.service.ProgresoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/progresos")
public class ProgresoController {

    private final ProgresoService progresoService;

    public ProgresoController(ProgresoService progresoService) {
        this.progresoService = progresoService;
    }

    @GetMapping
    public List<ProgresoDto> getAllProgresos() {
        return progresoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgresoDto> getProgresoById(@PathVariable Integer id) {
        ProgresoDto progresoDto = progresoService.findById(id);
        return progresoDto != null ? ResponseEntity.ok(progresoDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<ProgresoDto> getProgresosByUsuarioId(@PathVariable Integer idUsuario) {
        return progresoService.findByUsuarioId(idUsuario);
    }

    @GetMapping("/leccion/{idLeccion}")
    public List<ProgresoDto> getProgresosByLeccionId(@PathVariable Integer idLeccion) {
        return progresoService.findByLeccionId(idLeccion);
    }

    @GetMapping("/usuario/{idUsuario}/fecha")
    public List<ProgresoDto> getProgresosByUsuarioIdAndFechaBetween(
            @PathVariable Integer idUsuario,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return progresoService.findByUsuarioIdAndFechaBetween(idUsuario, startDate, endDate);
    }

    @PostMapping
    public ProgresoDto createProgreso(@RequestBody ProgresoDto progresoDto) {
        return progresoService.save(progresoDto);
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
