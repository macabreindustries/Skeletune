package com.example.skeletune.ui.common.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.skeletune.data.model.ChartMania;
import com.example.skeletune.data.model.PartidaMania;
import com.example.skeletune.data.repository.GameRepository;
import com.example.skeletune.data.network.Resource;

public class GameViewModel extends ViewModel {
    private final GameRepository gameRepository;

    private final MutableLiveData<Resource<ChartMania>> _chartData = new MutableLiveData<>();
    public LiveData<Resource<ChartMania>> getChartData() { return _chartData; }

    private final MutableLiveData<Resource<PartidaMania>> _gameResult = new MutableLiveData<>();
    public LiveData<Resource<PartidaMania>> getGameResult() { return _gameResult; }

    public GameViewModel() {
        this.gameRepository = new GameRepository();
    }

    // Acción: Descargar el mapa de notas (Chart) para empezar a jugar
    public void prepararNivel(Integer idChart) {
        gameRepository.getNivel(idChart).observeForever(resource -> {
            _chartData.setValue(resource);
        });
    }

    // Acción: Enviar el puntaje y precisión al servidor al terminar la canción
    public void finalizarPartida(PartidaMania resultado) {
        gameRepository.guardarPartida(resultado).observeForever(resource -> {
            _gameResult.setValue(resource);
        });
    }
}