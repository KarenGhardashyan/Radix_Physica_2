package com.example.radix_physica.Games;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.radix_physica.R;

public class LeaderboardActivity extends AppCompatActivity {
    private TextView highScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        highScoreTextView = findViewById(R.id.highScoreTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HighScore", 0);

        highScoreTextView.setText("High Score: " + highScore);
    }
}
