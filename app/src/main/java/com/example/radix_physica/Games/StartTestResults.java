package com.example.radix_physica.Games;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.radix_physica.R;

public class StartTestResults extends Fragment {

    TextView resultTextView;

    public StartTestResults() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_test_results, container, false);

        resultTextView = view.findViewById(R.id.result_text);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String answer1 = bundle.getString("answer1", "");
            String answer2 = bundle.getString("answer2", "");
            String answer3 = bundle.getString("answer3", "");

            String result = "Answer 1: " + answer1 + "\n" +
                    "Answer 2: " + answer2 + "\n" +
                    "Answer 3: " + answer3;
            resultTextView.setText(result);
        }

        return view;
    }

}
