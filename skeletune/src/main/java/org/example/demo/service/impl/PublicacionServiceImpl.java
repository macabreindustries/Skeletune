package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.PublicacionDto;
import org.example.demo.model.Media;
import org.example.demo.model.Publicacion;
import org.example.demo.model.Usuario;
import org.example.demo.repository.MediaRepository;
import org.example.demo.repository.PublicacionRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.PublicacionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final MediaRepository mediaRepository;
    private final ObjectMapper objectMapper;

    public PublicacionServiceImpl(PublicacionRepository publicacionRepository, UsuarioRepository usuarioRepository, MediaRepository mediaRepository, ObjectMapper objectMapper) {
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.mediaRepository = mediaRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicacionDto> findAll() {
        return publicacionRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PublicacionDto findById(Integer id) {
        return publicacionRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicacionDto> findByUsuarioId(Integer idUsuario) {
        return publicacionRepository.findByUsuarioId(idUsuario).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PublicacionDto save(PublicacionDto publicacionDto) {
        Publicacion publicacion = toEntity(publicacionDto);
        return toDto(publicacionRepository.save(publicacion));
    }

    @Override
    @Transactional
    public PublicacionDto update(Integer id, PublicacionDto publicacionDto) {
        return publicacionRepository.findById(id).map(existingPublicacion -> {
            BeanUtils.copyProperties(publicacionDto, existingPublicacion, "idPublicacion", "fechaPublicacion", "usuario", "mediaPrincipal", "mediaAdjuntos");
            if (publicacionDto.getIdUsuario() != null) {
                Usuario usuario = usuarioRepository.findById(publicacionDto.getIdUsuario())
                        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + publicacionDto.getIdUsuario()));
                existingPublicacion.setUsuario(usuario);
            }
            if (publicacionDto.getIdMediaPrincipal() != null) {
                Media media = mediaRepository.findById(publicacionDto.getIdMediaPrincipal())
                        .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + publicacionDto.getIdMediaPrincipal()));
                existingPublicacion.setMediaPrincipal(media);
            } else {
                existingPublicacion.setMediaPrincipal(null);
            }
            return toDto(publicacionRepository.save(existingPublicacion));
        }).orElse(null);
    }

    @Override
    @Transactional
    public PublicacionDto patch(Integer id, Map<String, Object> updates) {
        return publicacionRepository.findById(id).map(existingPublicacion -> {
            updates.forEach((key, value) -> {
                if ("idUsuario".equals(key)) {
                    Usuario usuario = usuarioRepository.findById((Integer) value)
                            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + value));
                    existingPublicacion.setUsuario(usuario);
                } else if ("idMediaPrincipal".equals(key)) {
                    if (value == null) {
                        existingPublicacion.setMediaPrincipal(null);
                    } else {
                        Media media = mediaRepository.findById((Integer) value)
                                .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + value));
                        existingPublicacion.setMediaPrincipal(media);
                    }
                } else {
                    Field field = ReflectionUtils.findField(Publicacion.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        Object convertedValue = objectMapper.convertValue(value, field.getType());
                        ReflectionUtils.setField(field, existingPublicacion, convertedValue);
                    }
                }
            });
            return toDto(publicacionRepository.save(existingPublicacion));
        }).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        publicacionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addMediaToPublicacion(Integer idPublicacion, Integer idMedia) {
        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new EntityNotFoundException("Publicacion no encontrada con id: " + idPublicacion));
        Media media = mediaRepository.findById(idMedia)
                .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + idMedia));
        publicacion.getMediaAdjuntos().add(media);
        publicacionRepository.save(publicacion);
    }

    @Override
    @Transactional
    public void removeMediaFromPublicacion(Integer idPublicacion, Integer idMedia) {
        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new EntityNotFoundException("Publicacion no encontrada con id: " + idPublicacion));
        Media media = mediaRepository.findById(idMedia)
                .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + idMedia));
        publicacion.getMediaAdjuntos().remove(media);
        publicacionRepository.save(publicacion);
    }

    private PublicacionDto toDto(Publicacion publicacion) {
        PublicacionDto dto = new PublicacionDto();
        BeanUtils.copyProperties(publicacion, dto, "usuario", "mediaPrincipal", "mediaAdjuntos");
        if (publicacion.getUsuario() != null) {
            dto.setIdUsuario(publicacion.getUsuario().getId());
        }
        if (publicacion.getMediaPrincipal() != null) {
            dto.setIdMediaPrincipal(publicacion.getMediaPrincipal().getIdMedia());
        }
        if (publicacion.getMediaAdjuntos() != null) {
            dto.setMediaAdjuntosIds(publicacion.getMediaAdjuntos().stream()
                    .map(Media::getIdMedia)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    private Publicacion toEntity(PublicacionDto dto) {
        Publicacion publicacion = new Publicacion();
        BeanUtils.copyProperties(dto, publicacion, "idUsuario", "idMediaPrincipal", "mediaAdjuntosIds");
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + dto.getIdUsuario()));
            publicacion.setUsuario(usuario);
        } else {
            throw new IllegalArgumentException("El campo idUsuario no puede ser nulo.");
        }
        if (dto.getIdMediaPrincipal() != null) {
            Media media = mediaRepository.findById(dto.getIdMediaPrincipal())
                    .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + dto.getIdMediaPrincipal()));
            publicacion.setMediaPrincipal(media);
        }
        if (dto.getMediaAdjuntosIds() != null) {
            dto.getMediaAdjuntosIds().forEach(idMedia -> {
                Media media = mediaRepository.findById(idMedia)
                        .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + idMedia));
                publicacion.getMediaAdjuntos().add(media);
            });
        }
        return publicacion;
    }
}
