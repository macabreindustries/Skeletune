package org.example.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.NovedadDto;
import org.example.demo.model.Novedad;
import org.example.demo.model.Usuario;
import org.example.demo.repository.NovedadRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.NovedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NovedadServiceImpl implements NovedadService {

    private final NovedadRepository novedadRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public NovedadServiceImpl(NovedadRepository novedadRepository, UsuarioRepository usuarioRepository) {
        this.novedadRepository = novedadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<NovedadDto> findAll() {
        return novedadRepository.findAll().stream()
                .map(NovedadDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public NovedadDto findById(Integer id) {
        return novedadRepository.findById(id)
                .map(NovedadDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Novedad no encontrada con id: " + id));
    }

    @Override
    public NovedadDto save(NovedadDto novedadDto) {
        Usuario admin = usuarioRepository.findById(novedadDto.getIdAdmin())
                .orElseThrow(() -> new EntityNotFoundException("Usuario (Admin) no encontrado con id: " + novedadDto.getIdAdmin()));

        Novedad novedad = new Novedad();
        novedad.setAdmin(admin);
        novedad.setTitulo(novedadDto.getTitulo());
        novedad.setContenido(novedadDto.getContenido());
        novedad.setImagenUrl(novedadDto.getImagenUrl());
        novedad.setImportancia(novedadDto.getImportancia());

        Novedad savedNovedad = novedadRepository.save(novedad);
        return new NovedadDto(savedNovedad);
    }

    @Override
    public NovedadDto update(Integer id, NovedadDto novedadDto) {
        Novedad existingNovedad = novedadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Novedad no encontrada con id: " + id));

        // Opcional: Validar que el admin que actualiza sea el mismo o tenga permisos
        if (novedadDto.getIdAdmin() != null && !existingNovedad.getAdmin().getId().equals(novedadDto.getIdAdmin())) {
            Usuario newAdmin = usuarioRepository.findById(novedadDto.getIdAdmin())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario (Admin) no encontrado con id: " + novedadDto.getIdAdmin()));
            existingNovedad.setAdmin(newAdmin);
        }

        existingNovedad.setTitulo(novedadDto.getTitulo());
        existingNovedad.setContenido(novedadDto.getContenido());
        existingNovedad.setImagenUrl(novedadDto.getImagenUrl());
        existingNovedad.setImportancia(novedadDto.getImportancia());

        Novedad updatedNovedad = novedadRepository.save(existingNovedad);
        return new NovedadDto(updatedNovedad);
    }

    @Override
    public void deleteById(Integer id) {
        if (!novedadRepository.existsById(id)) {
            throw new EntityNotFoundException("Novedad no encontrada con id: " + id);
        }
        novedadRepository.deleteById(id);
    }

    @Override
    public List<NovedadDto> getRecentNovedades() {
        return novedadRepository.findTop5ByOrderByFechaPublicacionDesc().stream()
                .map(NovedadDto::new)
                .collect(Collectors.toList());
    }
}
