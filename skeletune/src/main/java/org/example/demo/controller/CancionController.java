package org.example.demo.controller;

import org.example.demo.dto.CancionDto; // Podrías crear este DTO para agrupar filtros
import org.example.demo.model.Cancion;
import org.example.demo.service.CancionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
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
    public ResponseEntity<List<CancionDto>> getAllCanciones(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String artista,
            @RequestParam(required = false) Cancion.Dificultad dificultad,
            @RequestParam(required = false) String urlAudio,
            @RequestParam(required = false) String urlPartitura,
            @RequestParam(required = false) String imagenUrl) { // Añadido imagenUrl
        List<CancionDto> canciones = cancionService.findAll(titulo, artista, dificultad, urlAudio, urlPartitura, imagenUrl);
        return ResponseEntity.ok(canciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CancionDto> getCancionById(@PathVariable Integer id) {
        // Asumiendo que el servicio ahora tiene un método findById que puede lanzar una excepción si no lo encuentra
        CancionDto cancionDto = cancionService.findById(id);
        return cancionDto != null ? ResponseEntity.ok(cancionDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CancionDto> createCancion(@RequestBody CancionDto cancionDto) {
        CancionDto savedCancion = cancionService.save(cancionDto);
        // Construir la URI del nuevo recurso creado
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCancion.getIdCancion())
                .toUri();
        return ResponseEntity.created(location).body(savedCancion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CancionDto> updateCancion(@PathVariable Integer id, @RequestBody CancionDto cancionDto) {
        CancionDto updatedCancion = cancionService.update(id, cancionDto);
        return updatedCancion != null ? ResponseEntity.ok(updatedCancion) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CancionDto> patchCancion(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        CancionDto patchedCancion = cancionService.patch(id, updates);
        return patchedCancion != null ? ResponseEntity.ok(patchedCancion) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCancion(@PathVariable Integer id) {
        cancionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // SUGERENCIA: Unificar estos endpoints en uno solo para reducir llamadas de red
    @GetMapping("/titulos")
    public ResponseEntity<List<String>> getTitulos() {
        return ResponseEntity.ok(cancionService.findAllTitulos());
    }

    @GetMapping("/artistas")
    public ResponseEntity<List<String>> getArtistas() {
        return ResponseEntity.ok(cancionService.findAllArtistas());
    }

    @GetMapping("/dificultades")
    public ResponseEntity<List<Cancion.Dificultad>> getDificultades() {
        return ResponseEntity.ok(cancionService.findAllDificultades());
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
    public ResponseEntity<List<String>> getImagenUrls() {
        return ResponseEntity.ok(cancionService.findAllImagenUrls());
    }
}
