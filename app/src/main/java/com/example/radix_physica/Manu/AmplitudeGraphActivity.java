package com.example.radix_physica.Manu;

import static android.graphics.Color.WHITE;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.radix_physica.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.bouncycastle.pqc.jcajce.provider.qtesla.SignatureSpi;

import java.util.ArrayList;
import java.util.List;

public class AmplitudeGraphActivity extends AppCompatActivity {

    private TextInputLayout tValue, lengthValue;
    private TextInputEditText tEditText, lengthEditText;
    private Button addPointButton;
    private LineChart chart;

    private List<Entry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amplitude_graph);

        tEditText = findViewById(R.id.tedittext);
        lengthEditText = findViewById(R.id.lengthedittext);

        tValue = findViewById(R.id.tValue);
        lengthValue = findViewById(R.id.lengthValue);


        addPointButton = findViewById(R.id.addPointButton);
        chart = findViewById(R.id.chart);

        ImageButton back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoint();
            }
        });

        chart.setDragEnabled(true);

        chart.setScaleEnabled(true);
        chart.setDrawBorders(true);

        chart.setPinchZoom(true);

        chart.getAxisLeft().setDrawGridLines(true);
        chart.getAxisLeft().setGridColor(Color.parseColor("#00C853"));
        chart.getLegend().setTextColor(Color.parseColor("#FF0000"));

        Description description = new Description();
        description.setText("Graph");
        description.setTextColor(Color.parseColor("#FF6D00"));
        description.setTextSize(12f);
        chart.setDescription(description);


        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(WHITE);
        xAxis.setAxisLineColor(WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setTextColor(WHITE);
        yAxis.setAxisLineColor(WHITE);

        YAxis rightYAxis = chart.getAxisRight();
        rightYAxis.setTextColor(WHITE);
        rightYAxis.setAxisLineColor(WHITE);
    }

    private void addPoint() {
        String tText = tEditText.getText().toString();
        String lengthText = lengthEditText.getText().toString();

        entries.clear();

        try {
            float tValue = Float.parseFloat(tText);
            float lengthValue = Float.parseFloat(lengthText);

            ArrayList<Entry> periodEntries = new ArrayList<>();

            float pi = 3.14159265359F;
            float g = 9.8F;

            float period = (float) (2 * pi * Math.sqrt(lengthValue / g));

            for (float t = 0; t <= tValue; t += 0.1f) {
                float amplitude = (float) (g / (4 * Math.PI * Math.PI) * lengthValue);
                periodEntries.add(new Entry(t, (float) (amplitude * Math.cos(2 * pi * t / period))));
            }

            entries.addAll(periodEntries);

            updateGraph();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Неверный формат числа", Toast.LENGTH_SHORT).show();
        }
    }



    private void updateGraph() {
        LineDataSet dataSet = new LineDataSet(entries, "Points");
        dataSet.setColor(Color.GREEN);
        dataSet.setDrawCircles(true);
        dataSet.setValueTextColor(WHITE);
        dataSet.setValueTextSize(14f);

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);

        float maxXValue = Float.MIN_VALUE;
        for (Entry entry : entries) {
            if (entry.getX() > maxXValue) {
                maxXValue = entry.getX();
            }
        }
        chart.getXAxis().setAxisMaximum(maxXValue);

        Description description = new Description();
        description.setText("Graph");
        description.setTextSize(13f);
        description.setTextColor(WHITE);
        chart.setDescription(description);

        chart.invalidate();
    }
}

