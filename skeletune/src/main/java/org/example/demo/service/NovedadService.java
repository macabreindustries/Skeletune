package org.example.demo.service;

import org.example.demo.dto.NovedadDto;
import org.example.demo.model.Novedad;

import java.util.List;

public interface NovedadService {

    List<NovedadDto> findAll();

    NovedadDto findById(Integer id);

    NovedadDto save(NovedadDto novedadDto);

    NovedadDto update(Integer id, NovedadDto novedadDto);

    void deleteById(Integer id);

    List<NovedadDto> getRecentNovedades();
}
