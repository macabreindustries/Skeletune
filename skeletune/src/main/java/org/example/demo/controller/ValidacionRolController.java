package org.example.demo.controller;

import org.example.demo.dto.ValidacionRolDto;
import org.example.demo.service.ValidacionRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skeletune/api/validaciones-rol")
public class ValidacionRolController {

    @Autowired
    private ValidacionRolService validacionRolService;

    @GetMapping
    public List<ValidacionRolDto> getAllValidaciones() {
        return validacionRolService.getAllValidaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValidacionRolDto> getValidacionById(@PathVariable int id) {
        return validacionRolService.getValidacionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ValidacionRolDto createValidacion(@RequestBody ValidacionRolDto validacionRolDto) {
        return validacionRolService.saveValidacion(validacionRolDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValidacionRolDto> updateValidacion(@PathVariable int id, @RequestBody ValidacionRolDto validacionRolDto) {
        if (validacionRolService.getValidacionById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        validacionRolDto.setId_validacion(id);
        return ResponseEntity.ok(validacionRolService.saveValidacion(validacionRolDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValidacion(@PathVariable int id) {
        if (validacionRolService.getValidacionById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        validacionRolService.deleteValidacion(id);
        return ResponseEntity.noContent().build();
    }
}
