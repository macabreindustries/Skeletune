package org.example.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.MediaDto;
import org.example.demo.dto.PublicacionMediaDto;
import org.example.demo.model.Media;
import org.example.demo.model.Publicacion;
import org.example.demo.model.PublicacionMedia;
import org.example.demo.model.PublicacionMediaId;
import org.example.demo.repository.MediaRepository;
import org.example.demo.repository.PublicacionMediaRepository;
import org.example.demo.repository.PublicacionRepository;
import org.example.demo.service.PublicacionMediaService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionMediaServiceImpl implements PublicacionMediaService {

    private final PublicacionMediaRepository publicacionMediaRepository;
    private final PublicacionRepository publicacionRepository;
    private final MediaRepository mediaRepository;

    public PublicacionMediaServiceImpl(PublicacionMediaRepository publicacionMediaRepository, PublicacionRepository publicacionRepository, MediaRepository mediaRepository) {
        this.publicacionMediaRepository = publicacionMediaRepository;
        this.publicacionRepository = publicacionRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaDto> getMediaByPublicacionId(Integer idPublicacion) {
        return publicacionMediaRepository.findById_IdPublicacion(idPublicacion).stream()
                .map(PublicacionMedia::getMedia)
                .map(this::toMediaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PublicacionMediaDto addMediaToPublicacion(Integer idPublicacion, Integer idMedia) {
        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new EntityNotFoundException("Publicacion no encontrada con id: " + idPublicacion));
        Media media = mediaRepository.findById(idMedia)
                .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + idMedia));

        PublicacionMediaId id = new PublicacionMediaId(idPublicacion, idMedia);
        if (publicacionMediaRepository.existsById(id)) {
            throw new IllegalStateException("La media ya está asociada a la publicación.");
        }

        PublicacionMedia publicacionMedia = new PublicacionMedia(id, publicacion, media);
        publicacionMediaRepository.save(publicacionMedia);

        return toDto(publicacionMedia);
    }

    @Override
    @Transactional
    public void removeMediaFromPublicacion(Integer idPublicacion, Integer idMedia) {
        PublicacionMediaId id = new PublicacionMediaId(idPublicacion, idMedia);
        if (!publicacionMediaRepository.existsById(id)) {
            throw new EntityNotFoundException("La relación entre publicación y media no existe.");
        }
        publicacionMediaRepository.deleteById(id);
    }

    private PublicacionMediaDto toDto(PublicacionMedia publicacionMedia) {
        return new PublicacionMediaDto(
                publicacionMedia.getId().getIdPublicacion(),
                publicacionMedia.getId().getIdMedia()
        );
    }

    private MediaDto toMediaDto(Media media) {
        MediaDto dto = new MediaDto();
        BeanUtils.copyProperties(media, dto, "usuario");
        if (media.getUsuario() != null) {
            dto.setIdUsuario(media.getUsuario().getId());
        }
        return dto;
    }
}
