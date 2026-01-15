package com.example.skeletune.ui.common.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.skletune.R;
import com.google.android.material.appbar.MaterialToolbar;

public class newpublicationcargada extends Fragment {

    private static final String ARG_MEDIA_URI = "media_uri";
    private static final String ARG_IS_VIDEO = "is_video";

    private Uri mediaUri;
    private boolean isVideo;
    private VideoView vvMediaPreview; // Variable de instancia para mejor control

    public static newpublicationcargada newInstance(Uri mediaUri, boolean isVideo) {
        newpublicationcargada fragment = new newpublicationcargada();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MEDIA_URI, mediaUri);
        args.putBoolean(ARG_IS_VIDEO, isVideo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mediaUri = getArguments().getParcelable(ARG_MEDIA_URI);
            isVideo = getArguments().getBoolean(ARG_IS_VIDEO, false);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newpublicationcargada, container, false);

        // Referencias a las vistas
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        ImageView ivMediaPreview = view.findViewById(R.id.iv_media_preview);
        vvMediaPreview = view.findViewById(R.id.vv_media_preview);
        Button btnSiguiente = view.findViewById(R.id.btn_siguiente);

        // Configurar navegación de retroceso consistente
        toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());

        // Lógica de visualización multimedia
        if (mediaUri != null) {
            if (isVideo) {
                ivMediaPreview.setVisibility(View.GONE);
                vvMediaPreview.setVisibility(View.VISIBLE);
                vvMediaPreview.setVideoURI(mediaUri);
                vvMediaPreview.setOnPreparedListener(mp -> {
                    mp.setLooping(true); // El preview del video suele ser un loop
                    vvMediaPreview.start();
                });
            } else {
                ivMediaPreview.setVisibility(View.VISIBLE);
                vvMediaPreview.setVisibility(View.GONE);
                ivMediaPreview.setImageURI(mediaUri);
            }
        } else {
            Toast.makeText(getContext(), "Error al cargar el archivo", Toast.LENGTH_SHORT).show();
        }

        btnSiguiente.setOnClickListener(v -> navigateToSettings());

        return view;
    }

    private void navigateToSettings() {
        // Navegación hacia el último paso del flujo
        newpostsettings settingsFragment = newpostsettings.newInstance(mediaUri, isVideo);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, settingsFragment) // Usamos el contenedor estándar
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Detener el video para liberar el hardware del decodificador
        if (vvMediaPreview != null && vvMediaPreview.isPlaying()) {
            vvMediaPreview.pause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Limpieza de referencia para evitar memory leaks
        vvMediaPreview = null;
    }
}