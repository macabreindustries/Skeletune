package org.example.demo.repository;

import org.example.demo.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}
