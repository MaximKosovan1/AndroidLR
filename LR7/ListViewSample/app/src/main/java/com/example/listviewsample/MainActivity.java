package com.example.listviewsample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MetroPrefs";
    private static final String KEY_SELECTED_STATION = "selected_station";
    private SharedPreferences sharedPreferences;
    private TextView selectedStationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        selectedStationTextView = findViewById(R.id.selectedStationTextView);
        Button selectStationButton = findViewById(R.id.selectStationButton);

        String lastSelectedStation = sharedPreferences.getString(KEY_SELECTED_STATION, "Немає вибраної станції");
        selectedStationTextView.setText("Обрана станція: " + lastSelectedStation);

        selectStationButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StationListActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        String lastSelectedStation = sharedPreferences.getString(KEY_SELECTED_STATION, "Немає вибраної станції");
        selectedStationTextView.setText("Обрана станція: " + lastSelectedStation);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_reset) {
            sharedPreferences.edit().remove(KEY_SELECTED_STATION).apply();
            selectedStationTextView.setText("Обрана станція: Немає вибраної станції");
            return true;
        }
        if (item.getItemId() == R.id.menu_exit) {
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
