package org.example.demo.service.impl.juego;

import org.example.demo.dto.juego.ChartManiaDto;
import org.example.demo.model.Cancion;
import org.example.demo.model.Usuario;
import org.example.demo.model.juego.ChartMania;
import org.example.demo.repository.CancionRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.repository.juego.ChartManiaRepository;
import org.example.demo.service.juego.ChartManiaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChartManiaServiceImpl implements ChartManiaService {

    private final ChartManiaRepository chartManiaRepository;
    private final CancionRepository cancionRepository;
    private final UsuarioRepository usuarioRepository;

    public ChartManiaServiceImpl(ChartManiaRepository chartManiaRepository,
                                 CancionRepository cancionRepository,
                                 UsuarioRepository usuarioRepository) {
        this.chartManiaRepository = chartManiaRepository;
        this.cancionRepository = cancionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<ChartManiaDto> findAll(Integer idCancion, ChartMania.Dificultad dificultad, Float speedMultiplier) {
        List<ChartMania> chartManias = chartManiaRepository.findAll();

        return chartManias.stream()
                .filter(cm -> idCancion == null || (cm.getCancion() != null && cm.getCancion().getIdCancion().equals(idCancion)))
                .filter(cm -> dificultad == null || cm.getDificultad().equals(dificultad))
                .filter(cm -> speedMultiplier == null || cm.getSpeedMultiplier().equals(speedMultiplier))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChartManiaDto findById(Integer idChartMania) {
        return chartManiaRepository.findById(idChartMania)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public ChartManiaDto save(ChartManiaDto chartManiaDto) {
        ChartMania chartMania = convertToEntity(chartManiaDto);
        chartMania.setFechaCreacion(LocalDateTime.now());
        chartMania = chartManiaRepository.save(chartMania);
        return convertToDto(chartMania);
    }

    @Override
    public ChartManiaDto update(Integer idChartMania, ChartManiaDto chartManiaDto) {
        Optional<ChartMania> existingChartMania = chartManiaRepository.findById(idChartMania);
        if (existingChartMania.isPresent()) {
            ChartMania chartMania = existingChartMania.get();
            updateEntityFromDto(chartMania, chartManiaDto);
            chartMania = chartManiaRepository.save(chartMania);
            return convertToDto(chartMania);
        }
        return null;
    }

    @Override
    public ChartManiaDto patch(Integer idChartMania, Map<String, Object> updates) {
        Optional<ChartMania> existingChartMania = chartManiaRepository.findById(idChartMania);
        if (existingChartMania.isPresent()) {
            ChartMania chartManiaToUpdate = existingChartMania.get();

            if (updates.containsKey("idCancion")) {
                cancionRepository.findById(((Number) updates.get("idCancion")).intValue())
                        .ifPresent(chartManiaToUpdate::setCancion);
            }
            if (updates.containsKey("dificultad")) {
                chartManiaToUpdate.setDificultad(ChartMania.Dificultad.valueOf((String) updates.get("dificultad")));
            }
            if (updates.containsKey("speedMultiplier")) {
                chartManiaToUpdate.setSpeedMultiplier(((Number) updates.get("speedMultiplier")).floatValue());
            }
            if (updates.containsKey("numPistas")) {
                chartManiaToUpdate.setNumPistas(((Number) updates.get("numPistas")).byteValue());
            }
            if (updates.containsKey("createdBy")) {
                usuarioRepository.findById(((Number) updates.get("createdBy")).intValue())
                        .ifPresent(chartManiaToUpdate::setCreatedBy);
            }

            ChartMania updatedChartMania = chartManiaRepository.save(chartManiaToUpdate);
            return convertToDto(updatedChartMania);
        }
        return null;
    }

    @Override
    public void deleteById(Integer idChartMania) {
        chartManiaRepository.deleteById(idChartMania);
    }

    @Override
    public List<ChartMania.Dificultad> findAllDificultades() {
        return List.of(ChartMania.Dificultad.values());
    }

    @Override
    public List<Float> findAllSpeedMultipliers() {
        return chartManiaRepository.findAll().stream()
                .map(ChartMania::getSpeedMultiplier)
                .distinct()
                .collect(Collectors.toList());
    }

    private ChartManiaDto convertToDto(ChartMania chartMania) {
        ChartManiaDto dto = new ChartManiaDto();
        dto.setIdChartMania(chartMania.getIdChartMania());
        if (chartMania.getCancion() != null) {
            dto.setIdCancion(chartMania.getCancion().getIdCancion());
        }
        dto.setDificultad(chartMania.getDificultad());
        dto.setSpeedMultiplier(chartMania.getSpeedMultiplier());
        dto.setNumPistas(chartMania.getNumPistas());
        if (chartMania.getCreatedBy() != null) {
            dto.setCreatedBy(chartMania.getCreatedBy().getId());
        }
        dto.setFechaCreacion(chartMania.getFechaCreacion());
        return dto;
    }

    private ChartMania convertToEntity(ChartManiaDto dto) {
        ChartMania chartMania = new ChartMania();
        chartMania.setIdChartMania(dto.getIdChartMania());
        if (dto.getIdCancion() != null) {
            cancionRepository.findById(dto.getIdCancion()).ifPresent(chartMania::setCancion);
        }
        chartMania.setDificultad(dto.getDificultad());
        chartMania.setSpeedMultiplier(dto.getSpeedMultiplier());
        chartMania.setNumPistas(dto.getNumPistas());
        if (dto.getCreatedBy() != null) {
            usuarioRepository.findById(dto.getCreatedBy()).ifPresent(chartMania::setCreatedBy);
        }
        chartMania.setFechaCreacion(dto.getFechaCreacion());
        return chartMania;
    }

    private void updateEntityFromDto(ChartMania chartMania, ChartManiaDto dto) {
        if (dto.getIdCancion() != null) {
            cancionRepository.findById(dto.getIdCancion()).ifPresent(chartMania::setCancion);
        }
        chartMania.setDificultad(dto.getDificultad());
        chartMania.setSpeedMultiplier(dto.getSpeedMultiplier());
        chartMania.setNumPistas(dto.getNumPistas());
        if (dto.getCreatedBy() != null) {
            usuarioRepository.findById(dto.getCreatedBy()).ifPresent(chartMania::setCreatedBy);
        }
    }
}
