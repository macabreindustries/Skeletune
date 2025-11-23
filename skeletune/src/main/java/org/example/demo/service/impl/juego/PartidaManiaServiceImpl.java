package org.example.demo.service.impl.juego;

import org.example.demo.dto.juego.PartidaManiaDto;
import org.example.demo.model.Usuario;
import org.example.demo.model.juego.ChartMania;
import org.example.demo.model.juego.PartidaMania;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.repository.juego.ChartManiaRepository;
import org.example.demo.repository.juego.PartidaManiaRepository;
import org.example.demo.service.juego.PartidaManiaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal; // Importar BigDecimal
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartidaManiaServiceImpl implements PartidaManiaService {

    private final PartidaManiaRepository partidaManiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ChartManiaRepository chartManiaRepository;

    public PartidaManiaServiceImpl(PartidaManiaRepository partidaManiaRepository,
                                   UsuarioRepository usuarioRepository,
                                   ChartManiaRepository chartManiaRepository) {
        this.partidaManiaRepository = partidaManiaRepository;
        this.usuarioRepository = usuarioRepository;
        this.chartManiaRepository = chartManiaRepository;
    }

    @Override
    public List<PartidaManiaDto> findAll(Integer idUsuario, Integer idChartMania, Integer puntajeMin, BigDecimal accuracyMin) { // Cambiado Double a BigDecimal
        List<PartidaMania> partidaManias = partidaManiaRepository.findAll();

        return partidaManias.stream()
                .filter(pm -> idUsuario == null || (pm.getUsuario() != null && pm.getUsuario().getId().equals(idUsuario)))
                .filter(pm -> idChartMania == null || (pm.getChartMania() != null && pm.getChartMania().getIdChartMania().equals(idChartMania)))
                .filter(pm -> puntajeMin == null || pm.getPuntaje() >= puntajeMin)
                .filter(pm -> accuracyMin == null || pm.getAccuracy().compareTo(accuracyMin) >= 0) // Comparar BigDecimal
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PartidaManiaDto findById(Integer idPartidaMania) {
        return partidaManiaRepository.findById(idPartidaMania)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public PartidaManiaDto save(PartidaManiaDto partidaManiaDto) {
        PartidaMania partidaMania = convertToEntity(partidaManiaDto);
        partidaMania.setFecha(LocalDateTime.now());
        partidaMania = partidaManiaRepository.save(partidaMania);
        return convertToDto(partidaMania);
    }

    @Override
    public PartidaManiaDto update(Integer idPartidaMania, PartidaManiaDto partidaManiaDto) {
        Optional<PartidaMania> existingPartidaMania = partidaManiaRepository.findById(idPartidaMania);
        if (existingPartidaMania.isPresent()) {
            PartidaMania partidaMania = existingPartidaMania.get();
            updateEntityFromDto(partidaMania, partidaManiaDto);
            partidaMania = partidaManiaRepository.save(partidaMania);
            return convertToDto(partidaMania);
        }
        return null;
    }

    @Override
    public PartidaManiaDto patch(Integer idPartidaMania, Map<String, Object> updates) {
        Optional<PartidaMania> existingPartidaMania = partidaManiaRepository.findById(idPartidaMania);
        if (existingPartidaMania.isPresent()) {
            PartidaMania partidaManiaToUpdate = existingPartidaMania.get();

            if (updates.containsKey("idUsuario")) {
                usuarioRepository.findById(((Number) updates.get("idUsuario")).intValue())
                        .ifPresent(partidaManiaToUpdate::setUsuario);
            }
            if (updates.containsKey("idChartMania")) {
                chartManiaRepository.findById(((Number) updates.get("idChartMania")).intValue())
                        .ifPresent(partidaManiaToUpdate::setChartMania);
            }
            if (updates.containsKey("puntaje")) {
                partidaManiaToUpdate.setPuntaje((Integer) updates.get("puntaje"));
            }
            if (updates.containsKey("accuracy")) {
                // Convertir a BigDecimal
                Object accuracyValue = updates.get("accuracy");
                if (accuracyValue instanceof Number) {
                    partidaManiaToUpdate.setAccuracy(BigDecimal.valueOf(((Number) accuracyValue).doubleValue()));
                } else if (accuracyValue instanceof String) {
                    partidaManiaToUpdate.setAccuracy(new BigDecimal((String) accuracyValue));
                }
            }
            if (updates.containsKey("comboMax")) {
                partidaManiaToUpdate.setComboMax((Integer) updates.get("comboMax"));
            }
            if (updates.containsKey("perfects")) {
                partidaManiaToUpdate.setPerfects((Integer) updates.get("perfects"));
            }
            if (updates.containsKey("greats")) {
                partidaManiaToUpdate.setGreats((Integer) updates.get("greats"));
            }
            if (updates.containsKey("goods")) {
                partidaManiaToUpdate.setGoods((Integer) updates.get("goods"));
            }
            if (updates.containsKey("misses")) {
                partidaManiaToUpdate.setMisses((Integer) updates.get("misses"));
            }
            if (updates.containsKey("detalles")) {
                partidaManiaToUpdate.setDetalles((String) updates.get("detalles"));
            }

            PartidaMania updatedPartidaMania = partidaManiaRepository.save(partidaManiaToUpdate);
            return convertToDto(updatedPartidaMania);
        }
        return null;
    }

    @Override
    public void deleteById(Integer idPartidaMania) {
        partidaManiaRepository.deleteById(idPartidaMania);
    }

    @Override
    public List<Integer> findAllPuntajes() {
        return partidaManiaRepository.findAll().stream()
                .map(PartidaMania::getPuntaje)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<BigDecimal> findAllAccuracies() { // Cambiado List<Double> a List<BigDecimal>
        return partidaManiaRepository.findAll().stream()
                .map(PartidaMania::getAccuracy)
                .distinct()
                .collect(Collectors.toList());
    }

    private PartidaManiaDto convertToDto(PartidaMania partidaMania) {
        PartidaManiaDto dto = new PartidaManiaDto();
        dto.setIdPartidaMania(partidaMania.getIdPartidaMania());
        if (partidaMania.getUsuario() != null) {
            dto.setIdUsuario(partidaMania.getUsuario().getId());
        }
        if (partidaMania.getChartMania() != null) {
            dto.setIdChartMania(partidaMania.getChartMania().getIdChartMania());
        }
        dto.setFecha(partidaMania.getFecha());
        dto.setPuntaje(partidaMania.getPuntaje());
        dto.setAccuracy(partidaMania.getAccuracy());
        dto.setComboMax(partidaMania.getComboMax());
        dto.setPerfects(partidaMania.getPerfects());
        dto.setGreats(partidaMania.getGreats());
        dto.setGoods(partidaMania.getGoods());
        dto.setMisses(partidaMania.getMisses());
        dto.setDetalles(partidaMania.getDetalles());
        return dto;
    }

    private PartidaMania convertToEntity(PartidaManiaDto dto) {
        PartidaMania partidaMania = new PartidaMania();
        partidaMania.setIdPartidaMania(dto.getIdPartidaMania());
        if (dto.getIdUsuario() != null) {
            usuarioRepository.findById(dto.getIdUsuario()).ifPresent(partidaMania::setUsuario);
        }
        if (dto.getIdChartMania() != null) {
            chartManiaRepository.findById(dto.getIdChartMania()).ifPresent(partidaMania::setChartMania);
        }
        partidaMania.setFecha(dto.getFecha());
        partidaMania.setPuntaje(dto.getPuntaje());
        partidaMania.setAccuracy(dto.getAccuracy());
        partidaMania.setComboMax(dto.getComboMax());
        partidaMania.setPerfects(dto.getPerfects());
        partidaMania.setGreats(dto.getGreats());
        partidaMania.setGoods(dto.getGoods());
        partidaMania.setMisses(dto.getMisses());
        partidaMania.setDetalles(dto.getDetalles());
        return partidaMania;
    }

    private void updateEntityFromDto(PartidaMania partidaMania, PartidaManiaDto dto) {
        if (dto.getIdUsuario() != null) {
            usuarioRepository.findById(dto.getIdUsuario()).ifPresent(partidaMania::setUsuario);
        }
        if (dto.getIdChartMania() != null) {
            chartManiaRepository.findById(dto.getIdChartMania()).ifPresent(partidaMania::setChartMania);
        }
        partidaMania.setPuntaje(dto.getPuntaje());
        partidaMania.setAccuracy(dto.getAccuracy());
        partidaMania.setComboMax(dto.getComboMax());
        partidaMania.setPerfects(dto.getPerfects());
        partidaMania.setGreats(dto.getGreats());
        partidaMania.setGoods(dto.getGoods());
        partidaMania.setMisses(dto.getMisses());
        partidaMania.setDetalles(dto.getDetalles());
    }
}
