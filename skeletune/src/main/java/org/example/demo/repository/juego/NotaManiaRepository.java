package org.example.demo.repository.juego;

import org.example.demo.model.juego.NotaMania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaManiaRepository extends JpaRepository<NotaMania, Integer> {
    List<NotaMania> findByChartMania_IdChartMania(Integer idChartMania);
    List<NotaMania> findByTipo(NotaMania.Tipo tipo);
}
