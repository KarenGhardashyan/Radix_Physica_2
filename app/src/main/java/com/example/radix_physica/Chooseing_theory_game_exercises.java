package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Chooseing_theory_game_exercises extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseing_teory_game_exercises);

        Button theory = findViewById(R.id.theory);
        Mexanics_classes mexanicsClasses = new Mexanics_classes();
        TextView header = findViewById(R.id.header_title);

        if (mexanicsClasses.impulsOn = true){
            header.setText("ИМПУЛЬС");
        } else if (mexanicsClasses.energyOn = true) {
            header.setText("ЭНЕРГИЯ");
        } else if (mexanicsClasses.accelrationOn = true) {
            header.setText("УСКОРЕНИЕ");
        } else if (mexanicsClasses.newtonLawsOn = true) {
            header.setText("ЗАКОНЫ НЮТОНА");
        }else if (mexanicsClasses.basicsOn = true){
            header.setText("ОСНОВЫ ОСНОВ");
        } else if (mexanicsClasses.accelrationOn = true) {
            header.setText("МАЯАТНИКИ");
        }

        theory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        Mexanics_classes mexanicsClasses = new Mexanics_classes();
//
//        mexanicsClasses.accelrationOn = false;
//        mexanicsClasses.basicsOn = false;
//        mexanicsClasses.newtonLawsOn = false;
//        mexanicsClasses.energyOn = false;
//        mexanicsClasses.impulsOn = false;
//        mexanicsClasses.oscillationsOn = false;
//    }
}
