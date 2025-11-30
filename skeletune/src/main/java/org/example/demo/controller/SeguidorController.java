package org.example.demo.controller;

import org.example.demo.dto.SeguidorDto;
import org.example.demo.service.SeguidorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skeletune/api/seguidores")
public class SeguidorController {

    private final SeguidorService seguidorService;

    public SeguidorController(SeguidorService seguidorService) {
        this.seguidorService = seguidorService;
    }

    @GetMapping("/{idUsuario}/seguidores")
    public ResponseEntity<List<SeguidorDto>> getSeguidores(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(seguidorService.getSeguidores(idUsuario));
    }

    @GetMapping("/{idUsuario}/seguidos")
    public ResponseEntity<List<SeguidorDto>> getSeguidos(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(seguidorService.getSeguidos(idUsuario));
    }

    @PostMapping("/{idSeguidor}/follow/{idSeguido}")
    public ResponseEntity<SeguidorDto> follow(@PathVariable Integer idSeguidor, @PathVariable Integer idSeguido) {
        SeguidorDto nuevaRelacion = seguidorService.follow(idSeguidor, idSeguido);
        return new ResponseEntity<>(nuevaRelacion, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idSeguidor}/unfollow/{idSeguido}")
    public ResponseEntity<Void> unfollow(@PathVariable Integer idSeguidor, @PathVariable Integer idSeguido) {
        seguidorService.unfollow(idSeguidor, idSeguido);
        return ResponseEntity.noContent().build();
    }
}
