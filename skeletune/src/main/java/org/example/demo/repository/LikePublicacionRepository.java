package org.example.demo.repository;

import org.example.demo.model.LikePublicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikePublicacionRepository extends JpaRepository<LikePublicacion, Integer> {
    List<LikePublicacion> findByPublicacionIdPublicacion(Integer idPublicacion);
    List<LikePublicacion> findByUsuarioId(Integer idUsuario);
    Optional<LikePublicacion> findByUsuarioIdAndPublicacionIdPublicacion(Integer idUsuario, Integer idPublicacion);
    long countByPublicacionIdPublicacion(Integer idPublicacion);
}
