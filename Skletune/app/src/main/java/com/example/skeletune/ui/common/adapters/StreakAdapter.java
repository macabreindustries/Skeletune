package com.example.skeletune.ui.common.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skeletune.data.model.DayStatus;
import com.example.skletune.R;
import java.util.List;

public class StreakAdapter extends RecyclerView.Adapter<StreakAdapter.ViewHolder> {

    private final List<DayStatus> days;

    public StreakAdapter(List<DayStatus> days) {
        this.days = days;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el diseño de la "bolita" del día que creaste
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayStatus day = days.get(position);
        holder.tvDay.setText(day.getName());

        if (day.isCompleted()) {
            // Si el día está completado (Verde o Naranja)
            holder.ivStatus.setImageResource(R.drawable.ic_streak_active);
            holder.tvDay.setTextColor(Color.parseColor("#FF9800"));
        } else {
            // Si el día está pendiente (Gris)
            holder.ivStatus.setImageResource(R.drawable.ic_circle_empty);
            holder.tvDay.setTextColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    // ============================================================
    // AQUÍ ESTÁ EL VIEWHOLDER QUE FALTABA
    // ============================================================
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;
        ImageView ivStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Vinculamos las variables con los IDs de item_day_status.xml
            tvDay = itemView.findViewById(R.id.tv_day_initial);
            ivStatus = itemView.findViewById(R.id.iv_status_icon);
        }
    }
}