package com.example.skletune;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Certifications extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_certifications, container, false);
        Button botoncerti = view.findViewById(R.id.botonenviarcertificacion);

        botoncerti.setOnClickListener(v -> {
            // Crear el fragmento destino
            homeprofessor fragmentDestino = new homeprofessor();

            // Reemplazar el fragmento en el contenedor correcto
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

            // CRÍTICO: Usar R.id.fragment_container en lugar de android.R.id.content
            transaction.replace(R.id.fragment_container, fragmentDestino);

            // NO usar addToBackStack para que no pueda volver a Certifications
            // transaction.addToBackStack(null);

            transaction.commit();
        });

        return view;
    }
}