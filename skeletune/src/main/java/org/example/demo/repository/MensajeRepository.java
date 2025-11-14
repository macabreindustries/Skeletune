package org.example.demo.repository;

import org.example.demo.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

    @Query("SELECT m FROM Mensaje m WHERE (m.emisor.id = :idUsuario1 AND m.receptor.id = :idUsuario2) OR (m.emisor.id = :idUsuario2 AND m.receptor.id = :idUsuario1) ORDER BY m.fechaEnvio ASC")
    List<Mensaje> findConversation(@Param("idUsuario1") Integer idUsuario1, @Param("idUsuario2") Integer idUsuario2);
}
