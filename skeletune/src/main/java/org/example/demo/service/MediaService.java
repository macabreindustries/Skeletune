package org.example.demo.service;

import org.example.demo.dto.MediaDto;

import java.util.List;
import java.util.Map;

public interface MediaService {
    List<MediaDto> findAll();
    MediaDto findById(Integer id);
    List<MediaDto> findByUsuarioId(Integer idUsuario);
    MediaDto save(MediaDto mediaDto);
    MediaDto update(Integer id, MediaDto mediaDto);
    MediaDto patch(Integer id, Map<String, Object> updates);
    void deleteById(Integer id);
}
