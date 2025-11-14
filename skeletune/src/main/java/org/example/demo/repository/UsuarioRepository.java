package org.example.demo.repository;

import org.example.demo.model.Rol;
import org.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT DISTINCT u.nombre FROM Usuario u ORDER BY u.nombre")
    List<String> findAllNombres();

    @Query("SELECT DISTINCT u.correo FROM Usuario u ORDER BY u.correo")
    List<String> findAllCorreos();
    
    // Es más eficiente obtener los roles desde su propio repositorio,
    // pero si se quisiera desde aquí, sería así:
    @Query("SELECT DISTINCT u.rol FROM Usuario u")
    List<Rol> findAllRoles();
}
