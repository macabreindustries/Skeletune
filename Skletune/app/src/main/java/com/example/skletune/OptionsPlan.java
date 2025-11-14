package com.example.skletune;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

            Fragment fragmentDestino;

            if (rolSeleccionado.equals("estudiante")) {
                fragmentDestino = new home();
            }
            else {
                fragmentDestino = new Certifications();
            }

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(android.R.id.content, fragmentDestino);
            transaction.addToBackStack(null);
            transaction.commit();
        };

        botonBasic.setOnClickListener(listener);
        botonPremium.setOnClickListener(listener);

        return view;
    }
}
