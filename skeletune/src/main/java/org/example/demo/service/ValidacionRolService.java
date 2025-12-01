package org.example.demo.service;

import org.example.demo.dto.ValidacionRolDto;
import java.util.List;
import java.util.Optional;

public interface ValidacionRolService {
    List<ValidacionRolDto> getAllValidaciones();
    Optional<ValidacionRolDto> getValidacionById(int id);
    ValidacionRolDto saveValidacion(ValidacionRolDto validacionRolDto);
    void deleteValidacion(int id);
}
