package org.example.demo.service;

import org.example.demo.dto.RolDto;
import org.example.demo.dto.UsuarioDto;
import org.example.demo.model.Rol;
import org.example.demo.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDto> getAll();

    UsuarioDto getById(Integer id);

    UsuarioDto save(UsuarioDto usuarioDto);

    UsuarioDto login(String correo, String contrasena);

    UsuarioDto update(Integer id, UsuarioDto usuarioDto);

    void delete(Integer id);

    // --- Métodos para filtros ---
    List<String> findAllNombres();
    List<String> findAllCorreos();
    List<RolDto> findAllRoles();
}
