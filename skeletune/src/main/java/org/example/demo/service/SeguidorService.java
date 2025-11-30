package org.example.demo.service;

import org.example.demo.dto.SeguidorDto;

import java.util.List;

public interface SeguidorService {
    List<SeguidorDto> getSeguidores(Integer idUsuario);
    List<SeguidorDto> getSeguidos(Integer idUsuario);
    SeguidorDto follow(Integer idSeguidor, Integer idSeguido);
    void unfollow(Integer idSeguidor, Integer idSeguido);
}
