package org.example.demo.repository;

import org.example.demo.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
    List<Publicacion> findByUsuarioId(Integer idUsuario);
}
