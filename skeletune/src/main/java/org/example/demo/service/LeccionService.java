package org.example.demo.service;

import org.example.demo.dto.LeccionDto;
import org.example.demo.model.Leccion;

import java.util.List;
import java.util.Map;

public interface LeccionService {
    List<LeccionDto> findAll(String titulo, Leccion.Tipo tipo, Leccion.Nivel nivel, Integer idCancion, Integer idVideo);
    LeccionDto findById(Integer idLeccion);
    LeccionDto save(LeccionDto leccionDto);
    LeccionDto update(Integer idLeccion, LeccionDto leccionDto);
    LeccionDto patch(Integer idLeccion, Map<String, Object> updates);
    void deleteById(Integer idLeccion);
    List<String> findAllTitulos();
    List<Leccion.Tipo> findAllTipos();
    List<Leccion.Nivel> findAllNiveles();
}
