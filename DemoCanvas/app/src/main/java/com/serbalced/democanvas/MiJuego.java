package com.serbalced.democanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MiJuego extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder sh;

    public MiJuego(Context context) {
        super(context);

        sh = getHolder();
        sh.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new BucleJuego(this).start();


//        // CIRCULOS
//        canvas.drawCircle(max_x / 4, max_y - max_x / 4, max_x / 4, miPincel);
//        canvas.drawCircle(max_x - max_x / 4, max_y - max_x / 4, max_x / 4, miPincel);
//
//        // TRONCO
//        canvas.drawRect(max_x / 4, max_x / 2, max_x - max_x / 4, max_y - max_x / 4, miPincel);
//
//        // ELIPSE
//        //canvas.drawCircle(max_x / 2, (max_x / 4) * 2, max_x / 4, miPincel);
//        miPincel.setColor(Color.rgb(255, 192, 255));
//        RectF oval = new RectF(max_x / 4, max_x / 4, max_x - max_x / 4, max_x - max_x / 4);
//        canvas.drawArc(oval, 0, -180, true, miPincel);
//
//        miPincel.setStyle(Paint.Style.STROKE);
//        miPincel.setColor(Color.BLACK);
//        canvas.drawLine(max_x / 2, max_x / 4 - 10, max_x / 2, max_x / 4 + 100, miPincel);
//
//        canvas.drawCircle(max_x / 4, max_y - max_x / 4, max_x / 4, miPincel);
//        canvas.drawCircle(max_x - max_x / 4, max_y - max_x / 4, max_x / 4, miPincel);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
