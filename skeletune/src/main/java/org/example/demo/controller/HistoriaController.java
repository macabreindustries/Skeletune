package org.example.demo.controller;

import org.example.demo.dto.HistoriaDto;
import org.example.demo.service.HistoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/historia")
public class HistoriaController {

    private final HistoriaService historiaService;

    public HistoriaController(HistoriaService historiaService) {
        this.historiaService = historiaService;
    }

    @GetMapping
    public List<HistoriaDto> getAllHistorias() {
        return historiaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaDto> getHistoriaById(@PathVariable Integer id) {
        HistoriaDto historiaDto = historiaService.findById(id);
        return historiaDto != null ? ResponseEntity.ok(historiaDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<HistoriaDto> getHistoriasByUsuarioId(@PathVariable Integer idUsuario) {
        return historiaService.findByUsuarioId(idUsuario);
    }

    @PostMapping
    public HistoriaDto createHistoria(@RequestBody HistoriaDto historiaDto) {
        return historiaService.save(historiaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoriaDto> updateHistoria(@PathVariable Integer id, @RequestBody HistoriaDto historiaDto) {
        HistoriaDto updatedHistoria = historiaService.update(id, historiaDto);
        return updatedHistoria != null ? ResponseEntity.ok(updatedHistoria) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HistoriaDto> patchHistoria(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        HistoriaDto patchedHistoria = historiaService.patch(id, updates);
        return patchedHistoria != null ? ResponseEntity.ok(patchedHistoria) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoria(@PathVariable Integer id) {
        historiaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
