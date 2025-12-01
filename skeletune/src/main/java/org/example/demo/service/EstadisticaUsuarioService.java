package org.example.demo.service;

import org.example.demo.dto.EstadisticaUsuarioDto;

import java.util.Map;

public interface EstadisticaUsuarioService {
    EstadisticaUsuarioDto getEstadisticasByUsuarioId(Integer idUsuario);
    EstadisticaUsuarioDto createEstadisticas(Integer idUsuario);
    EstadisticaUsuarioDto updateEstadisticas(Integer idUsuario, EstadisticaUsuarioDto estadisticaDto);
    EstadisticaUsuarioDto patchEstadisticas(Integer idUsuario, Map<String, Object> updates);
}
