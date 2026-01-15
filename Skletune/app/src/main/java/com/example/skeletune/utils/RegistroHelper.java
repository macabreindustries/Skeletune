package com.example.skeletune.utils;

public class RegistroHelper {
    private static RegistroHelper instance;

    private String correo;
    private String nombre;
    private String contrasena;

    private RegistroHelper() {}

    public static RegistroHelper getInstance() {
        if (instance == null) {
            instance = new RegistroHelper();
        }
        return instance;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void limpiar() {
        correo = null;
        nombre = null;
        contrasena = null;
    }
}