package org.example.demo.service;

import org.example.demo.dto.CancionDto;
import org.example.demo.model.Cancion;

import java.util.List;
import java.util.Map;

public interface CancionService {

    List<CancionDto> findAll(String titulo, String artista, Cancion.Dificultad dificultad, String urlAudio, String urlPartitura, String imagenUrl); // Añadido imagenUrl

    CancionDto findById(Integer id);

    CancionDto save(CancionDto cancionDto);

    CancionDto update(Integer id, CancionDto cancionDto);

    CancionDto patch(Integer id, Map<String, Object> updates);

    void deleteById(Integer id);

    List<String> findAllTitulos();

    List<String> findAllArtistas();

    List<Cancion.Dificultad> findAllDificultades();

    List<String> findAllUrlAudios();

    List<String> findAllUrlPartituras();

    List<String> findAllImagenUrls(); // Nuevo método
}
