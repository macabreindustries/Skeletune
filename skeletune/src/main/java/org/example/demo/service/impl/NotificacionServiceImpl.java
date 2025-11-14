package org.example.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.NotificacionDto;
import org.example.demo.model.Notificacion;
import org.example.demo.model.Usuario;
import org.example.demo.repository.NotificacionRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public NotificacionServiceImpl(NotificacionRepository notificacionRepository, UsuarioRepository usuarioRepository) {
        this.notificacionRepository = notificacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<NotificacionDto> findAll() {
        return notificacionRepository.findAll().stream()
                .map(NotificacionDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificacionDto> findByUsuarioId(Integer idUsuario) {
        return notificacionRepository.findByUsuarioId(idUsuario).stream()
                .map(NotificacionDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public NotificacionDto markAsRead(Integer idNotificacion) {
        Notificacion notificacion = notificacionRepository.findById(idNotificacion)
                .orElseThrow(() -> new EntityNotFoundException("Notificacion no encontrada con id: " + idNotificacion));
        notificacion.setLeido(true);
        Notificacion updatedNotificacion = notificacionRepository.save(notificacion);
        return new NotificacionDto(updatedNotificacion);
    }

    @Override
    public NotificacionDto save(NotificacionDto notificacionDto) {
        Usuario usuario = usuarioRepository.findById(notificacionDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + notificacionDto.getIdUsuario()));

        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(usuario);
        notificacion.setTipo(notificacionDto.getTipo());
        notificacion.setTitulo(notificacionDto.getTitulo());
        notificacion.setMensaje(notificacionDto.getMensaje());
        notificacion.setIdReferencia(notificacionDto.getIdReferencia());
        notificacion.setTablaReferencia(notificacionDto.getTablaReferencia());

        Notificacion savedNotificacion = notificacionRepository.save(notificacion);
        return new NotificacionDto(savedNotificacion);
    }
}
