package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.ComentarioDto;
import org.example.demo.model.Comentario;
import org.example.demo.model.Publicacion;
import org.example.demo.model.Usuario;
import org.example.demo.repository.ComentarioRepository;
import org.example.demo.repository.PublicacionRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.ComentarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository, PublicacionRepository publicacionRepository, UsuarioRepository usuarioRepository, ObjectMapper objectMapper) {
        this.comentarioRepository = comentarioRepository;
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioDto> findByPublicacionId(Integer idPublicacion) {
        return comentarioRepository.findByPublicacionIdPublicacion(idPublicacion).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ComentarioDto findById(Integer id) {
        return comentarioRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    @Transactional
    public ComentarioDto save(ComentarioDto comentarioDto) {
        Comentario comentario = toEntity(comentarioDto);
        return toDto(comentarioRepository.save(comentario));
    }

    @Override
    @Transactional
    public ComentarioDto update(Integer id, ComentarioDto comentarioDto) {
        return comentarioRepository.findById(id).map(existingComentario -> {
            // Solo se puede actualizar el texto del comentario
            existingComentario.setComentario(comentarioDto.getComentario());
            return toDto(comentarioRepository.save(existingComentario));
        }).orElse(null);
    }

    @Override
    @Transactional
    public ComentarioDto patch(Integer id, Map<String, Object> updates) {
        return comentarioRepository.findById(id).map(existingComentario -> {
            updates.forEach((key, value) -> {
                if ("comentario".equals(key)) {
                    existingComentario.setComentario((String) value);
                }
                // No se permite cambiar idPublicacion o idUsuario
            });
            return toDto(comentarioRepository.save(existingComentario));
        }).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        comentarioRepository.deleteById(id);
    }

    private ComentarioDto toDto(Comentario comentario) {
        ComentarioDto dto = new ComentarioDto();
        BeanUtils.copyProperties(comentario, dto, "publicacion", "usuario");
        if (comentario.getPublicacion() != null) {
            dto.setIdPublicacion(comentario.getPublicacion().getIdPublicacion());
        }
        if (comentario.getUsuario() != null) {
            dto.setIdUsuario(comentario.getUsuario().getId());
        }
        return dto;
    }

    private Comentario toEntity(ComentarioDto dto) {
        Comentario comentario = new Comentario();
        BeanUtils.copyProperties(dto, comentario, "idPublicacion", "idUsuario");

        if (dto.getIdPublicacion() != null) {
            Publicacion publicacion = publicacionRepository.findById(dto.getIdPublicacion())
                    .orElseThrow(() -> new EntityNotFoundException("Publicacion no encontrada con id: " + dto.getIdPublicacion()));
            comentario.setPublicacion(publicacion);
        } else {
            throw new IllegalArgumentException("El campo idPublicacion no puede ser nulo.");
        }

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + dto.getIdUsuario()));
            comentario.setUsuario(usuario);
        } else {
            throw new IllegalArgumentException("El campo idUsuario no puede ser nulo.");
        }
        return comentario;
    }
}
