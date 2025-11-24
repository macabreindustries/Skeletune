package com.example.skletune;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class OptionsPlan extends Fragment {

    private String rolSeleccionado = "estudiante";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recuperar el rol que viene desde RolSelect
        if (getArguments() != null) {
            rolSeleccionado = getArguments().getString("rol", "estudiante");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_options_plan, container, false);

        Button botonBasic = view.findViewById(R.id.btnBasic);
        Button botonPremium = view.findViewById(R.id.btnPremium);

        // Listener general para los botones
        View.OnClickListener listener = v -> {

            // Aquí puedes guardar el plan seleccionado (Basic o Premium)
            String planSeleccionado = (v.getId() == R.id.btnBasic) ? "Basic" : "Premium";

            // Guardar en SharedPreferences o donde necesites
            // SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
            // prefs.edit().putString("rol", rolSeleccionado).putString("plan", planSeleccionado).apply();

            Toast.makeText(getContext(),
                    "Rol: " + rolSeleccionado + " | Plan: " + planSeleccionado,
                    Toast.LENGTH_SHORT).show();

            // Navegar a NavigationHostActivity
            Intent intent = new Intent(getActivity(), NavigationHostActivity.class);
            intent.putExtra("rol", rolSeleccionado);
            intent.putExtra("plan", planSeleccionado);
            startActivity(intent);

            if (getActivity() != null) {
                getActivity().finish(); // Cierra InicioSesion
            }
        };

        botonBasic.setOnClickListener(listener);
        botonPremium.setOnClickListener(listener);

        return view;
    }
}