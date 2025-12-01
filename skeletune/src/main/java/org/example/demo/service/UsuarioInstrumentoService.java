package org.example.demo.service;

import org.example.demo.dto.UsuarioInstrumentoDto;
import org.example.demo.model.UsuarioInstrumentoId;

import java.util.List;
import java.util.Optional;

public interface UsuarioInstrumentoService {
    List<UsuarioInstrumentoDto> getAllUsuarioInstrumentos();
    Optional<UsuarioInstrumentoDto> getUsuarioInstrumentoById(UsuarioInstrumentoId id);
    UsuarioInstrumentoDto saveUsuarioInstrumento(UsuarioInstrumentoDto usuarioInstrumentoDto);
    void deleteUsuarioInstrumento(UsuarioInstrumentoId id);
}
