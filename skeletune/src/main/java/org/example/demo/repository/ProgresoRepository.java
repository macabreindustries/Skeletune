package org.example.demo.repository;

import org.example.demo.model.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProgresoRepository extends JpaRepository<Progreso, Integer> {

    // Corrected to find by the 'id' field of the 'usuario' entity
    List<Progreso> findByUsuario_Id(Integer idUsuario);

    // Corrected to find by the 'idLeccion' field of the 'leccion' entity
    List<Progreso> findByLeccion_IdLeccion(Integer idLeccion);

    List<Progreso> findByFecha(LocalDate fecha);

    // Corrected the path from 'p.usuario.idUsuario' to 'p.usuario.id'
    @Query("SELECT p FROM Progreso p WHERE p.usuario.id = :idUsuario AND p.fecha = :fecha")
    List<Progreso> findByUsuarioAndFecha(@Param("idUsuario") Integer idUsuario, @Param("fecha") LocalDate fecha);
}
