package org.example.demo.service;

import org.example.demo.dto.ComentarioResponseDto;
import org.example.demo.dto.PublicacionDto;
import org.example.demo.dto.PublicacionFeedDto;
import org.example.demo.dto.UserProfileDto;

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
    List<PublicacionFeedDto> getSocialFeed();
    void toggleLike(Integer idPublicacion, Integer idUsuario);
    // Agrega estas líneas a tu archivo PublicacionService.java
    List<ComentarioResponseDto> getComentariosByPublicacionId(Integer idPublicacion);

    // Añade esto a tu interfaz PublicacionService
    ComentarioResponseDto saveComentario(Integer idPublicacion, Integer idUsuario, String texto);

    UserProfileDto getUserProfile(Integer idUsuario);
}
