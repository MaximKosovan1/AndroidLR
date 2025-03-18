package com.example.lr2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GameStatsDatabase db;
    private ListView listViewStats;
    private Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewStats = findViewById(R.id.listViewStats);
        btnStartGame = findViewById(R.id.btnStartGame);
        db = new GameStatsDatabase(this);

        List<String> gameStats = db.getAllGameStats();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gameStats);
        listViewStats.setAdapter(adapter);

        btnStartGame.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityGame.class);
            startActivity(intent);
        });
    }
}
