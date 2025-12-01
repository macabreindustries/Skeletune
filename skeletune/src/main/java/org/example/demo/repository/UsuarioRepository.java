package org.example.demo.repository;

import org.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u.nombre FROM Usuario u")
    List<String> findAllNombres();

    @Query("SELECT u.correo FROM Usuario u")
    List<String> findAllCorreos();
}
