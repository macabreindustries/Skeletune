package org.example.demo.service.impl;

import org.example.demo.dto.ValidacionRolDto;
import org.example.demo.model.Usuario;
import org.example.demo.model.ValidacionRol;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.repository.ValidacionRolRepository;
import org.example.demo.service.ValidacionRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ValidacionRolServiceImpl implements ValidacionRolService {

    @Autowired
    private ValidacionRolRepository validacionRolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Needed for conversion

    @Override
    public List<ValidacionRolDto> getAllValidaciones() {
        return validacionRolRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ValidacionRolDto> getValidacionById(int id) {
        return validacionRolRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public ValidacionRolDto saveValidacion(ValidacionRolDto validacionRolDto) {
        ValidacionRol validacionRol = convertToEntity(validacionRolDto);
        validacionRol = validacionRolRepository.save(validacionRol);
        return convertToDto(validacionRol);
    }

    @Override
    public void deleteValidacion(int id) {
        validacionRolRepository.deleteById(id);
    }

    private ValidacionRolDto convertToDto(ValidacionRol validacion) {
        ValidacionRolDto dto = new ValidacionRolDto();
        dto.setId_validacion(validacion.getId_validacion());
        dto.setFechaValidacion(validacion.getFechaValidacion());
        dto.setEstado(validacion.getEstado().name());
        if (validacion.getUsuarioValidado() != null) {
            dto.setId_usuario_validado(validacion.getUsuarioValidado().getId());
        }
        if (validacion.getAdminValidador() != null) {
            dto.setId_admin_validador(validacion.getAdminValidador().getId());
        }
        return dto;
    }

    private ValidacionRol convertToEntity(ValidacionRolDto dto) {
        ValidacionRol validacion = new ValidacionRol();
        validacion.setId_validacion(dto.getId_validacion());
        validacion.setFechaValidacion(dto.getFechaValidacion());
        validacion.setEstado(ValidacionRol.EstadoValidacion.valueOf(dto.getEstado()));

        Usuario usuarioValidado = usuarioRepository.findById(dto.getId_usuario_validado())
                .orElseThrow(() -> new RuntimeException("Usuario validado no encontrado"));
        validacion.setUsuarioValidado(usuarioValidado);

        Usuario adminValidador = usuarioRepository.findById(dto.getId_admin_validador())
                .orElseThrow(() -> new RuntimeException("Admin validador no encontrado"));
        validacion.setAdminValidador(adminValidador);

        return validacion;
    }
}
