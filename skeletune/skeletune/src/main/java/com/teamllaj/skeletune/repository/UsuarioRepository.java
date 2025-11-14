package com.teamllaj.skeletune.repository;

import com.teamllaj.skeletune.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Usuario.
 * Extiende JpaRepository para obtener operaciones CRUD básicas.
 * * Parámetros:
 * - Usuario: La entidad JPA con la que trabaja.
 * - Long: El tipo de la clave primaria (idUsuario).
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Aquí puedes añadir métodos de consulta personalizados si los necesitas,
    // como: findByCorreo(String correo)
}