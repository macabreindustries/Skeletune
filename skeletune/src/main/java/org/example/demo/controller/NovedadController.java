package org.example.demo.controller;

import org.example.demo.dto.NovedadDto;
import org.example.demo.service.NovedadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/novedades")
public class NovedadController {

    private final NovedadService novedadService;

    public NovedadController(NovedadService novedadService) {
        this.novedadService = novedadService;
    }

    @GetMapping
    public List<NovedadDto> getAllNovedades() {
        return novedadService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NovedadDto> getNovedadById(@PathVariable Integer id) {
        NovedadDto novedadDto = novedadService.findById(id);
        return novedadDto != null ? ResponseEntity.ok(novedadDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/{idAdmin}")
    public List<NovedadDto> getNovedadesByAdminId(@PathVariable Integer idAdmin) {
        return novedadService.findByAdminId(idAdmin);
    }

    @PostMapping
    public NovedadDto createNovedad(@RequestBody NovedadDto novedadDto) {
        return novedadService.save(novedadDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NovedadDto> updateNovedad(@PathVariable Integer id, @RequestBody NovedadDto novedadDto) {
        NovedadDto updatedNovedad = novedadService.update(id, novedadDto);
        return updatedNovedad != null ? ResponseEntity.ok(updatedNovedad) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NovedadDto> patchNovedad(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        NovedadDto patchedNovedad = novedadService.patch(id, updates);
        return patchedNovedad != null ? ResponseEntity.ok(patchedNovedad) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNovedad(@PathVariable Integer id) {
        novedadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
