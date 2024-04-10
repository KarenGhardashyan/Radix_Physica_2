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

import java.util.ArrayList;
import java.util.List;

public class VelocityGraphActivity extends AppCompatActivity {

    private TextInputLayout aValueLayout, tValueLayout, vValueLayout;
    private TextInputEditText aValueEditText, tValueEditText, vValueEditText;
    private Button addPointButton;
    private LineChart chart;

    private List<Entry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velocity_graph);

        aValueLayout = findViewById(R.id.aValue);
        tValueLayout = findViewById(R.id.tValue);
        vValueLayout = findViewById(R.id.tValue);

        aValueEditText = findViewById(R.id.aedittext);
        tValueEditText = findViewById(R.id.tedittext);
        vValueEditText = findViewById(R.id.vedittext);

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

        String aText = aValueEditText.getText().toString();
        String tText = tValueEditText.getText().toString();
        String vText = vValueEditText.getText().toString();

        try {
            float tValue = Float.parseFloat(tText);
            float aValue = Float.parseFloat(aText);
            float vValue = Float.parseFloat(vText);

            float xValue = tValue;
            float yValue = vValue + aValue * tValue;

            if (xValue < Float.MAX_VALUE && yValue < Float.MAX_VALUE){
                Entry newEntry = new Entry(xValue, yValue);
                entries.add(newEntry);
                updateGraph();
            } else {
                Toast.makeText(this, "Очень большое число !", Toast.LENGTH_SHORT).show();
            }
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

        Description description = new Description();
        description.setText("Graph");
        description.setTextSize(13f);
        description.setTextColor(WHITE);
        chart.setDescription(description);

        chart.invalidate();
    }
}

