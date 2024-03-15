package com.example.radix_physica.Topics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.radix_physica.R;

public class TopicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        //открытие анимацией
        Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.activity_anim_up);
        View rootView = findViewById(android.R.id.content);
        rootView.startAnimation(slideUpAnimation);
    }
}