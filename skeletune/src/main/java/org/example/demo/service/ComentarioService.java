package org.example.demo.service;

import org.example.demo.dto.ComentarioDto;

import java.util.List;
import java.util.Map;

public interface ComentarioService {
    List<ComentarioDto> findByPublicacionId(Integer idPublicacion);
    ComentarioDto findById(Integer id);
    ComentarioDto save(ComentarioDto comentarioDto);
    ComentarioDto update(Integer id, ComentarioDto comentarioDto);
    ComentarioDto patch(Integer id, Map<String, Object> updates);
    void deleteById(Integer id);
}
