package org.example.demo.service.juego;

import org.example.demo.dto.juego.ChartManiaDto;
import org.example.demo.model.juego.ChartMania;

import java.util.List;
import java.util.Map;

public interface ChartManiaService {
    List<ChartManiaDto> findAll(Integer idCancion, ChartMania.Dificultad dificultad, Float speedMultiplier);
    ChartManiaDto findById(Integer idChartMania);
    ChartManiaDto save(ChartManiaDto chartManiaDto);
    ChartManiaDto update(Integer idChartMania, ChartManiaDto chartManiaDto);
    ChartManiaDto patch(Integer idChartMania, Map<String, Object> updates);
    void deleteById(Integer idChartMania);
    List<ChartMania.Dificultad> findAllDificultades();
    List<Float> findAllSpeedMultipliers();
}
