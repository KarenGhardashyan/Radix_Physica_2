package com.example.radix_physica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {

    private TextView volumeTextView, forceTextView, ourForceTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_scroll, container, false);

        volumeTextView = view.findViewById(R.id.volumeTextView);
        forceTextView = view.findViewById(R.id.forceTextView);
        ourForceTextView = view.findViewById(R.id.ourForceTextView);

        // Извлечение переданных данных из аргументов
        Bundle args = getArguments();
        if (args != null) {
            float volume = args.getFloat("volume");
            float force = args.getFloat("force");
            float ourForce = args.getFloat("ourForce");

            // Установка значений в текстовые поля
            volumeTextView.setText("Объем: " + volume);
            forceTextView.setText("Сила: " + force);
            ourForceTextView.setText("Наша сила: " + ourForce);
        }

        return view;
    }
}
