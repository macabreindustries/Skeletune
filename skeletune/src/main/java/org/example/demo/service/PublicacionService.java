package org.example.demo.service;

import org.example.demo.dto.PublicacionDto;

import java.util.List;
import java.util.Map;

public interface PublicacionService {
    List<PublicacionDto> findAll();
    PublicacionDto findById(Integer id);
    List<PublicacionDto> findByUsuarioId(Integer idUsuario);
    PublicacionDto save(PublicacionDto publicacionDto);
    PublicacionDto update(Integer id, PublicacionDto publicacionDto);
    PublicacionDto patch(Integer id, Map<String, Object> updates);
    void deleteById(Integer id);
    void addMediaToPublicacion(Integer idPublicacion, Integer idMedia);
    void removeMediaFromPublicacion(Integer idPublicacion, Integer idMedia);
}
