package org.example.demo.controller;

import org.example.demo.dto.LeccionDto;
import org.example.demo.model.Leccion; // Importar la clase Leccion
import org.example.demo.service.LeccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/lecciones")
public class LeccionController {

    private final LeccionService leccionService;

    public LeccionController(LeccionService leccionService) {
        this.leccionService = leccionService;
    }

    @GetMapping
    public List<LeccionDto> getAllLecciones() {
        return leccionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeccionDto> getLeccionById(@PathVariable Integer id) {
        LeccionDto leccionDto = leccionService.findById(id);
        return leccionDto != null ? ResponseEntity.ok(leccionDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<LeccionDto> getLeccionByTitulo(@PathVariable String titulo) {
        LeccionDto leccionDto = leccionService.findByTitulo(titulo);
        return leccionDto != null ? ResponseEntity.ok(leccionDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/tipo/{tipo}")
    public List<LeccionDto> getLeccionesByTipo(@PathVariable Leccion.Tipo tipo) {
        return leccionService.findByTipo(tipo);
    }

    @GetMapping("/nivel/{nivel}")
    public List<LeccionDto> getLeccionesByNivel(@PathVariable Leccion.Nivel nivel) {
        return leccionService.findByNivel(nivel);
    }

    @GetMapping("/cancion/{idCancion}")
    public List<LeccionDto> getLeccionesByCancionId(@PathVariable Integer idCancion) {
        return leccionService.findByCancionId(idCancion);
    }

    @GetMapping("/video/{idVideo}")
    public List<LeccionDto> getLeccionesByVideoId(@PathVariable Integer idVideo) {
        return leccionService.findByVideoId(idVideo);
    }

    @PostMapping
    public LeccionDto createLeccion(@RequestBody LeccionDto leccionDto) {
        return leccionService.save(leccionDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeccionDto> updateLeccion(@PathVariable Integer id, @RequestBody LeccionDto leccionDto) {
        LeccionDto updatedLeccion = leccionService.update(id, leccionDto);
        return updatedLeccion != null ? ResponseEntity.ok(updatedLeccion) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LeccionDto> patchLeccion(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        LeccionDto patchedLeccion = leccionService.patch(id, updates);
        return patchedLeccion != null ? ResponseEntity.ok(patchedLeccion) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeccion(@PathVariable Integer id) {
        leccionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
