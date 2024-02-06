package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.radix_physica.R;
import com.example.radix_physica.TheoryTaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Mexanics_classes extends AppCompatActivity {

    //переменные по вкл. фрагмента

    private FrameLayout blurredBackgroundLayout;
    private boolean isFragmentBasicOn = false;
    private boolean isFragmentAcceleratioOn = false;
    private boolean isFragmentEnergyOn = false;
    private boolean isFragmentImpulsOn = false;
    private boolean isFragmentOscillationsOn = false;
    private boolean isFragmentNewtonLawsOn = false;

    //переменные по вкл топика темы.

    public boolean basicOn = false;
    public boolean acceleratioOn = false;
    public boolean energyOn = false;
    public boolean impulsOn = false;
    public boolean oscillationsOn = false;
    public boolean newtonLawsOn = false;

    //кнопки


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mexanics_classes);


        Button btnbasics = findViewById(R.id.btnBasics);
        Button btnAcceleration  = findViewById(R.id.btnAcceleration);
        Button btnEnergy = findViewById(R.id.btnEnergy);
        Button btnImpulse = findViewById(R.id.btnImpulse);
        Button btnOscillations = findViewById(R.id.btnOscillations);
        Button btnNewtonLaws = findViewById(R.id.btnNewtonLaws);


        bottomNavigationBar();


        btnbasics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                basicOn = true;
            }
        });
        btnAcceleration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                acceleratioOn = true;
            }
        });
        btnEnergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                energyOn = true;
            }
        });
        btnImpulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                impulsOn = true;
            }
        });
        btnOscillations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                oscillationsOn = true;
            }
        });
        btnNewtonLaws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                newtonLawsOn = true;
                if (isFragmentBasicOn){
                }
            }
        });
    }

    private void fragment_logic(){
        if (isFragmentBasicOn) {
            closeTheoryTaskFragment();
        } else {
            openTheoryTaskFragment();
        }
    }


    private void openTheoryTaskFragment() {
        TheoryTaskFragment theoryTaskFragment = new TheoryTaskFragment();

        if (isFragmentBasicOn == false){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            TheoryTaskFragment fragment = new TheoryTaskFragment();
            transaction.setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down);
            transaction.replace(R.id.frameforfragment, theoryTaskFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            isFragmentBasicOn = true;
            blurBackground(true); // Передаем флаг, указывающий исключить TheoryTaskFragment
        }
    }



    //TODO
    private void blurBackground(boolean excludeTheoryTaskFragment) {
        ViewGroup rootView = findViewById(android.R.id.content);

        if (!excludeTheoryTaskFragment) {
            blurredBackgroundLayout = new FrameLayout(this);
            blurredBackgroundLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#80000000"));
            blurredBackgroundLayout.setBackground(colorDrawable);

            rootView.addView(blurredBackgroundLayout);
        }
    }


    private void removeBlurredBackground() {
        if (blurredBackgroundLayout != null && blurredBackgroundLayout.getParent() != null) {
            ((ViewGroup) blurredBackgroundLayout.getParent()).removeView(blurredBackgroundLayout);
        }
    }

    private void closeTheoryTaskFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            isFragmentBasicOn = false;


            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(0, R.anim.slide_down);
            transaction.commit();

            basicOn = false;
            acceleratioOn = false;
            energyOn = false;
            impulsOn = false;
            oscillationsOn = false;
            newtonLawsOn = false;

            removeBlurredBackground();
        }
    }


    @SuppressLint("NonConstantResourceId")
    public void bottomNavigationBar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                Intent intent = new Intent(Mexanics_classes.this, physics_lobby.class );
                startActivity(intent);
            } else if (item.getItemId() == R.id.profile) {
                Intent intent = new Intent(Mexanics_classes.this, Profile.class );
                startActivity(intent);
            } else if (item.getItemId() == R.id.settings) {
                Intent intent = new Intent(Mexanics_classes.this, Profile.class );
                startActivity(intent);
            }

            if (selectedFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }

            return true;
        });
    }

}
