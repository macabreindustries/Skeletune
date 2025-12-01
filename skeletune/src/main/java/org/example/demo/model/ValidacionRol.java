package org.example.demo.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ValidacionRol")
public class ValidacionRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_validacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario_validado", nullable = false)
    private Usuario usuarioValidado;

    @ManyToOne
    @JoinColumn(name = "id_admin_validador", nullable = false)
    private Usuario adminValidador;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_validacion", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date fechaValidacion;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('pendiente','aprobado','rechazado') DEFAULT 'pendiente'")
    private EstadoValidacion estado;

    public enum EstadoValidacion {
        pendiente,
        aprobado,
        rechazado
    }

    // Getters and Setters
    public int getId_validacion() {
        return id_validacion;
    }

    public void setId_validacion(int id_validacion) {
        this.id_validacion = id_validacion;
    }

    public Usuario getUsuarioValidado() {
        return usuarioValidado;
    }

    public void setUsuarioValidado(Usuario usuarioValidado) {
        this.usuarioValidado = usuarioValidado;
    }

    public Usuario getAdminValidador() {
        return adminValidador;
    }

    public void setAdminValidador(Usuario adminValidador) {
        this.adminValidador = adminValidador;
    }

    public Date getFechaValidacion() {
        return fechaValidacion;
    }

    public void setFechaValidacion(Date fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    public EstadoValidacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoValidacion estado) {
        this.estado = estado;
    }
}
