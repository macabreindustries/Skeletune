package org.example.demo.controller;

import lombok.AllArgsConstructor;
import org.example.demo.dto.RolDto;
import org.example.demo.dto.UsuarioDto;
import org.example.demo.model.Rol;
import org.example.demo.model.Usuario;
import org.example.demo.service.RolService;
import org.example.demo.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Contract;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skeletune/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> list() {
        List<UsuarioDto> response = usuarioService.getAll()
                .stream().map(UsuarioDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.getById(id);
        return ResponseEntity.ok(UsuarioDto.fromEntity(usuario));
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> create(@RequestBody UsuarioDto dto) {

        Rol rol = rolService.getById(dto.getIdRol());

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .contrasena(dto.getContrasena()) // SIN ENCRIPTAR
                .fechaRegistro(dto.getFechaRegistro())
                .rol(rol)
                .build();

        Usuario saved = usuarioService.save(usuario);

        return ResponseEntity.ok(UsuarioDto.fromEntity(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> update(
            @PathVariable Integer id,
            @RequestBody UsuarioDto dto) {

        Rol rol = rolService.getById(dto.getIdRol());

        Usuario updated = usuarioService.update(id, Usuario.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .contrasena(dto.getContrasena())
                .fechaRegistro(dto.getFechaRegistro())
                .rol(rol)
                .build());

        return ResponseEntity.ok(UsuarioDto.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints para filtros ---

    @GetMapping("/nombres")
    public List<String> getNombres() {
        return usuarioService.findAllNombres();
    }

    @GetMapping("/correos")
    public List<String> getCorreos() {
        return usuarioService.findAllCorreos();
    }

    @GetMapping("/roles")
    public List<RolDto> getRoles() {
        return usuarioService.findAllRoles();
    }
}
