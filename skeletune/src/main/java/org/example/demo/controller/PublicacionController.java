package org.example.demo.controller;

import org.example.demo.dto.PublicacionDto;
import org.example.demo.service.PublicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/publicacion")
public class PublicacionController {

    private final PublicacionService publicacionService;

    public PublicacionController(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    @GetMapping
    public List<PublicacionDto> getAllPublicaciones() {
        return publicacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDto> getPublicacionById(@PathVariable Integer id) {
        PublicacionDto publicacionDto = publicacionService.findById(id);
        return publicacionDto != null ? ResponseEntity.ok(publicacionDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<PublicacionDto> getPublicacionesByUsuarioId(@PathVariable Integer idUsuario) {
        return publicacionService.findByUsuarioId(idUsuario);
    }

    @PostMapping
    public PublicacionDto createPublicacion(@RequestBody PublicacionDto publicacionDto) {
        return publicacionService.save(publicacionDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDto> updatePublicacion(@PathVariable Integer id, @RequestBody PublicacionDto publicacionDto) {
        PublicacionDto updatedPublicacion = publicacionService.update(id, publicacionDto);
        return updatedPublicacion != null ? ResponseEntity.ok(updatedPublicacion) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PublicacionDto> patchPublicacion(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        PublicacionDto patchedPublicacion = publicacionService.patch(id, updates);
        return patchedPublicacion != null ? ResponseEntity.ok(patchedPublicacion) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicacion(@PathVariable Integer id) {
        publicacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
