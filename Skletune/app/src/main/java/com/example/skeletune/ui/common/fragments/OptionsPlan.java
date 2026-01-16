package com.example.skeletune.ui.common.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.skletune.R;
import com.example.skeletune.utils.SessionManager;

public class OptionsPlan extends Fragment {

    private String rolSeleccionado = "estudiante";
    private SessionManager sessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rolSeleccionado = getArguments().getString("rol", "estudiante");
        }
        sessionManager = new SessionManager(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_options_plan, container, false);

        Button botonBasic = view.findViewById(R.id.btnBasic);
        Button botonPremium = view.findViewById(R.id.btnPremium);

        View.OnClickListener listener = v -> {
            String planSeleccionado = (v.getId() == R.id.btnBasic) ? "Basic" : "Premium";
            Toast.makeText(getContext(), "Rol: " + rolSeleccionado + " | Plan: " + planSeleccionado, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), NavigationHostActivity.class);
            intent.putExtra("rol", rolSeleccionado);
            intent.putExtra("plan", planSeleccionado);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            if (getActivity() != null) {
                getActivity().finish();
            }
        };

        botonBasic.setOnClickListener(listener);
        botonPremium.setOnClickListener(listener);

        return view;
    }
}
