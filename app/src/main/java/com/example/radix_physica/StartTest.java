package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.radix_physica.R;
import com.example.radix_physica.StartTestResults;

public class StartTest extends AppCompatActivity {

    EditText problem1, problem2, problem3;
    Button enter;
    RadioButton trueAnswer1, trueAnswer2, trueAnswer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        problem1 = findViewById(R.id.problem1_answer);
        problem2 = findViewById(R.id.problem2_answer);
        problem3 = findViewById(R.id.problem3_answer);
        trueAnswer1 = findViewById(R.id.answer1_option3);
        trueAnswer2 = findViewById(R.id.answer2_option3);
        trueAnswer3 = findViewById(R.id.answer3_option1);
        enter = findViewById(R.id.enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем ответы на вопросы теста
                String answer1 = problem1.getText().toString();
                String answer2 = problem2.getText().toString();
                String answer3 = problem3.getText().toString();

                String result = "Результаты теста:\n" +
                        "Вопрос 1: " + (trueAnswer1.isChecked() ? "Верно" : "Неверно") + "\n" +
                        "Вопрос 2: " + (trueAnswer2.isChecked() ? "Верно" : "Неверно") + "\n" +
                        "Вопрос 3: " + (trueAnswer3.isChecked() ? "Верно" : "Неверно") + "\n" +
                        "Задача 1: " + answer1 + "\n" +
                        "Задача 2: " + answer2 + "\n" +
                        "Задача 3: " + answer3;

                StartTestResults fragment = new StartTestResults();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
    }
}
