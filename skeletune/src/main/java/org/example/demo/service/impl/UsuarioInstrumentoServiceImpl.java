package org.example.demo.service.impl;

import org.example.demo.dto.UsuarioInstrumentoDto;
import org.example.demo.model.Instrumento;
import org.example.demo.model.Usuario;
import org.example.demo.model.UsuarioInstrumento;
import org.example.demo.model.UsuarioInstrumentoId;
import org.example.demo.repository.InstrumentoRepository;
import org.example.demo.repository.UsuarioInstrumentoRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.UsuarioInstrumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioInstrumentoServiceImpl implements UsuarioInstrumentoService {

    @Autowired
    private UsuarioInstrumentoRepository usuarioInstrumentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Override
    public List<UsuarioInstrumentoDto> getAllUsuarioInstrumentos() {
        return usuarioInstrumentoRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioInstrumentoDto> getUsuarioInstrumentoById(UsuarioInstrumentoId id) {
        return usuarioInstrumentoRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public UsuarioInstrumentoDto saveUsuarioInstrumento(UsuarioInstrumentoDto usuarioInstrumentoDto) {
        UsuarioInstrumento usuarioInstrumento = convertToEntity(usuarioInstrumentoDto);
        usuarioInstrumento = usuarioInstrumentoRepository.save(usuarioInstrumento);
        return convertToDto(usuarioInstrumento);
    }

    @Override
    public void deleteUsuarioInstrumento(UsuarioInstrumentoId id) {
        usuarioInstrumentoRepository.deleteById(id);
    }

    private UsuarioInstrumentoDto convertToDto(UsuarioInstrumento entity) {
        UsuarioInstrumentoDto dto = new UsuarioInstrumentoDto();
        dto.setIdUsuario(entity.getUsuario().getId());
        dto.setIdInstrumento(entity.getInstrumento().getId_instrumento());
        dto.setNivel(entity.getNivel().name());
        return dto;
    }

    private UsuarioInstrumento convertToEntity(UsuarioInstrumentoDto dto) {
        UsuarioInstrumento entity = new UsuarioInstrumento();
        UsuarioInstrumentoId id = new UsuarioInstrumentoId(dto.getIdUsuario(), dto.getIdInstrumento());
        entity.setId(id);

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        entity.setUsuario(usuario);

        Instrumento instrumento = instrumentoRepository.findById(dto.getIdInstrumento())
                .orElseThrow(() -> new RuntimeException("Instrumento no encontrado"));
        entity.setInstrumento(instrumento);

        entity.setNivel(UsuarioInstrumento.Nivel.valueOf(dto.getNivel()));
        return entity;
    }
}
