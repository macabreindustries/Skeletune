package com.example.skeletune.data.network;

import com.example.skeletune.data.model.Mensaje;
import com.example.skeletune.data.model.Notificacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessagingApiService {

    /**
     * Obtiene todos los mensajes (para construir la lista de conversaciones).
     */
    @GET("mensajes")
    Call<List<Mensaje>> getAllMessages();

    /**
     * Obtiene todos los mensajes de una conversación entre dos usuarios.
     */
    @GET("mensajes/conversacion/{idUsuario1}/{idUsuario2}")
    Call<List<Mensaje>> getConversation(
            @Path("idUsuario1") int idUsuario1,
            @Path("idUsuario2") int idUsuario2
    );

    /**
     * Envía un nuevo mensaje.
     */
    @POST("mensajes")
    Call<Mensaje> sendMessage(@Body Mensaje mensaje);

    /**
     * Obtiene todas las notificaciones para un usuario específico.
     */
    @GET("notificaciones/usuario/{idUsuario}")
    Call<List<Notificacion>> getNotificacionesPorUsuario(@Path("idUsuario") int idUsuario);

}
