package org.example.demo.service;

import org.example.demo.dto.PublicacionMediaDto;
import org.example.demo.dto.MediaDto;

import java.util.List;

public interface PublicacionMediaService {
    List<MediaDto> getMediaByPublicacionId(Integer idPublicacion);
    PublicacionMediaDto addMediaToPublicacion(Integer idPublicacion, Integer idMedia);
    void removeMediaFromPublicacion(Integer idPublicacion, Integer idMedia);
}
