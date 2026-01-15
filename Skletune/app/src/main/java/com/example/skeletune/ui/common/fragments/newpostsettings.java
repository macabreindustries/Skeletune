package com.example.skeletune.ui.common.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.skeletune.data.model.Publicacion;
import com.example.skeletune.ui.common.viewmodels.SocialViewModel;
import com.example.skeletune.utils.SessionManager;
import com.example.skletune.R;

public class newpostsettings extends Fragment {

    private static final String ARG_MEDIA_URI = "media_uri";
    private static final String ARG_IS_VIDEO = "is_video";

    private Uri mediaUri;
    private boolean isVideo;

    private SocialViewModel socialViewModel;
    private SessionManager sessionManager;

    private EditText editTextDescription;
    private Button btnPublish;
    private ProgressBar progressBar;

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
        if (getArguments() != null) {
            mediaUri = getArguments().getParcelable(ARG_MEDIA_URI);
            isVideo = getArguments().getBoolean(ARG_IS_VIDEO, false);
        }

        // Inicializar lógica de sesión y datos
        sessionManager = new SessionManager(requireContext());
        socialViewModel = new ViewModelProvider(this).get(SocialViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newpostsettings, container, false);

        // Inicializar vistas
        editTextDescription = view.findViewById(R.id.et_caption);
        btnPublish = view.findViewById(R.id.btn_siguiente);
        progressBar = view.findViewById(R.id.progressBar);

        btnPublish.setOnClickListener(v -> publicarContenido());

        return view;
    }

    private void publicarContenido() {
        String descripcion = editTextDescription.getText().toString().trim();
        int userId = sessionManager.getUserId();

        if (userId == -1) {
            Toast.makeText(getContext(), "Error de sesión", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Crear el objeto
        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setIdUsuario(userId);
        nuevaPublicacion.setTexto(descripcion);
        nuevaPublicacion.setIdMediaPrincipal(mediaUri.toString());

        // Observar el proceso a través del ViewModel
        socialViewModel.getFeed().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    btnPublish.setEnabled(false);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "¡Publicado con éxito!", Toast.LENGTH_SHORT).show();
                    volverAlMuro();
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    btnPublish.setEnabled(true);
                    Toast.makeText(getContext(), "Error: " + resource.message, Toast.LENGTH_LONG).show();
                    break;
            }
        });

        // NOTA: Deberías tener un método específico en tu SocialViewModel como 'crearPublicacion'
        // socialViewModel.crearPublicacion(nuevaPublicacion);
    }

    private void volverAlMuro() {
        // Regresa a la pantalla principal del host de navegación
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }
}