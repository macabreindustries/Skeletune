package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.MediaDto;
import org.example.demo.model.Media;
import org.example.demo.model.Usuario;
import org.example.demo.repository.MediaRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.MediaService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public MediaServiceImpl(MediaRepository mediaRepository, UsuarioRepository usuarioRepository, ObjectMapper objectMapper) {
        this.mediaRepository = mediaRepository;
        this.usuarioRepository = usuarioRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaDto> findAll() {
        return mediaRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MediaDto findById(Integer id) {
        return mediaRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaDto> findByUsuarioId(Integer idUsuario) {
        return mediaRepository.findByUsuarioId(idUsuario).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MediaDto save(MediaDto mediaDto) {
        Media media = toEntity(mediaDto);
        return toDto(mediaRepository.save(media));
    }

    @Override
    @Transactional
    public MediaDto update(Integer id, MediaDto mediaDto) {
        return mediaRepository.findById(id).map(existingMedia -> {
            // No se permite cambiar el usuario, tipo, url o fecha de subida.
            // Solo la descripción es editable.
            existingMedia.setDescripcion(mediaDto.getDescripcion());
            return toDto(mediaRepository.save(existingMedia));
        }).orElse(null);
    }

    @Override
    @Transactional
    public MediaDto patch(Integer id, Map<String, Object> updates) {
        return mediaRepository.findById(id).map(existingMedia -> {
            updates.forEach((key, value) -> {
                // Solo permitir la actualización de la descripción
                if ("descripcion".equals(key)) {
                    existingMedia.setDescripcion((String) value);
                }
            });
            return toDto(mediaRepository.save(existingMedia));
        }).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        mediaRepository.deleteById(id);
    }

    private MediaDto toDto(Media media) {
        MediaDto dto = new MediaDto();
        BeanUtils.copyProperties(media, dto, "usuario");
        if (media.getUsuario() != null) {
            dto.setIdUsuario(media.getUsuario().getId());
        }
        return dto;
    }

    private Media toEntity(MediaDto dto) {
        Media media = new Media();
        BeanUtils.copyProperties(dto, media, "idUsuario");
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + dto.getIdUsuario()));
            media.setUsuario(usuario);
        } else {
            throw new IllegalArgumentException("El campo idUsuario no puede ser nulo.");
        }
        return media;
    }
}
