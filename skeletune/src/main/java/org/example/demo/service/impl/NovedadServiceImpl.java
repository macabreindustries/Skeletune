package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.NovedadDto;
import org.example.demo.model.Novedad;
import org.example.demo.model.Usuario;
import org.example.demo.repository.NovedadRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.NovedadService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NovedadServiceImpl implements NovedadService {

    private final NovedadRepository novedadRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public NovedadServiceImpl(NovedadRepository novedadRepository, UsuarioRepository usuarioRepository, ObjectMapper objectMapper) {
        this.novedadRepository = novedadRepository;
        this.usuarioRepository = usuarioRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NovedadDto> findAll() {
        return novedadRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public NovedadDto findById(Integer id) {
        return novedadRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NovedadDto> findByAdminId(Integer idAdmin) {
        return novedadRepository.findByAdminId(idAdmin).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NovedadDto save(NovedadDto novedadDto) {
        Novedad novedad = toEntity(novedadDto);
        return toDto(novedadRepository.save(novedad));
    }

    @Override
    @Transactional
    public NovedadDto update(Integer id, NovedadDto novedadDto) {
        return novedadRepository.findById(id).map(existingNovedad -> {
            BeanUtils.copyProperties(novedadDto, existingNovedad, "idNovedad", "fechaPublicacion", "admin");
            if (novedadDto.getIdAdmin() != null) {
                Usuario admin = usuarioRepository.findById(novedadDto.getIdAdmin())
                        .orElseThrow(() -> new EntityNotFoundException("Usuario administrador no encontrado con id: " + novedadDto.getIdAdmin()));
                existingNovedad.setAdmin(admin);
            }
            return toDto(novedadRepository.save(existingNovedad));
        }).orElse(null);
    }

    @Override
    @Transactional
    public NovedadDto patch(Integer id, Map<String, Object> updates) {
        return novedadRepository.findById(id).map(existingNovedad -> {
            updates.forEach((key, value) -> {
                if ("idAdmin".equals(key)) {
                    Usuario admin = usuarioRepository.findById((Integer) value)
                            .orElseThrow(() -> new EntityNotFoundException("Usuario administrador no encontrado con id: " + value));
                    existingNovedad.setAdmin(admin);
                } else {
                    Field field = ReflectionUtils.findField(Novedad.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        Object convertedValue = objectMapper.convertValue(value, field.getType());
                        ReflectionUtils.setField(field, existingNovedad, convertedValue);
                    }
                }
            });
            return toDto(novedadRepository.save(existingNovedad));
        }).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        novedadRepository.deleteById(id);
    }

    private NovedadDto toDto(Novedad novedad) {
        NovedadDto dto = new NovedadDto();
        BeanUtils.copyProperties(novedad, dto, "admin");
        if (novedad.getAdmin() != null) {
            dto.setIdAdmin(novedad.getAdmin().getId());
        }
        return dto;
    }

    private Novedad toEntity(NovedadDto dto) {
        Novedad novedad = new Novedad();
        BeanUtils.copyProperties(dto, novedad, "idAdmin");
        if (dto.getIdAdmin() != null) {
            Usuario admin = usuarioRepository.findById(dto.getIdAdmin())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario administrador no encontrado con id: " + dto.getIdAdmin()));
            novedad.setAdmin(admin);
        } else {
            throw new IllegalArgumentException("El campo idAdmin no puede ser nulo.");
        }
        return novedad;
    }
}
