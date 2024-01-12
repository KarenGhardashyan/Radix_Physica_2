package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //открытие анимацией
        Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.activity_anim_up);
        View rootView = findViewById(android.R.id.content);
        rootView.startAnimation(slideUpAnimation);
    }
}