package org.example.demo.controller;

import org.example.demo.dto.RachaDTO;
import lombok.RequiredArgsConstructor;
import org.example.demo.service.RachaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("skeletune/api/racha")
@RequiredArgsConstructor
public class RachaController {

    private final RachaService rachaService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<RachaDTO> getRachaUsuario(@PathVariable int id) {
        return ResponseEntity.ok(rachaService.obtenerResumenRacha(id));
    }
}