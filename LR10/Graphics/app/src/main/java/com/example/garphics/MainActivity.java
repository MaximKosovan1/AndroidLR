package com.example.garphics;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PieChart genderChart = findViewById(R.id.genderChart);
        PieChart ageChart = findViewById(R.id.ageChart);

        setupGenderChart(genderChart);
        setupAgeChart(ageChart);
    }

    private void setupGenderChart(PieChart chart) {
        ArrayList<PieEntry> genderEntries = new ArrayList<>();
        genderEntries.add(new PieEntry(55f, "Чоловіки"));
        genderEntries.add(new PieEntry(45f, "Жінки"));

        PieDataSet dataSet = new PieDataSet(genderEntries, "Розподіл за статтю");

        int[] pastelColors = {
                Color.rgb(135, 206, 250), // Світло-блакитний
                Color.rgb(255, 182, 193)  // Світло-рожевий
        };
        dataSet.setColors(pastelColors);

        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.setUsePercentValues(true);
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(14f);
        chart.invalidate();
    }

    private void setupAgeChart(PieChart chart) {
        ArrayList<PieEntry> ageEntries = new ArrayList<>();
        ageEntries.add(new PieEntry(22f, "18-24 роки"));
        ageEntries.add(new PieEntry(35f, "25-34 роки"));
        ageEntries.add(new PieEntry(25f, "35-44 роки"));
        ageEntries.add(new PieEntry(18f, "45+ років"));

        PieDataSet dataSet = new PieDataSet(ageEntries, "Розподіл за віком");

        int[] pastelColors = {
                Color.rgb(152, 251, 152),
                Color.rgb(255, 218, 185),
                Color.rgb(176, 224, 230),
                Color.rgb(221, 160, 221)
        };
        dataSet.setColors(pastelColors);

        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.setUsePercentValues(true);
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(14f);
        chart.invalidate();
    }
}
