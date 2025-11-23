package org.example.demo.service;

import org.example.demo.dto.VideoEducativoDto;

import java.util.List;
import java.util.Map;

public interface VideoEducativoService {
    List<VideoEducativoDto> findAll(Integer idProfesor, String titulo);
    VideoEducativoDto findById(Integer idVideo);
    VideoEducativoDto save(VideoEducativoDto videoEducativoDto);
    VideoEducativoDto update(Integer idVideo, VideoEducativoDto videoEducativoDto);
    VideoEducativoDto patch(Integer idVideo, Map<String, Object> updates);
    void deleteById(Integer idVideo);
    List<String> findAllTitulos();
}
