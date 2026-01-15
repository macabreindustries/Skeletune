package com.example.skeletune.ui.student;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.skeletune.ui.common.fragments.CarouselAdapter;
import com.example.skeletune.ui.common.fragments.music;
import com.example.skeletune.ui.teacher.classes;
import com.example.skeletune.utils.SessionManager;
import com.example.skletune.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class home extends Fragment {

    // Usar Looper.getMainLooper() es una mejor práctica en fragmentos modernos
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private ViewPager2 viewPagerCarousel;
    private TabLayout tabIndicator;

    private SessionManager sessionManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos la vista
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 1. Configuración del Carrusel (UI Common)
        setupCarousel(view);

        // 2. Configuración de Botones de Navegación
        setupNavigationButtons(view);

        // A. Inicializar SessionManager
        sessionManager = new SessionManager(requireContext());

        // B. Buscar el TextView del nombre
        TextView tvUserName = view.findViewById(R.id.tv_username);

        // C. Obtener el nombre de la sesión y mostrarlo
        String nombre = sessionManager.getUserName();
        tvUserName.setText(nombre);



        return view;
    }

    private void setupCarousel(View view) {
        viewPagerCarousel = view.findViewById(R.id.vp_carousel);
        tabIndicator = view.findViewById(R.id.tab_carousel_indicator);

        List<Integer> images = Arrays.asList(
                R.drawable.carru1,
                R.drawable.carru2,
                R.drawable.carru3,
                R.drawable.carru4,
                R.drawable.carru5
        );

        CarouselAdapter adapter = new CarouselAdapter(images);
        viewPagerCarousel.setAdapter(adapter);

        new TabLayoutMediator(tabIndicator, viewPagerCarousel, (tab, position) -> {}).attach();

        // Lógica de Auto-scroll optimizada
        runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPagerCarousel != null && viewPagerCarousel.getAdapter() != null) {
                    int next = (viewPagerCarousel.getCurrentItem() + 1) % viewPagerCarousel.getAdapter().getItemCount();
                    viewPagerCarousel.setCurrentItem(next, true);
                    handler.postDelayed(this, 4000);
                }
            }
        };
    }

    private void setupNavigationButtons(View view) {
        // Botón Canciones -> music fragment (en ui/common/fragments)
        LinearLayout btnSongs = view.findViewById(R.id.btn_songs);
        btnSongs.setOnClickListener(v -> navegarAFragmento(new music()));

        // Botón Clases -> classes fragment (en ui/teacher o common)
        LinearLayout btnClasses = view.findViewById(R.id.btn_history); // Verifica si el ID es btn_history o btn_classes
        btnClasses.setOnClickListener(v -> navegarAFragmento(new classes()));
    }

    private void navegarAFragmento(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment) // Usamos tu contenedor estándar
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // Añadimos una transición suave
                .commit();
    }

    // Ciclo de vida para evitar que el carrusel siga corriendo si el usuario no está viendo la pantalla
    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Limpieza crítica para evitar memory leaks
        handler.removeCallbacksAndMessages(null);
    }
}