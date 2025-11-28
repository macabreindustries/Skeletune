package org.example.demo.repository;

import org.example.demo.model.VideoEducativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoEducativoRepository extends JpaRepository<VideoEducativo, Integer> {
    List<VideoEducativo> findByProfesor_Id(Integer idProfesor);
    List<VideoEducativo> findByTituloContainingIgnoreCase(String titulo);
}
