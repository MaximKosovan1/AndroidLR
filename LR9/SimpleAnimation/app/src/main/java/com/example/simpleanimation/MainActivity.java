package com.example.simpleanimation;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int MENU_ALPHA_ID = 1;
    private static final int MENU_SCALE_ID = 2;
    private static final int MENU_TRANSLATE_ID = 3;
    private static final int MENU_ROTATE_ID = 4;
    private static final int MENU_COMBO_ID = 5;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);

        registerForContextMenu(tv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, MENU_ALPHA_ID, 0, "Alpha");
        menu.add(0, MENU_SCALE_ID, 0, "Scale");
        menu.add(0, MENU_TRANSLATE_ID, 0, "Translate");
        menu.add(0, MENU_ROTATE_ID, 0, "Rotate");
        menu.add(0, MENU_COMBO_ID, 0, "Combo");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Animation anim = null;

        switch (item.getItemId()) {
            case MENU_ALPHA_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
                break;
            case MENU_SCALE_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.myscale);
                break;
            case MENU_TRANSLATE_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.mytrans);
                break;
            case MENU_ROTATE_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.myrotate);
                break;
            case MENU_COMBO_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
                break;
        }

        if (anim != null) {
            tv.startAnimation(anim);
        }

        return true;
    }
}
