package org.example.demo.controller;

import org.example.demo.dto.InstrumentoDto;
import org.example.demo.service.InstrumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skeletune/api/instrumentos")
public class InstrumentoController {

    @Autowired
    private InstrumentoService instrumentoService;

    @GetMapping
    public List<InstrumentoDto> getAllInstrumentos() {
        return instrumentoService.getAllInstrumentos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstrumentoDto> getInstrumentoById(@PathVariable int id) {
        return instrumentoService.getInstrumentoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public InstrumentoDto createInstrumento(@RequestBody InstrumentoDto instrumentoDto) {
        return instrumentoService.saveInstrumento(instrumentoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstrumentoDto> updateInstrumento(@PathVariable int id, @RequestBody InstrumentoDto instrumentoDto) {
        if (instrumentoService.getInstrumentoById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        instrumentoDto.setId_instrumento(id);
        return ResponseEntity.ok(instrumentoService.saveInstrumento(instrumentoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstrumento(@PathVariable int id) {
        if (instrumentoService.getInstrumentoById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        instrumentoService.deleteInstrumento(id);
        return ResponseEntity.noContent().build();
    }
}
