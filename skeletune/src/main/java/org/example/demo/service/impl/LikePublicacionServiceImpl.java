package org.example.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.LikePublicacionDto;
import org.example.demo.model.LikePublicacion;
import org.example.demo.model.Publicacion;
import org.example.demo.model.Usuario;
import org.example.demo.repository.LikePublicacionRepository;
import org.example.demo.repository.PublicacionRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.LikePublicacionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikePublicacionServiceImpl implements LikePublicacionService {

    private final LikePublicacionRepository likePublicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;

    public LikePublicacionServiceImpl(LikePublicacionRepository likePublicacionRepository, UsuarioRepository usuarioRepository, PublicacionRepository publicacionRepository) {
        this.likePublicacionRepository = likePublicacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.publicacionRepository = publicacionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public long getLikeCountForPublicacion(Integer idPublicacion) {
        return likePublicacionRepository.countByPublicacionIdPublicacion(idPublicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LikePublicacionDto> getLikesForPublicacion(Integer idPublicacion) {
        return likePublicacionRepository.findByPublicacionIdPublicacion(idPublicacion).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LikePublicacionDto likePublicacion(Integer idUsuario, Integer idPublicacion) {
        if (likePublicacionRepository.findByUsuarioIdAndPublicacionIdPublicacion(idUsuario, idPublicacion).isPresent()) {
            throw new IllegalStateException("El usuario ya ha dado like a esta publicación.");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + idUsuario));
        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new EntityNotFoundException("Publicación no encontrada con id: " + idPublicacion));

        LikePublicacion like = new LikePublicacion();
        like.setUsuario(usuario);
        like.setPublicacion(publicacion);

        return toDto(likePublicacionRepository.save(like));
    }

    @Override
    @Transactional
    public void unlikePublicacion(Integer idUsuario, Integer idPublicacion) {
        LikePublicacion like = likePublicacionRepository.findByUsuarioIdAndPublicacionIdPublicacion(idUsuario, idPublicacion)
                .orElseThrow(() -> new EntityNotFoundException("El like no existe."));
        likePublicacionRepository.delete(like);
    }

    private LikePublicacionDto toDto(LikePublicacion like) {
        LikePublicacionDto dto = new LikePublicacionDto();
        BeanUtils.copyProperties(like, dto, "usuario", "publicacion");
        if (like.getUsuario() != null) {
            dto.setIdUsuario(like.getUsuario().getId());
        }
        if (like.getPublicacion() != null) {
            dto.setIdPublicacion(like.getPublicacion().getIdPublicacion());
        }
        return dto;
    }
}
