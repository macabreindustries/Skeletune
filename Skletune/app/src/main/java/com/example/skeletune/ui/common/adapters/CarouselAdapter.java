package com.example.skeletune.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skletune.R;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private final List<Integer> imageList;
    private OnItemClickListener listener; // Variable para manejar el clic

    // --- INTERFAZ PARA EL CLIC ---
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Método para asignar el listener desde el Fragment (home.java)
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CarouselAdapter(List<Integer> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Asegúrate de que el layout se llame item_carrusel o item_carousel según tu proyecto
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrusel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        holder.imageView.setImageResource(imageList.get(position));

        // Configurar el evento de clic en el item completo
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            // Verifica que el ID del ImageView en item_carrusel sea imgCarousel
            imageView = itemView.findViewById(R.id.imgCarousel);
        }
    }
}