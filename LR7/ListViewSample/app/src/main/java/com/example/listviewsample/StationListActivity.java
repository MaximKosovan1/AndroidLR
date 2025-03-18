package com.example.listviewsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StationListActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MetroPrefs";
    private static final String KEY_SELECTED_STATION = "selected_station";
    private SharedPreferences sharedPreferences;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        listView = findViewById(R.id.listView);
        Button backButton = findViewById(R.id.backButton);

        Resources r = getResources();
        String[] stationsArray = r.getStringArray(R.array.stations);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stationsArray);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedStation = ((TextView) v).getText().toString();
                saveSelectedStation(selectedStation);
                Toast.makeText(getApplicationContext(), "Обрано: " + selectedStation, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        backButton.setOnClickListener(v -> finish());
    }

    private void saveSelectedStation(String station) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SELECTED_STATION, station);
        editor.apply();
    }
}
