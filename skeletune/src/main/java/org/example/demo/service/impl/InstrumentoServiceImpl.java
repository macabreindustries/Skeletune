package org.example.demo.service.impl;

import org.example.demo.dto.InstrumentoDto;
import org.example.demo.model.Instrumento;
import org.example.demo.repository.InstrumentoRepository;
import org.example.demo.service.InstrumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstrumentoServiceImpl implements InstrumentoService {

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Override
    public List<InstrumentoDto> getAllInstrumentos() {
        return instrumentoRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<InstrumentoDto> getInstrumentoById(int id) {
        return instrumentoRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public InstrumentoDto saveInstrumento(InstrumentoDto instrumentoDto) {
        Instrumento instrumento = convertToEntity(instrumentoDto);
        instrumento = instrumentoRepository.save(instrumento);
        return convertToDto(instrumento);
    }

    @Override
    public void deleteInstrumento(int id) {
        instrumentoRepository.deleteById(id);
    }

    private InstrumentoDto convertToDto(Instrumento instrumento) {
        InstrumentoDto dto = new InstrumentoDto();
        dto.setId_instrumento(instrumento.getId_instrumento());
        dto.setNombreInstrumento(instrumento.getNombreInstrumento());
        dto.setTipo(instrumento.getTipo());
        return dto;
    }

    private Instrumento convertToEntity(InstrumentoDto dto) {
        Instrumento instrumento = new Instrumento();
        instrumento.setId_instrumento(dto.getId_instrumento());
        instrumento.setNombreInstrumento(dto.getNombreInstrumento());
        instrumento.setTipo(dto.getTipo());
        return instrumento;
    }
}
