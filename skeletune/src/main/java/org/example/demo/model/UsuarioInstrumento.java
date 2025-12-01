package org.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "UsuarioInstrumento")
public class UsuarioInstrumento {

    @EmbeddedId
    private UsuarioInstrumentoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idInstrumento")
    @JoinColumn(name = "id_instrumento")
    private Instrumento instrumento;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('principiante','intermedio','avanzado') DEFAULT 'principiante'")
    private Nivel nivel;

    public enum Nivel {
        principiante,
        intermedio,
        avanzado
    }

    // Getters and Setters
    public UsuarioInstrumentoId getId() {
        return id;
    }

    public void setId(UsuarioInstrumentoId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }
}
