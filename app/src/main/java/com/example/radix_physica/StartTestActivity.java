package com.example.radix_physica;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private Button submit;
    private List<QuestionModel> selectedQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        databaseReference = FirebaseDatabase.getInstance().getReference("questions");
        submit = findViewById(R.id.submit_Button);
        selectedQuestions = new ArrayList<>();

        readDataFromFirebase();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedQuestions != null && !selectedQuestions.isEmpty()) {
                    checkAnswers();
                } else {
                    // Обработка случая, когда нет выбранных вопросов
                }
            }
        });
    }

    private void checkAnswers() {
        for (QuestionModel question : selectedQuestions) {
            String correctAnswer = question.getAnswer();
            String userAnswer = getUserAnswerForQuestion(question);

            // Находим TextView для соответствующего вопроса
            TextView questionTextView = findQuestionTextView(question);

            // Проверяем ответ пользователя
            if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                // Правильный ответ
                questionTextView.setText(question.getQuestion() + "\n\nВерно");
                questionTextView.setTextColor(Color.GREEN);
            } else {
                // Неправильный ответ или не введенный ответ
                questionTextView.setText(question.getQuestion() + "\n\nНеверно");
                questionTextView.setTextColor(Color.RED);
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
                // Обработка ошибок при чтении данных из базы данных
            }
        });
    }

    private void startTest(List<QuestionModel> allQuestions) {
        Collections.shuffle(allQuestions);

        selectedQuestions.clear(); // Очищаем список перед добавлением новых случайных вопросов

        selectedQuestions.addAll(allQuestions.subList(0, Math.min(8, allQuestions.size())));
        displayQuestions(selectedQuestions);
    }

    private void displayQuestions(List<QuestionModel> questions) {
        TextView textView1 = findViewById(R.id.mechanicsQuestionTextView1);
        TextView textView2 = findViewById(R.id.mechanicsQuestionTextView2);
        TextView textView3 = findViewById(R.id.thermodynamicsQuestionTextView1);
        TextView textView4 = findViewById(R.id.thermodynamicsQuestionTextView2);
        TextView textView5 = findViewById(R.id.electronicsQuestionTextView1);
        TextView textView6 = findViewById(R.id.electronicsQuestionTextView2);
        TextView textView7 = findViewById(R.id.opticsQuestionTextView1);
        TextView textView8 = findViewById(R.id.opticsQuestionTextView2);

        displayRandomQuestion(textView1, questions);
        displayRandomQuestion(textView2, questions);
        displayRandomQuestion(textView3, questions);
        displayRandomQuestion(textView4, questions);
        displayRandomQuestion(textView5, questions);
        displayRandomQuestion(textView6, questions);
        displayRandomQuestion(textView7, questions);
        displayRandomQuestion(textView8, questions);
    }

    private void displayRandomQuestion(TextView textView, List<QuestionModel> questions) {
        if (!questions.isEmpty()) {
            Collections.shuffle(questions);
            textView.setText(questions.get(0).getQuestion());
        }
    }

    private String getUserAnswerForQuestion(QuestionModel question) {
        EditText editText = null;
        switch (question.getTopic()) {
            case "Mechanics1":
                editText = findViewById(R.id.mechanicsAnswerEditText1);
                break;
            case "Mechanics2":
                editText = findViewById(R.id.mechanicsAnswerEditText2);
                break;
            case "Thermodynamics1":
                editText = findViewById(R.id.thermodynamicsAnswerEditText1);
                break;
            case "Thermodynamics2":
                editText = findViewById(R.id.thermodynamicsAnswerEditText2);
                break;
            case "Electronics1":
                editText = findViewById(R.id.electronicsAnswerEditText1);
                break;
            case "Electronics2":
                editText = findViewById(R.id.electronicsAnswerEditText2);
                break;
            case "Optics1":
                editText = findViewById(R.id.opticsAnswerEditText1);
                break;
            case "Optics2":
                editText = findViewById(R.id.opticsAnswerEditText2);
                break;
        }
        return editText != null ? editText.getText().toString() : null;
    }

    private TextView findQuestionTextView(QuestionModel question) {
        TextView textView = null;
        switch (question.getTopic()) {
            case "Mechanics1":
                textView = findViewById(R.id.mechanicsQuestionTextView1);
                break;
            case "Mechanics2":
                textView = findViewById(R.id.mechanicsQuestionTextView2);
                break;
            case "Thermodynamics1":
                textView = findViewById(R.id.thermodynamicsQuestionTextView1);
                break;
            case "Thermodynamics2":
                textView = findViewById(R.id.thermodynamicsQuestionTextView2);
                break;
            case "Electronics1":
                textView = findViewById(R.id.electronicsQuestionTextView1);
                break;
            case "Electronics2":
                textView = findViewById(R.id.electronicsQuestionTextView2);
                break;
            case "Optics1":
                textView = findViewById(R.id.opticsQuestionTextView1);
                break;
            case "Optics2":
                textView = findViewById(R.id.opticsQuestionTextView2);
                break;
        }
        return textView;
    }
}
