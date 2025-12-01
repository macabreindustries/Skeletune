package org.example.demo.service;

import org.example.demo.dto.ProgresoDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProgresoService {

    List<ProgresoDto> findAll(Integer idUsuario, Integer idLeccion, LocalDate fecha);

    ProgresoDto findById(Integer id);

    ProgresoDto save(ProgresoDto progresoDto);

    ProgresoDto update(Integer id, ProgresoDto progresoDto);

    ProgresoDto patch(Integer id, Map<String, Object> updates);

    void deleteById(Integer id);
}
