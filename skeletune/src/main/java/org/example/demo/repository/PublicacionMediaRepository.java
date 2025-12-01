package org.example.demo.repository;

import org.example.demo.model.PublicacionMedia;
import org.example.demo.model.PublicacionMediaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionMediaRepository extends JpaRepository<PublicacionMedia, PublicacionMediaId> {
    List<PublicacionMedia> findById_IdPublicacion(Integer idPublicacion);
    List<PublicacionMedia> findById_IdMedia(Integer idMedia);
}
