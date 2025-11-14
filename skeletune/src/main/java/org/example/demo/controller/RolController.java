package org.example.demo.controller;

import lombok.AllArgsConstructor;
import org.example.demo.dto.RolDto;
import org.example.demo.model.Rol;
import org.example.demo.service.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor

public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolDto>> list() {
        List<RolDto> response = rolService.getAll().stream()
                .map(r -> RolDto.builder()
                        .idRol(r.getId())
                        .nombreRol(r.getNombre())
                        .descripcion(r.getDescripcion())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDto> getById(@PathVariable Integer id) {

        Rol r = rolService.getById(id);

        RolDto dto = RolDto.builder()
                .idRol(r.getId())
                .nombreRol(r.getNombre())
                .descripcion(r.getDescripcion())
                .build();

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<RolDto> create(@RequestBody RolDto dto) {

        Rol rol = Rol.builder()
                .nombre(dto.getNombreRol())
                .descripcion(dto.getDescripcion())
                .build();

        Rol saved = rolService.save(rol);

        return ResponseEntity.ok(RolDto.builder()
                .idRol(saved.getId())
                .nombreRol(saved.getNombre())
                .descripcion(saved.getDescripcion())
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDto> update(@PathVariable Integer id,
                                         @RequestBody RolDto dto) {

        Rol newData = Rol.builder()
                .nombre(dto.getNombreRol())
                .descripcion(dto.getDescripcion())
                .build();

        Rol updated = rolService.update(id, newData);

        return ResponseEntity.ok(RolDto.builder()
                .idRol(updated.getId())
                .nombreRol(updated.getNombre())
                .descripcion(updated.getDescripcion())
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
