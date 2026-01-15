package com.example.skeletune.data.model;

public class Usuario {
    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String fechaRegistro;
    private Integer idRol;
    private String nombreRol;

    public Usuario() {}

    // Constructor para Registro rápido
    public Usuario(String nombre, String correo, String contrasena, Integer idRol) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.idRol = idRol;
    }

    // Getters y Setters...
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
}