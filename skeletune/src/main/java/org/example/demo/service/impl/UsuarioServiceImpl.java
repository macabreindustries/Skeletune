package org.example.demo.service.impl;

import org.example.demo.dto.RolDto;
import org.example.demo.model.Usuario;
import org.example.demo.repository.RolRepository;
import org.example.demo.repository.UsuarioRepository;
import org.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getById(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Integer id, Usuario newData) {
        Usuario usuario = getById(id); // Reutilizamos el método para obtener y lanzar excepción

        usuario.setNombre(newData.getNombre());
        usuario.setCorreo(newData.getCorreo());
        usuario.setContrasena(newData.getContrasena());
        usuario.setFechaRegistro(newData.getFechaRegistro());
        usuario.setRol(newData.getRol());

        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar el usuario con id: " + id + " porque no existe.");
        }
        usuarioRepository.deleteById(id);
    }

    // --- Implementación de métodos para filtros ---

    @Override
    public List<String> findAllNombres() {
        return usuarioRepository.findAllNombres();
    }

    @Override
    public List<String> findAllCorreos() {
        return usuarioRepository.findAllCorreos();
    }

    @Override
    public List<RolDto> findAllRoles() {
        // Es mucho más eficiente y correcto obtener los roles desde su propio repositorio
        return rolRepository.findAll().stream()
                .map(rol -> new RolDto(rol.getId(), rol.getNombre(), rol.getDescripcion()))
                .collect(Collectors.toList());
    }
}
