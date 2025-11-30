package org.example.demo.controller;

import org.example.demo.dto.EstadisticaUsuarioDto;
import org.example.demo.service.EstadisticaUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/estadisticas-usuario")
public class EstadisticaUsuarioController {

    private final EstadisticaUsuarioService estadisticaUsuarioService;

    public EstadisticaUsuarioController(EstadisticaUsuarioService estadisticaUsuarioService) {
        this.estadisticaUsuarioService = estadisticaUsuarioService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<EstadisticaUsuarioDto> getEstadisticasByUsuarioId(@PathVariable Integer idUsuario) {
        EstadisticaUsuarioDto estadisticaDto = estadisticaUsuarioService.getEstadisticasByUsuarioId(idUsuario);
        return ResponseEntity.ok(estadisticaDto);
    }

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<EstadisticaUsuarioDto> createEstadisticas(@PathVariable Integer idUsuario) {
        EstadisticaUsuarioDto estadisticaDto = estadisticaUsuarioService.createEstadisticas(idUsuario);
        return new ResponseEntity<>(estadisticaDto, HttpStatus.CREATED);
    }

    @PutMapping("/usuario/{idUsuario}")
    public ResponseEntity<EstadisticaUsuarioDto> updateEstadisticas(
            @PathVariable Integer idUsuario,
            @RequestBody EstadisticaUsuarioDto estadisticaDto) {
        EstadisticaUsuarioDto updatedEstadistica = estadisticaUsuarioService.updateEstadisticas(idUsuario, estadisticaDto);
        return ResponseEntity.ok(updatedEstadistica);
    }

    @PatchMapping("/usuario/{idUsuario}")
    public ResponseEntity<EstadisticaUsuarioDto> patchEstadisticas(
            @PathVariable Integer idUsuario,
            @RequestBody Map<String, Object> updates) {
        EstadisticaUsuarioDto patchedEstadistica = estadisticaUsuarioService.patchEstadisticas(idUsuario, updates);
        return ResponseEntity.ok(patchedEstadistica);
    }
}
