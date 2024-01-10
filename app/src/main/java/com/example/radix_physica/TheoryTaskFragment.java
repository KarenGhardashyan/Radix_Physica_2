package com.example.radix_physica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.Button;

public class TheoryTaskFragment extends Fragment {

    public TheoryTaskFragment() {
        // Пустой конструктор обязателен
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theory_task, container, false);
        return view;
    }
}
