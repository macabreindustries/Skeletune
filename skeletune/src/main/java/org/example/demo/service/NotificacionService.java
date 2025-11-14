package org.example.demo.service;

import org.example.demo.dto.NotificacionDto;

import java.util.List;

public interface NotificacionService {
    List<NotificacionDto> findAll();
    List<NotificacionDto> findByUsuarioId(Integer idUsuario);
    NotificacionDto markAsRead(Integer idNotificacion);
    NotificacionDto save(NotificacionDto notificacionDto);
}
