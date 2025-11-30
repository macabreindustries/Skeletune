package org.example.demo.service;

import org.example.demo.dto.ProgresoDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProgresoService {
    List<ProgresoDto> findAll();
    ProgresoDto findById(Integer id);
    List<ProgresoDto> findByUsuarioId(Integer idUsuario);
    List<ProgresoDto> findByLeccionId(Integer idLeccion);
    List<ProgresoDto> findByUsuarioIdAndFechaBetween(Integer idUsuario, LocalDate startDate, LocalDate endDate);
    ProgresoDto save(ProgresoDto progresoDto);
    ProgresoDto update(Integer id, ProgresoDto progresoDto);
    ProgresoDto patch(Integer id, Map<String, Object> updates);
    void deleteById(Integer id);
}
