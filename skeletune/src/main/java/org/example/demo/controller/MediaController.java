package org.example.demo.controller;

import org.example.demo.dto.MediaDto;
import org.example.demo.service.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping
    public List<MediaDto> getAllMedia() {
        return mediaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaDto> getMediaById(@PathVariable Integer id) {
        MediaDto mediaDto = mediaService.findById(id);
        return mediaDto != null ? ResponseEntity.ok(mediaDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<MediaDto> getMediaByUsuarioId(@PathVariable Integer idUsuario) {
        return mediaService.findByUsuarioId(idUsuario);
    }

    @PostMapping
    public MediaDto createMedia(@RequestBody MediaDto mediaDto) {
        return mediaService.save(mediaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaDto> updateMedia(@PathVariable Integer id, @RequestBody MediaDto mediaDto) {
        MediaDto updatedMedia = mediaService.update(id, mediaDto);
        return updatedMedia != null ? ResponseEntity.ok(updatedMedia) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MediaDto> patchMedia(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        MediaDto patchedMedia = mediaService.patch(id, updates);
        return patchedMedia != null ? ResponseEntity.ok(patchedMedia) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Integer id) {
        mediaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
