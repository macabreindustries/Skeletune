package org.example.demo.service;

import org.example.demo.dto.RachaDTO;

public interface RachaService {
    /**
     * Obtiene el resumen detallado de la racha, incluyendo estadísticas
     * y el historial de actividad de la semana y el mes.
     * * @param idUsuario ID del estudiante
     * @return DTO con la información procesada para la racha
     */
    RachaDTO obtenerResumenRacha(int idUsuario);
}