package org.example.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.ComentarioResponseDto;
import org.example.demo.dto.PublicacionDto;
import org.example.demo.dto.PublicacionFeedDto;
import org.example.demo.dto.UserProfileDto;
import org.example.demo.model.*;
import org.example.demo.repository.*;
import org.example.demo.service.PublicacionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final MediaRepository mediaRepository;
    private final LikePublicacionRepository likePublicacionRepository;
    private final ComentarioRepository comentarioRepository;

    public PublicacionServiceImpl(PublicacionRepository publicacionRepository,
                                  UsuarioRepository usuarioRepository,
                                  MediaRepository mediaRepository,
                                  LikePublicacionRepository likePublicacionRepository,
                                  ComentarioRepository comentarioRepository) {
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.mediaRepository = mediaRepository;
        this.likePublicacionRepository = likePublicacionRepository;
        this.comentarioRepository = comentarioRepository;
    }

    // ============================================================
    // CRUD MÉTODOS
    // ============================================================

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
        return publicacionRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(publicacionDto, existing, "idPublicacion", "fechaPublicacion");
            return toDto(publicacionRepository.save(existing));
        }).orElse(null);
    }

    @Override
    public PublicacionDto patch(Integer id, Map<String, Object> updates) { return null; }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        publicacionRepository.deleteById(id);
    }

    // ============================================================
    // SOCIAL MEDIA LOGIC (Feed, Likes, Comentarios, Perfil)
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<PublicacionFeedDto> getSocialFeed() {
        List<Publicacion> publicaciones = publicacionRepository.findAll();
        return publicaciones.stream().map(this::mapToFeedDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(Integer idUsuario) {
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        UserProfileDto dto = new UserProfileDto();
        dto.setNombre(user.getNombre());
        dto.setUrlAvatar(user.getUrlAvatar());

        // Obtener publicaciones del usuario convertidas a FeedDto (para el grid)
        List<Publicacion> misPubsEntidad = publicacionRepository.findByUsuarioId(idUsuario);
        List<PublicacionFeedDto> misPubsDto = misPubsEntidad.stream()
                .map(this::mapToFeedDto)
                .collect(Collectors.toList());

        dto.setMisPublicaciones(misPubsDto);
        dto.setSiguiendoCount(0); // Pendiente implementación lógica seguidores
        dto.setSeguidoresCount(0);

        int totalLikes = misPubsDto.stream().mapToInt(PublicacionFeedDto::getLikesCount).sum();
        dto.setTotalLikesRecibidos(totalLikes);

        return dto;
    }

    @Override
    @Transactional
    public void toggleLike(Integer idPublicacion, Integer idUsuario) {
        Optional<LikePublicacion> existingLike = likePublicacionRepository
                .findByUsuarioIdAndPublicacionIdPublicacion(idUsuario, idPublicacion);

        if (existingLike.isPresent()) {
            likePublicacionRepository.delete(existingLike.get());
        } else {
            LikePublicacion nuevoLike = new LikePublicacion();
            Usuario user = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            Publicacion pub = publicacionRepository.findById(idPublicacion)
                    .orElseThrow(() -> new EntityNotFoundException("Post no encontrado"));

            nuevoLike.setUsuario(user);
            nuevoLike.setPublicacion(pub);
            likePublicacionRepository.save(nuevoLike);
        }
    }

    @Override
    @Transactional
    public ComentarioResponseDto saveComentario(Integer idPublicacion, Integer idUsuario, String texto) {
        Publicacion pub = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new EntityNotFoundException("Publicación no encontrada"));
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Comentario nuevo = new Comentario();
        nuevo.setPublicacion(pub);
        nuevo.setUsuario(user);
        nuevo.setComentario(texto);
        Comentario guardado = comentarioRepository.save(nuevo);

        ComentarioResponseDto dto = new ComentarioResponseDto();
        dto.setComentario(guardado.getComentario());
        dto.setNombreUsuario(user.getNombre());
        dto.setAvatarUrl(user.getUrlAvatar());
        dto.setFechaRelativa("Ahora mismo");
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioResponseDto> getComentariosByPublicacionId(Integer idPublicacion) {
        return comentarioRepository.findByPublicacionIdPublicacion(idPublicacion).stream().map(c -> {
            ComentarioResponseDto dto = new ComentarioResponseDto();
            dto.setComentario(c.getComentario());
            if (c.getUsuario() != null) {
                dto.setNombreUsuario(c.getUsuario().getNombre());
                dto.setAvatarUrl(c.getUsuario().getUrlAvatar());
            }
            dto.setFechaRelativa(c.getFechaComentario() != null ?
                    calcularTiempoRelativo(c.getFechaComentario()) : "Reciente");
            return dto;
        }).collect(Collectors.toList());
    }

    // ============================================================
    // MÉTODOS DE MAPEO Y AUXILIARES
    // ============================================================

    private PublicacionFeedDto mapToFeedDto(Publicacion pub) {
        PublicacionFeedDto dto = new PublicacionFeedDto();
        dto.setIdPublicacion(pub.getIdPublicacion());
        dto.setTextoBody(pub.getTexto());
        dto.setTiempoPublicacion(pub.getFechaPublicacion() != null ?
                calcularTiempoRelativo(pub.getFechaPublicacion()) : "Reciente");

        if (pub.getUsuario() != null) {
            dto.setNombreUsuario(pub.getUsuario().getNombre());
            dto.setAvatarUrl(pub.getUsuario().getUrlAvatar());
        }
        if (pub.getMediaPrincipal() != null) {
            dto.setImageUrlContent(pub.getMediaPrincipal().getUrlArchivo());
        }

        dto.setLikesCount((int) likePublicacionRepository.countByPublicacionIdPublicacion(pub.getIdPublicacion()));
        dto.setCommentsCount((int) comentarioRepository.countByPublicacionIdPublicacion(pub.getIdPublicacion()));
        return dto;
    }

    private String calcularTiempoRelativo(LocalDateTime fecha) {
        Duration duration = Duration.between(fecha, LocalDateTime.now());
        if (duration.toMinutes() < 1) return "Ahora";
        if (duration.toMinutes() < 60) return "Hace " + duration.toMinutes() + " min";
        if (duration.toHours() < 24) return "Hace " + duration.toHours() + " h";
        return "Hace " + duration.toDays() + " d";
    }

    private PublicacionDto toDto(Publicacion publicacion) {
        PublicacionDto dto = new PublicacionDto();
        BeanUtils.copyProperties(publicacion, dto, "usuario", "mediaPrincipal");
        if (publicacion.getUsuario() != null) dto.setIdUsuario(publicacion.getUsuario().getId());
        if (publicacion.getMediaPrincipal() != null) dto.setIdMediaPrincipal(publicacion.getMediaPrincipal().getIdMedia());
        return dto;
    }

    private Publicacion toEntity(PublicacionDto dto) {
        Publicacion p = new Publicacion();
        BeanUtils.copyProperties(dto, p);
        return p;
    }

    @Override public void addMediaToPublicacion(Integer idPub, Integer idMedia) {}
    @Override public void removeMediaFromPublicacion(Integer idPub, Integer idMedia) {}
}