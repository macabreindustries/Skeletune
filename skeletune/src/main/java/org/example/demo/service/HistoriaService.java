package org.example.demo.service;

import org.example.demo.dto.HistoriaDto;

import java.util.List;
import java.util.Map;

public interface HistoriaService {
    List<HistoriaDto> findAll();
    HistoriaDto findById(Integer id);
    List<HistoriaDto> findByUsuarioId(Integer idUsuario);
    HistoriaDto save(HistoriaDto historiaDto);
    HistoriaDto update(Integer id, HistoriaDto historiaDto);
    HistoriaDto patch(Integer id, Map<String, Object> updates);
    void deleteById(Integer id);
}
