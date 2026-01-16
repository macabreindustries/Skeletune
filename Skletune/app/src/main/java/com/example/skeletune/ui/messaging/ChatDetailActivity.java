package com.example.skeletune.ui.messaging;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.skeletune.data.model.Mensaje;
import com.example.skeletune.data.model.User;
import com.example.skeletune.data.network.ApiService;
import com.example.skeletune.data.network.RetrofitClient;
import com.example.skeletune.utils.SessionManager;
import com.example.skletune.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailActivity extends AppCompatActivity {

    public static final String EXTRA_OTHER_USER = "other_user";
    private RecyclerView recyclerViewChat;
    private ChatAdapter chatAdapter;
    private List<Mensaje> messageList = new ArrayList<>();
    private EditText etMessage;
    private ImageButton btnSend;

    private ApiService apiService;
    private SessionManager sessionManager;
    private User otherUser;
    private int currentUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        // --- Inicialización ---
        sessionManager = new SessionManager(this);
        currentUserId = sessionManager.getUserId();
        otherUser = (User) getIntent().getSerializableExtra(EXTRA_OTHER_USER);

        if (otherUser == null || currentUserId == -1) {
            Toast.makeText(this, "Error: No se pudo cargar la información del chat.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // --- Configurar la Toolbar ---
        setupToolbar();

        // --- Configurar el RecyclerView ---
        setupRecyclerView();

        // --- Configurar el campo de texto y el botón de enviar ---
        etMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(v -> sendMessage());

        // --- Configurar API y cargar mensajes ---
        apiService = RetrofitClient.getInstance().getApiService();
        loadMessages();
    }

    private void setupToolbar() {
        ImageView ivBack = findViewById(R.id.iv_back);
        ImageView ivAvatar = findViewById(R.id.iv_avatar);
        TextView tvUserName = findViewById(R.id.tv_user_name);

        ivBack.setOnClickListener(v -> finish());
        tvUserName.setText(otherUser.getUsername());

        Glide.with(this)
                .load(otherUser.getAvatarUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .circleCrop()
                .into(ivAvatar);
    }

    private void setupRecyclerView() {
        recyclerViewChat = findViewById(R.id.recycler_view_chat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(messageList, currentUserId);
        recyclerViewChat.setAdapter(chatAdapter);
    }

    private void loadMessages() {
        apiService.getConversation(currentUserId, otherUser.getId()).enqueue(new Callback<List<Mensaje>>() {
            @Override
            public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    runOnUiThread(() -> {
                        messageList.clear();
                        messageList.addAll(response.body());
                        chatAdapter.notifyDataSetChanged();
                        // Mover la lista al último mensaje
                        if (!messageList.isEmpty()) {
                            recyclerViewChat.scrollToPosition(messageList.size() - 1);
                        }
                    });
                } else {
                    Toast.makeText(ChatDetailActivity.this, "No se pudo cargar la conversación.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable t) {
                Toast.makeText(ChatDetailActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        String messageText = etMessage.getText().toString().trim();
        if (messageText.isEmpty()) {
            return;
        }

        Mensaje newMessage = new Mensaje();
        // Asumiendo que Mensaje tiene setters. Si no, necesitarás un constructor.
        // newMessage.setIdEmisor(currentUserId);
        // newMessage.setIdReceptor(otherUser.getId());
        // newMessage.setMensaje(messageText);

        apiService.sendMessage(newMessage).enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                if (response.isSuccessful() && response.body() != null) {
                    runOnUiThread(() -> {
                        etMessage.setText("");
                        messageList.add(response.body());
                        chatAdapter.notifyItemInserted(messageList.size() - 1);
                        recyclerViewChat.scrollToPosition(messageList.size() - 1);
                    });
                } else {
                    Toast.makeText(ChatDetailActivity.this, "Error al enviar el mensaje.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                Toast.makeText(ChatDetailActivity.this, "Error de red al enviar.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}