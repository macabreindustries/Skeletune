package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.HistoriaDto;
import org.example.demo.model.Historia;
import org.example.demo.model.Media;
import org.example.demo.model.Usuario;
import org.example.demo.repository.HistoriaRepository;
import org.example.demo.repository.MediaRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.HistoriaService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HistoriaServiceImpl implements HistoriaService {

    private final HistoriaRepository historiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MediaRepository mediaRepository;
    private final ObjectMapper objectMapper;

    public HistoriaServiceImpl(HistoriaRepository historiaRepository, UsuarioRepository usuarioRepository, MediaRepository mediaRepository, ObjectMapper objectMapper) {
        this.historiaRepository = historiaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mediaRepository = mediaRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoriaDto> findAll() {
        return historiaRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HistoriaDto findById(Integer id) {
        return historiaRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoriaDto> findByUsuarioId(Integer idUsuario) {
        return historiaRepository.findByUsuarioId(idUsuario).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HistoriaDto save(HistoriaDto historiaDto) {
        Historia historia = toEntity(historiaDto);
        return toDto(historiaRepository.save(historia));
    }

    @Override
    @Transactional
    public HistoriaDto update(Integer id, HistoriaDto historiaDto) {
        return historiaRepository.findById(id).map(existingHistoria -> {
            BeanUtils.copyProperties(historiaDto, existingHistoria, "idHistoria", "fechaPublicacion", "usuario", "media");
            if (historiaDto.getIdUsuario() != null) {
                Usuario usuario = usuarioRepository.findById(historiaDto.getIdUsuario())
                        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + historiaDto.getIdUsuario()));
                existingHistoria.setUsuario(usuario);
            }
            if (historiaDto.getIdMedia() != null) {
                Media media = mediaRepository.findById(historiaDto.getIdMedia())
                        .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + historiaDto.getIdMedia()));
                existingHistoria.setMedia(media);
            }
            return toDto(historiaRepository.save(existingHistoria));
        }).orElse(null);
    }

    @Override
    @Transactional
    public HistoriaDto patch(Integer id, Map<String, Object> updates) {
        return historiaRepository.findById(id).map(existingHistoria -> {
            updates.forEach((key, value) -> {
                if ("idUsuario".equals(key)) {
                    Usuario usuario = usuarioRepository.findById((Integer) value)
                            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + value));
                    existingHistoria.setUsuario(usuario);
                } else if ("idMedia".equals(key)) {
                    Media media = mediaRepository.findById((Integer) value)
                            .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + value));
                    existingHistoria.setMedia(media);
                } else {
                    Field field = ReflectionUtils.findField(Historia.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        Object convertedValue = objectMapper.convertValue(value, field.getType());
                        ReflectionUtils.setField(field, existingHistoria, convertedValue);
                    }
                }
            });
            return toDto(historiaRepository.save(existingHistoria));
        }).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        historiaRepository.deleteById(id);
    }

    private HistoriaDto toDto(Historia historia) {
        HistoriaDto dto = new HistoriaDto();
        BeanUtils.copyProperties(historia, dto, "usuario", "media");
        if (historia.getUsuario() != null) {
            dto.setIdUsuario(historia.getUsuario().getId());
        }
        if (historia.getMedia() != null) {
            dto.setIdMedia(historia.getMedia().getIdMedia());
        }
        return dto;
    }

    private Historia toEntity(HistoriaDto dto) {
        Historia historia = new Historia();
        BeanUtils.copyProperties(dto, historia, "idUsuario", "idMedia");
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + dto.getIdUsuario()));
            historia.setUsuario(usuario);
        } else {
            throw new IllegalArgumentException("El campo idUsuario no puede ser nulo.");
        }
        if (dto.getIdMedia() != null) {
            Media media = mediaRepository.findById(dto.getIdMedia())
                    .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + dto.getIdMedia()));
            historia.setMedia(media);
        } else {
            throw new IllegalArgumentException("El campo idMedia no puede ser nulo.");
        }
        return historia;
    }
}
