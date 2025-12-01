package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.ProgresoDto;
import org.example.demo.model.Leccion;
import org.example.demo.model.Progreso;
import org.example.demo.model.Usuario;
import org.example.demo.repository.LeccionRepository;
import org.example.demo.repository.ProgresoRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.ProgresoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProgresoServiceImpl implements ProgresoService {

    private final ProgresoRepository progresoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LeccionRepository leccionRepository;
    private final ObjectMapper objectMapper;

    public ProgresoServiceImpl(ProgresoRepository progresoRepository, UsuarioRepository usuarioRepository, LeccionRepository leccionRepository, ObjectMapper objectMapper) {
        this.progresoRepository = progresoRepository;
        this.usuarioRepository = usuarioRepository;
        this.leccionRepository = leccionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgresoDto> findAll(Integer idUsuario, Integer idLeccion, LocalDate fecha) {
        List<Progreso> progresos;
        if (idUsuario != null && fecha != null) {
            progresos = progresoRepository.findByUsuarioAndFecha(idUsuario, fecha);
        } else if (idUsuario != null) {
            progresos = progresoRepository.findByUsuario_Id(idUsuario);
        } else if (idLeccion != null) {
            progresos = progresoRepository.findByLeccion_IdLeccion(idLeccion);
        } else if (fecha != null) {
            progresos = progresoRepository.findByFecha(fecha);
        } else {
            progresos = progresoRepository.findAll();
        }
        return progresos.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProgresoDto findById(Integer id) {
        return progresoRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Progreso no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public ProgresoDto save(ProgresoDto progresoDto) {
        Progreso progreso = toEntity(progresoDto);
        return toDto(progresoRepository.save(progreso));
    }

    @Override
    @Transactional
    public ProgresoDto update(Integer id, ProgresoDto progresoDto) {
        return progresoRepository.findById(id).map(existingProgreso -> {
            BeanUtils.copyProperties(progresoDto, existingProgreso, "idProgreso", "usuario", "leccion");
            if (progresoDto.getIdUsuario() != null) {
                Usuario usuario = usuarioRepository.findById(progresoDto.getIdUsuario())
                        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + progresoDto.getIdUsuario()));
                existingProgreso.setUsuario(usuario);
            }
            if (progresoDto.getIdLeccion() != null) {
                Leccion leccion = leccionRepository.findById(progresoDto.getIdLeccion())
                        .orElseThrow(() -> new EntityNotFoundException("Leccion no encontrada con id: " + progresoDto.getIdLeccion()));
                existingProgreso.setLeccion(leccion);
            }
            return toDto(progresoRepository.save(existingProgreso));
        }).orElseThrow(() -> new EntityNotFoundException("Progreso no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public ProgresoDto patch(Integer id, Map<String, Object> updates) {
        return progresoRepository.findById(id).map(existingProgreso -> {
            updates.forEach((key, value) -> {
                if ("idUsuario".equals(key)) {
                    Usuario usuario = usuarioRepository.findById((Integer) value)
                            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + value));
                    existingProgreso.setUsuario(usuario);
                } else if ("idLeccion".equals(key)) {
                    Leccion leccion = leccionRepository.findById((Integer) value)
                            .orElseThrow(() -> new EntityNotFoundException("Leccion no encontrada con id: " + value));
                    existingProgreso.setLeccion(leccion);
                } else {
                    Field field = ReflectionUtils.findField(Progreso.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        Object convertedValue = objectMapper.convertValue(value, field.getType());
                        ReflectionUtils.setField(field, existingProgreso, convertedValue);
                    }
                }
            });
            return toDto(progresoRepository.save(existingProgreso));
        }).orElseThrow(() -> new EntityNotFoundException("Progreso no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!progresoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar el progreso con id: " + id + " porque no existe.");
        }
        progresoRepository.deleteById(id);
    }

    private ProgresoDto toDto(Progreso progreso) {
        ProgresoDto dto = new ProgresoDto();
        BeanUtils.copyProperties(progreso, dto, "usuario", "leccion");
        if (progreso.getUsuario() != null) {
            dto.setIdUsuario(progreso.getUsuario().getId());
        }
        if (progreso.getLeccion() != null) {
            dto.setIdLeccion(progreso.getLeccion().getIdLeccion());
        }
        return dto;
    }

    private Progreso toEntity(ProgresoDto dto) {
        Progreso progreso = new Progreso();
        BeanUtils.copyProperties(dto, progreso, "idUsuario", "idLeccion");
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + dto.getIdUsuario()));
            progreso.setUsuario(usuario);
        } else {
            throw new IllegalArgumentException("El campo idUsuario no puede ser nulo.");
        }
        if (dto.getIdLeccion() != null) {
            Leccion leccion = leccionRepository.findById(dto.getIdLeccion())
                    .orElseThrow(() -> new EntityNotFoundException("Leccion no encontrada con id: " + dto.getIdLeccion()));
            progreso.setLeccion(leccion);
        }
        return progreso;
    }
}
