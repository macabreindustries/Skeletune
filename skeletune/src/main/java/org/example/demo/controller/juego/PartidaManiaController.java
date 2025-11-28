package org.example.demo.controller.juego;

import org.example.demo.dto.juego.PartidaManiaDto;
import org.example.demo.service.juego.PartidaManiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal; // Importar BigDecimal
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/juego/partidaMania")
public class PartidaManiaController {

    private final PartidaManiaService partidaManiaService;

    public PartidaManiaController(PartidaManiaService partidaManiaService) {
        this.partidaManiaService = partidaManiaService;
    }

    @GetMapping
    public List<PartidaManiaDto> getAllPartidaManias(
            @RequestParam(required = false) Integer idUsuario,
            @RequestParam(required = false) Integer idChartMania,
            @RequestParam(required = false) Integer puntajeMin,
            @RequestParam(required = false) BigDecimal accuracyMin) { // Cambiado Double a BigDecimal
        return partidaManiaService.findAll(idUsuario, idChartMania, puntajeMin, accuracyMin);
    }

    @GetMapping("/{idPartidaMania}")
    public ResponseEntity<PartidaManiaDto> getPartidaManiaById(@PathVariable Integer idPartidaMania) {
        PartidaManiaDto partidaManiaDto = partidaManiaService.findById(idPartidaMania);
        return partidaManiaDto != null ? ResponseEntity.ok(partidaManiaDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public PartidaManiaDto createPartidaMania(@RequestBody PartidaManiaDto partidaManiaDto) {
        return partidaManiaService.save(partidaManiaDto);
    }

    @PutMapping("/{idPartidaMania}")
    public ResponseEntity<PartidaManiaDto> updatePartidaMania(@PathVariable Integer idPartidaMania, @RequestBody PartidaManiaDto partidaManiaDto) {
        PartidaManiaDto updatedPartidaMania = partidaManiaService.update(idPartidaMania, partidaManiaDto);
        return updatedPartidaMania != null ? ResponseEntity.ok(updatedPartidaMania) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{idPartidaMania}")
    public ResponseEntity<PartidaManiaDto> patchPartidaMania(@PathVariable Integer idPartidaMania, @RequestBody Map<String, Object> updates) {
        PartidaManiaDto patchedPartidaMania = partidaManiaService.patch(idPartidaMania, updates);
        return patchedPartidaMania != null ? ResponseEntity.ok(patchedPartidaMania) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idPartidaMania}")
    public ResponseEntity<Void> deletePartidaMania(@PathVariable Integer idPartidaMania) {
        partidaManiaService.deleteById(idPartidaMania);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/puntajes")
    public List<Integer> getPuntajes() {
        return partidaManiaService.findAllPuntajes();
    }

    @GetMapping("/accuracies")
    public List<BigDecimal> getAccuracies() { // Cambiado List<Double> a List<BigDecimal>
        return partidaManiaService.findAllAccuracies();
    }
}
