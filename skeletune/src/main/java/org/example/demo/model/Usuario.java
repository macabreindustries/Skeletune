package org.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id; // Mantenlo así si tu repositorio ya usa "id"

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    // Cambiamos esto para que Spring SI pueda manejar la fecha
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    // Esto asegura que la fecha se ponga sola antes de guardar
    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

    @Column(name = "url_avatar")
    private String urlAvatar = "https://i.imgur.com/Heidi26.png"; // Imagen por defecto
}