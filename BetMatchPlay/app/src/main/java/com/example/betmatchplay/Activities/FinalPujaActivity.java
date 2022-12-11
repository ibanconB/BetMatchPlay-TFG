package com.example.betmatchplay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betmatchplay.Adapters.PujasAdapter;
import com.example.betmatchplay.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FinalPujaActivity extends AppCompatActivity {

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String fecha;


    PujasAdapter adapter;
    //Firebase
    FirebaseFirestore db;
    //EditText
    public TextView desc,usuario;
    //Button
    public Button btnPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_puja);
        db=FirebaseFirestore.getInstance();
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyy");

        fecha=(String)simpleDateFormat.format(calendar.getTime());

        desc=findViewById(R.id.textoPujaDescripcion);
        usuario=findViewById(R.id.etUsuarioGanador);

        desc.setText(PujaActivity.descripcion);
        usuario.setText(LoginActivity.correoUsuario);

        btnPagar=findViewById(R.id.btnPagar);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagar();
                btnPagar.setEnabled(false);
            }
        });

    }

    public void pagar() {
        db.collection("Historial").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Map<String, Object> mapa=new HashMap<>();
                        mapa.put("nombrePuja",PujaActivity.nombrePuja);
                        mapa.put("precioComprado",PujaActivity.dineroHistorial+"€");
                        mapa.put("correoUsuario",LoginActivity.correoUsuario);
                        mapa.put("fechaHora",fecha);
                        db.collection("Historial").document().set(mapa);
                    }
                });
        Toast.makeText(this, "Pago Efectuado.. Redireccionado al home", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(FinalPujaActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}