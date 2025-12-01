package org.example.demo.repository;

import org.example.demo.model.Historia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriaRepository extends JpaRepository<Historia, Integer> {
    List<Historia> findByUsuarioId(Integer idUsuario);
}
