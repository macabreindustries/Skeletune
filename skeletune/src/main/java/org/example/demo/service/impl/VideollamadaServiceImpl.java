package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.VideollamadaDto;
import org.example.demo.model.Usuario;
import org.example.demo.model.Videollamada;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.repository.VideollamadaRepository;
import org.example.demo.service.VideollamadaService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VideollamadaServiceImpl implements VideollamadaService {

    private final VideollamadaRepository videollamadaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public VideollamadaServiceImpl(VideollamadaRepository videollamadaRepository, UsuarioRepository usuarioRepository, ObjectMapper objectMapper) {
        this.videollamadaRepository = videollamadaRepository;
        this.usuarioRepository = usuarioRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideollamadaDto> findAll() {
        return videollamadaRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VideollamadaDto findById(Integer id) {
        return videollamadaRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Videollamada no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public VideollamadaDto save(VideollamadaDto videollamadaDto) {
        Videollamada videollamada = toEntity(videollamadaDto);
        return toDto(videollamadaRepository.save(videollamada));
    }

    @Override
    @Transactional
    public VideollamadaDto update(Integer id, VideollamadaDto videollamadaDto) {
        return videollamadaRepository.findById(id).map(existingVideollamada -> {
            BeanUtils.copyProperties(videollamadaDto, existingVideollamada, "idVideollamada", "fechaInicio", "emisor", "receptor");

            if (videollamadaDto.getIdEmisor() != null) {
                Usuario emisor = usuarioRepository.findById(videollamadaDto.getIdEmisor())
                        .orElseThrow(() -> new EntityNotFoundException("Emisor no encontrado con id: " + videollamadaDto.getIdEmisor()));
                existingVideollamada.setEmisor(emisor);
            }
            if (videollamadaDto.getIdReceptor() != null) {
                Usuario receptor = usuarioRepository.findById(videollamadaDto.getIdReceptor())
                        .orElseThrow(() -> new EntityNotFoundException("Receptor no encontrado con id: " + videollamadaDto.getIdReceptor()));
                existingVideollamada.setReceptor(receptor);
            }

            return toDto(videollamadaRepository.save(existingVideollamada));
        }).orElseThrow(() -> new EntityNotFoundException("Videollamada no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public VideollamadaDto patch(Integer id, Map<String, Object> updates) {
        return videollamadaRepository.findById(id).map(existingVideollamada -> {
            updates.forEach((key, value) -> {
                if ("idEmisor".equals(key)) {
                    Usuario emisor = usuarioRepository.findById((Integer) value)
                            .orElseThrow(() -> new EntityNotFoundException("Emisor no encontrado con id: " + value));
                    existingVideollamada.setEmisor(emisor);
                } else if ("idReceptor".equals(key)) {
                    Usuario receptor = usuarioRepository.findById((Integer) value)
                            .orElseThrow(() -> new EntityNotFoundException("Receptor no encontrado con id: " + value));
                    existingVideollamada.setReceptor(receptor);
                } else {
                    Field field = ReflectionUtils.findField(Videollamada.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        Object convertedValue = objectMapper.convertValue(value, field.getType());
                        ReflectionUtils.setField(field, existingVideollamada, convertedValue);
                    }
                }
            });
            return toDto(videollamadaRepository.save(existingVideollamada));
        }).orElseThrow(() -> new EntityNotFoundException("Videollamada no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!videollamadaRepository.existsById(id)) {
            throw new EntityNotFoundException("Videollamada no encontrada con id: " + id);
        }
        videollamadaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideollamadaDto> findByUsuario(Integer idUsuario) {
        return videollamadaRepository.findByEmisor_IdOrReceptor_Id(idUsuario, idUsuario).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private VideollamadaDto toDto(Videollamada videollamada) {
        VideollamadaDto dto = new VideollamadaDto();
        BeanUtils.copyProperties(videollamada, dto, "emisor", "receptor");
        if (videollamada.getEmisor() != null) {
            dto.setIdEmisor(videollamada.getEmisor().getId());
        }
        if (videollamada.getReceptor() != null) {
            dto.setIdReceptor(videollamada.getReceptor().getId());
        }
        return dto;
    }

    private Videollamada toEntity(VideollamadaDto dto) {
        Videollamada videollamada = new Videollamada();
        BeanUtils.copyProperties(dto, videollamada, "idEmisor", "idReceptor");

        if (dto.getIdEmisor() != null) {
            Usuario emisor = usuarioRepository.findById(dto.getIdEmisor())
                    .orElseThrow(() -> new EntityNotFoundException("Emisor no encontrado con id: " + dto.getIdEmisor()));
            videollamada.setEmisor(emisor);
        } else {
            throw new IllegalArgumentException("El idEmisor no puede ser nulo.");
        }

        if (dto.getIdReceptor() != null) {
            Usuario receptor = usuarioRepository.findById(dto.getIdReceptor())
                    .orElseThrow(() -> new EntityNotFoundException("Receptor no encontrado con id: " + dto.getIdReceptor()));
            videollamada.setReceptor(receptor);
        } else {
            throw new IllegalArgumentException("El idReceptor no puede ser nulo.");
        }

        return videollamada;
    }
}