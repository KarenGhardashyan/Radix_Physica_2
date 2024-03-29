package com.example.radix_physica.Manu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.radix_physica.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserSettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editTextName;
    private EditText editTextEmail;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserSettings();
            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            editTextName.setText(name);
            editTextEmail.setText(email);
        }
    }

    private void saveUserSettings() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String newName = editTextName.getText().toString().trim();
            final String newEmail = editTextEmail.getText().toString().trim();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build();

            currentUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserSettingsActivity.this, "Имя пользователя обновлено успешно!", Toast.LENGTH_SHORT).show();
                                updateEmail(newEmail);
                            } else {
                                Toast.makeText(UserSettingsActivity.this, "Ошибка при обновлении имени пользователя!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void updateEmail(final String newEmail) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserSettingsActivity.this, "Электронная почта пользователя обновлена успешно!", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            } else {
                                Toast.makeText(UserSettingsActivity.this, "Ошибка при обновлении электронной почты пользователя!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserSettingsActivity.this, "Проверьте вашу электронную почту для подтверждения адреса", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(UserSettingsActivity.this, "Не удалось отправить электронное письмо для подтверждения адреса", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}