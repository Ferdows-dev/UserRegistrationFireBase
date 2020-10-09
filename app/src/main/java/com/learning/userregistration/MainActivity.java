package com.learning.userregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
     private EditText emailET,passwordET;
     Button forgetPassword;
     FirebaseAuth mAuth;
     FirebaseUser mUser;
     String Email,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        forgetPassword = findViewById(R.id.fPassword);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

    }

    public void Signup(View view) {
         Email = emailET.getText().toString().trim();
         Password = passwordET.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Sign Up Succesfully done", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void LogIn(View view) {
        Email = emailET.getText().toString();
        Password = passwordET.getText().toString();



       if (mAuth!=null)
       {
           mAuth.signInWithEmailAndPassword(Email,Password)
                   .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               Intent intent = new Intent(MainActivity.this,UserProfile.class);
                               startActivity(intent);
                           }

                       }
                   }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(MainActivity.this, "Password incorrect", Toast.LENGTH_SHORT).show();

               }
           });
       }
    }

    public void forgetPassword(View view) {
        mAuth.sendPasswordResetEmail(Email)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}