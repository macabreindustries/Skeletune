package org.example.demo.service;

import org.example.demo.dto.VideollamadaDto;

import java.util.List;
import java.util.Map;

public interface VideollamadaService {

    List<VideollamadaDto> findAll();

    VideollamadaDto findById(Integer id);

    VideollamadaDto save(VideollamadaDto videollamadaDto);

    VideollamadaDto update(Integer id, VideollamadaDto videollamadaDto);

    VideollamadaDto patch(Integer id, Map<String, Object> updates);

    void deleteById(Integer id);

    List<VideollamadaDto> findByUsuario(Integer idUsuario);
}