package com.example.osu;

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

    // CORRECCIÓN FINAL: Se ha ajustado la mayúscula en "notaMania"
    @GET("juego/notaMania")
    Call<List<NotaMania>> getNotesForChart(@Query("idChartMania") int chartId);

    // CORRECCIÓN PROACTIVA: Se ha ajustado la ruta y la mayúscula para guardar la partida
    @POST("juego/partidaMania")
    Call<Void> savePartida(@Body PartidaMania partida);
}
