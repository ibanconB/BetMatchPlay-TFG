package com.example.betmatchplay.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.betmatchplay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class OlvidarPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button resetearPass;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_password);

        etEmail=findViewById(R.id.etEmailOP);
        resetearPass=findViewById(R.id.btnOlvidarPass);

        firebaseAuth=FirebaseAuth.getInstance();

        resetearPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetearContrasenia();
            }
        });
    }

    private void resetearContrasenia(){
        String email=etEmail.getText().toString().trim();


        if (email.isEmpty()){
            etEmail.setError("Campo requerido");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Introduce un email valido");
            etEmail.requestFocus();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(OlvidarPasswordActivity.this, "Comprueba tu email para resetar tu contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(OlvidarPasswordActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}