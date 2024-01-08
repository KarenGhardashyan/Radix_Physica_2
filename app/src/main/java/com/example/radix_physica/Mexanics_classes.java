package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mexanics_classes extends AppCompatActivity {

    public Boolean basicsOn = false;
    public Boolean accelrationOn = false;
    public Boolean newtonLawsOn = false;
    public Boolean energyOn = false;
    public Boolean impulsOn = false;
    public Boolean oscillationsOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mexanics_classes);

        Button basics = findViewById(R.id.btnBasics);
        Button acceleration = findViewById(R.id.btnAcceleration);
        Button newtonLaws = findViewById(R.id.btnNewtonLaws);
        Button impuls = findViewById(R.id.btnImpulse);
        Button energy = findViewById(R.id.btnEnergy);
        Button oscillations = findViewById(R.id.btnOscillations);

         basics.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 basicsOn = true;
                 Intent intent = new Intent(Mexanics_classes.this, Chooseing_theory_game_exercises.class);
                 startActivity(intent);
             }
         });

        acceleration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accelrationOn = true;
                Intent intent = new Intent(Mexanics_classes.this, Chooseing_theory_game_exercises.class);
                startActivity(intent);
            }
        });

        newtonLaws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtonLawsOn = true;
                Intent intent = new Intent(Mexanics_classes.this, Chooseing_theory_game_exercises.class);
                startActivity(intent);
            }
        });
        energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                energyOn = true;
                Intent intent = new Intent(Mexanics_classes.this, Chooseing_theory_game_exercises.class);
                startActivity(intent);
            }
        });
        impuls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impulsOn = true;
                Intent intent = new Intent(Mexanics_classes.this, Chooseing_theory_game_exercises.class);
                startActivity(intent);
            }
        });
        oscillations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oscillationsOn = true;
                Intent intent = new Intent(Mexanics_classes.this, Chooseing_theory_game_exercises.class);
                startActivity(intent);
            }
        });


    }
}