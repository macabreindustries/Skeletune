package org.example.demo.controller;

import org.example.demo.dto.LikePublicacionDto;
import org.example.demo.service.LikePublicacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skeletune/api/likes")
public class LikePublicacionController {

    private final LikePublicacionService likePublicacionService;

    public LikePublicacionController(LikePublicacionService likePublicacionService) {
        this.likePublicacionService = likePublicacionService;
    }

    @GetMapping("/publicacion/{idPublicacion}/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Integer idPublicacion) {
        return ResponseEntity.ok(likePublicacionService.getLikeCountForPublicacion(idPublicacion));
    }

    @GetMapping("/publicacion/{idPublicacion}")
    public ResponseEntity<List<LikePublicacionDto>> getLikesForPublicacion(@PathVariable Integer idPublicacion) {
        return ResponseEntity.ok(likePublicacionService.getLikesForPublicacion(idPublicacion));
    }

    @PostMapping("/usuario/{idUsuario}/publicacion/{idPublicacion}")
    public ResponseEntity<LikePublicacionDto> likePublicacion(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idPublicacion) {
        LikePublicacionDto like = likePublicacionService.likePublicacion(idUsuario, idPublicacion);
        return new ResponseEntity<>(like, HttpStatus.CREATED);
    }

    @DeleteMapping("/usuario/{idUsuario}/publicacion/{idPublicacion}")
    public ResponseEntity<Void> unlikePublicacion(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idPublicacion) {
        likePublicacionService.unlikePublicacion(idUsuario, idPublicacion);
        return ResponseEntity.noContent().build();
    }
}
