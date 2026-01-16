package com.example.skeletune.ui.common.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.skeletune.data.model.Conversation;
import com.example.skeletune.data.model.Mensaje;
import com.example.skeletune.data.model.Notificacion;
import com.example.skeletune.data.model.User;
import com.example.skeletune.data.network.ApiService;
import com.example.skeletune.data.network.RetrofitClient;
import com.example.skeletune.ui.common.adapters.ConversationsAdapter;
import com.example.skeletune.ui.common.adapters.NotificationsAdapter;
import com.example.skeletune.ui.messaging.ChatDetailActivity;
import com.example.skeletune.utils.SessionManager;
import com.example.skletune.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(position == 0 ? "Mensajes" : "Notificaciones");
        }).attach();
    }

    // --- CLASES INTERNAS ---

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return position == 0 ? new ConversationsFragment() : new Notifications_Fragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    public static class ConversationsFragment extends Fragment implements ConversationsAdapter.OnConversationClickListener {
        private RecyclerView recyclerView;
        private ConversationsAdapter adapter;
        private List<Conversation> conversationList = new ArrayList<>();
        private ApiService apiService;
        private SessionManager sessionManager;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_conversations, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            sessionManager = new SessionManager(getContext());
            apiService = RetrofitClient.getInstance().getApiService();

            recyclerView = view.findViewById(R.id.recycler_view_conversations);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new ConversationsAdapter(getContext(), conversationList, this);
            recyclerView.setAdapter(adapter);

            loadConversations();
        }

        private void loadConversations() {
            int currentUserId = sessionManager.getUserId();
            if (currentUserId == -1) return;

            apiService.getAllMensajes().enqueue(new Callback<List<Mensaje>>() {
                @Override
                public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        processMessages(response.body(), currentUserId);
                    }
                }

                @Override
                public void onFailure(Call<List<Mensaje>> call, Throwable t) {
                    Toast.makeText(getContext(), "Error de red al cargar conversaciones", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void processMessages(List<Mensaje> messages, int currentUserId) {
            Map<Integer, Mensaje> latestMessages = new ConcurrentHashMap<>();
            for (Mensaje msg : messages) {
                if (msg.getIdEmisor() == null || msg.getIdReceptor() == null || msg.getFechaEnvio() == null) continue;

                if (msg.getIdEmisor() == currentUserId || msg.getIdReceptor() == currentUserId) {
                    int otherUserId = msg.getIdEmisor() == currentUserId ? msg.getIdReceptor() : msg.getIdEmisor();

                    if (!latestMessages.containsKey(otherUserId) || msg.getFechaEnvio().isAfter(latestMessages.get(otherUserId).getFechaEnvio())) {
                        latestMessages.put(otherUserId, msg);
                    }
                }
            }

            conversationList.clear();
            for (Map.Entry<Integer, Mensaje> entry : latestMessages.entrySet()) {
                int otherUserId = entry.getKey();
                Mensaje lastMessage = entry.getValue();
                User otherUser = new User(otherUserId, "Usuario " + otherUserId, null);
                conversationList.add(new Conversation(otherUser, lastMessage));
            }

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    Collections.sort(conversationList, (c1, c2) -> c2.getLastMessage().getFechaEnvio().compareTo(c1.getLastMessage().getFechaEnvio()));
                    adapter.notifyDataSetChanged();
                });
            }
        }

        @Override
        public void onConversationClick(Conversation conversation) {
            Intent intent = new Intent(getActivity(), ChatDetailActivity.class);
            intent.putExtra(ChatDetailActivity.EXTRA_OTHER_USER, conversation.getOtherUser());
            startActivity(intent);
        }
    }

    public static class Notifications_Fragment extends Fragment {
        private RecyclerView recyclerView;
        private NotificationsAdapter adapter;
        private List<Notificacion> notificationList = new ArrayList<>();
        private ApiService apiService;
        private SessionManager sessionManager;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_notifications, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            sessionManager = new SessionManager(getContext());
            apiService = RetrofitClient.getInstance().getApiService();

            recyclerView = view.findViewById(R.id.recycler_view_notifications);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new NotificationsAdapter(getContext(), notificationList);
            recyclerView.setAdapter(adapter);

            loadNotifications();
        }

        private void loadNotifications() {
            int userId = sessionManager.getUserId();
            if (userId == -1) return;

            apiService.getNotificacionesByUsuario(userId).enqueue(new Callback<List<Notificacion>>() {
                @Override
                public void onResponse(Call<List<Notificacion>> call, Response<List<Notificacion>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if(getActivity() != null) {
                            getActivity().runOnUiThread(() -> {
                                notificationList.clear();
                                notificationList.addAll(response.body());
                                //Collections.sort(notificationList, (n1, n2) -> n2.getFecha().compareTo(n1.getFecha()));
                                adapter.notifyDataSetChanged();
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Notificacion>> call, Throwable t) {
                    Toast.makeText(getContext(), "Error de red al cargar notificaciones", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
