package com.example.radix_physica.RegAndLog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.radix_physica.Manu.PysicsLobbyActivity;
import com.example.radix_physica.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button without_account, registration, login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        registration = findViewById(R.id.Registration_act);
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
    }
}