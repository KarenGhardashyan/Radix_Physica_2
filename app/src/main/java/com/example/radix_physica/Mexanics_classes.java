package com.example.radix_physica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.radix_physica.R;
import com.example.radix_physica.TheoryTaskFragment;

public class Mexanics_classes extends AppCompatActivity {

    //переменные по вкл. фрагмента

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
        btnbasics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                btnbasics.setBackgroundResource(R.drawable.button_background);
                basicOn = true;
            }
        });
        btnAcceleration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                acceleratioOn = true;
                btnAcceleration.setBackgroundResource(R.drawable.button_background);
            }
        });
        btnEnergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                energyOn = true;
                btnEnergy.setBackgroundResource(R.drawable.button_background);
            }
        });
        btnImpulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                impulsOn = true;
                btnImpulse.setBackgroundResource(R.drawable.button_background);
            }
        });
        btnOscillations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                oscillationsOn = true;
                btnOscillations.setBackgroundResource(R.drawable.button_background);
            }
        });
        btnNewtonLaws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_logic();
                newtonLawsOn = true;
                if (isFragmentBasicOn){
                    btnNewtonLaws.setBackgroundResource(R.drawable.button_background);
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
            transaction.setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down);
            transaction.replace(R.id.frameforfragment, theoryTaskFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            isFragmentBasicOn = true;
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

            Button btnbasics = findViewById(R.id.btnBasics);
            Button btnAcceleration  = findViewById(R.id.btnAcceleration);
            Button btnEnergy = findViewById(R.id.btnEnergy);
            Button btnImpulse = findViewById(R.id.btnImpulse);
            Button btnOscillations = findViewById(R.id.btnOscillations);
            Button btnNewtonLaws = findViewById(R.id.btnNewtonLaws);

            btnNewtonLaws.setBackgroundResource(R.drawable.button_background_simple);
            btnAcceleration.setBackgroundResource(R.drawable.button_background_simple);
            btnOscillations.setBackgroundResource(R.drawable.button_background_simple);
            btnEnergy.setBackgroundResource(R.drawable.button_background_simple);
            btnImpulse.setBackgroundResource(R.drawable.button_background_simple);
            btnbasics.setBackgroundResource(R.drawable.button_background_simple);

            basicOn = false;
            acceleratioOn = false;
            energyOn = false;
            impulsOn = false;
            oscillationsOn = false;
            newtonLawsOn = false;
        }
    }


}
