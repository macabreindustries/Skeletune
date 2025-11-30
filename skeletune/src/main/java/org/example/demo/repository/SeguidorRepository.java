package org.example.demo.repository;

import org.example.demo.model.Seguidor;
import org.example.demo.model.SeguidorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguidorRepository extends JpaRepository<Seguidor, SeguidorId> {

    List<Seguidor> findById_IdSeguidor(Integer idSeguidor);

    List<Seguidor> findById_IdSeguido(Integer idSeguido);
}
