package org.example.demo.controller;

import org.example.demo.dto.CancionDto;
import org.example.demo.model.Cancion;
import org.example.demo.service.CancionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/cancion")
public class CancionController {

    private final CancionService cancionService;

    public CancionController(CancionService cancionService) {
        this.cancionService = cancionService;
    }

    @GetMapping
    public List<CancionDto> getAllCanciones(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String artista,
            @RequestParam(required = false) Cancion.Dificultad dificultad,
            @RequestParam(required = false) String urlAudio,
            @RequestParam(required = false) String urlPartitura,
            @RequestParam(required = false) String imagenUrl) { // Añadido imagenUrl
        return cancionService.findAll(titulo, artista, dificultad, urlAudio, urlPartitura, imagenUrl);
    }

    @GetMapping("/{titulo}")
    public ResponseEntity<CancionDto> getCancionByTitulo(@PathVariable String titulo) {
        CancionDto cancionDto = cancionService.findByTitulo(titulo);
        return cancionDto != null ? ResponseEntity.ok(cancionDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public CancionDto createCancion(@RequestBody CancionDto cancionDto) {
        return cancionService.save(cancionDto);
    }

    @PutMapping("/{titulo}")
    public ResponseEntity<CancionDto> updateCancion(@PathVariable String titulo, @RequestBody CancionDto cancionDto) {
        CancionDto updatedCancion = cancionService.update(titulo, cancionDto);
        return updatedCancion != null ? ResponseEntity.ok(updatedCancion) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{titulo}")
    public ResponseEntity<CancionDto> patchCancion(@PathVariable String titulo, @RequestBody Map<String, Object> updates) {
        CancionDto patchedCancion = cancionService.patch(titulo, updates);
        return patchedCancion != null ? ResponseEntity.ok(patchedCancion) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{titulo}")
    public ResponseEntity<Void> deleteCancion(@PathVariable String titulo) {
        cancionService.deleteByTitulo(titulo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/titulos")
    public List<String> getTitulos() {
        return cancionService.findAllTitulos();
    }

    @GetMapping("/artistas")
    public List<String> getArtistas() {
        return cancionService.findAllArtistas();
    }

    @GetMapping("/dificultades")
    public List<Cancion.Dificultad> getDificultades() {
        return cancionService.findAllDificultades();
    }

    @GetMapping("/url-audios")
    public List<String> getUrlAudios() {
        return cancionService.findAllUrlAudios();
    }

    @GetMapping("/url-partituras")
    public List<String> getUrlPartituras() {
        return cancionService.findAllUrlPartituras();
    }

    @GetMapping("/imagen-urls") // Nuevo endpoint
    public List<String> getImagenUrls() {
        return cancionService.findAllImagenUrls();
    }
}
