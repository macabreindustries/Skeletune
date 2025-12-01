package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicacionDto {
    private Integer idPublicacion;
    private Integer idUsuario;
    private String texto;
    private LocalDateTime fechaPublicacion;
    private Integer idMediaPrincipal;
    private Set<Integer> mediaAdjuntosIds;
}
