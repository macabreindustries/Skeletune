package com.teamllaj.skeletune.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Importa las anotaciones correctas (jakarta.persistence o javax.persistence)

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    // Asegúrate de que las propiedades coincidan con los nombres de la base de datos
    private Long idUsuario;

    // ... otros campos como correo, contrasena, etc.

    // Getters y Setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}