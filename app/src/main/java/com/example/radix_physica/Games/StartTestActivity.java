package com.example.radix_physica.Games;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.radix_physica.AddQuizAndQuestion.QuestionModel;
import com.example.radix_physica.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartTestActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private Button submitButton;
    private TextView scoreTextView;
    private List<QuestionModel> selectedQuestions;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        databaseReference = FirebaseDatabase.getInstance().getReference("moderated_questions");
        submitButton = findViewById(R.id.submitButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        selectedQuestions = new ArrayList<>();

        readDataFromFirebase();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedQuestions != null && !selectedQuestions.isEmpty()) {
                    checkAnswers();
                    scoreTextView.setText("Очки: " + score);
                } else {
                    Toast.makeText(StartTestActivity.this, "ошибка загрузки:", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkAnswers() {
        for (QuestionModel question : selectedQuestions) {
            String correctAnswer = question.getAnswer();
            String userAnswer = getUserAnswerForQuestion(question);

            if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                score++;
            }
        }
    }

    private void readDataFromFirebase() {
        final List<QuestionModel> allQuestions = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allQuestions.clear();
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    QuestionModel question = questionSnapshot.getValue(QuestionModel.class);
                    if (question != null) {
                        allQuestions.add(question);
                    }
                }
                startTest(allQuestions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void startTest(List<QuestionModel> allQuestions) {
        Collections.shuffle(allQuestions);

        selectedQuestions.clear();

        selectedQuestions.addAll(allQuestions.subList(0, Math.min(8, allQuestions.size())));
        displayQuestions(selectedQuestions);
    }

    private void displayQuestions(List<QuestionModel> questions) {
    }

    private String getUserAnswerForQuestion(QuestionModel question) {
        EditText editText = null;
        return editText != null ? editText.getText().toString() : null;
    }
}
