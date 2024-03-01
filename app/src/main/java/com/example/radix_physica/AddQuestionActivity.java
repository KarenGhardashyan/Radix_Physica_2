package com.example.radix_physica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText editTextQuestion;
    private EditText editTextAnswer;
    private Button buttonAddQuestion, buttonShowQuestions;
    private DatabaseReference mDatabase;
    private RadioGroup radioGroupTopic;


    FirebaseUser user;
    TextView textView;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        buttonAddQuestion = findViewById(R.id.buttonAddQuestion);
        textView = findViewById(R.id.Header);
        buttonShowQuestions = findViewById(R.id.buttonShowQuestions);
        radioGroupTopic = findViewById(R.id.radioGroupTopic);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.exercises).setChecked(true);

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
            }

            return true;
        });

        head();
        buttonShowQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShowQuestionsActivity.class));
            }
        });
        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestionToFirebase();
            }
        });
    }


    private void addQuestionToFirebase() {
        String question = editTextQuestion.getText().toString().trim();
        String answer = editTextAnswer.getText().toString().trim();

        if (question.isEmpty() || answer.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(radioGroupTopic.getCheckedRadioButtonId());
        if (selectedRadioButton == null) {
            Toast.makeText(this, "Выберите тему", Toast.LENGTH_SHORT).show();
            return;
        }
        String topic = selectedRadioButton.getText().toString();

        String key = mDatabase.child("questions").push().getKey();

        QuestionModel questionModel = new QuestionModel(question, answer, topic);

        mDatabase.child("questions").child(key).setValue(questionModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddQuestionActivity.this, "Задание добавлено", Toast.LENGTH_SHORT).show();
                            refresh();
                        } else {
                            Toast.makeText(AddQuestionActivity.this, "Ошибка: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void refresh() {
        recreate();
        editTextQuestion.setText("");
        editTextAnswer.setText("");
    }

    private void head() {
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            textView.setText("Добавьте задание " + user.getEmail());
        }
    }
}
