package org.example.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.demo.dto.MensajeDto;
import org.example.demo.model.Media;
import org.example.demo.model.Mensaje;
import org.example.demo.model.Usuario;
import org.example.demo.repository.MediaRepository;
import org.example.demo.repository.MensajeRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensajeServiceImpl implements MensajeService {

    private final MensajeRepository mensajeRepository;
    private final UsuarioRepository usuarioRepository;
    private final MediaRepository mediaRepository;

    @Autowired
    public MensajeServiceImpl(MensajeRepository mensajeRepository, UsuarioRepository usuarioRepository, MediaRepository mediaRepository) {
        this.mensajeRepository = mensajeRepository;
        this.usuarioRepository = usuarioRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public List<MensajeDto> findAll() {
        return mensajeRepository.findAll().stream()
                .map(MensajeDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MensajeDto> getConversation(Integer idUsuario1, Integer idUsuario2) {
        return mensajeRepository.findConversation(idUsuario1, idUsuario2).stream()
                .map(MensajeDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public MensajeDto sendMessage(MensajeDto mensajeDto) {
        Usuario emisor = usuarioRepository.findById(mensajeDto.getIdEmisor())
                .orElseThrow(() -> new EntityNotFoundException("Emisor no encontrado con id: " + mensajeDto.getIdEmisor()));
        Usuario receptor = usuarioRepository.findById(mensajeDto.getIdReceptor())
                .orElseThrow(() -> new EntityNotFoundException("Receptor no encontrado con id: " + mensajeDto.getIdReceptor()));

        Mensaje mensaje = new Mensaje();
        mensaje.setEmisor(emisor);
        mensaje.setReceptor(receptor);
        mensaje.setMensaje(mensajeDto.getMensaje());

        if (mensajeDto.getIdMedia() != null) {
            Media media = mediaRepository.findById(mensajeDto.getIdMedia())
                    .orElseThrow(() -> new EntityNotFoundException("Media no encontrada con id: " + mensajeDto.getIdMedia()));
            mensaje.setMedia(media);
        }

        Mensaje savedMensaje = mensajeRepository.save(mensaje);
        return new MensajeDto(savedMensaje);
    }

    @Override
    public MensajeDto updateMessage(Integer id, MensajeDto mensajeDto) {
        Mensaje existingMensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mensaje no encontrado con id: " + id));

        // Solo permitimos actualizar el contenido del texto del mensaje
        existingMensaje.setMensaje(mensajeDto.getMensaje());
        // Opcional: marcar como no visto si se edita
        existingMensaje.setVisto(false); 

        Mensaje updatedMensaje = mensajeRepository.save(existingMensaje);
        return new MensajeDto(updatedMensaje);
    }

    @Override
    public void deleteMessage(Integer id) {
        if (!mensajeRepository.existsById(id)) {
            throw new EntityNotFoundException("Mensaje no encontrado con id: " + id);
        }
        mensajeRepository.deleteById(id);
    }
}
