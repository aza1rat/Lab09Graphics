package com.example.lab09_graphicskashitsin;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import math.arr;
import math.interp;

public class MySurface extends SurfaceView implements OnTouchListener {
    float startX = 0, startY = 0;
    float xmin, xmax, ymin, ymax;
    float[] x, y;
    int n;
    Paint p;
    

    public void update()
    {
        xmin = arr.min(x, n);
        xmax = arr.max(x, n);
        ymin = arr.min(y, n);
        ymax = arr.max(y, n);
    }

    public MySurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        p.setColor(Color.RED);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw (Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        float x0 = 0.0f, y0 = 0.0f;
        for(int i = 0; i < n; i++)
        {
            float x1 = interp.map(x[i], xmin, xmax, 0, w - 1);
            float y1 = interp.map(y[i], ymin, ymax, h-1, 0);
            if (i > 0)
                canvas.drawLine(x0, y0, x1, y1, p);
            x0 = x1;
            y0 = y1;
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startX = view.getX() - motionEvent.getRawX();
                startY = view.getY() - motionEvent.getRawY();
            case MotionEvent.ACTION_MOVE:
                view.animate()
                        .x(motionEvent.getRawX() + startX)
                        .y(motionEvent.getRawY() + startY)
                        .setDuration(0)
                        .start();
                break;
            default:return false;
        }
        return true;
    }

}