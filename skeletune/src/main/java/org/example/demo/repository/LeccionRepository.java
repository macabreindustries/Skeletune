package org.example.demo.repository;

import org.example.demo.model.Leccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeccionRepository extends JpaRepository<Leccion, Integer> {
    List<Leccion> findByTituloContainingIgnoreCase(String titulo);
    List<Leccion> findByTipo(Leccion.Tipo tipo);
    List<Leccion> findByNivel(Leccion.Nivel nivel);
    List<Leccion> findByCancion_IdCancion(Integer idCancion);
    List<Leccion> findByVideo_IdVideo(Integer idVideo);
}
