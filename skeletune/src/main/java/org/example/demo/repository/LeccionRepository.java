package org.example.demo.repository;

import org.example.demo.model.Leccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeccionRepository extends JpaRepository<Leccion, Integer> {
    Optional<Leccion> findByTitulo(String titulo);
    List<Leccion> findByTipo(Leccion.Tipo tipo);
    List<Leccion> findByNivel(Leccion.Nivel nivel);
    List<Leccion> findByCancionIdCancion(Integer idCancion);
    List<Leccion> findByVideoIdVideo(Integer idVideo);
}
