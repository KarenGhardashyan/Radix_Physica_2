package com.example.radix_physica.Manu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;


import com.example.radix_physica.AddQuizAndQuestion.AddQuizActivity;
import com.example.radix_physica.AddQuizAndQuestion.AddTopicsActivity;
import com.example.radix_physica.Games.LeaderboardActivity;
import com.example.radix_physica.Topics.ElectricityActivity;
import com.example.radix_physica.Games.GenerateQuizActivity;
import com.example.radix_physica.Topics.Mexanics_classes;
import com.example.radix_physica.Topics.OpticsActivity;
import com.example.radix_physica.R;
import com.example.radix_physica.AddQuizAndQuestion.ShowQuestionsActivity;
import com.example.radix_physica.Topics.ThermodynamixActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PysicsLobbyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pysics_lobby);


        Button mechanicsButton = findViewById(R.id.mechanics);
        Button thermodynamix = findViewById(R.id.thermodynamics);
        Button optics = findViewById(R.id.optics);
        Button electrocity = findViewById(R.id.electricity);
        Button question = findViewById(R.id.questions);
        Button leaders = findViewById(R.id.leaderboards);
        Button topics = findViewById(R.id.addTopic);

//        Button userstoppics = findViewById(R.id.other);

//        userstoppics.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), UsersAddedTopicsActivity.class);
//                Pair<View, String> pair = new Pair<>(userstoppics, "usersadded");
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PysicsLobbyActivity.this, pair);
//                startActivity(intent, options.toBundle());
//            }
//        });

        topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTopicsActivity.class);
                Pair<View, String> pair = new Pair<>(topics, "addropic");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PysicsLobbyActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Button quiztest = findViewById(R.id.Quiztest);

        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        bottomNavigationView.getMenu().findItem(R.id.home).setCheckable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(0, 0);
            }else if (item.getItemId() == R.id.exercises) {
                startActivity(new Intent(getApplicationContext(), AddQuizActivity.class));
                overridePendingTransition(0, 0);
            }
            return true;
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowQuestionsActivity.class);
                Pair<View, String> pair = new Pair<>(question, "showquestions");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PysicsLobbyActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        });
        quiztest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GenerateQuizActivity.class);
                Pair<View, String> pair = new Pair<>(quiztest, "quiz");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PysicsLobbyActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        });


        thermodynamix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThermodynamixActivity.class);
                Pair<View, String> pair = new Pair<>(thermodynamix, "thermodynamics");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PysicsLobbyActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        });

        optics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OpticsActivity.class);
                Pair<View, String> pair = new Pair<>(optics, "optics");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PysicsLobbyActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        });
        electrocity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ElectricityActivity.class);
                Pair<View, String> pair = new Pair<>(electrocity, "electricity");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PysicsLobbyActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        });
        mechanicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mexanics_classes.class);
                Pair<View, String> pair = new Pair<>(mechanicsButton, "mexanics");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PysicsLobbyActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        });

        leaders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaderboardActivity.class);
                Pair<View, String> pair = new Pair<>(leaders, "leaders");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PysicsLobbyActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
