package org.example.demo.repository;

import org.example.demo.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    
    @Query("SELECT n FROM Notificacion n WHERE n.usuario.id = :idUsuario")
    List<Notificacion> findByUsuarioId(@Param("idUsuario") Integer idUsuario);
}
