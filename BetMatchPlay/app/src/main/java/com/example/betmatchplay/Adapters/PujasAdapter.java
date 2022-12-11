package com.example.betmatchplay.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betmatchplay.Activities.PujaActivity;
import com.example.betmatchplay.Models.Puja;
import com.example.betmatchplay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PujasAdapter extends RecyclerView.Adapter<PujasAdapter.PujaViewHolder> {

    public static String nombrePuja;
    public Context context;
    public ArrayList<Puja> list;

    public PujasAdapter(Context context, ArrayList<Puja> list) {
        this.context = context;
        this.list = list;
    }

    public PujasAdapter() {
    }

    @NonNull
    @Override
    public PujasAdapter.PujaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.pujas_rv_item,parent,false);
       return new PujaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PujasAdapter.PujaViewHolder holder, int position) {
        Puja puja=list.get(position);
        holder.titulo.setText(puja.titulo);
        holder.subtitulo.setText(puja.subtitulo);
        Picasso.get()
                .load(puja.imageURL)
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PujaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titulo,subtitulo;
        ImageView imagen;
        public PujaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=itemView.findViewById(R.id.tituloPujas);
            subtitulo=itemView.findViewById(R.id.subtituloPujas);
            imagen=itemView.findViewById(R.id.idIVPujas);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            nombrePuja=titulo.getText().toString();
            Toast.makeText(context, "position: "+position, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context, PujaActivity.class);
            intent.putExtra("nombre",list.get(position).getTitulo());
            context.startActivity(intent);
        }
    }
}
