package org.example.demo.service;

import org.example.demo.dto.LikePublicacionDto;

import java.util.List;

public interface LikePublicacionService {
    long getLikeCountForPublicacion(Integer idPublicacion);
    List<LikePublicacionDto> getLikesForPublicacion(Integer idPublicacion);
    LikePublicacionDto likePublicacion(Integer idUsuario, Integer idPublicacion);
    void unlikePublicacion(Integer idUsuario, Integer idPublicacion);
}
