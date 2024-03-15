package com.example.radix_physica.AddQuizAndQuestion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.radix_physica.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuiz extends AppCompatActivity {
    private EditText questionEditText, option1EditText, option2EditText, option3EditText, option4EditText, correctAnswerEditText;
    private Button addButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        questionEditText = findViewById(R.id.questionEditText);
        option1EditText = findViewById(R.id.option1EditText);
        option2EditText = findViewById(R.id.option2EditText);
        option3EditText = findViewById(R.id.option3EditText);
        option4EditText = findViewById(R.id.option4EditText);
        correctAnswerEditText = findViewById(R.id.correctAnswerEditText);
        addButton = findViewById(R.id.addButton);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("AddQuiz");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = questionEditText.getText().toString();
                String option1 = option1EditText.getText().toString();
                String option2 = option2EditText.getText().toString();
                String option3 = option3EditText.getText().toString();
                String option4 = option4EditText.getText().toString();
                String correctAnswer = correctAnswerEditText.getText().toString();

                Quiz quiz = new Quiz(question, option1, option2, option3, option4, correctAnswer);

                mDatabase.push().setValue(quiz);

                questionEditText.setText("");
                option1EditText.setText("");
                option2EditText.setText("");
                option3EditText.setText("");
                option4EditText.setText("");
                correctAnswerEditText.setText("");
            }
        });
    }
}
