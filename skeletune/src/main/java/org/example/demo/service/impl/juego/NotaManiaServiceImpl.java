package org.example.demo.service.impl.juego;

import org.example.demo.dto.juego.NotaManiaDto;
import org.example.demo.model.Media;
import org.example.demo.model.juego.ChartMania;
import org.example.demo.model.juego.NotaMania;
import org.example.demo.repository.MediaRepository;
import org.example.demo.repository.juego.ChartManiaRepository;
import org.example.demo.repository.juego.NotaManiaRepository;
import org.example.demo.service.juego.NotaManiaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotaManiaServiceImpl implements NotaManiaService {

    private final NotaManiaRepository notaManiaRepository;
    private final ChartManiaRepository chartManiaRepository;
    private final MediaRepository mediaRepository; // Inyectar MediaRepository

    public NotaManiaServiceImpl(NotaManiaRepository notaManiaRepository,
                                ChartManiaRepository chartManiaRepository,
                                MediaRepository mediaRepository) {
        this.notaManiaRepository = notaManiaRepository;
        this.chartManiaRepository = chartManiaRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public List<NotaManiaDto> findAll(Integer idChartMania, Integer tiempoMs, Byte carril, NotaMania.Tipo tipo) {
        List<NotaMania> notaManias = notaManiaRepository.findAll();

        return notaManias.stream()
                .filter(nm -> idChartMania == null || (nm.getChartMania() != null && nm.getChartMania().getIdChartMania().equals(idChartMania)))
                .filter(nm -> tiempoMs == null || nm.getTiempoMs().equals(tiempoMs))
                .filter(nm -> carril == null || nm.getCarril().equals(carril))
                .filter(nm -> tipo == null || nm.getTipo().equals(tipo))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotaManiaDto findById(Integer idNotaMania) {
        return notaManiaRepository.findById(idNotaMania)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public NotaManiaDto save(NotaManiaDto notaManiaDto) {
        NotaMania notaMania = convertToEntity(notaManiaDto);
        notaMania = notaManiaRepository.save(notaMania);
        return convertToDto(notaMania);
    }

    @Override
    public NotaManiaDto update(Integer idNotaMania, NotaManiaDto notaManiaDto) {
        Optional<NotaMania> existingNotaMania = notaManiaRepository.findById(idNotaMania);
        if (existingNotaMania.isPresent()) {
            NotaMania notaMania = existingNotaMania.get();
            updateEntityFromDto(notaMania, notaManiaDto);
            notaMania = notaManiaRepository.save(notaMania);
            return convertToDto(notaMania);
        }
        return null;
    }

    @Override
    public NotaManiaDto patch(Integer idNotaMania, Map<String, Object> updates) {
        Optional<NotaMania> existingNotaMania = notaManiaRepository.findById(idNotaMania);
        if (existingNotaMania.isPresent()) {
            NotaMania notaManiaToUpdate = existingNotaMania.get();

            if (updates.containsKey("idChartMania")) {
                chartManiaRepository.findById(((Number) updates.get("idChartMania")).intValue())
                        .ifPresent(notaManiaToUpdate::setChartMania);
            }
            if (updates.containsKey("tiempoMs")) {
                notaManiaToUpdate.setTiempoMs((Integer) updates.get("tiempoMs"));
            }
            if (updates.containsKey("carril")) {
                notaManiaToUpdate.setCarril(((Number) updates.get("carril")).byteValue());
            }
            if (updates.containsKey("duracionMs")) {
                notaManiaToUpdate.setDuracionMs((Integer) updates.get("duracionMs"));
            }
            if (updates.containsKey("imagenMediaId")) {
                mediaRepository.findById(((Number) updates.get("imagenMediaId")).intValue())
                        .ifPresent(notaManiaToUpdate::setImagenMedia);
            }
            if (updates.containsKey("tipo")) {
                notaManiaToUpdate.setTipo(NotaMania.Tipo.valueOf((String) updates.get("tipo")));
            }

            NotaMania updatedNotaMania = notaManiaRepository.save(notaManiaToUpdate);
            return convertToDto(updatedNotaMania);
        }
        return null;
    }

    @Override
    public void deleteById(Integer idNotaMania) {
        notaManiaRepository.deleteById(idNotaMania);
    }

    @Override
    public List<NotaMania.Tipo> findAllTipos() {
        return List.of(NotaMania.Tipo.values());
    }

    private NotaManiaDto convertToDto(NotaMania notaMania) {
        NotaManiaDto dto = new NotaManiaDto();
        dto.setIdNotaMania(notaMania.getIdNotaMania());
        if (notaMania.getChartMania() != null) {
            dto.setIdChartMania(notaMania.getChartMania().getIdChartMania());
        }
        dto.setTiempoMs(notaMania.getTiempoMs());
        dto.setCarril(notaMania.getCarril());
        dto.setDuracionMs(notaMania.getDuracionMs());
        if (notaMania.getImagenMedia() != null) {
            dto.setImagenMediaId(notaMania.getImagenMedia().getIdMedia());
        }
        dto.setTipo(notaMania.getTipo());
        return dto;
    }

    private NotaMania convertToEntity(NotaManiaDto dto) {
        NotaMania notaMania = new NotaMania();
        notaMania.setIdNotaMania(dto.getIdNotaMania());
        if (dto.getIdChartMania() != null) {
            chartManiaRepository.findById(dto.getIdChartMania()).ifPresent(notaMania::setChartMania);
        }
        notaMania.setTiempoMs(dto.getTiempoMs());
        notaMania.setCarril(dto.getCarril());
        notaMania.setDuracionMs(dto.getDuracionMs());
        if (dto.getImagenMediaId() != null) {
            mediaRepository.findById(dto.getImagenMediaId()).ifPresent(notaMania::setImagenMedia); // Usar mediaRepository
        }
        notaMania.setTipo(dto.getTipo());
        return notaMania;
    }

    private void updateEntityFromDto(NotaMania notaMania, NotaManiaDto dto) {
        if (dto.getIdChartMania() != null) {
            chartManiaRepository.findById(dto.getIdChartMania()).ifPresent(notaMania::setChartMania);
        }
        notaMania.setTiempoMs(dto.getTiempoMs());
        notaMania.setCarril(dto.getCarril());
        notaMania.setDuracionMs(dto.getDuracionMs());
        if (dto.getImagenMediaId() != null) {
            mediaRepository.findById(dto.getImagenMediaId()).ifPresent(notaMania::setImagenMedia); // Usar mediaRepository
        }
        notaMania.setTipo(dto.getTipo());
    }
}
