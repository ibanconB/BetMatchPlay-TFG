package com.example.betmatchplay.Activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betmatchplay.Adapters.CategoriasAdapter;
import com.example.betmatchplay.Adapters.PujasAdapter;
import com.example.betmatchplay.Models.Categoria;
import com.example.betmatchplay.Models.Puja;
import com.example.betmatchplay.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity  {

    //Button
    FloatingActionButton floatingActionButton;
    //RecyclerView Categorias
    private RecyclerView catRecyclerView;
    CategoriasAdapter catAdapter;
    private ArrayList<Categoria>categoriaList;
    //RecyclerView Pujas
    private RecyclerView pujaRecyclerView;
    PujasAdapter pujasAdapter;
    private ArrayList<Puja>pujaList;
    //Firebase
    private FirebaseFirestore db;
    ProgressDialog progressDialog;
    //Hashmap
    public static HashMap<Integer,Categoria>mapaCat=new HashMap<>();
    public static HashMap<Integer,Puja>mapaPuja=new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton=findViewById(R.id.btnHistorial);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, historialActivity.class);
                startActivity(i);
            }
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Pillando datos");
        progressDialog.show();

        catRecyclerView=findViewById(R.id.RVCategorias);
        catRecyclerView.setHasFixedSize(true);

        pujaRecyclerView=findViewById(R.id.RVPujas);
        pujaRecyclerView.setHasFixedSize(true);
        pujaRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        db=FirebaseFirestore.getInstance();
        categoriaList=new ArrayList<Categoria>();
        pujaList=new ArrayList<Puja>();

        catAdapter=new CategoriasAdapter(MainActivity.this,categoriaList);
        pujasAdapter=new PujasAdapter(MainActivity.this,pujaList);

        catRecyclerView.setAdapter(catAdapter);
        pujaRecyclerView.setAdapter(pujasAdapter);


        EventChangeListenerCategoria();
        for (int i = 0; i <categoriaList.size() ; i++) {
            mapaCat.put(i,categoriaList.get(i));
        }

        EventChangeListenerPujas();
        for (int i = 0; i <pujaList.size() ; i++) {
            mapaPuja.put(i,pujaList.get(i));
        }
    }

    private void EventChangeListenerCategoria(){
        db.collection("Categorias").orderBy("nombreCategoria", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error !=null){
                            Log.d("Entro", "onEvent: no hay error ");
                            if (progressDialog.isShowing()){
                                Log.d("Entro 2", "onEvent: no hay error 2 ");
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc:value.getDocumentChanges()) {
                            if (dc.getType()==DocumentChange.Type.ADDED){
                                categoriaList.add(dc.getDocument().toObject(Categoria.class));
                            }
                            catAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }

    private void EventChangeListenerPujas(){
        db.collection("Pujas").orderBy("titulo", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error !=null){
                            Log.d("Entro", "onEvent: no hay error ");
                            if (progressDialog.isShowing()){
                                Log.d("Entro 2", "onEvent: no hay error 2 ");
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc:value.getDocumentChanges()) {
                            if (dc.getType()==DocumentChange.Type.ADDED){
                                CategoriasAdapter categoAdapter=new CategoriasAdapter();
                                if (categoAdapter.pinchado){
                                    if (dc.getDocument().getString("categoria").equals(categoAdapter.cat)){
                                        pujaList.add(dc.getDocument().toObject(Puja.class));
                                    }
                                } else {
                                    pujaList.add(dc.getDocument().toObject(Puja.class));
                                }

                            }
                            catAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }




}




