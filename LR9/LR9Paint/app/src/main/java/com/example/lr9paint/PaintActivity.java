package com.example.lr9paint;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class PaintActivity extends AppCompatActivity {

    private PaintView paintView;
    private SeekBar seekBarWidth;
    private Button btnColorRed, btnColorBlue, btnColorGreen, btnApplyRGB, btnLoadImage, btnUndo, btnRedo;
    private EditText editRGB;

    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_paint);

        paintView = findViewById(R.id.paintView);
        seekBarWidth = findViewById(R.id.seekBarWidth);
        btnColorRed = findViewById(R.id.btnColorRed);
        btnColorBlue = findViewById(R.id.btnColorBlue);
        btnColorGreen = findViewById(R.id.btnColorGreen);
        editRGB = findViewById(R.id.editRGB);
        btnApplyRGB = findViewById(R.id.btnApplyRGB);
        btnLoadImage = findViewById(R.id.btnLoadImage);
        btnUndo = findViewById(R.id.btnUndo);
        btnRedo = findViewById(R.id.btnRedo);

        seekBarWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                paintView.setLineWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnColorRed.setOnClickListener(v -> paintView.setLineColor(Color.RED));
        btnColorBlue.setOnClickListener(v -> paintView.setLineColor(Color.BLUE));
        btnColorGreen.setOnClickListener(v -> paintView.setLineColor(Color.GREEN));

        btnApplyRGB.setOnClickListener(v -> {
            try {
                int color = Color.parseColor(editRGB.getText().toString());
                paintView.setLineColor(color);
            } catch (IllegalArgumentException e) {
                editRGB.setError("Невірний формат кольору (наприклад, #FF5733)");
            }
        });

        btnLoadImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        btnUndo.setOnClickListener(v -> paintView.undo());
        btnRedo.setOnClickListener(v -> paintView.redo());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                paintView.setBackgroundImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
