package com.example.skletune;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class homeprofessor extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homeprofessor, container, false);

        CardView cardHistory = view.findViewById(R.id.card_history);

        cardHistory.setOnClickListener(v -> {
            // Fragmento al que quieres ir
            alumnos alumnosfragment = new alumnos();

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(android.R.id.content, alumnosfragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
