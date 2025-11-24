package com.example.skletune;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class home extends Fragment {

    private Handler handler = new Handler();
    private Runnable runnable;
    private ViewPager2 viewPagerCarousel;
    private TabLayout tabIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPagerCarousel = view.findViewById(R.id.vp_carousel);
        tabIndicator = view.findViewById(R.id.tab_carousel_indicator);

        // Lista de imágenes del carrusel
        List<Integer> images = Arrays.asList(
                R.drawable.carru1,
                R.drawable.carru2,
                R.drawable.carru3,
                R.drawable.carru4,
                R.drawable.carru5
        );

        CarouselAdapter adapter = new CarouselAdapter(images);
        viewPagerCarousel.setAdapter(adapter);

        // Conectar los puntitos del TabLayout con ViewPager2
        new TabLayoutMediator(tabIndicator, viewPagerCarousel,
                (tab, position) -> {
                }).attach();

        // Auto-scroll del carrusel
        runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPagerCarousel.getAdapter() != null) {
                    int itemCount = viewPagerCarousel.getAdapter().getItemCount();
                    int current = viewPagerCarousel.getCurrentItem();
                    int next = (current + 1) % itemCount;

                    viewPagerCarousel.setCurrentItem(next, true);
                }
                handler.postDelayed(this, 4000);
            }
        };

        handler.postDelayed(runnable, 4000);

        // Pausar y reiniciar auto-scroll si el usuario desliza manualmente
        viewPagerCarousel.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 4000);
            }
        });

        // IMPORTANTE: Usar R.id.fragment_container en lugar de android.R.id.content
        LinearLayout btnSongs = view.findViewById(R.id.btn_songs);
        btnSongs.setOnClickListener(v -> {
            music musicFragment = new music();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            // Usar el contenedor correcto para mantener la barra de navegación visible
            transaction.replace(R.id.fragment_container, musicFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        LinearLayout btnClasses = view.findViewById(R.id.btn_history);
        btnClasses.setOnClickListener(v -> {
            classes classesFragment = new classes();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            // Usar el contenedor correcto para mantener la barra de navegación visible
            transaction.replace(R.id.fragment_container, classesFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
    }
}