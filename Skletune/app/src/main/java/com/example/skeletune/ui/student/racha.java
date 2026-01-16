package com.example.skeletune.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.gridlayout.widget.GridLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skeletune.data.model.RachaDTO;
import com.example.skletune.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalDate;

public class racha extends BottomSheetDialogFragment {

    private GridLayout gridCalendario;
    private RachaDTO rachaData;
    private TextView tvRachaActual, tvMejorRacha, tvTotalDias, tvBanner;

    // Método para recibir los datos desde el Home
    public void setRachaData(RachaDTO data) {
        this.rachaData = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_racha, container, false);

        // Vincular vistas
        gridCalendario = view.findViewById(R.id.grid_layout_calendario);
        tvRachaActual = view.findViewById(R.id.text_racha_actual);
        tvMejorRacha = view.findViewById(R.id.text_racha_mejor);
        tvTotalDias = view.findViewById(R.id.text_total_dias);
        tvBanner = view.findViewById(R.id.text_banner_notificacion);

        view.findViewById(R.id.button_close_modal).setOnClickListener(v -> dismiss());

        // Cargar los datos recibidos
        mostrarDatosReales();

        return view;
    }

    private void mostrarDatosReales() {
        if (rachaData != null) {
            tvRachaActual.setText(String.valueOf(rachaData.getRachaActual()));
            tvMejorRacha.setText(String.valueOf(rachaData.getMejorRacha()));
            tvTotalDias.setText(rachaData.getTotalDiasPracticados() + " días");

            // Lógica del banner dinámico
            int diferencia = rachaData.getMejorRacha() - rachaData.getRachaActual();
            if (diferencia > 0) {
                tvBanner.setText("🎉 ¡Increíble! Estás a solo " + diferencia + " días de tu mejor racha.");
            } else {
                tvBanner.setText("🔥 ¡Estás en tu mejor momento! Sigue así.");
            }

            generarCalendarioReal();
        }
    }

    private void generarCalendarioReal() {
        if (getContext() == null || rachaData == null) return;

        // Limpiar cuadritos previos si existen (excepto encabezados)
        // Como los encabezados son los primeros 7 hijos, empezamos a borrar desde el 7
        if (gridCalendario.getChildCount() > 7) {
            gridCalendario.removeViews(7, gridCalendario.getChildCount() - 7);
        }

        LocalDate hoy = LocalDate.now();

        // Generamos los últimos 28 días (4 semanas completas)
        for (int i = 27; i >= 0; i--) {
            LocalDate diaEvaluado = hoy.minusDays(i);
            String fechaStr = diaEvaluado.toString(); // Formato "yyyy-MM-dd"

            View diaView = new View(getContext());

            // Configurar tamaño del cuadrito
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 70; // Tamaño visual
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(6, 6, 6, 6);
            diaView.setLayoutParams(params);

            // Verificar si el día está en el historial del JSON
            if (rachaData.getHistorialMes().contains(fechaStr)) {
                diaView.setBackgroundResource(R.drawable.square_active); // Verde
            } else {
                diaView.setBackgroundResource(R.drawable.square_inactive); // Gris/Rojo
            }

            gridCalendario.addView(diaView);
        }
    }
}