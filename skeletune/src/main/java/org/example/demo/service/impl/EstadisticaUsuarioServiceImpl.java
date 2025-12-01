package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.EstadisticaUsuarioDto;
import org.example.demo.model.EstadisticaUsuario;
import org.example.demo.model.Usuario;
import org.example.demo.repository.EstadisticaUsuarioRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.EstadisticaUsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class EstadisticaUsuarioServiceImpl implements EstadisticaUsuarioService {

    private final EstadisticaUsuarioRepository estadisticaUsuarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public EstadisticaUsuarioServiceImpl(EstadisticaUsuarioRepository estadisticaUsuarioRepository, UsuarioRepository usuarioRepository, ObjectMapper objectMapper) {
        this.estadisticaUsuarioRepository = estadisticaUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public EstadisticaUsuarioDto getEstadisticasByUsuarioId(Integer idUsuario) {
        return estadisticaUsuarioRepository.findByUsuarioId(idUsuario)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Estadísticas no encontradas para el usuario con id: " + idUsuario));
    }

    @Override
    @Transactional
    public EstadisticaUsuarioDto createEstadisticas(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + idUsuario));

        if (estadisticaUsuarioRepository.findByUsuarioId(idUsuario).isPresent()) {
            throw new IllegalStateException("Las estadísticas para este usuario ya existen.");
        }

        EstadisticaUsuario estadistica = new EstadisticaUsuario();
        estadistica.setUsuario(usuario);
        return toDto(estadisticaUsuarioRepository.save(estadistica));
    }

    @Override
    @Transactional
    public EstadisticaUsuarioDto updateEstadisticas(Integer idUsuario, EstadisticaUsuarioDto estadisticaDto) {
        return estadisticaUsuarioRepository.findByUsuarioId(idUsuario).map(existingEstadistica -> {
            // Solo se actualizan los campos que no son el ID y la fecha de actualización
            existingEstadistica.setTotalMinutosPractica(estadisticaDto.getTotalMinutosPractica());
            existingEstadistica.setLeccionesCompletadas(estadisticaDto.getLeccionesCompletadas());
            existingEstadistica.setCancionesAprendidas(estadisticaDto.getCancionesAprendidas());
            existingEstadistica.setRachaDias(estadisticaDto.getRachaDias());
            existingEstadistica.setNivelGeneral(estadisticaDto.getNivelGeneral());
            return toDto(estadisticaUsuarioRepository.save(existingEstadistica));
        }).orElseThrow(() -> new EntityNotFoundException("Estadísticas no encontradas para el usuario con id: " + idUsuario));
    }

    @Override
    @Transactional
    public EstadisticaUsuarioDto patchEstadisticas(Integer idUsuario, Map<String, Object> updates) {
        return estadisticaUsuarioRepository.findByUsuarioId(idUsuario).map(existingEstadistica -> {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(EstadisticaUsuario.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    Object convertedValue = objectMapper.convertValue(value, field.getType());
                    ReflectionUtils.setField(field, existingEstadistica, convertedValue);
                }
            });
            return toDto(estadisticaUsuarioRepository.save(existingEstadistica));
        }).orElseThrow(() -> new EntityNotFoundException("Estadísticas no encontradas para el usuario con id: " + idUsuario));
    }

    private EstadisticaUsuarioDto toDto(EstadisticaUsuario estadistica) {
        EstadisticaUsuarioDto dto = new EstadisticaUsuarioDto();
        BeanUtils.copyProperties(estadistica, dto, "usuario");
        if (estadistica.getUsuario() != null) {
            dto.setIdUsuario(estadistica.getUsuario().getId());
        }
        return dto;
    }

    private EstadisticaUsuario toEntity(EstadisticaUsuarioDto dto) {
        EstadisticaUsuario estadistica = new EstadisticaUsuario();
        BeanUtils.copyProperties(dto, estadistica, "idUsuario");
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + dto.getIdUsuario()));
            estadistica.setUsuario(usuario);
        } else {
            throw new IllegalArgumentException("El campo idUsuario no puede ser nulo.");
        }
        return estadistica;
    }
}
