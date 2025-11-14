package com.teamllaj.skeletune.repository;

import com.teamllaj.skeletune.model.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Esta interfaz maneja todas las operaciones de persistencia de datos
 * para la entidad NotificationEntity.
 */
@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    // Al extender JpaRepository, Spring genera automáticamente métodos como:
    // - save(entity)
    // - findById(id)
    // - findAll()
    // - delete(entity)

    // Si quisieras buscar notificaciones por el nombre de usuario:
    // List<NotificationEntity> findByUsername(String username);
}