package org.example.demo.controller.juego;

import org.example.demo.dto.juego.FalloManiaDto;
import org.example.demo.model.juego.FalloMania;
import org.example.demo.service.juego.FalloManiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/juego/falloMania")
public class FalloManiaController {

    private final FalloManiaService falloManiaService;

    public FalloManiaController(FalloManiaService falloManiaService) {
        this.falloManiaService = falloManiaService;
    }

    @GetMapping
    public List<FalloManiaDto> getAllFalloManias(
            @RequestParam(required = false) Integer idPartidaMania,
            @RequestParam(required = false) Integer tiempoMs,
            @RequestParam(required = false) FalloMania.Tipo tipo) {
        return falloManiaService.findAll(idPartidaMania, tiempoMs, tipo);
    }

    @GetMapping("/{idFalloMania}")
    public ResponseEntity<FalloManiaDto> getFalloManiaById(@PathVariable Integer idFalloMania) {
        FalloManiaDto falloManiaDto = falloManiaService.findById(idFalloMania);
        return falloManiaDto != null ? ResponseEntity.ok(falloManiaDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public FalloManiaDto createFalloMania(@RequestBody FalloManiaDto falloManiaDto) {
        return falloManiaService.save(falloManiaDto);
    }

    @PutMapping("/{idFalloMania}")
    public ResponseEntity<FalloManiaDto> updateFalloMania(@PathVariable Integer idFalloMania, @RequestBody FalloManiaDto falloManiaDto) {
        FalloManiaDto updatedFalloMania = falloManiaService.update(idFalloMania, falloManiaDto);
        return updatedFalloMania != null ? ResponseEntity.ok(updatedFalloMania) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{idFalloMania}")
    public ResponseEntity<FalloManiaDto> patchFalloMania(@PathVariable Integer idFalloMania, @RequestBody Map<String, Object> updates) {
        FalloManiaDto patchedFalloMania = falloManiaService.patch(idFalloMania, updates);
        return patchedFalloMania != null ? ResponseEntity.ok(patchedFalloMania) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idFalloMania}")
    public ResponseEntity<Void> deleteFalloMania(@PathVariable Integer idFalloMania) {
        falloManiaService.deleteById(idFalloMania);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipos")
    public List<FalloMania.Tipo> getTipos() {
        return falloManiaService.findAllTipos();
    }
}
