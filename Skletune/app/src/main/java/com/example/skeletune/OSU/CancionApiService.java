package com.example.skeletune.OSU;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CancionApiService {
    @GET("cancion")
    Call<List<Cancion>> getCanciones();

    @GET("juego/chartMania") 
    Call<List<ChartMania>> getChartsForSong(@Query("idCancion") int cancionId);

    @GET("juego/notaMania")
    Call<List<NotaMania>> getNotesForChart(@Query("idChartMania") int chartId);

    @POST("juego/partidaMania")
    Call<Void> savePartida(@Body PartidaMania partida);

    // Endpoint para obtener todas las partidas de un usuario
    @GET("juego/partidaMania")
    Call<List<PartidaMania>> getPartidasPorUsuario(@Query("idUsuario") int idUsuario);
}
