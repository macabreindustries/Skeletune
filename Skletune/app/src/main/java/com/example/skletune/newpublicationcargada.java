package com.example.skletune;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.Toast;
import com.google.android.material.appbar.MaterialToolbar;

public class newpublicationcargada extends Fragment {

    private static final String ARG_MEDIA_URI = "media_uri";
    private static final String ARG_IS_VIDEO = "is_video";

    private Uri mediaUri;
    private boolean isVideo;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newpublicationcargada, container, false);

        // Referencias a las vistas
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        ImageView ivMediaPreview = view.findViewById(R.id.iv_media_preview);
        VideoView vvMediaPreview = view.findViewById(R.id.vv_media_preview);
        Button btnSiguiente = view.findViewById(R.id.btn_siguiente);

        // Configurar botón de retroceso en toolbar
        toolbar.setNavigationOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        // Mostrar la imagen o video seleccionado
        if (mediaUri != null) {
            if (isVideo) {
                // Mostrar video
                ivMediaPreview.setVisibility(View.GONE);
                vvMediaPreview.setVisibility(View.VISIBLE);
                vvMediaPreview.setVideoURI(mediaUri);
                vvMediaPreview.start();
            } else {
                // Mostrar imagen
                ivMediaPreview.setVisibility(View.VISIBLE);
                vvMediaPreview.setVisibility(View.GONE);
                ivMediaPreview.setImageURI(mediaUri);
            }
        } else {
            Toast.makeText(getContext(), "Error al cargar el archivo", Toast.LENGTH_SHORT).show();
        }

        // Configurar el botón Siguiente
        btnSiguiente.setOnClickListener(v -> {
            navigateToSettings();
        });

        return view;
    }

    /**
     * Navega al fragment NewPublicationSettings
     */
    private void navigateToSettings() {
        if (getActivity() != null) {
            newpostsettings settingsFragment = newpostsettings.newInstance(mediaUri, isVideo);

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, settingsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pausar video si está reproduciendo
        VideoView vvMediaPreview = getView() != null ? getView().findViewById(R.id.vv_media_preview) : null;
        if (vvMediaPreview != null && vvMediaPreview.isPlaying()) {
            vvMediaPreview.pause();
        }
    }
}