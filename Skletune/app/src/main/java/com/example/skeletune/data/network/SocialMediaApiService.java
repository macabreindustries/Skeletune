package com.example.skeletune.data.network;

import com.example.skeletune.data.model.PublicacionFeedDto;
import com.example.skeletune.data.model.ComentarioResponseDto; // Tu nuevo DTO
import com.example.skeletune.data.model.UserProfileDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SocialMediaApiService {

    @GET("/skeletune/api/publicacion/feed")
    Call<List<PublicacionFeedDto>> getSocialFeed();

    // Método para dar/quitar like
    @POST("/skeletune/api/publicacion/{id}/like")
    Call<Void> toggleLike(@Path("id") Integer idPublicacion, @Query("idUsuario") Integer idUsuario);

    // Método para obtener comentarios de un post
    @GET("/skeletune/api/publicacion/{id}/comentarios")
    Call<List<ComentarioResponseDto>> getComentarios(@Path("id") Integer idPublicacion);

    @POST("/skeletune/api/publicacion/{id}/comentarios")
    Call<ComentarioResponseDto> publicarComentario(
            @Path("id") Integer idPublicacion,
            @Query("idUsuario") Integer idUsuario,
            @Query("texto") String texto
    );

    // Añade esto a tu interfaz SocialMediaApiService
    @GET("/skeletune/api/publicacion/usuario/{id}/perfil")
    Call<UserProfileDto> getUserProfile(@Path("id") Integer idUsuario);
}