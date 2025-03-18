package com.example.shapedrawable;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int IDM_LINE = 101;
    private static final int IDM_OVAL = 102;
    private static final int IDM_RECT = 103;
    private static final int IDM_ROUNDRECT = 104;
    private static final int IDM_STAR = 105;
    private static final int IDM_ARC = 106;

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage = findViewById(R.id.image);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true; // Примушує оновлення меню
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, IDM_LINE, Menu.NONE, "Line");
        menu.add(Menu.NONE, IDM_OVAL, Menu.NONE, "Oval");
        menu.add(Menu.NONE, IDM_RECT, Menu.NONE, "Rectangle");
        menu.add(Menu.NONE, IDM_ROUNDRECT, Menu.NONE, "Round Rect. Fill");
        menu.add(Menu.NONE, IDM_STAR, Menu.NONE, "Path");
        menu.add(Menu.NONE, IDM_ARC, Menu.NONE, "Arc");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ShapeDrawable d = null;
        switch (item.getItemId()) {
            case IDM_LINE:
                d = new ShapeDrawable(new RectShape());
                d.getPaint().setColor(Color.MAGENTA);
                d.getPaint().setStrokeWidth(5);
                d.getPaint().setStyle(Paint.Style.STROKE);
                break;
            case IDM_OVAL:
                d = new ShapeDrawable(new OvalShape());
                d.getPaint().setColor(Color.RED);
                break;
            case IDM_RECT:
                d = new ShapeDrawable(new RectShape());
                d.getPaint().setColor(Color.BLUE);
                break;
            case IDM_ROUNDRECT:
                float[] radii = {25, 25, 25, 25, 25, 25, 25, 25};
                d = new ShapeDrawable(new RoundRectShape(radii, null, null));
                d.getPaint().setColor(Color.WHITE);
                break;
            case IDM_STAR:
                Path p = new Path();
                p.moveTo(50, 0);
                p.lineTo(20, 100);
                p.lineTo(80, 40);
                p.lineTo(0, 40);
                p.lineTo(60, 100);
                p.close();
                d = new ShapeDrawable(new PathShape(p, 100, 100));
                d.getPaint().setColor(Color.YELLOW);
                d.getPaint().setStyle(Paint.Style.STROKE);
                d.getPaint().setStrokeWidth(5);
                break;
            case IDM_ARC:
                d = new ShapeDrawable(new ArcShape(0, 180));
                d.getPaint().setColor(Color.GREEN);
                break;
        }
        if (d != null) {
            mImage.setBackground(d);
        }
        return true;
    }
}