package com.example.radix_physica;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.radix_physica.MainActivity;
import com.example.radix_physica.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logoImageView = findViewById(R.id.logo_image_view);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);

        logoImageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);

                Pair<View, String> pair = new Pair<>(logoImageView, "logo_image");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this, pair);

                startActivity(intent, options.toBundle());

                finish();
            }
        }, SPLASH_DELAY);
    }
}
