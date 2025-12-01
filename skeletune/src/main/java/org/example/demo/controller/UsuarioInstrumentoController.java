package org.example.demo.controller;

import org.example.demo.dto.UsuarioInstrumentoDto;
import org.example.demo.model.UsuarioInstrumentoId;
import org.example.demo.service.UsuarioInstrumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skeletune/api/usuario-instrumentos")
public class UsuarioInstrumentoController {

    @Autowired
    private UsuarioInstrumentoService usuarioInstrumentoService;

    @GetMapping
    public List<UsuarioInstrumentoDto> getAllUsuarioInstrumentos() {
        return usuarioInstrumentoService.getAllUsuarioInstrumentos();
    }

    @GetMapping("/{idUsuario}/{idInstrumento}")
    public ResponseEntity<UsuarioInstrumentoDto> getUsuarioInstrumentoById(@PathVariable int idUsuario, @PathVariable int idInstrumento) {
        UsuarioInstrumentoId id = new UsuarioInstrumentoId(idUsuario, idInstrumento);
        return usuarioInstrumentoService.getUsuarioInstrumentoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UsuarioInstrumentoDto createUsuarioInstrumento(@RequestBody UsuarioInstrumentoDto usuarioInstrumentoDto) {
        return usuarioInstrumentoService.saveUsuarioInstrumento(usuarioInstrumentoDto);
    }

    @PutMapping("/{idUsuario}/{idInstrumento}")
    public ResponseEntity<UsuarioInstrumentoDto> updateUsuarioInstrumento(@PathVariable int idUsuario, @PathVariable int idInstrumento, @RequestBody UsuarioInstrumentoDto usuarioInstrumentoDto) {
        UsuarioInstrumentoId id = new UsuarioInstrumentoId(idUsuario, idInstrumento);
        if (usuarioInstrumentoService.getUsuarioInstrumentoById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Ensure the DTO has the correct IDs for the update
        usuarioInstrumentoDto.setIdUsuario(idUsuario);
        usuarioInstrumentoDto.setIdInstrumento(idInstrumento);
        return ResponseEntity.ok(usuarioInstrumentoService.saveUsuarioInstrumento(usuarioInstrumentoDto));
    }

    @DeleteMapping("/{idUsuario}/{idInstrumento}")
    public ResponseEntity<Void> deleteUsuarioInstrumento(@PathVariable int idUsuario, @PathVariable int idInstrumento) {
        UsuarioInstrumentoId id = new UsuarioInstrumentoId(idUsuario, idInstrumento);
        if (usuarioInstrumentoService.getUsuarioInstrumentoById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioInstrumentoService.deleteUsuarioInstrumento(id);
        return ResponseEntity.noContent().build();
    }
}
