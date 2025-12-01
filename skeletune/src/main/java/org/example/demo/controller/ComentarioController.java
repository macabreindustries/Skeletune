package org.example.demo.controller;

import org.example.demo.dto.ComentarioDto;
import org.example.demo.service.ComentarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping("/publicacion/{idPublicacion}")
    public List<ComentarioDto> getComentariosByPublicacionId(@PathVariable Integer idPublicacion) {
        return comentarioService.findByPublicacionId(idPublicacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioDto> getComentarioById(@PathVariable Integer id) {
        ComentarioDto comentarioDto = comentarioService.findById(id);
        return comentarioDto != null ? ResponseEntity.ok(comentarioDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ComentarioDto createComentario(@RequestBody ComentarioDto comentarioDto) {
        return comentarioService.save(comentarioDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComentarioDto> updateComentario(@PathVariable Integer id, @RequestBody ComentarioDto comentarioDto) {
        ComentarioDto updatedComentario = comentarioService.update(id, comentarioDto);
        return updatedComentario != null ? ResponseEntity.ok(updatedComentario) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ComentarioDto> patchComentario(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        ComentarioDto patchedComentario = comentarioService.patch(id, updates);
        return patchedComentario != null ? ResponseEntity.ok(patchedComentario) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Integer id) {
        comentarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
