package org.example.demo.repository;

import org.example.demo.model.Videollamada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideollamadaRepository extends JpaRepository<Videollamada, Integer> {

    // Métodos de consulta personalizados si los necesitas en el futuro
    // Por ejemplo, buscar videollamadas por usuario:
    List<Videollamada> findByEmisor_IdOrReceptor_Id(Integer idEmisor, Integer idReceptor);
}