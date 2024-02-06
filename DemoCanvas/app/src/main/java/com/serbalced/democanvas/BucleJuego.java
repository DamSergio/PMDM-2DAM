package com.serbalced.democanvas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class BucleJuego extends Thread{
    private SurfaceView surfaceView;

    public BucleJuego(SurfaceView sv) {
        this.surfaceView = sv;
    }

    @Override
    public void run() {
        super.run();

        // INICIALIZO LAS VARIABLES DEL GUEGO
        Canvas canvas = surfaceView.getHolder().lockCanvas();
        float max_x = canvas.getWidth(); // ancho (eje x)
        float max_y = canvas.getHeight(); // alto (eje y)
        surfaceView.getHolder().unlockCanvasAndPost(canvas);

        // CALCULO LAS COORDINADAS INICIALES
        float y = max_y;

        // CARGO LOS OBJETOS
        Bitmap robot = BitmapFactory.decodeResource(surfaceView.getResources() ,R.drawable.robot1);

        while (true) {
            canvas = surfaceView.getHolder().lockCanvas();;

            // RENDERIZAMOS
            canvas.drawColor(Color.BLACK);

            Paint miPincel = new Paint();
            miPincel.setStyle(Paint.Style.STROKE);
            miPincel.setColor(Color.BLUE);
            miPincel.setStrokeWidth(10);
            miPincel.setTextSize(100);

            canvas.drawText("Hola mundo!", 100, 100, miPincel);
            canvas.drawCircle(max_x / 2, max_y / 2, 200, miPincel);

            canvas.drawBitmap(robot, max_x / 2, y - robot.getHeight(), null);
            //FIN DE RENDERIZAR

            // ACTUALIZAR COORDENADAS
            y -= 10;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            surfaceView.getHolder().unlockCanvasAndPost(canvas);
        }
    }
}
