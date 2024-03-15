package com.example.radix_physica.RegAndLog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.radix_physica.Manu.physics_lobby;
import com.example.radix_physica.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmailLogin);
        editTextPassword = findViewById(R.id.editTextPasswordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && user.isEmailVerified()) {
            startActivity(new Intent(LoginActivity.this, physics_lobby.class));
            finish();
            return;
        } else {
            Toast.makeText(LoginActivity.this, "Если вы только что зарегистрировались проверьте ел. почту.", Toast.LENGTH_SHORT).show();
        }


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("введите имейл");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("введите пароль");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                startActivity(new Intent(LoginActivity.this, physics_lobby.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Пожайлуста проверьте ел. почту", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Ошибка входа.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}