package com.example.skletune;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class Nepublication extends Fragment {

    private ActivityResultLauncher<String> pickMediaLauncher;
    private Uri selectedMediaUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar el launcher para seleccionar imágenes y videos
        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedMediaUri = uri;
                        Toast.makeText(getContext(), "Archivo seleccionado: " + uri.getLastPathSegment(),
                                Toast.LENGTH_SHORT).show();

                        // Determinar si es imagen o video
                        boolean isVideo = isVideoFile(uri);

                        // Navegar al siguiente fragment con la imagen/video cargado
                        navigateToLoadedFragment(uri, isVideo);
                    } else {
                        Toast.makeText(getContext(), "No se seleccionó ningún archivo",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nepublication, container, false);

        // Obtener referencia al botón
        ImageButton btnAddMedia = view.findViewById(R.id.btn_add_media);

        // Configurar el click listener
        btnAddMedia.setOnClickListener(v -> {
            // Abrir selector de archivos para imágenes y videos
            openMediaPicker();
        });

        return view;
    }

    /**
     * Abre el selector de archivos para elegir imágenes o videos
     */
    private void openMediaPicker() {
        // Imágenes Y videos
        pickMediaLauncher.launch("image/*,video/*");
    }

    /**
     * Determina si el archivo seleccionado es un video
     */
    private boolean isVideoFile(Uri uri) {
        String mimeType = getContext().getContentResolver().getType(uri);
        return mimeType != null && mimeType.startsWith("video/");
    }

    /**
     * Navega al fragment NewPublicationCargada con el archivo seleccionado
     */
    private void navigateToLoadedFragment(Uri mediaUri, boolean isVideo) {
        if (getActivity() != null) {
            newpublicationcargada cargadaFragment = newpublicationcargada.newInstance(mediaUri, isVideo);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, cargadaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    /**
     * Método opcional para obtener la URI del archivo seleccionado
     */
    public Uri getSelectedMediaUri() {
        return selectedMediaUri;
    }
}