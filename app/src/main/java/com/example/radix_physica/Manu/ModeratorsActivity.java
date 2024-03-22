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
import com.example.radix_physica.AddQuizAndQuestion.AddTopicsActivity;
import com.example.radix_physica.AddQuizAndQuestion.ModerateQuizActivity;
import com.example.radix_physica.AddQuizAndQuestion.TopicModel;
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

    private Button addModerator, approve, reject, to;
    private TextView questionTextView, answerTextView, topicTextView;
    private DatabaseReference databaseReference;
    private DataSnapshot questionSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderators);


        databaseReference = FirebaseDatabase.getInstance().getReference("topics");

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
        DatabaseReference moderatedQuestionsRef = FirebaseDatabase.getInstance().getReference().child("moderated_topics");

        getRandomTopicFromDatabase();

        approve.setVisibility(View.INVISIBLE);
        reject.setVisibility(View.INVISIBLE);

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

                    reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recreate();
                            destroyArticleFromUnmoderated();
                        }
                    });

                    approve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseReference newModeratedQuestionRef = moderatedQuestionsRef.push();

                            String question = questionTextView.getText().toString();
                            String answer = answerTextView.getText().toString();
                            String topic = topicTextView.getText().toString();

                            TopicModel moderatedQuestion = new TopicModel(question, answer, topic);

                            newModeratedQuestionRef.setValue(moderatedQuestion)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(ModeratorsActivity.this, "Статья одобрена и добавлена в базу", Toast.LENGTH_SHORT).show();
                                            recreate();
                                            destroyArticleFromUnmoderated();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ModeratorsActivity.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getRandomTopicFromDatabase() {
        DatabaseReference questionsRef = FirebaseDatabase.getInstance().getReference().child("topics");

        questionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long numberOfTopics = dataSnapshot.getChildrenCount();

                    Random random = new Random();
                    long randomIndex = random.nextLong() % numberOfTopics;

                    int currentIndex = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if (currentIndex == randomIndex) {

                            questionSnapshot = snapshot;
                            TopicModel randomTopic = snapshot.getValue(TopicModel.class);
                            String questionContent = randomTopic.getTopicContent();
                            String answer = randomTopic.getWebLinks();
                            String topic = randomTopic.getTopicHead();

                            questionTextView.setText(questionContent);
                            answerTextView.setText(answer);
                            topicTextView.setText(topic);

                            break;
                        }
                        currentIndex++;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Список статей пуст", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Ошибка загрузки данных: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void destroyArticleFromUnmoderated() {
        if (questionSnapshot != null) {
            questionSnapshot.getRef().removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ModeratorsActivity.this, "Статья удалена из базы данных", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModeratorsActivity.this, "Ошибка при удалении статьи: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(ModeratorsActivity.this, "Ошибка: questionSnapshot является null", Toast.LENGTH_SHORT).show();
        }
    }

}