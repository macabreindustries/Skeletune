package org.example.demo.service;

import org.example.demo.dto.NovedadDto;

import java.util.List;
import java.util.Map;

public interface NovedadService {
    List<NovedadDto> findAll();
    NovedadDto findById(Integer id);
    List<NovedadDto> findByAdminId(Integer idAdmin);
    NovedadDto save(NovedadDto novedadDto);
    NovedadDto update(Integer id, NovedadDto novedadDto);
    NovedadDto patch(Integer id, Map<String, Object> updates);
    void deleteById(Integer id);
}
