package org.example.demo.repository.juego;

import org.example.demo.model.juego.ChartMania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChartManiaRepository extends JpaRepository<ChartMania, Integer> {
    List<ChartMania> findByCancion_IdCancion(Integer idCancion);
    List<ChartMania> findByDificultad(ChartMania.Dificultad dificultad);
    List<ChartMania> findBySpeedMultiplier(Float speedMultiplier);
}
