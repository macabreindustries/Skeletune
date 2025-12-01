package org.example.demo.repository;

import org.example.demo.model.UsuarioInstrumento;
import org.example.demo.model.UsuarioInstrumentoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioInstrumentoRepository extends JpaRepository<UsuarioInstrumento, UsuarioInstrumentoId> {
}
