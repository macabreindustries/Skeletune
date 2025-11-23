package org.example.demo.service.juego;

import org.example.demo.dto.juego.NotaManiaDto;
import org.example.demo.model.juego.NotaMania;

import java.util.List;
import java.util.Map;

public interface NotaManiaService {
    List<NotaManiaDto> findAll(Integer idChartMania, Integer tiempoMs, Byte carril, NotaMania.Tipo tipo);
    NotaManiaDto findById(Integer idNotaMania);
    NotaManiaDto save(NotaManiaDto notaManiaDto);
    NotaManiaDto update(Integer idNotaMania, NotaManiaDto notaManiaDto);
    NotaManiaDto patch(Integer idNotaMania, Map<String, Object> updates);
    void deleteById(Integer idNotaMania);
    List<NotaMania.Tipo> findAllTipos();
}
