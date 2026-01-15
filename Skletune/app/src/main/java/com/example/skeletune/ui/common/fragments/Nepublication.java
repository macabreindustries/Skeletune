package com.example.skeletune.ui.common.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.skeletune.ui.common.viewmodels.SocialViewModel; // Conexión con la lógica social
import com.example.skletune.R;

public class Nepublication extends Fragment {

    private ActivityResultLauncher<String> pickMediaLauncher;
    private SocialViewModel socialViewModel; // Preparado para la lógica de red social

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar el ViewModel compartido o específico
        socialViewModel = new ViewModelProvider(this).get(SocialViewModel.class);

        // Configurar el launcher para seleccionar archivos multimedia
        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        boolean isVideo = isVideoFile(uri);
                        navigateToLoadedFragment(uri, isVideo);
                    } else {
                        Toast.makeText(getContext(), "Operación cancelada", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nepublication, container, false);

        ImageButton btnAddMedia = view.findViewById(R.id.btn_add_media);

        btnAddMedia.setOnClickListener(v -> openMediaPicker());

        return view;
    }

    private void openMediaPicker() {
        // Mantenemos la selección de imágenes y videos
        pickMediaLauncher.launch("*/*"); // Algunos dispositivos requieren */* para filtrar luego por MIME
    }

    private boolean isVideoFile(Uri uri) {
        if (getContext() == null) return false;
        String mimeType = getContext().getContentResolver().getType(uri);
        return mimeType != null && mimeType.startsWith("video/");
    }

    private void navigateToLoadedFragment(Uri mediaUri, boolean isVideo) {
        // Usamos el fragmento de destino que procesará la subida
        newpublicationcargada cargadaFragment = newpublicationcargada.newInstance(mediaUri, isVideo);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, cargadaFragment) // Consistente con NavigationHostActivity
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}