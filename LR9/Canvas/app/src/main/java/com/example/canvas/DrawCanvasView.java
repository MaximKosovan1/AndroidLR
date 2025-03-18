package com.example.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;

class DrawCanvasView extends View {
    // Константи, що визначають початкову координату об’єкта
    private static final int START_X = 10;
    private static final int START_Y = 10;
    private ShapeDrawable mDrawable;
    public DrawCanvasView(Context context) {
        super(context);
        setFocusable(true);
        mDrawable = new ShapeDrawable();
    }
    // Метод, що завантажує об’єкт ShapeDrawable для рисування
    public void setDrawable(ShapeDrawable shape) {
        mDrawable = shape;
// Прив’язуємо об’єкт ShapeDrawable
        mDrawable.setBounds(START_X, START_Y,
                START_X + mDrawable.getIntrinsicWidth(),
                START_Y + mDrawable.getIntrinsicHeight() );
// Вимагаємо перерисування графіки
        invalidate();
    }
    // Перерисування графічного об’єкту
    @Override
    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
    }
}
