package org.example.demo.repository.juego;

import org.example.demo.model.juego.FalloMania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FalloManiaRepository extends JpaRepository<FalloMania, Integer> {
    List<FalloMania> findByPartidaMania_IdPartidaMania(Integer idPartidaMania);
    List<FalloMania> findByTipo(FalloMania.Tipo tipo);
}
