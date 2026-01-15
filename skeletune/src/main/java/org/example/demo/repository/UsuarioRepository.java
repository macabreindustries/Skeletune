package org.example.demo.repository;

import org.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Importante para evitar errores de compilación

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Spring Data JPA generará la consulta automáticamente:
    // "SELECT * FROM usuario WHERE correo = ?"
    Optional<Usuario> findByCorreo(String correo);

    // --- Métodos que ya tenías ---
    @Query("SELECT u.nombre FROM Usuario u")
    List<String> findAllNombres();

    @Query("SELECT u.correo FROM Usuario u")
    List<String> findAllCorreos();
}