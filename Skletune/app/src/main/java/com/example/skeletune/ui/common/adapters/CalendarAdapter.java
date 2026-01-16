package com.example.skeletune.ui.common.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.skletune.R;

import java.util.List;

public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private List<Boolean> dias;

    public CalendarAdapter(Context context, List<Boolean> dias) {
        this.context = context;
        this.dias = dias;
    }

    @Override
    public int getCount() { return dias.size(); }
    @Override
    public Object getItem(int i) { return dias.get(i); }
    @Override
    public long getItemId(int i) { return i; }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = new View(context);
            // Tamaño del cuadrito
            view.setLayoutParams(new GridView.LayoutParams(40, 40));
        }

        // Lógica de colores: Verde si hubo actividad, Gris/Rojo si no
        if (dias.get(i)) {
            view.setBackgroundResource(R.drawable.square_active); // Crea un XML verde
        } else {
            view.setBackgroundResource(R.drawable.square_inactive); // Crea un XML gris o rojo
        }

        return view;
    }
}