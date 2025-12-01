package org.example.demo.service;

import org.example.demo.dto.InstrumentoDto;
import java.util.List;
import java.util.Optional;

public interface InstrumentoService {
    List<InstrumentoDto> getAllInstrumentos();
    Optional<InstrumentoDto> getInstrumentoById(int id);
    InstrumentoDto saveInstrumento(InstrumentoDto instrumentoDto);
    void deleteInstrumento(int id);
}
