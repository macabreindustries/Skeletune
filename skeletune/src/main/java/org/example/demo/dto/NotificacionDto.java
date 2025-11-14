package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Notificacion;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDto {

    private Integer idNotificacion;
    private Integer idUsuario;
    private Notificacion.Tipo tipo;
    private String titulo;
    private String mensaje;
    private Integer idReferencia;
    private String tablaReferencia;
    private LocalDateTime fecha;
    private boolean leido;

    public NotificacionDto(Notificacion notificacion) {
        this.idNotificacion = notificacion.getIdNotificacion();
        this.idUsuario = notificacion.getUsuario().getId();
        this.tipo = notificacion.getTipo();
        this.titulo = notificacion.getTitulo();
        this.mensaje = notificacion.getMensaje();
        this.idReferencia = notificacion.getIdReferencia();
        this.tablaReferencia = notificacion.getTablaReferencia();
        this.fecha = notificacion.getFecha();
        this.leido = notificacion.isLeido();
    }
}
