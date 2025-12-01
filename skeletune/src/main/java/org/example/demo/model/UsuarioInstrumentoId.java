package org.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsuarioInstrumentoId implements Serializable {

    @Column(name = "id_usuario")
    private int idUsuario;

    @Column(name = "id_instrumento")
    private int idInstrumento;

    // Constructors, Getters, Setters, equals, and hashCode

    public UsuarioInstrumentoId() {}

    public UsuarioInstrumentoId(int idUsuario, int idInstrumento) {
        this.idUsuario = idUsuario;
        this.idInstrumento = idInstrumento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdInstrumento() {
        return idInstrumento;
    }

    public void setIdInstrumento(int idInstrumento) {
        this.idInstrumento = idInstrumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioInstrumentoId that = (UsuarioInstrumentoId) o;
        return idUsuario == that.idUsuario && idInstrumento == that.idInstrumento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idInstrumento);
    }
}
