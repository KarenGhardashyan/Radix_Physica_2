package com.example.radix_physica.Manu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.radix_physica.AddQuizAndQuestion.AddQuestionActivity;
import com.example.radix_physica.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.settings).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(Settings.this, physics_lobby.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(Settings.this, Profile.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.exercises) {
                startActivity(new Intent(Settings.this, AddQuestionActivity.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.moderator) {
                startActivity(new Intent(Settings.this, ModeratorsActivity.class));
                overridePendingTransition(0, 0);
            }

            return true;
        });
    }
}