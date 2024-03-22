package com.example.radix_physica.Manu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radix_physica.AddQuizAndQuestion.AddQuiz;
import com.example.radix_physica.AddQuizAndQuestion.ModerateQuizActivity;
import com.example.radix_physica.AddQuizAndQuestion.TopicModel;
import com.example.radix_physica.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.settings).setChecked(true);

        TextView moderator = findViewById(R.id.moderatorButton2);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference moderatorsRef = FirebaseDatabase.getInstance().getReference().child("Moderators");
        String userEmail = user.getEmail();

        moderator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, ModerateQuizActivity.class));
            }
        });

        moderatorsRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    moderator.setVisibility(View.VISIBLE);
                    moderator.setEnabled(true);


                } else {
                   moderator.setVisibility(View.INVISIBLE);
                    moderator.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigationView.getMenu().findItem(R.id.home).setCheckable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), PysicsLobbyActivity.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.exercises) {
                startActivity(new Intent(getApplicationContext(), AddQuiz.class));
                overridePendingTransition(0, 0);
            }
            return true;
        });
    }
}