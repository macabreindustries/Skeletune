package org.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RachaDTO {
    private int rachaActual;
    private int mejorRacha;
    private int totalDiasPracticados;
    private Map<String, Boolean> estadoSemana;
    private List<LocalDate> historialMes;
}