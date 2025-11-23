package org.example.demo.controller;

import org.example.demo.dto.VideoEducativoDto;
import org.example.demo.service.VideoEducativoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skeletune/api/videoEducativo")
public class VideoEducativoController {

    private final VideoEducativoService videoEducativoService;

    public VideoEducativoController(VideoEducativoService videoEducativoService) {
        this.videoEducativoService = videoEducativoService;
    }

    @GetMapping
    public List<VideoEducativoDto> getAllVideoEducativos(
            @RequestParam(required = false) Integer idProfesor,
            @RequestParam(required = false) String titulo) {
        return videoEducativoService.findAll(idProfesor, titulo);
    }

    @GetMapping("/{idVideo}")
    public ResponseEntity<VideoEducativoDto> getVideoEducativoById(@PathVariable Integer idVideo) {
        VideoEducativoDto videoEducativoDto = videoEducativoService.findById(idVideo);
        return videoEducativoDto != null ? ResponseEntity.ok(videoEducativoDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public VideoEducativoDto createVideoEducativo(@RequestBody VideoEducativoDto videoEducativoDto) {
        return videoEducativoService.save(videoEducativoDto);
    }

    @PutMapping("/{idVideo}")
    public ResponseEntity<VideoEducativoDto> updateVideoEducativo(@PathVariable Integer idVideo, @RequestBody VideoEducativoDto videoEducativoDto) {
        VideoEducativoDto updatedVideoEducativo = videoEducativoService.update(idVideo, videoEducativoDto);
        return updatedVideoEducativo != null ? ResponseEntity.ok(updatedVideoEducativo) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{idVideo}")
    public ResponseEntity<VideoEducativoDto> patchVideoEducativo(@PathVariable Integer idVideo, @RequestBody Map<String, Object> updates) {
        VideoEducativoDto patchedVideoEducativo = videoEducativoService.patch(idVideo, updates);
        return patchedVideoEducativo != null ? ResponseEntity.ok(patchedVideoEducativo) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idVideo}")
    public ResponseEntity<Void> deleteVideoEducativo(@PathVariable Integer idVideo) {
        videoEducativoService.deleteById(idVideo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/titulos")
    public List<String> getTitulos() {
        return videoEducativoService.findAllTitulos();
    }
}
