package org.example.demo.repository;

import org.example.demo.model.EstadisticaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadisticaUsuarioRepository extends JpaRepository<EstadisticaUsuario, Integer> {
    Optional<EstadisticaUsuario> findByUsuarioId(Integer idUsuario);
}
