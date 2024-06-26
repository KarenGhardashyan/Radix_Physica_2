package com.example.radix_physica.RegAndLog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.radix_physica.Manu.PysicsLobbyActivity;
import com.example.radix_physica.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button guestMode,moderatorMode, registration, login;
    private FirebaseAuth mAuth;
    private static final String GUEST_EMAIL = "sictst1@gmail.com";
    private static final String MODERATOR_EMAIL = "sictst4@gmail.com";
    private static final String GUEST_PASSWORD = "Samsung2023";
    private static final String MODERATOR_PASSWORD = "Samsung2023";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        registration = findViewById(R.id.Registration_act);
        guestMode = findViewById(R.id.GuestMode);
        moderatorMode = findViewById(R.id.ModeratorMode);
        login = findViewById(R.id.login);

//        FirebaseUser user = mAuth.getCurrentUser();
//
//        if (user != null && user.isEmailVerified()) {
//            startActivity(new Intent(MainActivity.this, PysicsLobbyActivity.class));
//            finish();
//            return;
//        } else {
//            Toast.makeText(MainActivity.this, "Зарегистрируйтесь", Toast.LENGTH_SHORT).show();
//        }

        guestMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAsGuest();
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        moderatorMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAsModerator();
            }
        });
    }

    private void signInAsGuest() {
        mAuth.signInWithEmailAndPassword(GUEST_EMAIL, GUEST_PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Вы зашли как гость.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, PysicsLobbyActivity.class));
                            finish();
                        } else {
                            Log.w("MainActivity", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void signInAsModerator() {
        mAuth.signInWithEmailAndPassword(MODERATOR_EMAIL, MODERATOR_PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Вы зашли как модератор.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, PysicsLobbyActivity.class));
                            finish();
                        } else {
                            Log.w("MainActivity", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}