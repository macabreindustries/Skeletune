package com.example.skeletune.ui.student;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.skeletune.data.model.DayStatus;
import com.example.skeletune.data.model.RachaDTO;
import com.example.skeletune.data.network.ApiService;
import com.example.skeletune.data.network.RetrofitClient;
import com.example.skeletune.ui.common.adapters.StreakAdapter;
import com.example.skeletune.ui.common.adapters.CarouselAdapter;
import com.example.skeletune.ui.common.fragments.feed;
import com.example.skeletune.ui.common.fragments.music;
import com.example.skeletune.ui.teacher.classes;
import com.example.skeletune.utils.SessionManager;
import com.example.skletune.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends Fragment {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private ViewPager2 viewPagerCarousel;
    private TabLayout tabIndicator;
    private SessionManager sessionManager;

    // --- VARIABLES DE LA RACHA ---
    private LinearLayout sectionStreak;
    private TextView tvStreakDays;
    private RecyclerView rvStreak;
    private RachaDTO rachaData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 1. Inicializar Vistas
        sectionStreak = view.findViewById(R.id.section_streak);
        tvStreakDays = view.findViewById(R.id.tv_streak_days);
        rvStreak = view.findViewById(R.id.rv_week_status);
        sessionManager = new SessionManager(requireContext());

        // 2. Configurar RecyclerView (LayoutManager horizontal)
        rvStreak.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // 3. Cargar Datos Reales desde la API
        cargarDatosRacha();

        // 4. Configuraciones adicionales
        setupCarousel(view);
        setupNavigationButtons(view);

        // 5. Usuario
        TextView tvUserName = view.findViewById(R.id.tv_username);
        if (sessionManager.getUserName() != null) {
            tvUserName.setText(sessionManager.getUserName());
        }

        return view;
    }

    private void cargarDatosRacha() {
        int idUsuario = sessionManager.getUserId();

        // Usamos el Singleton corregido para crear la interfaz de racha
        RetrofitClient.getInstance().getRetrofit().create(ApiService.class)
                .obtenerRacha(idUsuario)
                .enqueue(new Callback<RachaDTO>() {
                    @Override
                    public void onResponse(Call<RachaDTO> call, Response<RachaDTO> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            rachaData = response.body();
                            actualizarInterfazRacha(rachaData);
                        } else {
                            Log.e("API_ERROR", "Respuesta no exitosa: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<RachaDTO> call, Throwable t) {
                        Log.e("API_ERROR", "Fallo de conexión: " + t.getMessage());
                    }
                });
    }

    private void actualizarInterfazRacha(RachaDTO data) {
        if (data == null) return;

        // 1. Forzar el texto del TextView
        tvStreakDays.setText(data.getRachaActual() + " días");
        Log.d("RACHA_DEBUG", "Actualizando UI con racha: " + data.getRachaActual());

        // 2. Limpiar y llenar la lista
        List<DayStatus> weekList = new ArrayList<>();
        Map<String, Boolean> estadoSemana = data.getEstadoSemana();

        if (estadoSemana != null) {
            // Log para ver qué días estamos procesando
            for (Map.Entry<String, Boolean> entry : estadoSemana.entrySet()) {
                weekList.add(new DayStatus(entry.getKey(), entry.getValue()));
                Log.d("RACHA_DEBUG", "Día: " + entry.getKey() + " Estado: " + entry.getValue());
            }
        }

        // 3. RE-INSTANCIAR EL ADAPTADOR (Esto es clave si no se refresca)
        StreakAdapter adapter = new StreakAdapter(weekList);
        rvStreak.setAdapter(adapter);

        // 4. Notificar al LayoutManager
        rvStreak.scheduleLayoutAnimation();
    }

    private void setupCarousel(View view) {
        viewPagerCarousel = view.findViewById(R.id.vp_carousel);
        tabIndicator = view.findViewById(R.id.tab_carousel_indicator);

        List<Integer> images = Arrays.asList(
                R.drawable.carru1, R.drawable.carru2, R.drawable.carru3,
                R.drawable.carru4, R.drawable.carru5
        );

        CarouselAdapter adapter = new CarouselAdapter(images);

        // --- NUEVO: Configurar el clic para ir al Social Media ---
        adapter.setOnItemClickListener(position -> {
            // Usamos el método navegarAFragmento que ya tienes en tu clase home
            navegarAFragmento(new feed());
        });

        viewPagerCarousel.setAdapter(adapter);

        // --- EFECTO DE ZOOM ---
        viewPagerCarousel.setOffscreenPageLimit(3);
        viewPagerCarousel.setPageTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
            page.setScaleX(0.85f + r * 0.15f);
            page.setAlpha(0.5f + r * 0.5f);
        });

        new TabLayoutMediator(tabIndicator, viewPagerCarousel, (tab, position) -> {}).attach();

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
        view.findViewById(R.id.btn_songs).setOnClickListener(v -> navegarAFragmento(new music()));
        view.findViewById(R.id.btn_history).setOnClickListener(v -> navegarAFragmento(new classes()));

        if (sectionStreak != null) {
            // En home.java, dentro de setupNavigationButtons
            sectionStreak.setOnClickListener(v -> {
                if (rachaData != null) {
                    racha rachaModal = new racha();
                    rachaModal.setRachaData(rachaData); // <-- PASAMOS LA INFO REAL
                    rachaModal.show(getParentFragmentManager(), "RachaModal");
                }
            });
        }
    }

    private void navegarAFragmento(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (runnable != null) handler.postDelayed(runnable, 4000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}