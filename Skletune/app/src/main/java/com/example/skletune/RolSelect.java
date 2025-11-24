package com.example.skletune;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RolSelect extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rol_select, container, false);

        Button botonEstudiante = view.findViewById(R.id.botonimgEstu);
        Button botonProfesor = view.findViewById(R.id.botonimgProg);

        // --------- ESTUDIANTE ---------
        botonEstudiante.setOnClickListener(v -> {
            OptionsPlan fragmentDestino = new OptionsPlan();

            // Mandamos el rol seleccionado
            Bundle args = new Bundle();
            args.putString("rol", "estudiante");
            fragmentDestino.setArguments(args);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(android.R.id.content, fragmentDestino);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // --------- PROFESOR ---------
        botonProfesor.setOnClickListener(v -> {
            OptionsPlan fragmentDestino = new OptionsPlan();

            // Mandamos el rol seleccionado
            Bundle args = new Bundle();
            args.putString("rol", "profesor");
            fragmentDestino.setArguments(args);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(android.R.id.content, fragmentDestino);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
