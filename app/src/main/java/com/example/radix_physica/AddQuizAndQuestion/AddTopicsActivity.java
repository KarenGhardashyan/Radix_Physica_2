package com.example.radix_physica.AddQuizAndQuestion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.radix_physica.Manu.ModeratorsActivity;
import com.example.radix_physica.Manu.Profile;
import com.example.radix_physica.Manu.Settings;
import com.example.radix_physica.Manu.PysicsLobbyActivity;
import com.example.radix_physica.R;
import com.example.radix_physica.RegAndLog.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTopicsActivity extends AppCompatActivity {

    private TextInputEditText editTextTopicHead;
    private TextInputEditText editTextTopic;
    private TextInputEditText editTextWeb;
    private Button buttonAddTopic, buttonShowTopics;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private TextView textView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topics);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        editTextTopicHead = findViewById(R.id.editTextTopicHead);
        editTextTopic = findViewById(R.id.editTextTopic);
        editTextWeb = findViewById(R.id.editTextWebCode);
        buttonAddTopic = findViewById(R.id.buttonAddTopics);
        textView = findViewById(R.id.Header);
        buttonShowTopics = findViewById(R.id.buttonShowQuestions);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.exercises).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), PysicsLobbyActivity.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.settings) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.moderator) {
                startActivity(new Intent(getApplicationContext(), ModeratorsActivity.class));
                overridePendingTransition(0, 0);
            }
            return true;
        });

        setHeaderText();

        buttonShowTopics.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ShowQuestionsActivity.class)));

        buttonAddTopic.setOnClickListener(v -> addTopicToFirebase());
    }

    private void addTopicToFirebase() {
        String topicHead = editTextTopicHead.getText().toString().trim();
        String topicContent = editTextTopic.getText().toString().trim();
        String webLinks = editTextWeb.getText().toString().trim();

        if (topicHead.isEmpty() || topicContent.isEmpty() || webLinks.isEmpty()) {
            Toast.makeText(this, "Пожайлуста заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        String topicId = mDatabase.child("topics").push().getKey();

        TopicModel topicModel = new TopicModel(topicHead, topicContent, webLinks);

        mDatabase.child("topics").child(topicId).setValue(topicModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddTopicsActivity.this, "Topic added successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(AddTopicsActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        editTextTopicHead.setText("");
        editTextTopic.setText("");
        editTextWeb.setText("");
    }

    private void setHeaderText() {
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText("Add a new topic: " + user.getEmail());
        }
    }
}
