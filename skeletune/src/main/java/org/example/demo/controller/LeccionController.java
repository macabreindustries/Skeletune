package org.example.demo.controller;

import org.example.demo.dto.LeccionDto;
import org.example.demo.model.Leccion;
import org.example.demo.service.LeccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/leccion")
public class LeccionController {

    private final LeccionService leccionService;

    public LeccionController(LeccionService leccionService) {
        this.leccionService = leccionService;
    }

    @GetMapping
    public List<LeccionDto> getAllLecciones(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Leccion.Tipo tipo,
            @RequestParam(required = false) Leccion.Nivel nivel,
            @RequestParam(required = false) Integer idCancion,
            @RequestParam(required = false) Integer idVideo) {
        return leccionService.findAll(titulo, tipo, nivel, idCancion, idVideo);
    }

    @GetMapping("/{idLeccion}")
    public ResponseEntity<LeccionDto> getLeccionById(@PathVariable Integer idLeccion) {
        LeccionDto leccionDto = leccionService.findById(idLeccion);
        return leccionDto != null ? ResponseEntity.ok(leccionDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public LeccionDto createLeccion(@RequestBody LeccionDto leccionDto) {
        return leccionService.save(leccionDto);
    }

    @PutMapping("/{idLeccion}")
    public ResponseEntity<LeccionDto> updateLeccion(@PathVariable Integer idLeccion, @RequestBody LeccionDto leccionDto) {
        LeccionDto updatedLeccion = leccionService.update(idLeccion, leccionDto);
        return updatedLeccion != null ? ResponseEntity.ok(updatedLeccion) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{idLeccion}")
    public ResponseEntity<LeccionDto> patchLeccion(@PathVariable Integer idLeccion, @RequestBody Map<String, Object> updates) {
        LeccionDto patchedLeccion = leccionService.patch(idLeccion, updates);
        return patchedLeccion != null ? ResponseEntity.ok(patchedLeccion) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idLeccion}")
    public ResponseEntity<Void> deleteLeccion(@PathVariable Integer idLeccion) {
        leccionService.deleteById(idLeccion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/titulos")
    public List<String> getTitulos() {
        return leccionService.findAllTitulos();
    }

    @GetMapping("/tipos")
    public List<Leccion.Tipo> getTipos() {
        return leccionService.findAllTipos();
    }

    @GetMapping("/niveles")
    public List<Leccion.Nivel> getNiveles() {
        return leccionService.findAllNiveles();
    }
}
