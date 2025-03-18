package com.example.lr2;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ActivityStats extends AppCompatActivity {

    private GameStatsDatabase db;
    private ListView listViewStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        listViewStats = findViewById(R.id.listViewStats);
        db = new GameStatsDatabase(this);

        List<String> gameStats = db.getAllGameStats();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gameStats);
        listViewStats.setAdapter(adapter);
    }
}
