package com.example.radix_physica.AddQuizAndQuestion;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.radix_physica.Manu.ModeratorsActivity;
import com.example.radix_physica.Manu.Profile;
import com.example.radix_physica.Manu.Settings;
import com.example.radix_physica.Manu.physics_lobby;
import com.example.radix_physica.R;
import com.example.radix_physica.RegAndLog.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText editTextQuestion;
    private EditText editTextAnswer;
    private Button buttonAddQuestion, buttonShowQuestions, switchToAddQuiz;
    private DatabaseReference mDatabase;
    private RadioGroup radioGroupTopic;
    private FirebaseUser user;
    private TextView textView;
    private FirebaseAuth auth;

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
        switchToAddQuiz = findViewById(R.id.switchToAddQuiz);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.exercises).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), physics_lobby.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.settings) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(0, 0);
            }else if (item.getItemId() == R.id.moderator) {
                startActivity(new Intent(getApplicationContext(), ModeratorsActivity.class));
                overridePendingTransition(0, 0);
            }
            return true;
        });

        head();

        buttonShowQuestions.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ShowQuestionsActivity.class)));

        buttonAddQuestion.setOnClickListener(v -> addQuestionToFirebase());

        switchToAddQuiz.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddQuiz.class)));
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
        questionModel.setModerated(false);


        mDatabase.child("questions").child(key).setValue(questionModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddQuestionActivity.this, "Задание добавлено на проверку ", Toast.LENGTH_SHORT).show();
                        refresh();
                    } else {
                        Toast.makeText(AddQuestionActivity.this, "Ошибка: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
        } else {
            textView.setText("Добавьте задание " + user.getEmail());
        }
    }
}
