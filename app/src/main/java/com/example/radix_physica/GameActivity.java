package com.example.radix_physica;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    EditText rhoEditText, heightEditText, areaEditText, strengthEditText, safetyCoefficientEditText;
    Button calculateButton;
    long density, height, area,  maxPressure, strength,   force, ourForce, volume, p;


    boolean isFragmentBasicOn = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rhoEditText = findViewById(R.id.rho);
        heightEditText = findViewById(R.id.height);
        areaEditText = findViewById(R.id.area);
        strengthEditText = findViewById(R.id.strength);



        calculateButton = findViewById(R.id.calculate);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rhoText = rhoEditText.getText().toString();
                String heightText = heightEditText.getText().toString();
                String areaText = areaEditText.getText().toString();
                String strengthText = strengthEditText.getText().toString();

                try {
                    density = Long.parseLong(rhoText);
                    height = Long.parseLong(heightText);
                    area = Long.parseLong(areaText);
                    strength = Long.parseLong(strengthText);

                    volume = area * height;
                    maxPressure = strength * 1000000;
                    force = maxPressure * area;
                    p = volume * density * 10 / area;
                    ourForce = p * area;

                    if (ourForce < force) {
                        openResultFragment();
                        reloadActivity();

                    }else {
                        Toast.makeText(GameActivity.this, "Материал не выдерживает силу", Toast.LENGTH_SHORT).show();
                        animateVibration();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(GameActivity.this, "Пожалуйста, введите числовые значения", Toast.LENGTH_SHORT).show();
                }

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        });

        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        };

        areaEditText.setOnKeyListener(onKeyListener);
    }

    private void reloadActivity() {
        recreate();
    }

    //TODO
    private void openResultFragment() {
        if (!isFragmentBasicOn) {
            ResultFragment fragment = new ResultFragment();
            Bundle bundle = new Bundle();
            bundle.putFloat("volume", volume);
            bundle.putFloat("force", force);
            bundle.putFloat("ourForce", ourForce);
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down);
            transaction.replace(R.id.frameforfragment, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            isFragmentBasicOn = true;
        }
    }


    private void animateVibration() {
        ImageView imageView = findViewById(R.id.bashnya);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, -10f, 10f);
        animatorX.setRepeatCount(ObjectAnimator.INFINITE);
        animatorX.setRepeatMode(ObjectAnimator.REVERSE);

        animatorX.start();

    }
}