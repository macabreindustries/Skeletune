package org.example.demo.repository;

import org.example.demo.model.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProgresoRepository extends JpaRepository<Progreso, Integer> {
    List<Progreso> findByUsuarioId(Integer idUsuario);
    List<Progreso> findByLeccionIdLeccion(Integer idLeccion);
    List<Progreso> findByUsuarioIdAndFechaBetween(Integer idUsuario, LocalDate startDate, LocalDate endDate);
}
