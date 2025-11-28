package org.example.demo.controller.juego;

import org.example.demo.dto.juego.ChartManiaDto;
import org.example.demo.model.juego.ChartMania;
import org.example.demo.service.juego.ChartManiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/juego/chartMania")
public class ChartManiaController {

    private final ChartManiaService chartManiaService;

    public ChartManiaController(ChartManiaService chartManiaService) {
        this.chartManiaService = chartManiaService;
    }

    @GetMapping
    public List<ChartManiaDto> getAllChartManias(
            @RequestParam(required = false) Integer idCancion,
            @RequestParam(required = false) ChartMania.Dificultad dificultad,
            @RequestParam(required = false) Float speedMultiplier) {
        return chartManiaService.findAll(idCancion, dificultad, speedMultiplier);
    }

    @GetMapping("/{idChartMania}")
    public ResponseEntity<ChartManiaDto> getChartManiaById(@PathVariable Integer idChartMania) {
        ChartManiaDto chartManiaDto = chartManiaService.findById(idChartMania);
        return chartManiaDto != null ? ResponseEntity.ok(chartManiaDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ChartManiaDto createChartMania(@RequestBody ChartManiaDto chartManiaDto) {
        return chartManiaService.save(chartManiaDto);
    }

    @PutMapping("/{idChartMania}")
    public ResponseEntity<ChartManiaDto> updateChartMania(@PathVariable Integer idChartMania, @RequestBody ChartManiaDto chartManiaDto) {
        ChartManiaDto updatedChartMania = chartManiaService.update(idChartMania, chartManiaDto);
        return updatedChartMania != null ? ResponseEntity.ok(updatedChartMania) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{idChartMania}")
    public ResponseEntity<ChartManiaDto> patchChartMania(@PathVariable Integer idChartMania, @RequestBody Map<String, Object> updates) {
        ChartManiaDto patchedChartMania = chartManiaService.patch(idChartMania, updates);
        return patchedChartMania != null ? ResponseEntity.ok(patchedChartMania) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idChartMania}")
    public ResponseEntity<Void> deleteChartMania(@PathVariable Integer idChartMania) {
        chartManiaService.deleteById(idChartMania);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dificultades")
    public List<ChartMania.Dificultad> getDificultades() {
        return chartManiaService.findAllDificultades();
    }

    @GetMapping("/speed-multipliers")
    public List<Float> getSpeedMultipliers() {
        return chartManiaService.findAllSpeedMultipliers();
    }
}
