package org.example.demo.repository.juego;

import org.example.demo.model.juego.PartidaMania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal; // Importar BigDecimal
import java.util.List;

@Repository
public interface PartidaManiaRepository extends JpaRepository<PartidaMania, Integer> {
    List<PartidaMania> findByUsuario_Id(Integer idUsuario);
    List<PartidaMania> findByChartMania_IdChartMania(Integer idChartMania);
    List<PartidaMania> findByPuntajeGreaterThanEqual(Integer puntaje);
    List<PartidaMania> findByAccuracyGreaterThanEqual(BigDecimal accuracy); // Cambiado Double a BigDecimal
}
