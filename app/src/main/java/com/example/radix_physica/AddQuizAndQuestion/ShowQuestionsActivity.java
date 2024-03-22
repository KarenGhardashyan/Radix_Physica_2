package com.example.radix_physica.AddQuizAndQuestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.radix_physica.Manu.Profile;
import com.example.radix_physica.Manu.Settings;
import com.example.radix_physica.Manu.PysicsLobbyActivity;
import com.example.radix_physica.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionsActivity extends AppCompatActivity {

    private ListView listViewQuestions;
    private DatabaseReference databaseReference;
    private List<String> questionList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_questions);

        databaseReference = FirebaseDatabase.getInstance().getReference("moderatedQuiz");

        listViewQuestions = findViewById(R.id.listViewQuestions);
        questionList = new ArrayList<>();
        TextView add = findViewById(R.id.ButtonText);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questionList);
        listViewQuestions.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.exercises).setChecked(true);
        bottomNavigationView.getMenu().findItem(R.id.exercises).setCheckable(false);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), PysicsLobbyActivity.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.settings) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(0, 0);
            }

            return true;
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Quiz quiz = snapshot.getValue(Quiz.class);
                    if (quiz != null) {
                        String fullQuestion = "\nВопрос: " + quiz.getQuestion() + "\nПравильный ответ: " + quiz.getCorrectAnswer();
                        questionList.add(fullQuestion);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShowQuestionsActivity.this, "Ошибка: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddQuiz.class);
                startActivity(intent);
            }
        });

    }
}
