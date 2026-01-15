package org.example.demo.controller;

import lombok.AllArgsConstructor;
import org.example.demo.dto.RolDto;
import org.example.demo.dto.UsuarioDto;
import org.example.demo.service.RolService;
import org.example.demo.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/skeletune/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    /**
     * ENDPOINT DE LOGIN (LA SOLUCIÓN AL ERROR 500)
     * Recibe correo y contraseña como parámetros de consulta (RequestParams).
     * Android lo llama mediante @Query en Retrofit.
     */
    @PostMapping("/login")
    public ResponseEntity<UsuarioDto> login(
            @RequestParam String correo,
            @RequestParam String contrasena) {

        UsuarioDto usuarioDto = usuarioService.login(correo, contrasena);

        if (usuarioDto != null) {
            // Si las credenciales son correctas, devolvemos el usuario (200 OK)
            return ResponseEntity.ok(usuarioDto);
        } else {
            // Si fallan, devolvemos 401 Unauthorized (Sin autorización)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * REGISTRO DE USUARIOS
     * Solo se usa la primera vez que el usuario crea su cuenta.
     */
    /**
     * REGISTRO DE USUARIOS - CORREGIDO
     */
    @PostMapping
    public ResponseEntity<UsuarioDto> create(@RequestBody UsuarioDto dto) {
        // 1. Guardamos el usuario (esto ya vimos que funciona en MySQL)
        UsuarioDto savedDto = usuarioService.save(dto);

        // 2. En lugar de construir la URI de localización (que es opcional),
        // devolvemos directamente el objeto guardado con estado CREATED (201).
        // Esto evita el error "id must not be null".
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> list() {
        List<UsuarioDto> response = usuarioService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getById(@PathVariable Integer id) {
        UsuarioDto usuarioDto = usuarioService.getById(id);
        return ResponseEntity.ok(usuarioDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> update(
            @PathVariable Integer id,
            @RequestBody UsuarioDto dto) {
        UsuarioDto updatedDto = usuarioService.update(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints para filtros y búsquedas ---

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

