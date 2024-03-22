package com.example.radix_physica.Manu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radix_physica.AddQuizAndQuestion.AddQuiz;
import com.example.radix_physica.AddQuizAndQuestion.TopicModel;
import com.example.radix_physica.R;
import com.example.radix_physica.RegAndLog.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    FirebaseAuth auth;
    ImageButton logOutButton;
    TextView textView, textViewProgress;
    ProgressBar progressBarQuiz;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String userEmail = user.getEmail();
        DatabaseReference moderatorsRef = FirebaseDatabase.getInstance().getReference().child("Moderators");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        logOutButton = findViewById(R.id.LogOut);
        textView = findViewById(R.id.name);
        progressBarQuiz = findViewById(R.id.progressBarQuiz);
        textViewProgress = findViewById(R.id.textView3);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);

        SharedPreferences sharedPreferences = getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HighScore", 0);


        textViewProgress.setText("Опросы " + highScore);

        progressBarQuiz.setProgress(highScore );

        bottomNavigationView.getMenu().findItem(R.id.profile).setCheckable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), PysicsLobbyActivity.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.settings) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.exercises) {
                startActivity(new Intent(getApplicationContext(), AddQuiz.class));
                overridePendingTransition(0, 0);
            }
            return true;
        });


        if (user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            textView.setText(user.getDisplayName());

        }
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}