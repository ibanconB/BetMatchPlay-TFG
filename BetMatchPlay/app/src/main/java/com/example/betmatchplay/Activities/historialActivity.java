package com.example.betmatchplay.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.betmatchplay.Adapters.CategoriasAdapter;
import com.example.betmatchplay.Adapters.HistorialAdapter;
import com.example.betmatchplay.Models.Historial;
import com.example.betmatchplay.Models.Puja;
import com.example.betmatchplay.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class historialActivity extends AppCompatActivity {


    //RecyclerView Pujas
    private RecyclerView historialRecycler;
    HistorialAdapter historialAdapter;
    private ArrayList<Historial>historialList;

    //Firebase
    public FirebaseFirestore db;
    ProgressDialog progressDialog;
    //Hashmap
    public static HashMap<Integer,Historial> mapaHistorial=new HashMap<>();
    //TextView
    public TextView usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Pillando datos");
        progressDialog.show();

        usuario=findViewById(R.id.tvUsuario);
        usuario.setText("HISTORIAL DE "+LoginActivity.correoUsuario);

        historialRecycler=findViewById(R.id.RVhistorial);
        historialRecycler.setHasFixedSize(true);
        historialRecycler.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        historialList=new ArrayList<Historial>();

        historialAdapter=new HistorialAdapter(historialActivity.this,historialList);
        historialRecycler.setAdapter(historialAdapter);


        mostrarHistorial();
        for (int i = 0; i <historialList.size() ; i++) {
            mapaHistorial.put(i,historialList.get(i));
        }

    }

    public void mostrarHistorial(){
        db.collection("Historial").orderBy("correoUsuario", Query.Direction.ASCENDING)
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
                                    if (dc.getDocument().getString("correoUsuario").equals(LoginActivity.correoUsuario)){
                                        System.out.println("holaaaaaaaaa");
                                        historialList.add(dc.getDocument().toObject(Historial.class));
                                    }
                                }

                            }
                            historialAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }

                });

    }

}
