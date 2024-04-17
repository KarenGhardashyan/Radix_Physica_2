package com.example.radix_physica.Manu;

import static android.graphics.Color.WHITE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.radix_physica.AddQuizAndQuestion.AddQuizActivity;
import com.example.radix_physica.AddQuizAndQuestion.ModerateQuizActivity;
import com.example.radix_physica.AddQuizAndQuestion.Moderator;
import com.example.radix_physica.R;
import com.example.radix_physica.RegAndLog.LoginActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        logOutButton = findViewById(R.id.LogOut);
        textView = findViewById(R.id.name);
        progressBarQuiz = findViewById(R.id.progressBarQuiz);
        textViewProgress = findViewById(R.id.textView3);
        Button settingsPage = findViewById(R.id.settings);
        Button moderatorsPage = findViewById(R.id.moderatorsPage);


        LineChart chart = findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 30));
        entries.add(new Entry(1, 50));
        entries.add(new Entry(2, 60));
        entries.add(new Entry(3, 20));
        entries.add(new Entry(4, 80));

        LineDataSet dataSet = new LineDataSet(entries, "Время использования по дням");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setCircleColor(Color.RED);
        dataSet.setCircleRadius(5);
        dataSet.setLineWidth(2);
        dataSet.setValueTextSize(12);

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setTextSize(12);

        Description description = new Description();
        description.setText("График времени использования по дням");
        description.setTextSize(14);
        chart.setDescription(description);

        Legend legend = chart.getLegend();
        legend.setTextSize(12);

        chart.setDragEnabled(true);

        chart.setScaleEnabled(true);
        chart.setDrawBorders(true);

        chart.setPinchZoom(true);

        chart.getAxisLeft().setDrawGridLines(true);
        chart.getAxisLeft().setGridColor(Color.parseColor("#00C853"));
        chart.getLegend().setTextColor(Color.parseColor("#FF0000"));

        settingsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserSettingsActivity.class));
            }
        });

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Moderators");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Moderator moderator = snapshot.getValue(Moderator.class);
                        if (moderator != null && moderator.getEmail().equals(user.getEmail())) {

                            moderatorsPage.setVisibility(View.VISIBLE);
                            moderatorsPage.setEnabled(true);

                            moderatorsPage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getApplicationContext(), ModerateQuizActivity.class));
                                }
                            });
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        textView.setText(user.getDisplayName());

        SharedPreferences sharedPreferences = getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HighScore", 0);
        textViewProgress.setText("Опросы " + highScore);
        progressBarQuiz.setProgress(highScore);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);

        bottomNavigationView.getMenu().findItem(R.id.profile).setCheckable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), PysicsLobbyActivity.class));
                overridePendingTransition(0, 0);
            }  else if (item.getItemId() == R.id.exercises) {
                startActivity(new Intent(getApplicationContext(), AddQuizActivity.class));
                overridePendingTransition(0, 0);
            }
            return true;
        });

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