package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.LeccionDto;
import org.example.demo.model.Cancion;
import org.example.demo.model.Leccion;
import org.example.demo.model.VideoEducativo;
import org.example.demo.repository.CancionRepository;
import org.example.demo.repository.LeccionRepository;
import org.example.demo.repository.VideoEducativoRepository;
import org.example.demo.service.LeccionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeccionServiceImpl implements LeccionService {

    private final LeccionRepository leccionRepository;
    private final CancionRepository cancionRepository;
    private final VideoEducativoRepository videoEducativoRepository;
    private final ObjectMapper objectMapper;

    public LeccionServiceImpl(LeccionRepository leccionRepository, CancionRepository cancionRepository, VideoEducativoRepository videoEducativoRepository, ObjectMapper objectMapper) {
        this.leccionRepository = leccionRepository;
        this.cancionRepository = cancionRepository;
        this.videoEducativoRepository = videoEducativoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeccionDto> findAll() {
        return leccionRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LeccionDto findById(Integer id) {
        return leccionRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public LeccionDto findByTitulo(String titulo) {
        return leccionRepository.findByTitulo(titulo).map(this::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeccionDto> findByTipo(Leccion.Tipo tipo) {
        return leccionRepository.findByTipo(tipo).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeccionDto> findByNivel(Leccion.Nivel nivel) {
        return leccionRepository.findByNivel(nivel).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeccionDto> findByCancionId(Integer idCancion) {
        return leccionRepository.findByCancionIdCancion(idCancion).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeccionDto> findByVideoId(Integer idVideo) {
        return leccionRepository.findByVideoIdVideo(idVideo).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LeccionDto save(LeccionDto leccionDto) {
        Leccion leccion = toEntity(leccionDto);
        return toDto(leccionRepository.save(leccion));
    }

    @Override
    @Transactional
    public LeccionDto update(Integer id, LeccionDto leccionDto) {
        return leccionRepository.findById(id).map(existingLeccion -> {
            BeanUtils.copyProperties(leccionDto, existingLeccion, "idLeccion", "cancion", "video");
            if (leccionDto.getIdCancion() != null) {
                Cancion cancion = cancionRepository.findById(leccionDto.getIdCancion())
                        .orElseThrow(() -> new EntityNotFoundException("Canción no encontrada con id: " + leccionDto.getIdCancion()));
                existingLeccion.setCancion(cancion);
            } else {
                existingLeccion.setCancion(null);
            }
            if (leccionDto.getIdVideo() != null) {
                VideoEducativo video = videoEducativoRepository.findById(leccionDto.getIdVideo())
                        .orElseThrow(() -> new EntityNotFoundException("Video educativo no encontrado con id: " + leccionDto.getIdVideo()));
                existingLeccion.setVideo(video);
            } else {
                existingLeccion.setVideo(null);
            }
            return toDto(leccionRepository.save(existingLeccion));
        }).orElse(null);
    }

    @Override
    @Transactional
    public LeccionDto patch(Integer id, Map<String, Object> updates) {
        return leccionRepository.findById(id).map(existingLeccion -> {
            updates.forEach((key, value) -> {
                if ("idCancion".equals(key)) {
                    if (value == null) {
                        existingLeccion.setCancion(null);
                    } else {
                        Cancion cancion = cancionRepository.findById((Integer) value)
                                .orElseThrow(() -> new EntityNotFoundException("Canción no encontrada con id: " + value));
                        existingLeccion.setCancion(cancion);
                    }
                } else if ("idVideo".equals(key)) {
                    if (value == null) {
                        existingLeccion.setVideo(null);
                    } else {
                        VideoEducativo video = videoEducativoRepository.findById((Integer) value)
                                .orElseThrow(() -> new EntityNotFoundException("Video educativo no encontrado con id: " + value));
                        existingLeccion.setVideo(video);
                    }
                } else {
                    Field field = ReflectionUtils.findField(Leccion.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        Object convertedValue = objectMapper.convertValue(value, field.getType());
                        ReflectionUtils.setField(field, existingLeccion, convertedValue);
                    }
                }
            });
            return toDto(leccionRepository.save(existingLeccion));
        }).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        leccionRepository.deleteById(id);
    }

    private LeccionDto toDto(Leccion leccion) {
        LeccionDto dto = new LeccionDto();
        BeanUtils.copyProperties(leccion, dto, "cancion", "video");
        if (leccion.getCancion() != null) {
            dto.setIdCancion(leccion.getCancion().getIdCancion());
        }
        if (leccion.getVideo() != null) {
            dto.setIdVideo(leccion.getVideo().getIdVideo());
        }
        return dto;
    }

    private Leccion toEntity(LeccionDto dto) {
        Leccion leccion = new Leccion();
        BeanUtils.copyProperties(dto, leccion, "idCancion", "idVideo");
        if (dto.getIdCancion() != null) {
            Cancion cancion = cancionRepository.findById(dto.getIdCancion())
                    .orElseThrow(() -> new EntityNotFoundException("Canción no encontrada con id: " + dto.getIdCancion()));
            leccion.setCancion(cancion);
        }
        if (dto.getIdVideo() != null) {
            VideoEducativo video = videoEducativoRepository.findById(dto.getIdVideo())
                    .orElseThrow(() -> new EntityNotFoundException("Video educativo no encontrado con id: " + dto.getIdVideo()));
            leccion.setVideo(video);
        }
        return leccion;
    }
}
