package org.example.demo.controller;

import org.example.demo.dto.MediaDto;
import org.example.demo.dto.PublicacionMediaDto;
import org.example.demo.service.PublicacionMediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skeletune/api/publicacion-media")
public class PublicacionMediaController {

    private final PublicacionMediaService publicacionMediaService;

    public PublicacionMediaController(PublicacionMediaService publicacionMediaService) {
        this.publicacionMediaService = publicacionMediaService;
    }

    @GetMapping("/{idPublicacion}/media")
    public ResponseEntity<List<MediaDto>> getMediaByPublicacion(@PathVariable Integer idPublicacion) {
        return ResponseEntity.ok(publicacionMediaService.getMediaByPublicacionId(idPublicacion));
    }

    @PostMapping("/{idPublicacion}/media/{idMedia}")
    public ResponseEntity<PublicacionMediaDto> addMediaToPublicacion(
            @PathVariable Integer idPublicacion,
            @PathVariable Integer idMedia) {
        PublicacionMediaDto result = publicacionMediaService.addMediaToPublicacion(idPublicacion, idMedia);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idPublicacion}/media/{idMedia}")
    public ResponseEntity<Void> removeMediaFromPublicacion(
            @PathVariable Integer idPublicacion,
            @PathVariable Integer idMedia) {
        publicacionMediaService.removeMediaFromPublicacion(idPublicacion, idMedia);
        return ResponseEntity.noContent().build();
    }
}
