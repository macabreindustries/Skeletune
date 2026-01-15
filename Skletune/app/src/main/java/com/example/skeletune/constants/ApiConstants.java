package com.example.skeletune.constants;

public class ApiConstants {
    // Base URL - Cambia según tu configuración
    // Para emulador Android usa: http://10.0.2.2:8080/
    // Para dispositivo físico usa tu IP local: http://192.168.X.X:8080/
    public static final String BASE_URL = "http://10.0.2.2:8080/";

    // ----------------ENDPOINTS------------------------------------
    public static final String USUARIOS = "skeletune/api/usuarios";
    public static final String CANCIONES = "skeletune/api/cancion";
    public static final String NOVEDADES = "skeletune/api/novedades";
    public static final String NOTIFICACIONES = "skeletune/api/notificaciones";
    public static final String MENSAJES = "skeletune/api/mensajes";
    public static final String COMENTARIOS = "skeletune/api/comentarios";
    public static final String ESTADISTICAS = "skeletune/api/estadisticas-usuario";
    public static final String HISTORIAS = "skeletune/api/historia";
    public static final String INSTRUMENTOS = "skeletune/api/instrumentos";
    public static final String LECCIONES = "skeletune/api/lecciones";
    public static final String LIKES = "skeletune/api/likes";
    public static final String PROGRESO = "skeletune/api/progreso";
    public static final String PUBLICACIONES = "skeletune/api/publicacion";
    public static final String PUBLICACIONESMEDIA = "skeletune/api/publicacion-media";
    public static final String MEDIA = "skeletune/api/media";
    public static final String SEGUIDORES = "skeletune/api/seguidores";
    public static final String VIDEOSEDUCATIVOS = "skeletune/api/videoEducativo";
    public static final String USUARIOINTRUMENTO = "skeletune/api/usuario-instrumentos";
    public static final String VALIDACIONESROLES = "skeletune/api/validaciones-rol";


    // ----------------- JUEGO -----------------------

    public static final String CHARTMANIA = "skeletune/api/juego/chartMania";
    public static final String FALLOMANIA = "skeletune/api/juego/falloMania";

    public static final String NOTAMANIA = "skeletune/api/juego/notaMania";
    public static final String PARTIDAMANIA = "skeletune/api/juego/partidaMania";
    // Timeout en segundos
    public static final int CONNECT_TIMEOUT = 30;
    public static final int READ_TIMEOUT = 30;
    public static final int WRITE_TIMEOUT = 30;
}