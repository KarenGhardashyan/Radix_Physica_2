package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
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
        ImageButton thermodynamix = findViewById(R.id.thermodynamics);
        ImageButton optics = findViewById(R.id.optics);
        ImageButton electrocity = findViewById(R.id.electricity);
        Button question = findViewById(R.id.questions);
        Button game = findViewById(R.id.game1);
        Button game2 = findViewById(R.id.game2);
        Button startTest = findViewById(R.id.startTest);
        Button finishTest = findViewById(R.id.finishingtest);
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        bottomNavigationView.getMenu().findItem(R.id.home).setCheckable(false);




        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(physics_lobby.this, Profile.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.settings) {
                startActivity(new Intent(physics_lobby.this, Settings.class));
                overridePendingTransition(0, 0);
            }else if (item.getItemId() == R.id.exercises) {
                startActivity(new Intent(physics_lobby.this, AddQuestionActivity.class));
                overridePendingTransition(0, 0);
            }
            return true;
        });

        mechanicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, Mexanics_classes.class);
                Pair<View, String> pair = new Pair<>(mechanicsButton, "mexanics_button");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(physics_lobby.this, pair);
                startActivity(intent, options.toBundle());
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, GameActivity.class);
                Pair<View, String> pair = new Pair<>(game, "build_the_town");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(physics_lobby.this, pair);
                startActivity(intent, options.toBundle());
            }
        });
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, StartTestActivity.class);
                startActivity(intent);
            }
        });
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, ShowQuestionsActivity.class);
                startActivity(intent);
            }
        });
        finishTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, ShowQuestionsActivity.class);
                startActivity(intent);
            }
        });

        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, BuildBridgeActivity.class);
                Pair<View, String> pair = new Pair<>(game2, "build_bridge_transition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(physics_lobby.this, pair);
                startActivity(intent, options.toBundle());
            }
        });

        thermodynamix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, ThermodynamixActivity.class);
                Pair<View, String> pair = new Pair<>(thermodynamix, "thermodynamics");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(physics_lobby.this, pair);
                startActivity(intent, options.toBundle());
            }
        });

        optics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, OpticsActivity.class);
                Pair<View, String> pair = new Pair<>(optics, "optics");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(physics_lobby.this, pair);
                startActivity(intent, options.toBundle());
            }
        });
        electrocity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, ElectricityActivity.class);
                Pair<View, String> pair = new Pair<>(electrocity, "electricity");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(physics_lobby.this, pair);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
