package com.example.skeletune.data.network;

import com.example.skeletune.constants.ApiConstants;
import com.example.skeletune.data.model.Cancion;
import com.example.skeletune.data.model.ChartMania;
import com.example.skeletune.data.model.Comentario;
import com.example.skeletune.data.model.EstadisticaUsuario;
import com.example.skeletune.data.model.FalloMania;
import com.example.skeletune.data.model.Historia;
import com.example.skeletune.data.model.Instrumento;
import com.example.skeletune.data.model.Leccion;
import com.example.skeletune.data.model.LikePublicacion;
import com.example.skeletune.data.model.Media;
import com.example.skeletune.data.model.Mensaje;
import com.example.skeletune.data.model.NotaMania;
import com.example.skeletune.data.model.Notificacion;
import com.example.skeletune.data.model.Novedad;
import com.example.skeletune.data.model.PartidaMania;
import com.example.skeletune.data.model.Progreso;
import com.example.skeletune.data.model.Publicacion;
import com.example.skeletune.data.model.PublicacionMedia;
import com.example.skeletune.data.model.Seguidor;
import com.example.skeletune.data.model.Usuario;
import com.example.skeletune.data.model.UsuarioInstrumento;
import com.example.skeletune.data.model.ValidacionRol;
import com.example.skeletune.data.model.VideoEducativo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {



    // --- BLOQUE 1: AUTENTICACIÓN Y PERFIL ---
    // Endpoints: USUARIOS, VALIDACIONESROLES, ESTADISTICAS

    // --- BLOQUE VALIDACIONES DE ROL (SEGURIDAD) ---

    // 1. Obtener todas las reglas de validación de roles
    @GET(ApiConstants.VALIDACIONESROLES)
    Call<List<ValidacionRol>> getAllValidaciones();

    // 2. Obtener una validación específica por su ID
    @GET(ApiConstants.VALIDACIONESROLES + "/{id}")
    Call<ValidacionRol> getValidacionById(@Path("id") int id);

    // 3. Crear una nueva regla de validación o permiso
    @POST(ApiConstants.VALIDACIONESROLES)
    Call<ValidacionRol> createValidacion(@Body ValidacionRol validacionRol);

    // 4. Actualizar una regla de validación existente
    @PUT(ApiConstants.VALIDACIONESROLES + "/{id}")
    Call<ValidacionRol> updateValidacion(
            @Path("id") int id,
            @Body ValidacionRol validacionRol
    );

    // 5. Eliminar una regla de validación
    @DELETE(ApiConstants.VALIDACIONESROLES + "/{id}")
    Call<Void> deleteValidacion(@Path("id") int id);

    // --- BLOQUE USUARIOS (PERFILES Y ACCESO) ---

    // NUEVO: Método para iniciar sesión
    @POST(ApiConstants.USUARIOS + "/login")
    Call<Usuario> login(
            @Query("correo") String correo,
            @Query("contrasena") String contrasena
    );


    // 1. Obtener la lista completa de usuarios...
    @GET(ApiConstants.USUARIOS)
    Call<List<Usuario>> getUsuarios();

    // 2. Obtener un usuario específico por su ID
    @GET(ApiConstants.USUARIOS + "/{id}")
    Call<Usuario> getUsuarioById(@Path("id") int id);

    // 3. Crear un nuevo usuario (Registro de cuenta)
    @POST(ApiConstants.USUARIOS)
    Call<Usuario> createUsuario(@Body Usuario usuario);

    // 4. Actualizar información del perfil del usuario
    @PUT(ApiConstants.USUARIOS + "/{id}")
    Call<Usuario> updateUsuario(@Path("id") int id, @Body Usuario usuario);

    // 5. Eliminar cuenta de usuario
    @DELETE(ApiConstants.USUARIOS + "/{id}")
    Call<Void> deleteUsuario(@Path("id") int id);

    // --- BLOQUE ESTADÍSTICAS DE USUARIO ---

    // 1. Obtener las estadísticas de un usuario específico
    @GET(ApiConstants.ESTADISTICAS + "/usuario/{idUsuario}")
    Call<EstadisticaUsuario> getEstadisticasByUsuarioId(@Path("idUsuario") Integer idUsuario);

    // 2. Crear estadísticas iniciales para un usuario
    // Nota: El backend lo recibe por PathVariable, no por Body
    @POST(ApiConstants.ESTADISTICAS + "/usuario/{idUsuario}")
    Call<EstadisticaUsuario> createEstadisticas(@Path("idUsuario") Integer idUsuario);

    // 3. Actualizar estadísticas (Puntajes, racha, etc.)
    @PUT(ApiConstants.ESTADISTICAS + "/usuario/{idUsuario}")
    Call<EstadisticaUsuario> updateEstadisticas(
            @Path("idUsuario") Integer idUsuario,
            @Body EstadisticaUsuario estadistica
    );

    // 4. Actualización parcial (ej. solo subir la racha o puntos)
    @PATCH(ApiConstants.ESTADISTICAS + "/usuario/{idUsuario}")
    Call<EstadisticaUsuario> patchEstadisticas(
            @Path("idUsuario") Integer idUsuario,
            @Body Map<String, Object> updates
    );

    // --- BLOQUE 2: CONTENIDO MUSICAL Y EDUCATIVO ---
    // Endpoints: CANCIONES, LECCIONES, VIDEOSEDUCATIVOS, INSTRUMENTOS


    // --- BLOQUE VIDEOS EDUCATIVOS ---

    // 1. Obtener todos los videos (con filtros opcionales de profesor o título)
    @GET(ApiConstants.VIDEOSEDUCATIVOS)
    Call<List<VideoEducativo>> getAllVideoEducativos(
            @Query("idProfesor") Integer idProfesor,
            @Query("titulo") String titulo
    );

    // 2. Obtener un video específico por su ID
    @GET(ApiConstants.VIDEOSEDUCATIVOS + "/{idVideo}")
    Call<VideoEducativo> getVideoEducativoById(@Path("idVideo") Integer idVideo);

    // 3. Subir o registrar un nuevo video educativo
    @POST(ApiConstants.VIDEOSEDUCATIVOS)
    Call<VideoEducativo> createVideoEducativo(@Body VideoEducativo video);

    // 4. Actualizar video completo
    @PUT(ApiConstants.VIDEOSEDUCATIVOS + "/{idVideo}")
    Call<VideoEducativo> updateVideoEducativo(
            @Path("idVideo") Integer idVideo,
            @Body VideoEducativo video
    );

    // 5. Actualización parcial (ej. cambiar solo la descripción)
    @PATCH(ApiConstants.VIDEOSEDUCATIVOS + "/{idVideo}")
    Call<VideoEducativo> patchVideoEducativo(
            @Path("idVideo") Integer idVideo,
            @Body Map<String, Object> updates
    );

    // 6. Eliminar video
    @DELETE(ApiConstants.VIDEOSEDUCATIVOS + "/{idVideo}")
    Call<Void> deleteVideoEducativo(@Path("idVideo") Integer idVideo);

    // 7. Obtener solo la lista de títulos de videos
    @GET(ApiConstants.VIDEOSEDUCATIVOS + "/titulos")
    Call<List<String>> getVideoTitulos();

    // --- BLOQUE LECCIONES EDUCATIVAS ---

    // 1. Obtener todas las lecciones disponibles
    @GET(ApiConstants.LECCIONES)
    Call<List<Leccion>> getAllLecciones();

    // 2. Obtener una lección por su ID único
    @GET(ApiConstants.LECCIONES + "/{id}")
    Call<Leccion> getLeccionById(@Path("id") Integer id);

    // 3. Buscar lección por título exacto
    @GET(ApiConstants.LECCIONES + "/titulo/{titulo}")
    Call<Leccion> getLeccionByTitulo(@Path("titulo") String titulo);

    // 4. Filtrar lecciones por TIPO (Ej. TEORIA, PRACTICA)
    @GET(ApiConstants.LECCIONES + "/tipo/{tipo}")
    Call<List<Leccion>> getLeccionesByTipo(@Path("tipo") String tipo);

    // 5. Filtrar lecciones por NIVEL (Ej. BASICO, INTERMEDIO, AVANZADO)
    @GET(ApiConstants.LECCIONES + "/nivel/{nivel}")
    Call<List<Leccion>> getLeccionesByNivel(@Path("nivel") String nivel);

    // 6. Obtener lecciones que pertenecen a una canción específica
    @GET(ApiConstants.LECCIONES + "/cancion/{idCancion}")
    Call<List<Leccion>> getLeccionesByCancionId(@Path("idCancion") Integer idCancion);

    // 7. Crear una nueva lección
    @POST(ApiConstants.LECCIONES)
    Call<Leccion> createLeccion(@Body Leccion leccion);

    // 8. Actualizar lección completa
    @PUT(ApiConstants.LECCIONES + "/{id}")
    Call<Leccion> updateLeccion(@Path("id") Integer id, @Body Leccion leccion);

    // 9. Borrar lección
    @DELETE(ApiConstants.LECCIONES + "/{id}")
    Call<Void> deleteLeccion(@Path("id") Integer id);

    // --- BLOQUE INSTRUMENTOS ---

    // 1. Obtener la lista completa de instrumentos disponibles
    @GET(ApiConstants.INSTRUMENTOS)
    Call<List<Instrumento>> getAllInstrumentos();

    // 2. Obtener detalles de un instrumento por su ID
    @GET(ApiConstants.INSTRUMENTOS + "/{id}")
    Call<Instrumento> getInstrumentoById(@Path("id") int id);

    // 3. Crear un nuevo instrumento (Generalmente solo para administradores)
    @POST(ApiConstants.INSTRUMENTOS)
    Call<Instrumento> createInstrumento(@Body Instrumento instrumento);

    // 4. Actualizar un instrumento existente
    @PUT(ApiConstants.INSTRUMENTOS + "/{id}")
    Call<Instrumento> updateInstrumento(@Path("id") int id, @Body Instrumento instrumento);

    // 5. Eliminar un instrumento
    @DELETE(ApiConstants.INSTRUMENTOS + "/{id}")
    Call<Void> deleteInstrumento(@Path("id") int id);


    // --- BLOQUE CANCIONES (INTEGRADO) ---
    // --- NUEVOS MÉTODOS PARA MUSICSWIPE (TINDER MUSICAL) ---
    @GET(ApiConstants.CANCIONES + "/aleatoria")
    Call<Cancion> getCancionAleatoria();

        @POST(ApiConstants.CANCIONES + "/{id}/interaccion")
        Call<Void> registrarInteraccion(
                @Path("id") int idCancion,
                @Query("tipo") String tipo
        );

    // --- MÉTODOS CRUD ESTÁNDAR ---
    @GET(ApiConstants.CANCIONES)
    Call<List<Cancion>> getAllCanciones(
            @Query("titulo") String titulo,
            @Query("artista") String artista,
            @Query("dificultad") String dificultad,
            @Query("urlAudio") String urlAudio,
            @Query("urlPartitura") String urlPartitura,
            @Query("imagenUrl") String imagenUrl
    );

    @GET(ApiConstants.CANCIONES + "/{id}")
    Call<Cancion> getCancionById(@Path("id") Integer id);

    @POST(ApiConstants.CANCIONES)
    Call<Cancion> createCancion(@Body Cancion cancion);

    @PUT(ApiConstants.CANCIONES + "/{id}")
    Call<Cancion> updateCancion(@Path("id") Integer id, @Body Cancion cancion);

    @PATCH(ApiConstants.CANCIONES + "/{id}")
    Call<Cancion> patchCancion(@Path("id") Integer id, @Body Map<String, Object> updates);

    @DELETE(ApiConstants.CANCIONES + "/{id}")
    Call<Void> deleteCancion(@Path("id") Integer id);

    // --- ENDPOINTS PARA FILTROS Y LISTAS ---
    @GET(ApiConstants.CANCIONES + "/titulos")
    Call<List<String>> getTitulos();

    @GET(ApiConstants.CANCIONES + "/artistas")
    Call<List<String>> getArtistas();

    @GET(ApiConstants.CANCIONES + "/dificultades")
    Call<List<String>> getDificultades();

    @GET(ApiConstants.CANCIONES + "/url-audios")
    Call<List<String>> getUrlAudios();

    @GET(ApiConstants.CANCIONES + "/url-partituras")
    Call<List<String>> getUrlPartituras();

    @GET(ApiConstants.CANCIONES + "/imagen-urls")
    Call<List<String>> getImagenUrls();

    // --- BLOQUE 3: COMUNIDAD Y RED SOCIAL ---
    // Endpoints: PUBLICACIONES, COMENTARIOS, LIKES, SEGUIDORES, HISTORIAS

    // --- BLOQUE SEGUIDORES (SISTEMA SOCIAL) ---

    // 1. Obtener la lista de personas que siguen a un usuario
    @GET(ApiConstants.SEGUIDORES + "/{idUsuario}/seguidores")
    Call<List<Seguidor>> getSeguidores(@Path("idUsuario") Integer idUsuario);

    // 2. Obtener la lista de personas a las que sigue un usuario
    @GET(ApiConstants.SEGUIDORES + "/{idUsuario}/seguidos")
    Call<List<Seguidor>> getSeguidos(@Path("idUsuario") Integer idUsuario);

    // 3. Seguir a un usuario (idSeguidor es el usuario actual, idSeguido es el perfil visitado)
    @POST(ApiConstants.SEGUIDORES + "/{idSeguidor}/follow/{idSeguido}")
    Call<Seguidor> follow(
            @Path("idSeguidor") Integer idSeguidor,
            @Path("idSeguido") Integer idSeguido
    );

    // 4. Dejar de seguir a un usuario
    @DELETE(ApiConstants.SEGUIDORES + "/{idSeguidor}/unfollow/{idSeguido}")
    Call<Void> unfollow(
            @Path("idSeguidor") Integer idSeguidor,
            @Path("idSeguido") Integer idSeguido
    );

    // --- BLOQUE PUBLICACIONES (MURO SOCIAL) ---

    // 1. Obtener todas las publicaciones (Feed principal)
    @GET(ApiConstants.PUBLICACIONES)
    Call<List<Publicacion>> getAllPublicaciones();

    // 2. Obtener una publicación específica por su ID
    @GET(ApiConstants.PUBLICACIONES + "/{id}")
    Call<Publicacion> getPublicacionById(@Path("id") Integer id);

    // 3. Obtener todas las publicaciones de un usuario (Muro del perfil)
    @GET(ApiConstants.PUBLICACIONES + "/usuario/{idUsuario}")
    Call<List<Publicacion>> getPublicacionesByUsuarioId(@Path("idUsuario") Integer idUsuario);

    // 4. Crear una nueva publicación (Postear)
    @POST(ApiConstants.PUBLICACIONES)
    Call<Publicacion> createPublicacion(@Body Publicacion publicacion);

    // 5. Actualizar publicación completa
    @PUT(ApiConstants.PUBLICACIONES + "/{id}")
    Call<Publicacion> updatePublicacion(@Path("id") Integer id, @Body Publicacion publicacion);

    // 6. Actualización parcial (ej. editar solo el texto del post)
    @PATCH(ApiConstants.PUBLICACIONES + "/{id}")
    Call<Publicacion> patchPublicacion(@Path("id") Integer id, @Body Map<String, Object> updates);

    // 7. Eliminar publicación
    @DELETE(ApiConstants.PUBLICACIONES + "/{id}")
    Call<Void> deletePublicacion(@Path("id") Integer id);

    // --- BLOQUE LIKES ---

    // 1. Obtener el número total de likes de una publicación
    @GET(ApiConstants.LIKES + "/publicacion/{idPublicacion}/count")
    Call<Long> getLikeCount(@Path("idPublicacion") Integer idPublicacion);

    // 2. Obtener la lista de personas que dieron like a una publicación
    @GET(ApiConstants.LIKES + "/publicacion/{idPublicacion}")
    Call<List<LikePublicacion>> getLikesForPublicacion(@Path("idPublicacion") Integer idPublicacion);

    // 3. Dar Like (Se pasan ambos IDs en la URL)
    @POST(ApiConstants.LIKES + "/usuario/{idUsuario}/publicacion/{idPublicacion}")
    Call<LikePublicacion> likePublicacion(
            @Path("idUsuario") Integer idUsuario,
            @Path("idPublicacion") Integer idPublicacion
    );

    // 4. Quitar Like (Unlike)
    @DELETE(ApiConstants.LIKES + "/usuario/{idUsuario}/publicacion/{idPublicacion}")
    Call<Void> unlikePublicacion(
            @Path("idUsuario") Integer idUsuario,
            @Path("idPublicacion") Integer idPublicacion
    );

    // --- BLOQUE COMENTARIOS ---

    // 1. Obtener comentarios de una publicación específica
    // En el backend: /publicacion/{idPublicacion}
    @GET(ApiConstants.COMENTARIOS + "/publicacion/{idPublicacion}")
    Call<List<Comentario>> getComentariosByPublicacionId(@Path("idPublicacion") Integer idPublicacion);

    // 2. Obtener un comentario específico por su ID
    @GET(ApiConstants.COMENTARIOS + "/{id}")
    Call<Comentario> getComentarioById(@Path("id") Integer id);

    // 3. Crear un nuevo comentario
    // El objeto Comentario debe tener idUsuario, idPublicacion y el contenido
    @POST(ApiConstants.COMENTARIOS)
    Call<Comentario> createComentario(@Body Comentario comentario);

    // 4. Actualizar un comentario (Cuerpo completo)
    @PUT(ApiConstants.COMENTARIOS + "/{id}")
    Call<Comentario> updateComentario(@Path("id") Integer id, @Body Comentario comentario);

    // 5. Borrar un comentario
    @DELETE(ApiConstants.COMENTARIOS + "/{id}")
    Call<Void> deleteComentario(@Path("id") Integer id);


    // --- BLOQUE HISTORIAS ---

    // 1. Obtener todas las historias (Feed global)
    @GET(ApiConstants.HISTORIAS)
    Call<List<Historia>> getAllHistorias();

    // 2. Obtener una historia específica por su ID
    @GET(ApiConstants.HISTORIAS + "/{id}")
    Call<Historia> getHistoriaById(@Path("id") Integer id);

    // 3. Obtener las historias de un usuario concreto (Muro del usuario)
    @GET(ApiConstants.HISTORIAS + "/usuario/{idUsuario}")
    Call<List<Historia>> getHistoriasByUsuarioId(@Path("idUsuario") Integer idUsuario);

    // 4. Crear una nueva historia (Subir texto/enlace a media)
    @POST(ApiConstants.HISTORIAS)
    Call<Historia> createHistoria(@Body Historia historia);

    // 5. Actualizar historia completa
    @PUT(ApiConstants.HISTORIAS + "/{id}")
    Call<Historia> updateHistoria(@Path("id") Integer id, @Body Historia historia);

    // 6. Actualización parcial (ej. corregir solo el pie de foto o texto)
    @PATCH(ApiConstants.HISTORIAS + "/{id}")
    Call<Historia> patchHistoria(@Path("id") Integer id, @Body Map<String, Object> updates);

    // 7. Eliminar historia
    @DELETE(ApiConstants.HISTORIAS + "/{id}")
    Call<Void> deleteHistoria(@Path("id") Integer id);



    // --- BLOQUE 4: PROGRESO Y PERSONALIZACIÓN ---
    // Endpoints: PROGRESO, USUARIOINTRUMENTO

    // --- BLOQUE USUARIO-INSTRUMENTOS ---

    // 1. Obtener todos los registros de usuarios y sus instrumentos
    @GET(ApiConstants.USUARIOINTRUMENTO)
    Call<List<UsuarioInstrumento>> getAllUsuarioInstrumentos();

    // 2. Obtener la relación específica de un usuario con un instrumento
    @GET(ApiConstants.USUARIOINTRUMENTO + "/{idUsuario}/{idInstrumento}")
    Call<UsuarioInstrumento> getUsuarioInstrumentoById(
            @Path("idUsuario") int idUsuario,
            @Path("idInstrumento") int idInstrumento
    );

    // 3. Asignar un nuevo instrumento a un usuario
    @POST(ApiConstants.USUARIOINTRUMENTO)
    Call<UsuarioInstrumento> createUsuarioInstrumento(@Body UsuarioInstrumento usuarioInstrumento);

    // 4. Actualizar el nivel o datos de un instrumento para un usuario
    @PUT(ApiConstants.USUARIOINTRUMENTO + "/{idUsuario}/{idInstrumento}")
    Call<UsuarioInstrumento> updateUsuarioInstrumento(
            @Path("idUsuario") int idUsuario,
            @Path("idInstrumento") int idInstrumento,
            @Body UsuarioInstrumento usuarioInstrumento
    );

    // 5. Eliminar la relación (cuando un usuario ya no toca ese instrumento)
    @DELETE(ApiConstants.USUARIOINTRUMENTO + "/{idUsuario}/{idInstrumento}")
    Call<Void> deleteUsuarioInstrumento(
            @Path("idUsuario") int idUsuario,
            @Path("idInstrumento") int idInstrumento
    );

    // --- BLOQUE PROGRESO DE APRENDIZAJE ---

    // 1. Obtener progresos con filtros opcionales (Usuario, Lección o Fecha)
    @GET(ApiConstants.PROGRESO)
    Call<List<Progreso>> getAllProgresos(
            @Query("idUsuario") Integer idUsuario,
            @Query("idLeccion") Integer idLeccion,
            @Query("fecha") String fecha // Formato YYYY-MM-DD
    );

    // 2. Obtener un registro de progreso específico por ID
    @GET(ApiConstants.PROGRESO + "/{id}")
    Call<Progreso> getProgresoById(@Path("id") Integer id);

    // 3. Crear un nuevo registro de progreso (Al terminar una lección)
    @POST(ApiConstants.PROGRESO)
    Call<Progreso> createProgreso(@Body Progreso progreso);

    // 4. Actualizar registro de progreso completo
    @PUT(ApiConstants.PROGRESO + "/{id}")
    Call<Progreso> updateProgreso(@Path("id") Integer id, @Body Progreso progreso);

    // 5. Actualización parcial (ej. cambiar solo el estado de completado)
    @PATCH(ApiConstants.PROGRESO + "/{id}")
    Call<Progreso> patchProgreso(@Path("id") Integer id, @Body Map<String, Object> updates);

    // 6. Eliminar un registro de progreso
    @DELETE(ApiConstants.PROGRESO + "/{id}")
    Call<Void> deleteProgreso(@Path("id") Integer id);



    // --- BLOQUE 5: COMUNICACIÓN Y ALERTAS ---
    // Endpoints: MENSAJES, NOTIFICACIONES, NOVEDADES

    // --- BLOQUE NOVEDADES ---

    // 1. Obtener todas las novedades (Feed de noticias general)
    @GET(ApiConstants.NOVEDADES)
    Call<List<Novedad>> getAllNovedades();

    // 2. Obtener una novedad específica por su ID
    @GET(ApiConstants.NOVEDADES + "/{id}")
    Call<Novedad> getNovedadById(@Path("id") Integer id);

    // 3. Obtener novedades publicadas por un administrador específico
    @GET(ApiConstants.NOVEDADES + "/admin/{idAdmin}")
    Call<List<Novedad>> getNovedadesByAdminId(@Path("idAdmin") Integer idAdmin);

    // 4. Crear una nueva novedad (Anuncio)
    @POST(ApiConstants.NOVEDADES)
    Call<Novedad> createNovedad(@Body Novedad novedad);

    // 5. Actualizar novedad completa
    @PUT(ApiConstants.NOVEDADES + "/{id}")
    Call<Novedad> updateNovedad(@Path("id") Integer id, @Body Novedad novedad);

    // 6. Actualización parcial (ej. corregir solo el título o el cuerpo)
    @PATCH(ApiConstants.NOVEDADES + "/{id}")
    Call<Novedad> patchNovedad(@Path("id") Integer id, @Body Map<String, Object> updates);

    // 7. Eliminar novedad
    @DELETE(ApiConstants.NOVEDADES + "/{id}")
    Call<Void> deleteNovedad(@Path("id") Integer id);

    // --- BLOQUE NOTIFICACIONES ---

    // 1. Obtener todas las notificaciones (uso general)
    @GET(ApiConstants.NOTIFICACIONES)
    Call<List<Notificacion>> getAllNotificaciones();

    // 2. Obtener notificaciones específicas de un usuario
    @GET(ApiConstants.NOTIFICACIONES + "/usuario/{idUsuario}")
    Call<List<Notificacion>> getNotificacionesByUsuario(@Path("idUsuario") Integer idUsuario);

    // 3. Marcar una notificación como leída
    // Nota: El backend no pide Body, solo el ID en la URL
    @PUT(ApiConstants.NOTIFICACIONES + "/{idNotificacion}/leido")
    Call<Notificacion> markAsRead(@Path("idNotificacion") Integer idNotificacion);

    // 4. Crear una notificación (ej. desde el sistema hacia el usuario)
    @POST(ApiConstants.NOTIFICACIONES)
    Call<Notificacion> createNotificacion(@Body Notificacion notificacion);


    // --- BLOQUE MENSAJERÍA (CHAT) ---

    // 1. Obtener todos los mensajes (Uso administrativo o auditoría)
    @GET(ApiConstants.MENSAJES)
    Call<List<Mensaje>> getAllMensajes();

    // 2. Obtener la conversación entre dos usuarios específicos
    @GET(ApiConstants.MENSAJES + "/conversacion/{idUsuario1}/{idUsuario2}")
    Call<List<Mensaje>> getConversation(
            @Path("idUsuario1") Integer idUsuario1,
            @Path("idUsuario2") Integer idUsuario2
    );

    // 3. Enviar un nuevo mensaje
    @POST(ApiConstants.MENSAJES)
    Call<Mensaje> sendMessage(@Body Mensaje mensaje);

    // 4. Editar un mensaje (si el backend lo permite)
    @PUT(ApiConstants.MENSAJES + "/{id}")
    Call<Mensaje> updateMessage(@Path("id") Integer id, @Body Mensaje mensaje);

    // 5. Eliminar un mensaje
    @DELETE(ApiConstants.MENSAJES + "/{id}")
    Call<Void> deleteMessage(@Path("id") Integer id);

    // --- BLOQUE 6: MULTIMEDIA PESADA ---
    // Endpoint: PUBLICACIONESMEDIACONTROLLER
    // Nota: Aquí se usa @Multipart si subes archivos reales (JPG/MP4)

    // --- BLOQUE ASOCIACIÓN PUBLICACIÓN-MEDIA ---

    // 1. Obtener todos los archivos multimedia asociados a una publicación específica
    @GET(ApiConstants.PUBLICACIONESMEDIA + "/{idPublicacion}/media")
    Call<List<Media>> getMediaByPublicacion(@Path("idPublicacion") Integer idPublicacion);

    // 2. Vincular un archivo de media existente a una publicación
    @POST(ApiConstants.PUBLICACIONESMEDIA + "/{idPublicacion}/media/{idMedia}")
    Call<PublicacionMedia> addMediaToPublicacion(
            @Path("idPublicacion") Integer idPublicacion,
            @Path("idMedia") Integer idMedia
    );

    // 3. Desvincular un archivo de media de una publicación
    @DELETE(ApiConstants.PUBLICACIONESMEDIA + "/{idPublicacion}/media/{idMedia}")
    Call<Void> removeMediaFromPublicacion(
            @Path("idPublicacion") Integer idPublicacion,
            @Path("idMedia") Integer idMedia
    );

    // --- BLOQUE MEDIA ---

    // 1. Obtener toda la media registrada
    @GET(ApiConstants.MEDIA)
    Call<List<Media>> getAllMedia();

    // 2. Obtener un archivo de media específico por su ID
    @GET(ApiConstants.MEDIA + "/{id}")
    Call<Media> getMediaById(@Path("id") Integer id);

    // 3. Obtener toda la media subida por un usuario específico
    @GET(ApiConstants.MEDIA + "/usuario/{idUsuario}")
    Call<List<Media>> getMediaByUsuarioId(@Path("idUsuario") Integer idUsuario);

    // 4. Registrar nueva media (URL o metadatos)
    @POST(ApiConstants.MEDIA)
    Call<Media> createMedia(@Body Media media);

    // 5. Actualizar información de media
    @PUT(ApiConstants.MEDIA + "/{id}")
    Call<Media> updateMedia(@Path("id") Integer id, @Body Media media);

    // 6. Actualización parcial (ej. cambiar solo la descripción o URL)
    @PATCH(ApiConstants.MEDIA + "/{id}")
    Call<Media> patchMedia(@Path("id") Integer id, @Body Map<String, Object> updates);

    // 7. Eliminar registro de media
    @DELETE(ApiConstants.MEDIA + "/{id}")
    Call<Void> deleteMedia(@Path("id") Integer id);

    // ULTIMO BLOQUE: SECCION DE JUEGOS

    // --- BLOQUE JUEGO (CHART MANIA) ---

    // 1. Obtener todos los mapas de notas con filtros (Canción, Dificultad, Velocidad)
    @GET(ApiConstants.CHARTMANIA)
    Call<List<ChartMania>> getAllChartManias(
            @Query("idCancion") Integer idCancion,
            @Query("dificultad") String dificultad,
            @Query("speedMultiplier") Float speedMultiplier
    );

    // 2. Obtener un mapa de notas específico por ID
    @GET(ApiConstants.CHARTMANIA + "/{idChartMania}")
    Call<ChartMania> getChartManiaById(@Path("idChartMania") Integer idChartMania);

    // 3. Crear un nuevo Chart (Subir un mapa de notas)
    @POST(ApiConstants.CHARTMANIA)
    Call<ChartMania> createChartMania(@Body ChartMania chartMania);

    // 4. Actualizar un Chart completo
    @PUT(ApiConstants.CHARTMANIA + "/{idChartMania}")
    Call<ChartMania> updateChartMania(
            @Path("idChartMania") Integer idChartMania,
            @Body ChartMania chartMania
    );

    // 5. Borrar un Chart
    @DELETE(ApiConstants.CHARTMANIA + "/{idChartMania}")
    Call<Void> deleteChartMania(@Path("idChartMania") Integer idChartMania);

    // 6. Obtener lista de dificultades disponibles en el juego
    @GET(ApiConstants.CHARTMANIA + "/dificultades")
    Call<List<String>> getChartDificultades();

    // 7. Obtener lista de multiplicadores de velocidad disponibles
    @GET(ApiConstants.CHARTMANIA + "/speed-multipliers")
    Call<List<Float>> getSpeedMultipliers();

    // --- BLOQUE JUEGO (FALLOS MANIA) ---

    // 1. Obtener lista de fallos con filtros (ID Partida, tiempo en milisegundos o tipo de error)
    @GET(ApiConstants.FALLOMANIA)
    Call<List<FalloMania>> getAllFalloManias(
            @Query("idPartidaMania") Integer idPartidaMania,
            @Query("tiempoMs") Integer tiempoMs,
            @Query("tipo") String tipo
    );

    // 2. Obtener un fallo específico por ID
    @GET(ApiConstants.FALLOMANIA + "/{idFalloMania}")
    Call<FalloMania> getFalloManiaById(@Path("idFalloMania") Integer idFalloMania);

    // 3. Registrar un nuevo fallo (Se llama cada vez que el usuario pierde una nota)
    @POST(ApiConstants.FALLOMANIA)
    Call<FalloMania> createFalloMania(@Body FalloMania falloMania);

    // 4. Actualizar registro de fallo
    @PUT(ApiConstants.FALLOMANIA + "/{idFalloMania}")
    Call<FalloMania> updateFalloMania(@Path("idFalloMania") Integer idFalloMania, @Body FalloMania falloMania);

    // 5. Eliminar registro de fallo
    @DELETE(ApiConstants.FALLOMANIA + "/{idFalloMania}")
    Call<Void> deleteFalloMania(@Path("idFalloMania") Integer idFalloMania);

    // 6. Obtener los tipos de fallos (Ej. MISS, BAD, LATE...)
    @GET(ApiConstants.FALLOMANIA + "/tipos")
    Call<List<String>> getFalloTipos();

    // --- BLOQUE JUEGO (NOTAS MANIA) ---

    // 1. Obtener todas las notas de un mapa (Chart) específico
    @GET(ApiConstants.NOTAMANIA)
    Call<List<NotaMania>> getAllNotaManias(
            @Query("idChartMania") Integer idChartMania,
            @Query("tiempoMs") Integer tiempoMs,
            @Query("carril") Byte carril,
            @Query("tipo") String tipo
    );

    // 2. Obtener una nota específica por ID
    @GET(ApiConstants.NOTAMANIA + "/{idNotaMania}")
    Call<NotaMania> getNotaManiaById(@Path("idNotaMania") Integer idNotaMania);

    // 3. Crear una nueva nota (Para el editor de niveles)
    @POST(ApiConstants.NOTAMANIA)
    Call<NotaMania> createNotaMania(@Body NotaMania notaMania);

    // 4. Actualizar una nota existente
    @PUT(ApiConstants.NOTAMANIA + "/{idNotaMania}")
    Call<NotaMania> updateNotaMania(@Path("idNotaMania") Integer idNotaMania, @Body NotaMania notaMania);

    // 5. Eliminar una nota
    @DELETE(ApiConstants.NOTAMANIA + "/{idNotaMania}")
    Call<Void> deleteNotaMania(@Path("idNotaMania") Integer idNotaMania);

    // 6. Obtener los tipos de notas (SIMPLE, LONG, etc.)
    @GET(ApiConstants.NOTAMANIA + "/tipos")
    Call<List<String>> getNotaTipos();

    // --- BLOQUE JUEGO (PARTIDAS / RESULTADOS) ---

    // 1. Obtener todas las partidas (útil para Rankings o Historial)
    @GET(ApiConstants.PARTIDAMANIA)
    Call<List<PartidaMania>> getAllPartidaManias(
            @Query("idUsuario") Integer idUsuario,
            @Query("idChartMania") Integer idChartMania,
            @Query("puntajeMin") Integer puntajeMin,
            @Query("accuracyMin") Double accuracyMin
    );

    // 2. Obtener los detalles de una partida específica
    @GET(ApiConstants.PARTIDAMANIA + "/{idPartidaMania}")
    Call<PartidaMania> getPartidaManiaById(@Path("idPartidaMania") Integer idPartidaMania);

    // 3. Guardar el resultado de una partida (Al terminar de jugar)
    @POST(ApiConstants.PARTIDAMANIA)
    Call<PartidaMania> createPartidaMania(@Body PartidaMania partidaMania);

    // 4. Actualizar una partida (raramente usado, pero disponible)
    @PUT(ApiConstants.PARTIDAMANIA + "/{idPartidaMania}")
    Call<PartidaMania> updatePartidaMania(
            @Path("idPartidaMania") Integer idPartidaMania,
            @Body PartidaMania partidaMania
    );

    // 5. Eliminar un registro de partida
    @DELETE(ApiConstants.PARTIDAMANIA + "/{idPartidaMania}")
    Call<Void> deletePartidaMania(@Path("idPartidaMania") Integer idPartidaMania);

    // 6. Obtener lista de todos los puntajes registrados
    @GET(ApiConstants.PARTIDAMANIA + "/puntajes")
    Call<List<Integer>> getTodosLosPuntajes();

    // 7. Obtener lista de todas las precisiones (accuracy) registradas
    @GET(ApiConstants.PARTIDAMANIA + "/accuracies")
    Call<List<Double>> getTodasLasAccuracies();



}


