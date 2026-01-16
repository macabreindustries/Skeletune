package org.example.demo.controller;

import org.example.demo.dto.ComentarioResponseDto;
import org.example.demo.dto.PublicacionDto;
import org.example.demo.dto.PublicacionFeedDto;
import org.example.demo.dto.UserProfileDto; // Asegúrate de tener este import
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

    // ============================================================
    // ENDPOINTS SOCIAL MEDIA (Likes, Comentarios y Perfil)
    // ============================================================

    @GetMapping("/feed")
    public ResponseEntity<List<PublicacionFeedDto>> getSocialFeed() {
        return ResponseEntity.ok(publicacionService.getSocialFeed());
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> toggleLike(
            @PathVariable("id") Integer id,
            @RequestParam("idUsuario") Integer idUsuario) {
        publicacionService.toggleLike(id, idUsuario);
        return ResponseEntity.ok().build();
    }

    // --- NUEVO: Endpoint para cargar la pantalla de Perfil ---
    @GetMapping("/usuario/{id}/perfil")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable("id") Integer id) {
        UserProfileDto profile = publicacionService.getUserProfile(id);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<ComentarioResponseDto>> getComentarios(
            @PathVariable("id") Integer id) {
        List<ComentarioResponseDto> comentarios = publicacionService.getComentariosByPublicacionId(id);
        return ResponseEntity.ok(comentarios);
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<ComentarioResponseDto> addComentario(
            @PathVariable("id") Integer id,
            @RequestParam("idUsuario") Integer idUsuario,
            @RequestParam("texto") String texto) {
        ComentarioResponseDto nuevo = publicacionService.saveComentario(id, idUsuario, texto);
        return ResponseEntity.ok(nuevo);
    }

    // ============================================================
    // ENDPOINTS CRUD ESTÁNDAR
    // ============================================================

    @GetMapping
    public ResponseEntity<List<PublicacionDto>> getAllPublicaciones() {
        return ResponseEntity.ok(publicacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDto> getPublicacionById(@PathVariable("id") Integer id) {
        PublicacionDto publicacionDto = publicacionService.findById(id);
        return publicacionDto != null ? ResponseEntity.ok(publicacionDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PublicacionDto>> getPublicacionesByUsuarioId(@PathVariable("idUsuario") Integer idUsuario) {
        return ResponseEntity.ok(publicacionService.findByUsuarioId(idUsuario));
    }

    @PostMapping
    public ResponseEntity<PublicacionDto> createPublicacion(@RequestBody PublicacionDto publicacionDto) {
        return ResponseEntity.ok(publicacionService.save(publicacionDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDto> updatePublicacion(
            @PathVariable("id") Integer id,
            @RequestBody PublicacionDto publicacionDto) {
        PublicacionDto updatedPublicacion = publicacionService.update(id, publicacionDto);
        return updatedPublicacion != null ? ResponseEntity.ok(updatedPublicacion) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PublicacionDto> patchPublicacion(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, Object> updates) {
        PublicacionDto patchedPublicacion = publicacionService.patch(id, updates);
        return patchedPublicacion != null ? ResponseEntity.ok(patchedPublicacion) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicacion(@PathVariable("id") Integer id) {
        publicacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}