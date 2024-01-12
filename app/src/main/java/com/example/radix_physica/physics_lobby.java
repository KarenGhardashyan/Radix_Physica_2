package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class physics_lobby extends AppCompatActivity {

    ImageButton mechanicsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pysics_lobby);

        ImageButton mechanicsButton = findViewById(R.id.mexanics);

        //открытие анимацией
        Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.activity_anim_up);
        View rootView = findViewById(android.R.id.content);
        rootView.startAnimation(slideUpAnimation);

        mechanicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(physics_lobby.this, Mexanics_classes.class);
                startActivity(intent);
            }
        });


    }
}
