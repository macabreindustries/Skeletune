package org.example.demo.service.impl;

import org.example.demo.dto.VideoEducativoDto;
import org.example.demo.model.Media;
import org.example.demo.model.Usuario;
import org.example.demo.model.VideoEducativo;
import org.example.demo.repository.MediaRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.repository.VideoEducativoRepository;
import org.example.demo.service.VideoEducativoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoEducativoServiceImpl implements VideoEducativoService {

    private final VideoEducativoRepository videoEducativoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MediaRepository mediaRepository;

    public VideoEducativoServiceImpl(VideoEducativoRepository videoEducativoRepository,
                                     UsuarioRepository usuarioRepository,
                                     MediaRepository mediaRepository) {
        this.videoEducativoRepository = videoEducativoRepository;
        this.usuarioRepository = usuarioRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public List<VideoEducativoDto> findAll(Integer idProfesor, String titulo) {
        List<VideoEducativo> videos = videoEducativoRepository.findAll();

        return videos.stream()
                .filter(v -> idProfesor == null || (v.getProfesor() != null && v.getProfesor().getId().equals(idProfesor)))
                .filter(v -> titulo == null || (v.getTitulo() != null && v.getTitulo().toLowerCase().contains(titulo.toLowerCase())))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VideoEducativoDto findById(Integer idVideo) {
        return videoEducativoRepository.findById(idVideo)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public VideoEducativoDto save(VideoEducativoDto videoEducativoDto) {
        VideoEducativo videoEducativo = convertToEntity(videoEducativoDto);
        videoEducativo.setFechaSubida(LocalDateTime.now());
        videoEducativo = videoEducativoRepository.save(videoEducativo);
        return convertToDto(videoEducativo);
    }

    @Override
    public VideoEducativoDto update(Integer idVideo, VideoEducativoDto videoEducativoDto) {
        Optional<VideoEducativo> existingVideoEducativo = videoEducativoRepository.findById(idVideo);
        if (existingVideoEducativo.isPresent()) {
            VideoEducativo videoEducativo = existingVideoEducativo.get();
            updateEntityFromDto(videoEducativo, videoEducativoDto);
            videoEducativo = videoEducativoRepository.save(videoEducativo);
            return convertToDto(videoEducativo);
        }
        return null;
    }

    @Override
    public VideoEducativoDto patch(Integer idVideo, Map<String, Object> updates) {
        Optional<VideoEducativo> existingVideoEducativo = videoEducativoRepository.findById(idVideo);
        if (existingVideoEducativo.isPresent()) {
            VideoEducativo videoEducativoToUpdate = existingVideoEducativo.get();

            if (updates.containsKey("idProfesor")) {
                usuarioRepository.findById(((Number) updates.get("idProfesor")).intValue())
                        .ifPresent(videoEducativoToUpdate::setProfesor);
            }
            if (updates.containsKey("titulo")) {
                videoEducativoToUpdate.setTitulo((String) updates.get("titulo"));
            }
            if (updates.containsKey("descripcion")) {
                videoEducativoToUpdate.setDescripcion((String) updates.get("descripcion"));
            }
            if (updates.containsKey("urlVideo")) {
                videoEducativoToUpdate.setUrlVideo((String) updates.get("urlVideo"));
            }
            if (updates.containsKey("idThumbnailMedia")) {
                mediaRepository.findById(((Number) updates.get("idThumbnailMedia")).intValue())
                        .ifPresent(videoEducativoToUpdate::setThumbnailMedia);
            }

            VideoEducativo updatedVideoEducativo = videoEducativoRepository.save(videoEducativoToUpdate);
            return convertToDto(updatedVideoEducativo);
        }
        return null;
    }

    @Override
    public void deleteById(Integer idVideo) {
        videoEducativoRepository.deleteById(idVideo);
    }

    @Override
    public List<String> findAllTitulos() {
        return videoEducativoRepository.findAll().stream()
                .map(VideoEducativo::getTitulo)
                .distinct()
                .collect(Collectors.toList());
    }

    private VideoEducativoDto convertToDto(VideoEducativo videoEducativo) {
        VideoEducativoDto dto = new VideoEducativoDto();
        dto.setIdVideo(videoEducativo.getIdVideo());
        if (videoEducativo.getProfesor() != null) {
            dto.setIdProfesor(videoEducativo.getProfesor().getId());
        }
        dto.setTitulo(videoEducativo.getTitulo());
        dto.setDescripcion(videoEducativo.getDescripcion());
        dto.setUrlVideo(videoEducativo.getUrlVideo());
        if (videoEducativo.getThumbnailMedia() != null) {
            dto.setIdThumbnailMedia(videoEducativo.getThumbnailMedia().getIdMedia());
        }
        dto.setFechaSubida(videoEducativo.getFechaSubida());
        return dto;
    }

    private VideoEducativo convertToEntity(VideoEducativoDto dto) {
        VideoEducativo videoEducativo = new VideoEducativo();
        videoEducativo.setIdVideo(dto.getIdVideo());
        if (dto.getIdProfesor() != null) {
            usuarioRepository.findById(dto.getIdProfesor()).ifPresent(videoEducativo::setProfesor);
        }
        videoEducativo.setTitulo(dto.getTitulo());
        videoEducativo.setDescripcion(dto.getDescripcion());
        videoEducativo.setUrlVideo(dto.getUrlVideo());
        if (dto.getIdThumbnailMedia() != null) {
            mediaRepository.findById(dto.getIdThumbnailMedia()).ifPresent(videoEducativo::setThumbnailMedia);
        }
        videoEducativo.setFechaSubida(dto.getFechaSubida());
        return videoEducativo;
    }

    private void updateEntityFromDto(VideoEducativo videoEducativo, VideoEducativoDto dto) {
        if (dto.getIdProfesor() != null) {
            usuarioRepository.findById(dto.getIdProfesor()).ifPresent(videoEducativo::setProfesor);
        }
        videoEducativo.setTitulo(dto.getTitulo());
        videoEducativo.setDescripcion(dto.getDescripcion());
        videoEducativo.setUrlVideo(dto.getUrlVideo());
        if (dto.getIdThumbnailMedia() != null) {
            mediaRepository.findById(dto.getIdThumbnailMedia()).ifPresent(videoEducativo::setThumbnailMedia);
        }
    }
}
