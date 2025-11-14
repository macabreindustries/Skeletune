package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.Mensaje;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeDto {

    private Integer idMensaje;
    private Integer idEmisor;
    private Integer idReceptor;
    private String mensaje;
    private Integer idMedia;
    private LocalDateTime fechaEnvio;
    private boolean visto;

    public MensajeDto(Mensaje mensaje) {
        this.idMensaje = mensaje.getIdMensaje();
        this.idEmisor = mensaje.getEmisor().getId();
        this.idReceptor = mensaje.getReceptor().getId();
        this.mensaje = mensaje.getMensaje();
        if (mensaje.getMedia() != null) {
            this.idMedia = mensaje.getMedia().getIdMedia();
        }
        this.fechaEnvio = mensaje.getFechaEnvio();
        this.visto = mensaje.isVisto();
    }
}
