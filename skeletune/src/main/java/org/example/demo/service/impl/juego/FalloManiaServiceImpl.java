package org.example.demo.service.impl.juego;

import org.example.demo.dto.juego.FalloManiaDto;
import org.example.demo.model.juego.FalloMania;
import org.example.demo.model.juego.PartidaMania;
import org.example.demo.repository.juego.FalloManiaRepository;
import org.example.demo.repository.juego.PartidaManiaRepository;
import org.example.demo.service.juego.FalloManiaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FalloManiaServiceImpl implements FalloManiaService {

    private final FalloManiaRepository falloManiaRepository;
    private final PartidaManiaRepository partidaManiaRepository;

    public FalloManiaServiceImpl(FalloManiaRepository falloManiaRepository,
                                 PartidaManiaRepository partidaManiaRepository) {
        this.falloManiaRepository = falloManiaRepository;
        this.partidaManiaRepository = partidaManiaRepository;
    }

    @Override
    public List<FalloManiaDto> findAll(Integer idPartidaMania, Integer tiempoMs, FalloMania.Tipo tipo) {
        List<FalloMania> falloManias = falloManiaRepository.findAll();

        return falloManias.stream()
                .filter(fm -> idPartidaMania == null || (fm.getPartidaMania() != null && fm.getPartidaMania().getIdPartidaMania().equals(idPartidaMania)))
                .filter(fm -> tiempoMs == null || fm.getTiempoMs().equals(tiempoMs))
                .filter(fm -> tipo == null || fm.getTipo().equals(tipo))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FalloManiaDto findById(Integer idFalloMania) {
        return falloManiaRepository.findById(idFalloMania)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public FalloManiaDto save(FalloManiaDto falloManiaDto) {
        FalloMania falloMania = convertToEntity(falloManiaDto);
        falloMania = falloManiaRepository.save(falloMania);
        return convertToDto(falloMania);
    }

    @Override
    public FalloManiaDto update(Integer idFalloMania, FalloManiaDto falloManiaDto) {
        Optional<FalloMania> existingFalloMania = falloManiaRepository.findById(idFalloMania);
        if (existingFalloMania.isPresent()) {
            FalloMania falloMania = existingFalloMania.get();
            updateEntityFromDto(falloMania, falloManiaDto);
            falloMania = falloManiaRepository.save(falloMania);
            return convertToDto(falloMania);
        }
        return null;
    }

    @Override
    public FalloManiaDto patch(Integer idFalloMania, Map<String, Object> updates) {
        Optional<FalloMania> existingFalloMania = falloManiaRepository.findById(idFalloMania);
        if (existingFalloMania.isPresent()) {
            FalloMania falloManiaToUpdate = existingFalloMania.get();

            if (updates.containsKey("idPartidaMania")) {
                partidaManiaRepository.findById(((Number) updates.get("idPartidaMania")).intValue())
                        .ifPresent(falloManiaToUpdate::setPartidaMania);
            }
            if (updates.containsKey("tiempoMs")) {
                falloManiaToUpdate.setTiempoMs((Integer) updates.get("tiempoMs"));
            }
            if (updates.containsKey("tipo")) {
                falloManiaToUpdate.setTipo(FalloMania.Tipo.valueOf((String) updates.get("tipo")));
            }
            if (updates.containsKey("desviacionMs")) {
                falloManiaToUpdate.setDesviacionMs((Integer) updates.get("desviacionMs"));
            }

            FalloMania updatedFalloMania = falloManiaRepository.save(falloManiaToUpdate);
            return convertToDto(updatedFalloMania);
        }
        return null;
    }

    @Override
    public void deleteById(Integer idFalloMania) {
        falloManiaRepository.deleteById(idFalloMania);
    }

    @Override
    public List<FalloMania.Tipo> findAllTipos() {
        return List.of(FalloMania.Tipo.values());
    }

    private FalloManiaDto convertToDto(FalloMania falloMania) {
        FalloManiaDto dto = new FalloManiaDto();
        dto.setIdFalloMania(falloMania.getIdFalloMania());
        if (falloMania.getPartidaMania() != null) {
            dto.setIdPartidaMania(falloMania.getPartidaMania().getIdPartidaMania());
        }
        dto.setTiempoMs(falloMania.getTiempoMs());
        dto.setTipo(falloMania.getTipo());
        dto.setDesviacionMs(falloMania.getDesviacionMs());
        return dto;
    }

    private FalloMania convertToEntity(FalloManiaDto dto) {
        FalloMania falloMania = new FalloMania();
        falloMania.setIdFalloMania(dto.getIdFalloMania());
        if (dto.getIdPartidaMania() != null) {
            partidaManiaRepository.findById(dto.getIdPartidaMania()).ifPresent(falloMania::setPartidaMania);
        }
        falloMania.setTiempoMs(dto.getTiempoMs());
        falloMania.setTipo(dto.getTipo());
        falloMania.setDesviacionMs(dto.getDesviacionMs());
        return falloMania;
    }

    private void updateEntityFromDto(FalloMania falloMania, FalloManiaDto dto) {
        if (dto.getIdPartidaMania() != null) {
            partidaManiaRepository.findById(dto.getIdPartidaMania()).ifPresent(falloMania::setPartidaMania);
        }
        falloMania.setTiempoMs(dto.getTiempoMs());
        falloMania.setTipo(dto.getTipo());
        falloMania.setDesviacionMs(dto.getDesviacionMs());
    }
}
