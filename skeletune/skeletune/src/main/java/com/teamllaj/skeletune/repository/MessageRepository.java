package com.teamllaj.skeletune.repository;
import com.teamllaj.skeletune.model.entity.MessageEntity; // Importación de la entidad renombrada
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MessageRepository extends JpaRepository <MessageEntity, Long> {

    // Método para buscar mensajes por nombre de usuario, ordenados por fecha descendente
    List<MessageEntity> findByUsernameOrderBySentAtDesc(String username);
}