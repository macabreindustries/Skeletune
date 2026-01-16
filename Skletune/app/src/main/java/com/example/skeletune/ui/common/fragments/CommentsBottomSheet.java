package com.example.skeletune.ui.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.skeletune.data.model.ComentarioResponseDto;
import com.example.skeletune.data.network.RetrofitClient;
import com.example.skeletune.data.network.SocialMediaApiService;
import com.example.skeletune.ui.common.adapters.CommentsAdapter;
import com.example.skletune.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsBottomSheet extends BottomSheetDialogFragment {

    private int idPublicacion;
    private RecyclerView rvComments;
    private CommentsAdapter adapter;
    private List<ComentarioResponseDto> commentList = new ArrayList<>();

    public static CommentsBottomSheet newInstance(int idPublicacion) {
        CommentsBottomSheet fragment = new CommentsBottomSheet();
        Bundle args = new Bundle();
        args.putInt("id_pub", idPublicacion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPublicacion = getArguments().getInt("id_pub");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_comments_sheet, container, false);

        // Referencias a la lista
        rvComments = v.findViewById(R.id.rv_comments_modal);
        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommentsAdapter(commentList);
        rvComments.setAdapter(adapter);

        // Referencias a la barra de escritura (NUEVO)
        EditText etComment = v.findViewById(R.id.et_new_comment);
        ImageView btnSend = v.findViewById(R.id.btn_send_comment);

        // Escuchador del botón (NUEVO)
        btnSend.setOnClickListener(view -> {
            String texto = etComment.getText().toString().trim();
            if (!texto.isEmpty()) {
                enviarComentario(texto, etComment);
            } else {
                Toast.makeText(getContext(), "Escribe un comentario", Toast.LENGTH_SHORT).show();
            }
        });

        cargarComentarios();

        return v;
    }

    // Método para enviar el comentario al servidor
    private void enviarComentario(String texto, EditText etComment) {
        // Usamos el ID 4 fijo como prueba, o sessionManager.getUserId() si ya lo tienes
        int idUsuario = 4;

        RetrofitClient.getInstance().getRetrofit().create(SocialMediaApiService.class)
                .publicarComentario(idPublicacion, idUsuario, texto)
                .enqueue(new Callback<ComentarioResponseDto>() {
                    @Override
                    public void onResponse(Call<ComentarioResponseDto> call, Response<ComentarioResponseDto> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Limpiamos el texto
                            etComment.setText("");
                            // Añadimos el nuevo comentario a la lista local para que se vea al instante
                            commentList.add(response.body());
                            adapter.notifyItemInserted(commentList.size() - 1);
                            rvComments.scrollToPosition(commentList.size() - 1);
                        } else {
                            Toast.makeText(getContext(), "Error al enviar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ComentarioResponseDto> call, Throwable t) {
                        Toast.makeText(getContext(), "Fallo de red", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cargarComentarios() {
        RetrofitClient.getInstance().getRetrofit().create(SocialMediaApiService.class)
                .getComentarios(idPublicacion)
                .enqueue(new Callback<List<ComentarioResponseDto>>() {
                    @Override
                    public void onResponse(Call<List<ComentarioResponseDto>> call, Response<List<ComentarioResponseDto>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            commentList.clear();
                            commentList.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ComentarioResponseDto>> call, Throwable t) {
                        Toast.makeText(getContext(), "Error al cargar comentarios", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}