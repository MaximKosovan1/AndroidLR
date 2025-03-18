package com.example.lr4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerLanguage, spinnerFont;
    private ImageView flagImage;
    private TextView tvWelcome;
    private String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale(); // Завантажуємо локаль до виклику super.onCreate()
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        spinnerFont = findViewById(R.id.spinnerFont);
        flagImage = findViewById(R.id.flagImage);
        tvWelcome = findViewById(R.id.tvWelcome);

        // Шрифти
        Typeface roboto = ResourcesCompat.getFont(this, R.font.roboto);
        Typeface lobster = ResourcesCompat.getFont(this, R.font.lobster);
        Typeface openSans = ResourcesCompat.getFont(this, R.font.open_sans);

        // Налаштування адаптера для вибору шрифту
        String[] fonts = {"Roboto", "Lobster", "Open Sans"};
        ArrayAdapter<String> fontAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fonts);
        spinnerFont.setAdapter(fontAdapter);

        spinnerFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                switch (position) {
                    case 0:
                        tvWelcome.setTypeface(roboto);
                        break;
                    case 1:
                        tvWelcome.setTypeface(lobster);
                        break;
                    case 2:
                        tvWelcome.setTypeface(openSans);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Мови
        String[] languages = {"English", "Українська", "Français"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        spinnerLanguage.setAdapter(adapter);

        // Встановлення вибраної мови в Spinner
        switch (currentLang) {
            case "en":
                spinnerLanguage.setSelection(0);
                flagImage.setImageResource(R.drawable.flag_us);
                break;
            case "ua":
                spinnerLanguage.setSelection(1);
                flagImage.setImageResource(R.drawable.flag_ua);
                break;
            case "fr":
                spinnerLanguage.setSelection(2);
                flagImage.setImageResource(R.drawable.flag_fr);
                break;
        }

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedLang = position == 0 ? "en" : position == 1 ? "ua" : "fr";
                if (!selectedLang.equals(currentLang)) {
                    setLocale(selectedLang);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setLocale(String lang) {
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        currentLang = prefs.getString("My_Lang", "en");

        Locale locale = new Locale(currentLang);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}