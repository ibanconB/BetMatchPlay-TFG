package com.example.betmatchplay.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betmatchplay.Activities.MainActivity;
import com.example.betmatchplay.Activities.PujaActivity;
import com.example.betmatchplay.Models.Categoria;
import com.example.betmatchplay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriaViewHolder> {

    public static String cat;
    public static boolean pinchado=false;
    public Context context;
    public ArrayList<Categoria> list;



    public CategoriasAdapter(Context context, ArrayList<Categoria> list) {
        this.context = context;
        this.list = list;

    }

    public CategoriasAdapter() {
    }

    @NonNull
    @Override
    public CategoriasAdapter.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.categorias_rv_item,parent,false);
        return new CategoriaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriasAdapter.CategoriaViewHolder holder, int position) {
        Categoria categoria=list.get(position);
        holder.nombreCat.setText(categoria.nombreCategoria);
        Picasso.get()
                .load(categoria.imageURL)
                .into(holder.imageURL);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombreCat;
        ImageView imageURL;
        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreCat=itemView.findViewById(R.id.TVCategorias);
            imageURL=itemView.findViewById(R.id.idIVCategorias);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
             cat=nombreCat.getText().toString();
             if (cat.equals("Todas")){
                 pinchado=false;
             }else {
                 pinchado=true;
             }
            Intent intent=new Intent(context, MainActivity.class);
            context.startActivity(intent);

        }
    }



}
