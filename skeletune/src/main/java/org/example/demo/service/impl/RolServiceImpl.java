package org.example.demo.service.impl;

import lombok.AllArgsConstructor;
import org.example.demo.model.Rol;
import org.example.demo.repository.RolRepository;
import org.example.demo.service.RolService;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service

public class RolServiceImpl implements RolService {

    private final RolRepository repo;

    @Override
    public List<Rol> getAll() {
        return repo.findAll();
    }

    @Override
    public Rol getById(Integer id) {
        return repo.findById(id).orElseThrow(() ->
                new RuntimeException("Rol no encontrado"));
    }

    @Override
    public Rol save(Rol rol) {
        return repo.save(rol);
    }

    @Override
    public Rol update(Integer id, Rol newData) {

        Rol rol = repo.findById(id).orElseThrow(() ->
                new RuntimeException("Rol no encontrado"));

        rol.setNombre(newData.getNombre());
        rol.setDescripcion(newData.getDescripcion());

        return repo.save(rol);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
