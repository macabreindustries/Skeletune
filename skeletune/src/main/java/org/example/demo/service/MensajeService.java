package org.example.demo.service;

import org.example.demo.dto.MensajeDto;

import java.util.List;

public interface MensajeService {
    List<MensajeDto> findAll();
    List<MensajeDto> getConversation(Integer idUsuario1, Integer idUsuario2);
    MensajeDto sendMessage(MensajeDto mensajeDto);
    MensajeDto updateMessage(Integer id, MensajeDto mensajeDto);
    void deleteMessage(Integer id);
}
