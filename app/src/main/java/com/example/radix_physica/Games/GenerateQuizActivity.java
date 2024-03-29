package com.example.radix_physica.Games;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.radix_physica.AddQuizAndQuestion.Quiz;
import com.example.radix_physica.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class GenerateQuizActivity extends AppCompatActivity {
    private TextView questionTextView, incorrectCounterTextView, scoreTextView;
    private Button option1Button, option2Button, option3Button, option4Button, nextButton;
    private DatabaseReference mDatabase;
    private ArrayList<Quiz> quizList;
    private int currentQuestionIndex = 0;
    private int incorrectAnswers = 0;
    private int score = 0;
    FirebaseUser user;
    FirebaseAuth auth;

    private SharedPreferences sharedPreferences;
    private int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_quiz);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        questionTextView = findViewById(R.id.questionTextView);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);
        incorrectCounterTextView = findViewById(R.id.incorrectCounterTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        nextButton = findViewById(R.id.nextButton);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("moderatedQuiz");

        quizList = new ArrayList<>();


        ImageButton back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadQuizData();

        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option1Button.getText().toString());
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option2Button.getText().toString());
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option3Button.getText().toString());
            }
        });

        option4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option4Button.getText().toString());
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizList.size() > 0) {
                    showNextQuestion();
                }
            }
        });

        sharedPreferences = getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE);
        highScore = sharedPreferences.getInt("HighScore", 0);
    }

    private void loadQuizData() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Quiz quiz = snapshot.getValue(Quiz.class);
                    quizList.add(quiz);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void showNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < quizList.size()) {
            Quiz quiz = quizList.get(currentQuestionIndex);
            questionTextView.setText(quiz.getQuestion());

            ArrayList<String> options = new ArrayList<>();
            options.add(quiz.getOption1());
            options.add(quiz.getOption2());
            options.add(quiz.getOption3());
            options.add(quiz.getOption4());
            Collections.shuffle(options);

            option1Button.setText(options.get(0));
            option2Button.setText(options.get(1));
            option3Button.setText(options.get(2));
            option4Button.setText(options.get(3));

            option1Button.setEnabled(true);
            option2Button.setEnabled(true);
            option3Button.setEnabled(true);
            option4Button.setEnabled(true);
        } else {
            questionTextView.setText("Больше нет вопросов ! Вы очень умны");
            option1Button.setVisibility(View.GONE);
            option2Button.setVisibility(View.GONE);
            option3Button.setVisibility(View.GONE);
            option4Button.setVisibility(View.GONE);
            nextButton.setEnabled(false);
        }
    }

    private void checkAnswer(String selectedOption) {
        Quiz currentQuiz = quizList.get(currentQuestionIndex);
        if (selectedOption.equals(currentQuiz.getCorrectAnswer())) {
            score++;
            scoreTextView.setText("очки: " + score);
        } else {
            incorrectAnswers++;
            incorrectCounterTextView.setText("Неправельные ответы: " + incorrectAnswers);

            if (incorrectAnswers >= 3) {
                questionTextView.setText("Ты выбрал 3 неверных ответов, Ты проиграл. Вот твои очки : " + score);
                option1Button.setEnabled(false);
                option2Button.setEnabled(false);
                option3Button.setEnabled(false);
                option4Button.setEnabled(false);
                nextButton.setEnabled(false);
            }
        }

        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);

        if (score > highScore) {
            highScore = score;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HighScore", highScore);
            editor.apply();

            String name = user.getDisplayName();
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();

                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                userRef.child("username").setValue(name);
                userRef.child("highScore").setValue(highScore);
            }
        }


    }
}