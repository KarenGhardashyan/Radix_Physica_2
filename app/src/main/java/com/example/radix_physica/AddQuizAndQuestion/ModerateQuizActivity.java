package com.example.radix_physica.AddQuizAndQuestion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radix_physica.Manu.ModeratorsActivity;
import com.example.radix_physica.Manu.Profile;
import com.example.radix_physica.Manu.Settings;
import com.example.radix_physica.Manu.PysicsLobbyActivity;
import com.example.radix_physica.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ModerateQuizActivity extends AppCompatActivity {

    private Button to, approve, reject;
    private TextView textViewQuestion, textViewAnswer1, textViewAnswer2, textViewAnswer3, textViewAnswer4, textViewtrueAnswer;
    private DatabaseReference databaseReference, moderatedQuizRef;
    private DataSnapshot questionSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderate_quiz);

        to = findViewById(R.id.buttonToModerQuestion);
        approve = findViewById(R.id.buttonApprove);
        reject = findViewById(R.id.buttonReject);
        textViewQuestion = findViewById(R.id.textViewQuestionContent);
        textViewAnswer1 = findViewById(R.id.textViewAnswer1);
        textViewAnswer2 = findViewById(R.id.textViewAnswer2);
        textViewAnswer3 = findViewById(R.id.textViewAnswer3);
        textViewAnswer4 = findViewById(R.id.textViewAnswer4);
        textViewtrueAnswer = findViewById(R.id.textViewTrueAnswer);

        databaseReference = FirebaseDatabase.getInstance().getReference("AddQuiz");
        moderatedQuizRef = FirebaseDatabase.getInstance().getReference("moderatedQuiz");

        ImageButton back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModeratorsActivity.class);
                startActivity(intent);
            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = textViewQuestion.getText().toString();
                String answer1 = textViewAnswer1.getText().toString();
                String answer2 = textViewAnswer2.getText().toString();
                String answer3 = textViewAnswer3.getText().toString();
                String answer4 = textViewAnswer4.getText().toString();
                String trueAnswer = textViewtrueAnswer.getText().toString();

                Quiz moderatedQuiz = new Quiz(question, answer1, answer2, answer3, answer4, trueAnswer);

                moderatedQuizRef.push().setValue(moderatedQuiz)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(ModerateQuizActivity.this, "Вопрос одобрен и добавлен в базу", Toast.LENGTH_SHORT).show();

                                destroyActorFromNotModeratedQuestions();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(ModerateQuizActivity.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destroyActorFromNotModeratedQuestions();
            }
        });
    }

    private void getRandomQuestionFromDatabase() {
        DatabaseReference questionsRef = FirebaseDatabase.getInstance().getReference().child("AddQuiz");

        questionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long numberOfQuestions = dataSnapshot.getChildrenCount();

                    Random random = new Random();
                    long randomIndex = random.nextLong() % numberOfQuestions;

                    int currentIndex = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if (currentIndex == randomIndex) {

                            questionSnapshot = snapshot;
                            Quiz randomQuiz = snapshot.getValue(Quiz.class);
                            String questionContent = randomQuiz.getQuestion();
                            String answer1 = randomQuiz.getOption1();
                            String answer2 = randomQuiz.getOption2();
                            String answer3 = randomQuiz.getOption3();
                            String answer4 = randomQuiz.getOption4();
                            String trueAnswer = randomQuiz.getCorrectAnswer();

                            textViewQuestion.setText(questionContent);
                            textViewAnswer1.setText(answer1);
                            textViewAnswer2.setText(answer2);
                            textViewAnswer3.setText(answer3);
                            textViewAnswer4.setText(answer4);
                            textViewtrueAnswer.setText(trueAnswer);

                            break;
                        }
                        currentIndex++;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Список вопросов пуст", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Ошибка загрузки данных: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void destroyActorFromNotModeratedQuestions() {
        if (questionSnapshot != null) {
            questionSnapshot.getRef().removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModerateQuizActivity.this, "Ошибка при удалении вопроса: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(ModerateQuizActivity.this, "Ошибка: questionSnapshot является null", Toast.LENGTH_SHORT).show();
        }
    }

}
