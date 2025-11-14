package com.example.skletune;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CreatePassword extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_password, container, false);

        Button botonSiguiente = view.findViewById(R.id.botonenviarcontrasena);

        botonSiguiente.setOnClickListener(v -> {
            // Crear el fragmento destino
            login fragmentDestino = new login();

            // Reemplazar el fragmento actual por el destino
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(android.R.id.content, fragmentDestino);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}