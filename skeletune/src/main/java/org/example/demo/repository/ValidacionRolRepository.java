package org.example.demo.repository;

import org.example.demo.model.ValidacionRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidacionRolRepository extends JpaRepository<ValidacionRol, Integer> {
}
