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

import com.example.betmatchplay.Firebase.UsuarioFB;
import com.example.betmatchplay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //EditText
    private EditText nombre,mail,password,repPassword;
    //Textview
    private TextView yaTengoCuenta;
    //Button
    private Button btnRegistrar;
    //Strings
    private String name,email,pass,passwordre;
    //Firebase
    private FirebaseAuth firebaseAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth=FirebaseAuth.getInstance();


        nombre=findViewById(R.id.etPassLogin);
        mail=findViewById(R.id.etEmail);
        password=findViewById(R.id.etPassword);
        repPassword=findViewById(R.id.etRepPassword);

        btnRegistrar=findViewById(R.id.btnRegistro);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    registrarUsuario();
            }
        });





        yaTengoCuenta=findViewById(R.id.tvTengoCuenta);
        yaTengoCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    private void registrarUsuario(){
        name=nombre.getText().toString();
        email=mail.getText().toString();
        pass=password.getText().toString();
        passwordre=repPassword.getText().toString();

        if (name.isEmpty()){
            nombre.setError("Campo requerido");
            nombre.requestFocus();
            return;
        }
        if (email.isEmpty()){
            mail.setError("Campo requerido");
            mail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail.setError("Introduce un email valido");
            mail.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            password.setError("Campo requerido");
            password.requestFocus();
            return;
        }
        if (pass.length()<6){
            password.setError("La contraseña debe ser mayor a 6 caracteres");
            password.requestFocus();
            return;
        }
        if (passwordre.isEmpty()){
            repPassword.setError("Campo requerido");
            repPassword.requestFocus();
            return;

        }else if(!passwordre.equals(pass)){
            repPassword.setError("Las contraseñas no coinciden");
            repPassword.requestFocus();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UsuarioFB usuario=new UsuarioFB(name,email,pass);

                           FirebaseDatabase.getInstance().getReference("Usuarios")
                                   .child(FirebaseAuth.getInstance().getUid())
                                   .setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(RegisterActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                                       startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                       finish();
                                   }else{
                                       Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
                        }
                    }
                });


    }





}