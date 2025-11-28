package org.example.demo.service;

import org.example.demo.dto.CancionDto;
import org.example.demo.model.Cancion;

import java.util.List;
import java.util.Map;

public interface CancionService {

    List<CancionDto> findAll(String titulo, String artista, Cancion.Dificultad dificultad, String urlAudio, String urlPartitura, String imagenUrl); // Añadido imagenUrl

    CancionDto findByTitulo(String titulo);

    CancionDto save(CancionDto cancionDto);

    CancionDto update(String titulo, CancionDto cancionDto);

    CancionDto patch(String titulo, Map<String, Object> updates);

    void deleteByTitulo(String titulo);

    List<String> findAllTitulos();

    List<String> findAllArtistas();

    List<Cancion.Dificultad> findAllDificultades();

    List<String> findAllUrlAudios();

    List<String> findAllUrlPartituras();

    List<String> findAllImagenUrls(); // Nuevo método
}
