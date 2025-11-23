package org.example.demo.service.juego;

import org.example.demo.dto.juego.FalloManiaDto;
import org.example.demo.model.juego.FalloMania;

import java.util.List;
import java.util.Map;

public interface FalloManiaService {
    List<FalloManiaDto> findAll(Integer idPartidaMania, Integer tiempoMs, FalloMania.Tipo tipo);
    FalloManiaDto findById(Integer idFalloMania);
    FalloManiaDto save(FalloManiaDto falloManiaDto);
    FalloManiaDto update(Integer idFalloMania, FalloManiaDto falloManiaDto);
    FalloManiaDto patch(Integer idFalloMania, Map<String, Object> updates);
    void deleteById(Integer idFalloMania);
    List<FalloMania.Tipo> findAllTipos();
}
