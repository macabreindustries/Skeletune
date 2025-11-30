package org.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class HistoriaDto {
    private Integer idHistoria;
    private Integer idUsuario;
    private Integer idMedia;
    private LocalDateTime fechaPublicacion;
    private LocalDateTime expiraEn;
}
