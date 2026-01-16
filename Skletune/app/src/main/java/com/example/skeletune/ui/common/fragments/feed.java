package com.example.skeletune.ui.common.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skeletune.data.model.PublicacionFeedDto;
import com.example.skeletune.data.network.RetrofitClient;
import com.example.skeletune.data.network.SocialMediaApiService;
import com.example.skeletune.ui.common.adapters.PostAdapter;
import com.example.skeletune.utils.SessionManager; // Asegúrate de tener esta utilidad
import com.example.skletune.R;

import java.util.ArrayList;
import java.util.List;

public class feed extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<PublicacionFeedDto> postList = new ArrayList<>();
    private SessionManager sessionManager; // Para obtener el ID del usuario logueado

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        // Inicializar SessionManager
        sessionManager = new SessionManager(requireContext());

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recycler_feed_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // --- CONFIGURACIÓN DEL ADAPTER CON LISTENERS ---
        adapter = new PostAdapter(postList, getContext(), new PostAdapter.OnPostInteractionListener() {
            @Override
            public void onLikeClick(PublicacionFeedDto post, int position) {
                // Ejecutamos la lógica de dar/quitar like
                ejecutarLike(post, position);
            }

            @Override
            public void onCommentClick(PublicacionFeedDto post) {
                // Abrimos el modal de comentarios que creamos
                CommentsBottomSheet sheet = CommentsBottomSheet.newInstance(post.getIdPublicacion());
                sheet.show(getChildFragmentManager(), "CommentsSheet");
            }
        });

        recyclerView.setAdapter(adapter);

        cargarFeed();

        return view;
    }

    private void cargarFeed() {
        RetrofitClient.getInstance().getRetrofit().create(SocialMediaApiService.class)
                .getSocialFeed()
                .enqueue(new retrofit2.Callback<List<PublicacionFeedDto>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<PublicacionFeedDto>> call,
                                           retrofit2.Response<List<PublicacionFeedDto>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            postList.clear();
                            postList.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<PublicacionFeedDto>> call, Throwable t) {
                        Log.e("SOCIAL_ERROR", "Error al cargar feed: " + t.getMessage());
                    }
                });
    }

    private void ejecutarLike(PublicacionFeedDto post, int position) {
        int idUsuario = sessionManager.getUserId();
        Log.d("LIKE_TEST", "Enviando Like - Usuario: " + idUsuario + " Post: " + post.getIdPublicacion());

        // 1. Cambio Visual Optimista (Inmediato)
        boolean estabaLikeado = post.isLikedByMe();
        post.setLikedByMe(!estabaLikeado);

        if (post.isLikedByMe()) {
            post.setLikesCount(post.getLikesCount() + 1);
        } else {
            post.setLikesCount(post.getLikesCount() - 1);
        }
        adapter.notifyItemChanged(position);

        // 2. Llamada a la API
        RetrofitClient.getInstance().getRetrofit().create(SocialMediaApiService.class)
                .toggleLike(post.getIdPublicacion(), idUsuario)
                .enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                        if (!response.isSuccessful()) {
                            // Si falla el servidor, revertimos el cambio visual
                            revertirLike(post, position);
                            Toast.makeText(getContext(), "Error al procesar Like", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                        // Si falla la red, revertimos
                        revertirLike(post, position);
                    }
                });
    }

    private void revertirLike(PublicacionFeedDto post, int position) {
        post.setLikedByMe(!post.isLikedByMe());
        if (post.isLikedByMe()) {
            post.setLikesCount(post.getLikesCount() + 1);
        } else {
            post.setLikesCount(post.getLikesCount() - 1);
        }
        adapter.notifyItemChanged(position);
    }
}