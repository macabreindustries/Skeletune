package com.example.osu;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;

public class PartidaMania {

    @SerializedName("idPartidaMania")
    private Integer idPartidaMania;

    @SerializedName("idUsuario")
    private Integer idUsuario;

    @SerializedName("idChartMania")
    private Integer idChartMania;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("puntaje")
    private Integer puntaje;

    @SerializedName("accuracy")
    private BigDecimal accuracy;

    @SerializedName("comboMax")
    private Integer comboMax;

    @SerializedName("perfects")
    private Integer perfects;

    @SerializedName("greats")
    private Integer greats;

    @SerializedName("goods")
    private Integer goods;

    @SerializedName("misses")
    private Integer misses;

    @SerializedName("detalles")
    private String detalles;

    // Setters
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public void setIdChartMania(Integer idChartMania) { this.idChartMania = idChartMania; }
    public void setPuntaje(Integer puntaje) { this.puntaje = puntaje; }
    public void setAccuracy(BigDecimal accuracy) { this.accuracy = accuracy; }
    public void setComboMax(Integer comboMax) { this.comboMax = comboMax; }
    public void setPerfects(Integer perfects) { this.perfects = perfects; }
    public void setGreats(Integer greats) { this.greats = greats; }
    public void setGoods(Integer goods) { this.goods = goods; }
    public void setMisses(Integer misses) { this.misses = misses; }
}
