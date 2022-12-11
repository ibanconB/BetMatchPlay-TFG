package com.example.betmatchplay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betmatchplay.Adapters.PujasAdapter;
import com.example.betmatchplay.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PujaActivity extends AppCompatActivity {

    PujasAdapter adapter;
    //Firebase
    FirebaseFirestore db;
    //LayoutItems
    private TextView holdertitulo,holderSubtitulo,holderPrecio;
    private EditText holderDineroPuja;
    private ImageView holderImagen;
    private Button btnPujar;

    //Statics
    public static String descripcion;
    public static String dineroHistorial;
    public static String nombrePuja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puja);
        adapter=new PujasAdapter();
        db=FirebaseFirestore.getInstance();

        holdertitulo=findViewById(R.id.holderTituloPujas);
        holderDineroPuja=findViewById(R.id.etPujar);
        holderSubtitulo=findViewById(R.id.holderSubtituloPujas);
        holderPrecio=findViewById(R.id.holderPrecio);
        holderImagen=findViewById(R.id.holderImageView);
        btnPujar=findViewById(R.id.btnPujar);
        btnPujar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pujar();
                holderDineroPuja.setText("");
            }
        });


        meterDatos(adapter.nombrePuja);
        comprobar();
    }

    public void meterDatos(String titulo){
        db.collection("Pujas").document(titulo).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                             holdertitulo.setText(documentSnapshot.getString("titulo"));
                             holderSubtitulo.setText(documentSnapshot.getString("subtitulo"));
                             holderPrecio.setText(documentSnapshot.getString("Precio")+"€");
                        Picasso.get()
                                .load(documentSnapshot.getString("imageURL"))
                                .into(holderImagen);
                    }
                });
    }
    public void pujar(){
        String dinero=holderDineroPuja.getText().toString();


        if (dinero.isEmpty()){
            holderDineroPuja.setError("Por favor introduce tu puja");
            return;
        }else {
            int puja=Integer.parseInt(dinero);
            db.collection("Pujas").document(adapter.nombrePuja).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            int num =Integer.parseInt(documentSnapshot.getString("Precio"));
                            if (puja>0){
                                int res=puja+num;
                                Map<String, Object> mapa=new HashMap<>();
                                mapa.put("Precio",""+res);
                                mapa.put("titulo",documentSnapshot.getString("titulo"));
                                mapa.put("subtitulo",documentSnapshot.getString("subtitulo"));
                                mapa.put("imageURL",documentSnapshot.getString("imageURL"));
                                mapa.put("categoria",documentSnapshot.getString("categoria"));
                                mapa.put("limite",documentSnapshot.getString("limite"));
                                db.collection("Pujas").document(adapter.nombrePuja).set(mapa);
                                holderPrecio.setText(""+res+"€");

                                int limite =Integer.parseInt(documentSnapshot.getString("limite"));
                                if (res>=limite){

                                    descripcion=documentSnapshot.getString("subtitulo");
                                    dineroHistorial=""+res;
                                    nombrePuja=documentSnapshot.getString("titulo");
                                    Intent i=new Intent(PujaActivity.this,FinalPujaActivity.class);
                                    startActivity(i);
                                    finish();
                                }

                            }else{
                                Toast.makeText(PujaActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }


    public void comprobar(){
        db.collection("Pujas").document(adapter.nombrePuja).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        int num = Integer.parseInt(documentSnapshot.getString("Precio"));
                        int limite =Integer.parseInt(documentSnapshot.getString("limite"));
                        if (num>=limite){
                            btnPujar.setEnabled(false);
                            holderPrecio.setText("LA PUJA HA ACABADO");
                        }
                    }
                });
    }

}