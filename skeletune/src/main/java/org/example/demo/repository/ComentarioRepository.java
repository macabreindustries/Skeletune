package org.example.demo.repository;

import org.example.demo.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByPublicacionIdPublicacion(Integer idPublicacion);
    List<Comentario> findByUsuarioId(Integer idUsuario);
}
