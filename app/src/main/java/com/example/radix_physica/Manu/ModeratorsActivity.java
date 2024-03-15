package com.example.radix_physica.Manu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.radix_physica.AddQuizAndQuestion.AddModeratorActivity;
import com.example.radix_physica.AddQuizAndQuestion.AddQuestionActivity;
import com.example.radix_physica.AddQuizAndQuestion.ModerateQuizActivity;
import com.example.radix_physica.AddQuizAndQuestion.QuestionModel;
import com.example.radix_physica.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ModeratorsActivity extends AppCompatActivity {

    Button addModerator, approve, reject, to;
    TextView  questionTextView, answerTextView, topicTextView;
    private DatabaseReference databaseReference;

    private DataSnapshot questionSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderators);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.moderator).setChecked(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("questions");

        to = findViewById(R.id.buttonToModerQuiz);
        questionTextView = findViewById(R.id.textViewQuestionContent);
        answerTextView = findViewById(R.id.textViewAnswer);
        approve = findViewById(R.id.buttonApprove);
        reject = findViewById(R.id.buttonReject);
        addModerator = findViewById(R.id.addMod);
        topicTextView = findViewById(R.id.textViewTopics);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userEmail = user.getEmail();
        DatabaseReference moderatorsRef = FirebaseDatabase.getInstance().getReference().child("Moderators");
        DatabaseReference moderatedQuestionsRef = FirebaseDatabase.getInstance().getReference().child("moderated_questions");

        getRandomQuestionFromDatabase();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), physics_lobby.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.settings) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(0, 0);
            }else if (item.getItemId() == R.id.exercises) {
                startActivity(new Intent(getApplicationContext(), AddQuestionActivity.class));
                overridePendingTransition(0, 0);
            }

            return true;
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModerateQuizActivity.class);
                startActivity(intent);
            }
        });

        if (user.getEmail().equalsIgnoreCase("karenkrakin@gmail.com")) {
            addModerator.setVisibility(View.VISIBLE);
            addModerator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AddModeratorActivity.class);
                    startActivity(intent);
                }
            });
        }

        moderatorsRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    approve.setVisibility(View.VISIBLE);
                    reject.setVisibility(View.VISIBLE);

                    approve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseReference newModeratedQuestionRef = moderatedQuestionsRef.push();

                            String question = questionTextView.getText().toString();
                            String answer = answerTextView.getText().toString();
                            String topic = topicTextView.getText().toString();

                            QuestionModel moderatedQuestion = new QuestionModel(question, answer, topic);
                            moderatedQuestion.setModerated(true);

                            newModeratedQuestionRef.setValue(moderatedQuestion)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(ModeratorsActivity.this, "Вопрос одобрен и добавлен в базу", Toast.LENGTH_SHORT).show();

                                            destrocActorFromNotModeratedQuestions();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(ModeratorsActivity.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            reject.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    destrocActorFromNotModeratedQuestions();
                                }
                            });
                        }
                    });
                } else {
                    answerTextView.setText("");
                    questionTextView.setText("");

                    approve.setVisibility(View.INVISIBLE);
                    reject.setVisibility(View.INVISIBLE);

                    Toast.makeText(ModeratorsActivity.this, "Вы не модератор!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ModeratorsActivity.this, "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRandomQuestionFromDatabase() {
        DatabaseReference questionsRef = FirebaseDatabase.getInstance().getReference().child("questions");

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
                            QuestionModel randomQuestion = snapshot.getValue(QuestionModel.class);
                            String questionContent = randomQuestion.getQuestion();
                            String answer = randomQuestion.getAnswer();
                            String topic = randomQuestion.getTopic();

                            questionTextView.setText(questionContent);
                            answerTextView.setText(answer);
                            topicTextView.setText(topic);

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

    public void destrocActorFromNotModeratedQuestions(){
        questionSnapshot.getRef().removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(ModeratorsActivity.this, "Вопрос удален из базы данных", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ModeratorsActivity.this, "Ошибка при удалении вопроса: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
