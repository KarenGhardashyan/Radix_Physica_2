package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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

        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);



        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.profile) {
                Intent profileIntent = new Intent(physics_lobby.this, Profile.class );
                startActivity(profileIntent);
            } else if (item.getItemId() == R.id.settings) {
                Intent settingsIntent = new Intent(physics_lobby.this, Profile.class );
                startActivity(settingsIntent);
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
    }
}
