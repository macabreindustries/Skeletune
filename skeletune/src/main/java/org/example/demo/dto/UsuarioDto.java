package org.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import org.example.demo.model.Rol;
import org.example.demo.model.Usuario;

import java.time.LocalDateTime;

@Builder
@Data
public class UsuarioDto {

    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private LocalDateTime fechaRegistro;

    private Integer idRol;
    private String nombreRol;

    public static UsuarioDto fromEntity(Usuario u) {
        if (u == null) return null;

        return UsuarioDto.builder()
                .idUsuario(u.getId())
                .nombre(u.getNombre())
                .correo(u.getCorreo())
                .contrasena(u.getContrasena())
                .fechaRegistro(u.getFechaRegistro())
                // Verificamos que el rol no sea nulo antes de pedirle el ID
                .idRol(u.getRol() != null ? u.getRol().getId() : null)
                .nombreRol(u.getRol() != null ? u.getRol().getNombre() : "SIN_ROL")
                .build();
    }
    public Usuario toEntity(Rol rol) {
        return Usuario.builder()
                .id(this.idUsuario)
                .nombre(this.nombre)
                .correo(this.correo)
                .contrasena(this.contrasena)
                .fechaRegistro(this.fechaRegistro)
                .rol(rol)
                .build();
    }
}
