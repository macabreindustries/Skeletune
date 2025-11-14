package org.example.demo.service;

import org.example.demo.dto.RolDto;
import org.example.demo.dto.UsuarioDto;
import org.example.demo.model.Rol;
import org.example.demo.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> getAll();

    Usuario getById(Integer id);

    Usuario save(Usuario usuario);

    Usuario update(Integer id, Usuario usuario);

    void delete(Integer id);

    // --- Métodos para filtros ---
    List<String> findAllNombres();
    List<String> findAllCorreos();
    List<RolDto> findAllRoles();
}
