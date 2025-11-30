package org.example.demo.service.impl;

import org.example.demo.dto.RolDto;
import org.example.demo.dto.UsuarioDto;
import org.example.demo.model.Rol;
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
    public List<UsuarioDto> getAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto getById(Integer id) {
        return usuarioRepository.findById(id)
                .map(UsuarioDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Override
    public UsuarioDto save(UsuarioDto usuarioDto) {
        Rol rol = rolRepository.findById(usuarioDto.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + usuarioDto.getIdRol()));

        Usuario usuario = usuarioDto.toEntity(rol);

        // --- ¡Punto Crítico de Seguridad! ---
        // Aquí es donde deberías encriptar la contraseña antes de guardarla.
        // Ejemplo: usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return UsuarioDto.fromEntity(savedUsuario);
    }

    @Override
    public UsuarioDto update(Integer id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setCorreo(usuarioDto.getCorreo());
        // No actualizamos la contraseña aquí a menos que se maneje explícitamente
        // (ej. un campo "nuevaContrasena" en el DTO).

        if (usuarioDto.getIdRol() != null && !usuarioDto.getIdRol().equals(usuario.getRol().getId())) {
            Rol nuevoRol = rolRepository.findById(usuarioDto.getIdRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + usuarioDto.getIdRol()));
            usuario.setRol(nuevoRol);
        }

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return UsuarioDto.fromEntity(updatedUsuario);
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
