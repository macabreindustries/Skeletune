package com.example.skeletune.ui.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.skeletune.data.model.Notificacion;
import com.example.skletune.R;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {

    private final List<Notificacion> notificaciones;
    private final Context context;

    public NotificationsAdapter(Context context, List<Notificacion> notificaciones) {
        this.context = context;
        this.notificaciones = notificaciones;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notificacion notificacion = notificaciones.get(position);
        holder.bind(notificacion);
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivAvatar;
        private final TextView tvMessage;
        private final TextView tvTimestamp;
        private final View unreadDot;

        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_notification_avatar);
            tvMessage = itemView.findViewById(R.id.tv_notification_message);
            tvTimestamp = itemView.findViewById(R.id.tv_notification_timestamp);
            unreadDot = itemView.findViewById(R.id.unread_dot_notification);
        }

        void bind(Notificacion notificacion) {
            tvMessage.setText(notificacion.getMensaje());
            tvTimestamp.setText("2 h"); // Placeholder para la fecha

            if (!notificacion.isLeido()) {
                unreadDot.setVisibility(View.VISIBLE);
            } else {
                unreadDot.setVisibility(View.GONE);
            }

            // Aquí necesitaríamos la URL del avatar, que no viene en el DTO de Notificacion.
            // Por ahora, usamos un placeholder.
            Glide.with(context)
                .load(R.mipmap.ic_launcher_round) // Placeholder
                .circleCrop()
                .into(ivAvatar);
        }
    }
}
