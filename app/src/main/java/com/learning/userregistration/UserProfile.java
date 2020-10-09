package com.learning.userregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    EditText userName,userEmail,verify;
    ImageView profilePic;
    Boolean verified;
    String isVerified, notVerified;
    Button updateProfile,verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = findViewById(R.id.name);
        userEmail = findViewById(R.id.email);
        verify = findViewById(R.id.verify);
        updateProfile = findViewById(R.id.updateBtn);
        profilePic = findViewById(R.id.imgrVW);
        verifyBtn = findViewById(R.id.verifyBtn);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        userName.setText(mUser.getDisplayName());
        userEmail.setText(mUser.getEmail());
        verified = mUser.isEmailVerified();

        isVerified = "Email is verified";
        notVerified = "Email is not verified,Please try again";

        if (verified){
            verify.setText(isVerified);
        }else {
            verifyBtn.setVisibility(View.VISIBLE);
            verify.setText(notVerified);
        }

        Uri photoUri = mUser.getPhotoUrl();
        Picasso.with(UserProfile.this).load(photoUri).into(profilePic);

        Log.e("PHOTO", "onCreate: "+ photoUri);


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(userName.getText().toString())
                        .setPhotoUri(Uri.parse("https://images.pexels.com/photos/1619493/pexels-photo-1619493.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"))
                        .build();

                mUser.updateProfile(changeRequest)
                        .addOnCompleteListener(UserProfile.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(UserProfile.this, "Updated Succesfully", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(UserProfile.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    public void Verify(View view) {
        mUser.sendEmailVerification().addOnCompleteListener(UserProfile.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UserProfile.this, "Verficatopn done,Check your email", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void Database(View view) {
        startActivity(new Intent(UserProfile.this,DatabaseActivity.class));
    }
}