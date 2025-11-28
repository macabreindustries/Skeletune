package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.CancionDto;
import org.example.demo.model.Cancion;
import org.example.demo.model.Usuario;
import org.example.demo.repository.CancionRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.CancionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CancionServiceImpl implements CancionService {

    private final CancionRepository cancionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public CancionServiceImpl(CancionRepository cancionRepository, UsuarioRepository usuarioRepository, ObjectMapper objectMapper) {
        this.cancionRepository = cancionRepository;
        this.usuarioRepository = usuarioRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CancionDto> findAll(String titulo, String artista, Cancion.Dificultad dificultad, String urlAudio, String urlPartitura, String imagenUrl) {
        List<Cancion> canciones;
        if (titulo != null) {
            canciones = cancionRepository.findByTituloContainingIgnoreCase(titulo);
        } else if (artista != null) {
            canciones = cancionRepository.findByArtistaContainingIgnoreCase(artista);
        } else if (dificultad != null) {
            canciones = cancionRepository.findByDificultad(dificultad);
        } else if (urlAudio != null) {
            canciones = cancionRepository.findByUrlAudio(urlAudio);
        } else if (urlPartitura != null) {
            canciones = cancionRepository.findByUrlPartitura(urlPartitura);
        } else if (imagenUrl != null) { // Nuevo filtro por imagenUrl
            canciones = cancionRepository.findByImagenUrl(imagenUrl);
        }
        else {
            canciones = cancionRepository.findAll();
        }
        return canciones.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CancionDto findByTitulo(String titulo) {
        return cancionRepository.findByTitulo(titulo).map(this::toDto).orElse(null);
    }

    @Override
    @Transactional
    public CancionDto save(CancionDto cancionDto) {
        Cancion cancion = toEntity(cancionDto);
        return toDto(cancionRepository.save(cancion));
    }

    @Override
    @Transactional
    public CancionDto update(String titulo, CancionDto cancionDto) {
        return cancionRepository.findByTitulo(titulo).map(existingCancion -> {
            BeanUtils.copyProperties(cancionDto, existingCancion, "idCancion", "fechaSubida", "admin");
            if (cancionDto.getIdAdmin() != null) {
                Usuario admin = usuarioRepository.findById(cancionDto.getIdAdmin())
                        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + cancionDto.getIdAdmin()));
                existingCancion.setAdmin(admin);
            }
            return toDto(cancionRepository.save(existingCancion));
        }).orElse(null);
    }

    @Override
    @Transactional
    public CancionDto patch(String titulo, Map<String, Object> updates) {
        return cancionRepository.findByTitulo(titulo).map(existingCancion -> {
            updates.forEach((key, value) -> {
                if ("idAdmin".equals(key)) {
                    Usuario admin = usuarioRepository.findById((Integer) value)
                            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + value));
                    existingCancion.setAdmin(admin);
                } else if ("imagenUrl".equals(key)) { // Nuevo campo en patch
                    existingCancion.setImagenUrl((String) value);
                }
                else {
                    Field field = ReflectionUtils.findField(Cancion.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        // Usa ObjectMapper para manejar conversiones (ej. String a Enum)
                        Object convertedValue = objectMapper.convertValue(value, field.getType());
                        ReflectionUtils.setField(field, existingCancion, convertedValue);
                    }
                }
            });
            return toDto(cancionRepository.save(existingCancion));
        }).orElse(null);
    }

    @Override
    @Transactional
    public void deleteByTitulo(String titulo) {
        cancionRepository.deleteByTitulo(titulo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllTitulos() {
        return cancionRepository.findAllTitulos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllArtistas() {
        return cancionRepository.findAllArtistas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cancion.Dificultad> findAllDificultades() {
        return cancionRepository.findAllDificultades();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllUrlAudios() {
        return cancionRepository.findAllUrlAudios();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllUrlPartituras() {
        return cancionRepository.findAllUrlPartituras();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllImagenUrls() { // Nuevo método
        return cancionRepository.findAllImagenUrls();
    }

    private CancionDto toDto(Cancion cancion) {
        CancionDto dto = new CancionDto();
        BeanUtils.copyProperties(cancion, dto, "admin");
        if (cancion.getAdmin() != null) {
            dto.setIdAdmin(cancion.getAdmin().getId());
        }
        return dto;
    }

    private Cancion toEntity(CancionDto dto) {
        Cancion cancion = new Cancion();
        BeanUtils.copyProperties(dto, cancion, "idAdmin");
        if (dto.getIdAdmin() != null) {
            Usuario admin = usuarioRepository.findById(dto.getIdAdmin())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + dto.getIdAdmin()));
            cancion.setAdmin(admin);
        } else {
            throw new IllegalArgumentException("El campo idAdmin no puede ser nulo.");
        }
        return cancion;
    }
}
