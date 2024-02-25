package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class physics_lobby extends AppCompatActivity {

    ImageButton mechanicsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pysics_lobby);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        ImageButton mechanicsButton = findViewById(R.id.mechanics);

        Button game = findViewById(R.id.game1);
        Button startTest = findViewById(R.id.startTest);
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);



        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(physics_lobby.this, Profile.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.settings) {
                startActivity(new Intent(physics_lobby.this, Settings.class));
                overridePendingTransition(0, 0);
            }
            return true;
        });

        mechanicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, Mexanics_classes.class);
                startActivity(intent);
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, GameActivity.class);
                startActivity(intent);
            }
        });
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, StartTest.class);
                startActivity(intent);
            }
        });
    }
}
