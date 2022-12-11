package com.example.betmatchplay.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betmatchplay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Textview
    TextView noTengoCuenta,olvidarPassword;


    //EditText
    EditText email,password;

    //Botones
    Button btnLogin;

    //Strings
    String correo,contrasenia;

    //Firebase
    FirebaseAuth firebaseAuth;

    //Static
    public static String correoUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        noTengoCuenta=findViewById(R.id.tvRegistrarse);
        olvidarPassword=findViewById(R.id.olvidarcontrasenia);
        olvidarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,OlvidarPasswordActivity.class));
            }
        });
        email=findViewById(R.id.etEmail);
        password=findViewById(R.id.etPassLogin);
        noTengoCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });



        btnLogin=findViewById(R.id.btnIniciarSesion);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IniciarSesion();
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();

    }


    private void IniciarSesion(){
        correo=email.getText().toString().trim();
        contrasenia=password.getText().toString().trim();

        if (correo.isEmpty()){
            email.setError("Campo requerido");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            email.setError("Por favor entra un email valido");
            email.requestFocus();
            return;
        }

        if (contrasenia.isEmpty()){
            password.setError("Campo requerido");
            password.requestFocus();
            return;
        }
        if (contrasenia.length()<6){
            password.setError("La contraseña debe ser mayor a 6 caracteres");
        }

        firebaseAuth.signInWithEmailAndPassword(correo,contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //redirecciona al main
                    correoUsuario=email.getText().toString();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }else {
                    Toast.makeText(LoginActivity.this, "¡Error al acceder! Chequea tus credenciales", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}