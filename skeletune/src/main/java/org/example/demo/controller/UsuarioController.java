package org.example.demo.controller;

import lombok.AllArgsConstructor;
import org.example.demo.dto.RolDto;
import org.example.demo.dto.UsuarioDto;
import org.example.demo.model.Rol;
import org.example.demo.model.Usuario;
import org.example.demo.service.RolService;
import org.example.demo.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Contract;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        // El servicio ahora devuelve directamente la lista de DTOs
        List<UsuarioDto> response = usuarioService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getById(@PathVariable Integer id) {
        // El servicio ahora devuelve directamente el DTO
        UsuarioDto usuarioDto = usuarioService.getById(id);
        return ResponseEntity.ok(usuarioDto);
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> create(@RequestBody UsuarioDto dto) {
        // La lógica de creación debe estar en el servicio.
        // El servicio debería encargarse de buscar el rol y encriptar la contraseña.
        UsuarioDto savedDto = usuarioService.save(dto); // Asumiendo que el servicio ahora acepta un DTO
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getIdUsuario()) // Usar el getter correcto del DTO
                .toUri();
        return ResponseEntity.created(location).body(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> update(
            @PathVariable Integer id,
            @RequestBody UsuarioDto dto) {
        // La lógica de actualización también debería estar en el servicio
        UsuarioDto updatedDto = usuarioService.update(id, dto); // Asumiendo que el servicio ahora acepta un DTO
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints para filtros ---

    @GetMapping("/nombres")
    public ResponseEntity<List<String>> getNombres() {
        return ResponseEntity.ok(usuarioService.findAllNombres());
    }

    @GetMapping("/correos")
    public ResponseEntity<List<String>> getCorreos() {
        return ResponseEntity.ok(usuarioService.findAllCorreos());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RolDto>> getRoles() {
        return ResponseEntity.ok(usuarioService.findAllRoles());
    }
}
