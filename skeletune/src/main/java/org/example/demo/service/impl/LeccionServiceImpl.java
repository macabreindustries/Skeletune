package org.example.demo.service.impl;

import org.example.demo.dto.LeccionDto;
import org.example.demo.model.Cancion;
import org.example.demo.model.Leccion;
import org.example.demo.model.VideoEducativo;
import org.example.demo.repository.CancionRepository;
import org.example.demo.repository.LeccionRepository;
import org.example.demo.repository.VideoEducativoRepository;
import org.example.demo.service.LeccionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeccionServiceImpl implements LeccionService {

    private final LeccionRepository leccionRepository;
    private final CancionRepository cancionRepository;
    private final VideoEducativoRepository videoEducativoRepository;

    public LeccionServiceImpl(LeccionRepository leccionRepository,
                              CancionRepository cancionRepository,
                              VideoEducativoRepository videoEducativoRepository) {
        this.leccionRepository = leccionRepository;
        this.cancionRepository = cancionRepository;
        this.videoEducativoRepository = videoEducativoRepository;
    }

    @Override
    public List<LeccionDto> findAll(String titulo, Leccion.Tipo tipo, Leccion.Nivel nivel, Integer idCancion, Integer idVideo) {
        List<Leccion> lecciones = leccionRepository.findAll();

        return lecciones.stream()
                .filter(l -> titulo == null || (l.getTitulo() != null && l.getTitulo().toLowerCase().contains(titulo.toLowerCase())))
                .filter(l -> tipo == null || l.getTipo().equals(tipo))
                .filter(l -> nivel == null || l.getNivel().equals(nivel))
                .filter(l -> idCancion == null || (l.getCancion() != null && l.getCancion().getIdCancion().equals(idCancion)))
                .filter(l -> idVideo == null || (l.getVideo() != null && l.getVideo().getIdVideo().equals(idVideo)))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeccionDto findById(Integer idLeccion) {
        return leccionRepository.findById(idLeccion)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public LeccionDto save(LeccionDto leccionDto) {
        Leccion leccion = convertToEntity(leccionDto);
        leccion = leccionRepository.save(leccion);
        return convertToDto(leccion);
    }

    @Override
    public LeccionDto update(Integer idLeccion, LeccionDto leccionDto) {
        Optional<Leccion> existingLeccion = leccionRepository.findById(idLeccion);
        if (existingLeccion.isPresent()) {
            Leccion leccion = existingLeccion.get();
            updateEntityFromDto(leccion, leccionDto);
            leccion = leccionRepository.save(leccion);
            return convertToDto(leccion);
        }
        return null;
    }

    @Override
    public LeccionDto patch(Integer idLeccion, Map<String, Object> updates) {
        Optional<Leccion> existingLeccion = leccionRepository.findById(idLeccion);
        if (existingLeccion.isPresent()) {
            Leccion leccionToUpdate = existingLeccion.get();

            if (updates.containsKey("titulo")) {
                leccionToUpdate.setTitulo((String) updates.get("titulo"));
            }
            if (updates.containsKey("descripcion")) {
                leccionToUpdate.setDescripcion((String) updates.get("descripcion"));
            }
            if (updates.containsKey("tipo")) {
                leccionToUpdate.setTipo(Leccion.Tipo.valueOf((String) updates.get("tipo")));
            }
            if (updates.containsKey("nivel")) {
                leccionToUpdate.setNivel(Leccion.Nivel.valueOf((String) updates.get("nivel")));
            }
            if (updates.containsKey("idCancion")) {
                cancionRepository.findById(((Number) updates.get("idCancion")).intValue())
                        .ifPresent(leccionToUpdate::setCancion);
            }
            if (updates.containsKey("idVideo")) {
                videoEducativoRepository.findById(((Number) updates.get("idVideo")).intValue())
                        .ifPresent(leccionToUpdate::setVideo);
            }

            Leccion updatedLeccion = leccionRepository.save(leccionToUpdate);
            return convertToDto(updatedLeccion);
        }
        return null;
    }

    @Override
    public void deleteById(Integer idLeccion) {
        leccionRepository.deleteById(idLeccion);
    }

    @Override
    public List<String> findAllTitulos() {
        return leccionRepository.findAll().stream()
                .map(Leccion::getTitulo)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Leccion.Tipo> findAllTipos() {
        return List.of(Leccion.Tipo.values());
    }

    @Override
    public List<Leccion.Nivel> findAllNiveles() {
        return List.of(Leccion.Nivel.values());
    }

    private LeccionDto convertToDto(Leccion leccion) {
        LeccionDto dto = new LeccionDto();
        dto.setIdLeccion(leccion.getIdLeccion());
        dto.setTitulo(leccion.getTitulo());
        dto.setDescripcion(leccion.getDescripcion());
        dto.setTipo(leccion.getTipo());
        dto.setNivel(leccion.getNivel());
        if (leccion.getCancion() != null) {
            dto.setIdCancion(leccion.getCancion().getIdCancion());
        }
        if (leccion.getVideo() != null) {
            dto.setIdVideo(leccion.getVideo().getIdVideo());
        }
        return dto;
    }

    private Leccion convertToEntity(LeccionDto dto) {
        Leccion leccion = new Leccion();
        leccion.setIdLeccion(dto.getIdLeccion());
        leccion.setTitulo(dto.getTitulo());
        leccion.setDescripcion(dto.getDescripcion());
        leccion.setTipo(dto.getTipo());
        leccion.setNivel(dto.getNivel());
        if (dto.getIdCancion() != null) {
            cancionRepository.findById(dto.getIdCancion()).ifPresent(leccion::setCancion);
        }
        if (dto.getIdVideo() != null) {
            videoEducativoRepository.findById(dto.getIdVideo()).ifPresent(leccion::setVideo);
        }
        return leccion;
    }

    private void updateEntityFromDto(Leccion leccion, LeccionDto dto) {
        leccion.setTitulo(dto.getTitulo());
        leccion.setDescripcion(dto.getDescripcion());
        leccion.setTipo(dto.getTipo());
        leccion.setNivel(dto.getNivel());
        if (dto.getIdCancion() != null) {
            cancionRepository.findById(dto.getIdCancion()).ifPresent(leccion::setCancion);
        }
        if (dto.getIdVideo() != null) {
            videoEducativoRepository.findById(dto.getIdVideo()).ifPresent(leccion::setVideo);
        }
    }
}
