package com.example.skletune;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class newpostsettings extends Fragment {

    private static final String ARG_MEDIA_URI = "media_uri";
    private static final String ARG_IS_VIDEO = "is_video";

    private Uri mediaUri;
    private boolean isVideo;

    /**
     * Método estático para crear una nueva instancia del fragment con parámetros
     */
    public static newpostsettings newInstance(Uri mediaUri, boolean isVideo) {
        newpostsettings fragment = new newpostsettings();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MEDIA_URI, mediaUri);
        args.putBoolean(ARG_IS_VIDEO, isVideo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recuperar los argumentos pasados desde el fragment anterior
        if (getArguments() != null) {
            mediaUri = getArguments().getParcelable(ARG_MEDIA_URI);
            isVideo = getArguments().getBoolean(ARG_IS_VIDEO, false);

            // Aquí ya tienes acceso a la URI del archivo y si es video o no
            // Puedes usarlos para configurar tu publicación
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_newpostsettings, container, false);

        // Aquí puedes usar mediaUri e isVideo para configurar tus vistas
        // Por ejemplo: mostrar una miniatura, guardar la referencia del archivo, etc.

        return view;
    }

    /**
     * Métodos públicos para acceder a los datos del archivo
     */
    public Uri getMediaUri() {
        return mediaUri;
    }

    public boolean isVideo() {
        return isVideo;
    }
}