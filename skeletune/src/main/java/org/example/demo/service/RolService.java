package org.example.demo.service;

import org.example.demo.model.Rol;

import java.util.List;

public interface RolService {

    List<Rol> getAll();

    Rol getById(Integer id);

    Rol save(Rol rol);

    Rol update(Integer id, Rol rol);

    void delete(Integer id);
}
