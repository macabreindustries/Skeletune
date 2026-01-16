package org.example.demo.service.impl;

import org.example.demo.dto.RachaDTO;
import org.example.demo.model.EstadisticaUsuario;
import org.example.demo.repository.EstadisticaUsuarioRepository;
import org.example.demo.repository.ProgresoRepository;
import org.example.demo.service.RachaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor // Lombok genera el constructor para la inyección de dependencias
public class RachaServiceImpl implements RachaService {

    private final EstadisticaUsuarioRepository estadisticaRepo;
    private final ProgresoRepository progresoRepo;

    @Override
    public RachaDTO obtenerResumenRacha(int idUsuario) {
        RachaDTO dto = new RachaDTO();

        // El nombre ahora debe coincidir con el de tu repositorio corregido
        EstadisticaUsuario stats = estadisticaRepo.findByUsuario_Id(idUsuario)
                .orElse(new EstadisticaUsuario());

        dto.setRachaActual(stats.getRachaDias());
        dto.setMejorRacha(stats.getRachaDias());

        // 2. Historial del mes (últimos 30 días)
        List<LocalDate> actividad = progresoRepo.findActividadReciente(idUsuario, LocalDate.now().minusDays(30));
        dto.setHistorialMes(actividad);
        dto.setTotalDiasPracticados(actividad.size());

        // 3. Estado de la semana actual (Lunes a Domingo)
        Map<String, Boolean> semana = new LinkedHashMap<>();
        String[] nombresDias = {"L", "M", "M", "J", "V", "S", "D"};
        LocalDate lunes = LocalDate.now().with(DayOfWeek.MONDAY);

        for (int i = 0; i < 7; i++) {
            LocalDate diaEvaluado = lunes.plusDays(i);
            semana.put(nombresDias[i], actividad.contains(diaEvaluado));
        }
        dto.setEstadoSemana(semana);

        return dto;
    }
}
