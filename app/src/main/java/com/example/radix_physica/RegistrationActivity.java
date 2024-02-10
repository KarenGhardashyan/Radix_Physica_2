package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText edName, edEmail, edPassword, edPasswordRetry;
    private DatabaseReference mDatabase;
    private String USER_KEY = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button save = findViewById(R.id.submit);
        init();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mDatabase.push().getKey();
                String name = edName.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String password_retry = edPasswordRetry.getText().toString();

                User newUser = new User(id, name, email, password, password_retry);
                mDatabase.child(id).setValue(newUser);
            }
        });


    }

    private void init(){
        edName = findViewById(R.id.All_Name);
        edEmail = findViewById(R.id.editTextEmail);
        edPassword = findViewById(R.id.password);
        edPassword = findViewById(R.id.password_retry);
        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY);

    }

    public void onClickRead(View view) {

    }
}