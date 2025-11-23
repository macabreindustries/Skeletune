package org.example.demo.service.juego;

import org.example.demo.dto.juego.PartidaManiaDto;

import java.math.BigDecimal; // Importar BigDecimal
import java.util.List;
import java.util.Map;

public interface PartidaManiaService {
    List<PartidaManiaDto> findAll(Integer idUsuario, Integer idChartMania, Integer puntajeMin, BigDecimal accuracyMin); // Cambiado Double a BigDecimal
    PartidaManiaDto findById(Integer idPartidaMania);
    PartidaManiaDto save(PartidaManiaDto partidaManiaDto);
    PartidaManiaDto update(Integer idPartidaMania, PartidaManiaDto partidaManiaDto);
    PartidaManiaDto patch(Integer idPartidaMania, Map<String, Object> updates);
    void deleteById(Integer idPartidaMania);
    List<Integer> findAllPuntajes();
    List<BigDecimal> findAllAccuracies(); // Cambiado List<Double> a List<BigDecimal>
}
