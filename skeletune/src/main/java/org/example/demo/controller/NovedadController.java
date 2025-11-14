package org.example.demo.controller;

import org.example.demo.dto.NovedadDto;
import org.example.demo.service.NovedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skeletune/api/novedades")
public class NovedadController {

    private final NovedadService novedadService;

    @Autowired
    public NovedadController(NovedadService novedadService) {
        this.novedadService = novedadService;
    }

    @GetMapping
    public ResponseEntity<List<NovedadDto>> getAllNovedades() {
        return ResponseEntity.ok(novedadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NovedadDto> getNovedadById(@PathVariable Integer id) {
        return ResponseEntity.ok(novedadService.findById(id));
    }

    @PostMapping
    public ResponseEntity<NovedadDto> createNovedad(@RequestBody NovedadDto novedadDto) {
        NovedadDto createdNovedad = novedadService.save(novedadDto);
        return new ResponseEntity<>(createdNovedad, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NovedadDto> updateNovedad(@PathVariable Integer id, @RequestBody NovedadDto novedadDto) {
        return ResponseEntity.ok(novedadService.update(id, novedadDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNovedad(@PathVariable Integer id) {
        novedadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recientes")
    public ResponseEntity<List<NovedadDto>> getRecentNovedades() {
        return ResponseEntity.ok(novedadService.getRecentNovedades());
    }
}
