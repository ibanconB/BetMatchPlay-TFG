package com.example.betmatchplay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betmatchplay.Models.Historial;
import com.example.betmatchplay.R;

import java.util.ArrayList;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    public Context context;
    public ArrayList<Historial> list;

    public HistorialAdapter(Context context, ArrayList<Historial> list) {
        this.context = context;
        this.list = list;
    }

    public HistorialAdapter() {
    }

    @NonNull
    @Override
    public HistorialAdapter.HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.historial_rv_item,parent,false);
        return new HistorialViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialAdapter.HistorialViewHolder holder, int position) {
        Historial historial=list.get(position);
        holder.nombrePuja.setText(historial.nombrePuja);
        holder.precio.setText(historial.precioComprado);
        holder.fechaHora.setText(historial.fechaHora);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HistorialViewHolder extends RecyclerView.ViewHolder {
        TextView nombrePuja,precio,fechaHora;
        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            nombrePuja=itemView.findViewById(R.id.etNombrePujaHistorial);
            precio=itemView.findViewById(R.id.etPrecioHistorial);
            fechaHora=itemView.findViewById(R.id.etfechaHora);


        }
    }
}
