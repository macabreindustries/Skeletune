package com.example.skeletune.ui.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.skeletune.data.model.UserProfileDto;
import com.example.skeletune.data.network.RetrofitClient;
import com.example.skeletune.data.network.SocialMediaApiService;
import com.example.skeletune.ui.common.adapters.ProfilePostAdapter;
import com.example.skletune.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile extends Fragment {

    private ImageView imgProfile;
    private TextView tvName, tvFollowing, tvFollowers, tvLikes;
    private RecyclerView rvPosts;
    private ProfilePostAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        // 1. Vincular vistas (Basado en el XML que me pasaste)
        imgProfile = v.findViewById(R.id.foto_perfil);
        tvName = v.findViewById(R.id.tv_profile_name); // Asegúrate de agregar este ID en tu XML
        tvFollowing = v.findViewById(R.id.tv_following_count);
        tvFollowers = v.findViewById(R.id.tv_followers_count);
        tvLikes = v.findViewById(R.id.tv_likes_total);

        rvPosts = v.findViewById(R.id.posts_recycler_view);
        rvPosts.setLayoutManager(new GridLayoutManager(getContext(), 3)); // 3 columnas para que parezca Instagram

        cargarDatosPerfil();

        return v;
    }

    private void cargarDatosPerfil() {
        int idUsuarioActual = 4; // ID de prueba por ahora

        RetrofitClient.getInstance().getRetrofit().create(SocialMediaApiService.class)
                .getUserProfile(idUsuarioActual)
                .enqueue(new Callback<UserProfileDto>() {
                    @Override
                    public void onResponse(Call<UserProfileDto> call, Response<UserProfileDto> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UserProfileDto profile = response.body();

                            // Llenar datos de texto
                            tvName.setText(profile.getNombre());
                            tvFollowing.setText(String.valueOf(profile.getSiguiendoCount()));
                            tvFollowers.setText(String.valueOf(profile.getSeguidoresCount()));
                            tvLikes.setText(String.valueOf(profile.getTotalLikesRecibidos()));

                            // Cargar Foto de Perfil Circular
                            Glide.with(requireContext())
                                    .load(profile.getUrlAvatar())
                                    .circleCrop()
                                    .into(imgProfile);

                            // Configurar el Grid de fotos
                            adapter = new ProfilePostAdapter(profile.getMisPublicaciones());
                            rvPosts.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserProfileDto> call, Throwable t) {
                        Toast.makeText(getContext(), "Error al cargar perfil", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}