package com.example.skeletune.ui.messaging;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.skeletune.data.model.Mensaje;
import com.example.skletune.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private final List<Mensaje> mensajes;
    private final int currentUserId;

    // Constantes para identificar el tipo de vista
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    public ChatAdapter(List<Mensaje> mensajes, int currentUserId) {
        this.mensajes = mensajes;
        this.currentUserId = currentUserId;
    }

    /**
     * Este método decide qué diseño usar (burbuja azul o gris) para cada mensaje.
     */
    @Override
    public int getItemViewType(int position) {
        Mensaje mensaje = mensajes.get(position);
        // Si el ID del emisor del mensaje es igual al ID del usuario actual, es un mensaje enviado.
        if (mensaje.getIdEmisor() == currentUserId) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        // Inflamos el diseño correspondiente según el tipo de vista
        if (viewType == VIEW_TYPE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_sent, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_received, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Mensaje mensaje = mensajes.get(position);
        holder.bind(mensaje);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    /**
     * El ViewHolder que contiene las vistas para cada burbuja de mensaje.
     */
    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMessageBody;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageBody = itemView.findViewById(R.id.tv_message_body);
        }

        void bind(Mensaje mensaje) {
            tvMessageBody.setText(mensaje.getMensaje());
        }
    }
}