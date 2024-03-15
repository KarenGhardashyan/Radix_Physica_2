package com.example.radix_physica.AddQuizAndQuestion;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.radix_physica.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddModeratorActivity extends AppCompatActivity {

    private EditText editTextModeratorEmail;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moderator);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        editTextModeratorEmail = findViewById(R.id.editTextModeratorEmail);

        Button buttonAddModerator = findViewById(R.id.buttonAddModerator);
        buttonAddModerator.setOnClickListener(v -> addModeratorToFirebase());

    }

    private void addModeratorToFirebase() {
        String email = editTextModeratorEmail.getText().toString().trim();

        if (!TextUtils.isEmpty(email)) {
            String key = mDatabase.child("Moderators").push().getKey();
            Moderator moderator = new Moderator(email);

            if (key != null) {
                mDatabase.child("Moderators").child(key).setValue(moderator)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AddModeratorActivity.this, "Модератор добавлен успешно", Toast.LENGTH_SHORT).show();
                            editTextModeratorEmail.setText("");
                        })
                        .addOnFailureListener(e -> Toast.makeText(AddModeratorActivity.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(AddModeratorActivity.this, "Ошибка при создании ключа", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AddModeratorActivity.this, "Введите email модератора", Toast.LENGTH_SHORT).show();
        }
    }
}
