package org.example.demo.controller.juego;

import org.example.demo.dto.juego.NotaManiaDto;
import org.example.demo.model.juego.NotaMania;
import org.example.demo.service.juego.NotaManiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/juego/notaMania")
public class NotaManiaController {

    private final NotaManiaService notaManiaService;

    public NotaManiaController(NotaManiaService notaManiaService) {
        this.notaManiaService = notaManiaService;
    }

    @GetMapping
    public List<NotaManiaDto> getAllNotaManias(
            @RequestParam(required = false) Integer idChartMania,
            @RequestParam(required = false) Integer tiempoMs,
            @RequestParam(required = false) Byte carril,
            @RequestParam(required = false) NotaMania.Tipo tipo) {
        return notaManiaService.findAll(idChartMania, tiempoMs, carril, tipo);
    }

    @GetMapping("/{idNotaMania}")
    public ResponseEntity<NotaManiaDto> getNotaManiaById(@PathVariable Integer idNotaMania) {
        NotaManiaDto notaManiaDto = notaManiaService.findById(idNotaMania);
        return notaManiaDto != null ? ResponseEntity.ok(notaManiaDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public NotaManiaDto createNotaMania(@RequestBody NotaManiaDto notaManiaDto) {
        return notaManiaService.save(notaManiaDto);
    }

    @PutMapping("/{idNotaMania}")
    public ResponseEntity<NotaManiaDto> updateNotaMania(@PathVariable Integer idNotaMania, @RequestBody NotaManiaDto notaManiaDto) {
        NotaManiaDto updatedNotaMania = notaManiaService.update(idNotaMania, notaManiaDto);
        return updatedNotaMania != null ? ResponseEntity.ok(updatedNotaMania) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{idNotaMania}")
    public ResponseEntity<NotaManiaDto> patchNotaMania(@PathVariable Integer idNotaMania, @RequestBody Map<String, Object> updates) {
        NotaManiaDto patchedNotaMania = notaManiaService.patch(idNotaMania, updates);
        return patchedNotaMania != null ? ResponseEntity.ok(patchedNotaMania) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idNotaMania}")
    public ResponseEntity<Void> deleteNotaMania(@PathVariable Integer idNotaMania) {
        notaManiaService.deleteById(idNotaMania);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipos")
    public List<NotaMania.Tipo> getTipos() {
        return notaManiaService.findAllTipos();
    }
}
