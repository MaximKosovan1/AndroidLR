package com.example.lr9paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Stack;

public class PaintView extends View {
    private Bitmap bmp;
    private Canvas bmpCanvas;
    private Paint paintForLine;
    private HashMap<Integer, Path> pathMap;
    private HashMap<Integer, Point> previousPointMap;

    private Stack<Bitmap> undoStack = new Stack<>();
    private Stack<Bitmap> redoStack = new Stack<>();

    public void init(Context context) {
        paintForLine = new Paint();
        paintForLine.setColor(Color.BLUE);
        paintForLine.setAntiAlias(true);
        paintForLine.setStrokeWidth(6);
        paintForLine.setStyle(Paint.Style.STROKE);
        paintForLine.setStrokeCap(Paint.Cap.ROUND);

        pathMap = new HashMap<>();
        previousPointMap = new HashMap<>();
    }
    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bmpCanvas = new Canvas(bmp);
        bmp.eraseColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, 0, 0, null);
        for (Path path : pathMap.values()) {
            canvas.drawPath(path, paintForLine);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int actionIndex = event.getActionIndex();
        int pointerId = event.getPointerId(actionIndex);

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            saveState();
            touchStarted(event.getX(actionIndex), event.getY(actionIndex), pointerId);
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            touchEnded(pointerId);
        } else {
            touchMoved(event);
        }

        invalidate();
        return true;
    }

    private void saveState() {
        undoStack.push(Bitmap.createBitmap(bmp));
        redoStack.clear();
    }

    private void touchStarted(float x, float y, int lineID) {
        Path path = new Path();
        path.moveTo(x, y);
        pathMap.put(lineID, path);
        previousPointMap.put(lineID, new Point((int) x, (int) y));
    }

    private void touchMoved(MotionEvent event) {
        for (int i = 0; i < event.getPointerCount(); i++) {
            int pointerId = event.getPointerId(i);
            float x = event.getX(i);
            float y = event.getY(i);

            Path path = pathMap.get(pointerId);
            Point point = previousPointMap.get(pointerId);

            if (path != null && point != null) {
                path.quadTo(point.x, point.y, (x + point.x) / 2, (y + point.y) / 2);
                point.x = (int) x;
                point.y = (int) y;
            }
        }
    }

    private void touchEnded(int lineID) {
        Path path = pathMap.get(lineID);
        if (path != null) {
            bmpCanvas.drawPath(path, paintForLine);
            path.reset();
        }
    }

    public void setLineWidth(int width) {
        paintForLine.setStrokeWidth(width);
    }

    public void setLineColor(int color) {
        paintForLine.setColor(color);
    }

    public void setBackgroundImage(Bitmap bitmap) {
        bmpCanvas.drawBitmap(bitmap, 0, 0, null);
        invalidate();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(Bitmap.createBitmap(bmp));
            bmp = undoStack.pop();
            bmpCanvas = new Canvas(bmp);
            invalidate();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(Bitmap.createBitmap(bmp));
            bmp = redoStack.pop();
            bmpCanvas = new Canvas(bmp);
            invalidate();
        }
    }
}
