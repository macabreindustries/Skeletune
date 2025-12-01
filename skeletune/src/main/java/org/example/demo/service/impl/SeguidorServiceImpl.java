package org.example.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.SeguidorDto;
import org.example.demo.model.Seguidor;
import org.example.demo.model.SeguidorId;
import org.example.demo.model.Usuario;
import org.example.demo.repository.SeguidorRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.SeguidorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeguidorServiceImpl implements SeguidorService {

    private final SeguidorRepository seguidorRepository;
    private final UsuarioRepository usuarioRepository;

    public SeguidorServiceImpl(SeguidorRepository seguidorRepository, UsuarioRepository usuarioRepository) {
        this.seguidorRepository = seguidorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeguidorDto> getSeguidores(Integer idUsuario) {
        return seguidorRepository.findById_IdSeguido(idUsuario).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeguidorDto> getSeguidos(Integer idUsuario) {
        return seguidorRepository.findById_IdSeguidor(idUsuario).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SeguidorDto follow(Integer idSeguidor, Integer idSeguido) {
        Usuario seguidor = usuarioRepository.findById(idSeguidor)
                .orElseThrow(() -> new EntityNotFoundException("Usuario seguidor no encontrado con id: " + idSeguidor));
        Usuario seguido = usuarioRepository.findById(idSeguido)
                .orElseThrow(() -> new EntityNotFoundException("Usuario seguido no encontrado con id: " + idSeguido));

        SeguidorId seguidorId = new SeguidorId(idSeguidor, idSeguido);
        if (seguidorRepository.existsById(seguidorId)) {
            throw new IllegalStateException("El usuario ya sigue al otro usuario.");
        }

        Seguidor nuevaRelacion = new Seguidor();
        nuevaRelacion.setId(seguidorId);
        nuevaRelacion.setSeguidor(seguidor);
        nuevaRelacion.setSeguido(seguido);

        return toDto(seguidorRepository.save(nuevaRelacion));
    }

    @Override
    @Transactional
    public void unfollow(Integer idSeguidor, Integer idSeguido) {
        SeguidorId seguidorId = new SeguidorId(idSeguidor, idSeguido);
        if (!seguidorRepository.existsById(seguidorId)) {
            throw new EntityNotFoundException("La relación de seguimiento no existe.");
        }
        seguidorRepository.deleteById(seguidorId);
    }

    private SeguidorDto toDto(Seguidor seguidor) {
        return new SeguidorDto(
                seguidor.getId().getIdSeguidor(),
                seguidor.getId().getIdSeguido(),
                seguidor.getFechaSeguimiento()
        );
    }
}
