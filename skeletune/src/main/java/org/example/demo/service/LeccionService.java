package org.example.demo.service;

import org.example.demo.dto.LeccionDto;
import org.example.demo.model.Leccion; // Importar la clase Leccion

import java.util.List;
import java.util.Map;

public interface LeccionService {
    List<LeccionDto> findAll();
    LeccionDto findById(Integer id);
    LeccionDto findByTitulo(String titulo);
    List<LeccionDto> findByTipo(Leccion.Tipo tipo);
    List<LeccionDto> findByNivel(Leccion.Nivel nivel);
    List<LeccionDto> findByCancionId(Integer idCancion);
    List<LeccionDto> findByVideoId(Integer idVideo);
    LeccionDto save(LeccionDto leccionDto);
    LeccionDto update(Integer id, LeccionDto leccionDto);
    LeccionDto patch(Integer id, Map<String, Object> updates);
    void deleteById(Integer id);
}
