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

import java.time.LocalDateTime;
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

    // --- NUEVO MÉTODO: LOGIN ---
    @Override
    public UsuarioDto login(String correo, String contrasena) {
        // Buscamos al usuario usando el método que agregamos al Repository
        // Si no existe el correo, el .orElse(null) hará que 'usuario' sea nulo
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        // Validamos: que el usuario exista y que la contraseña sea igual
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            // Convertimos la entidad a DTO usando tu método manual para mantener consistencia
            return UsuarioDto.fromEntity(usuario);
        }

        // Si llegamos aquí, es que los datos son incorrectos
        return null;
    }

    // --- MÉTODOS EXISTENTES (Mantenidos sin cambios) ---

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
        // 1. Log para ver qué está llegando realmente desde Android
        System.out.println("RECIBIDO DESDE ANDROID -> Nombre: " + usuarioDto.getNombre() +
                ", Correo: " + usuarioDto.getCorreo() +
                ", Rol: " + usuarioDto.getIdRol());

        // 2. Garantizar un ID de Rol válido (evita el Error 500 de findById(null))
        Integer idRolFinal = (usuarioDto.getIdRol() != null) ? usuarioDto.getIdRol() : 1;

        Rol rol = rolRepository.findById(idRolFinal)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + idRolFinal));

        Usuario usuario = usuarioDto.toEntity(rol);

        // 3. Plan B si el nombre llega nulo (por eso ves "Usuario Nuevo")
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            usuario.setNombre("Usuario Sin Nombre");
        }

        usuario.setFechaRegistro(LocalDateTime.now());
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return UsuarioDto.fromEntity(savedUsuario);
    }

    @Override
    public UsuarioDto update(Integer id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setCorreo(usuarioDto.getCorreo());

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
        return rolRepository.findAll().stream()
                .map(rol -> new RolDto(rol.getId(), rol.getNombre(), rol.getDescripcion()))
                .collect(Collectors.toList());
    }
}