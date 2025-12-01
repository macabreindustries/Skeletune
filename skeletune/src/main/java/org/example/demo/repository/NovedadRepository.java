package org.example.demo.repository;

import org.example.demo.model.Novedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovedadRepository extends JpaRepository<Novedad, Integer> {
    List<Novedad> findByAdminId(Integer idAdmin);
}
