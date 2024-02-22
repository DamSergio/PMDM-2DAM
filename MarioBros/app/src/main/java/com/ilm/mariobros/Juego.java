package com.ilm.mariobros;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ilm.mariobros.controles.Control;
import com.ilm.mariobros.controles.Toque;

import java.io.IOException;
import java.util.ArrayList;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private SurfaceHolder holder;
    private BucleJuego bucle;
    private Bitmap mapa;
    private float mapa_w, mapa_h, mario_w, mario_h;
    private float maxX, maxY;
    private final int X = 0;
    private final int Y = 1;
    private final int IZQ = 0;
    private final int DER = 1;
    private final int ARR = 2;
    private final int TIEMPO_CRUCE_PANTALLA = 10;
    private int movMapa = 0;
    private int estado = 0;

    // VECTORES
    private float posicionMapa[] = new float[2];
    private float posicionMario[] = new float[2]; // VECTOR M DE POSICION
    private float velocidadMario[] = new float[2]; // VECTOR V (VX, VY)
    private float gravedadMario[] = new float[2]; // VECTOR G (GX, GY)
    private float posicionInicialMario[] = new float[2];
    private float deltaT; // TIEMPO ENTRE CADA FRAME
    private Bitmap mario;
    private boolean hayToque;
    private Control[] controles = new Control[3];
    private ArrayList<Toque> toques = new ArrayList<>();
    private boolean saltando = false;
    private MediaPlayer mp;
    private MediaPlayer fin;
    private MediaPlayer salto;
    private AppCompatActivity actividad;
    private boolean finJuego = false;
   
    private static final String TAG = Juego.class.getSimpleName();

    public Juego(Activity context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    public void inicializar() {
        mp = MediaPlayer.create(getContext(), R.raw.mario);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.start();
            }
        });

        salto = MediaPlayer.create(getContext(), R.raw.salto);

        fin = MediaPlayer.create(getContext(), R.raw.levelcomplete);

        mapa = BitmapFactory.decodeResource(getResources(), R.drawable.mapamario);
        mapa_h = mapa.getHeight();
        mapa_w = mapa.getWidth();

        Canvas c=holder.lockCanvas();
        maxX = c.getWidth();
        maxY = c.getHeight();
        holder.unlockCanvasAndPost(c);

        // DELTAT = 1 / MAX_FPS
        deltaT = 1.0f / BucleJuego.MAX_FPS;

        // COORDENADA INICIALES MAPA
        posicionMapa[X] = 0;
        posicionMapa[Y] = (maxY-mapa_h)/2;

        // MARIO
        mario = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
        mario_w = mario.getWidth();
        mario_h = mario.getHeight();

        velocidadMario[X] = maxX / TIEMPO_CRUCE_PANTALLA; // ESPACIO ENTRE TIEMPO (en segundos)
        velocidadMario[Y] = 0;

        // GRAVEDAD
        gravedadMario[X] = 0;
        gravedadMario[Y] = 0;

        // COORDENADA INICIALES MARIO
        posicionInicialMario[X] = maxX * 0.1f;
        posicionInicialMario[Y] = (posicionMapa[Y] + (mapa_h * 0.9f)) - mario_h / 3 * 2;

        posicionMario[X] = posicionInicialMario[X];
        posicionMario[Y] = posicionInicialMario[Y];

        // CONTROLES

        controles[IZQ] = new Control(getContext(), 0, maxY - (maxY-mapa_h)/2 + 10);
        controles[IZQ].cargar(R.drawable.flecha_izda);

        controles[DER] = new Control(getContext(), controles[IZQ].ancho() + 10, maxY - (maxY-mapa_h)/2 + 10);
        controles[DER].cargar(R.drawable.flecha_dcha);

        controles[ARR] = new Control(getContext(), maxX - controles[DER].ancho(), maxY - (maxY-mapa_h)/2 + 10);
        controles[ARR].cargar(R.drawable.flecha_up);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // se crea la superficie, creamos el game loop

        // Para interceptar los eventos de la SurfaceView
        getHolder().addCallback(this);

        inicializar();

        // creamos el game loop
        bucle = new BucleJuego(getHolder(), this);

        // Hacer la Vista focusable para que pueda capturar eventos
        setFocusable(true);

        //comenzar el bucle
        bucle.start();
        hayToque = false;
        setOnTouchListener(this);
    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    public void actualizar() {
        if (finJuego) {
            return;
        }

        float posMario = posicionMario[X];

        // ESTA SALTANDO
        if (saltando) {
            velocidadMario[Y] += deltaT * gravedadMario[Y];
            posicionMario[Y] += deltaT * velocidadMario[Y];
        }

        // IZQUIERDA
        if (controles[IZQ].pulsado) {
            posicionMario[X] -= deltaT * velocidadMario[X];
            estado++;
        }

        //controles[DER].pulsado = true; // FEO: BORRAR LUEGO
        // DERECHA
        if (controles[DER].pulsado) {
            posicionMario[X] += deltaT * velocidadMario[X];
            estado++;
        }

        // SALTO
        //if (!saltando) // -> ACTIVAR PARA SALTAR SOLO UNA VEZ
            if (controles[ARR].pulsado) {
                saltando = true;
                velocidadMario[Y] = -velocidadMario[X] * 2;
                gravedadMario[Y] = -velocidadMario[Y];

                salto.start();
            }


        // REINICIAR EL ESTADO MIENTRAS SE MUEVE
        if (estado > 3) {
            estado = 1;
        }

        // ESTA QUIETO
        if (!hayToque) {
            estado = 0;
        }

        // HA LLEGADO AL 70% DE LA PANTALLA Y SE MUEVE EL FONDO
        if (posicionMario[X] >= maxX * 0.7 && movMapa <= mapa_w - maxX) {
            posicionMario[X] = posMario;
            movMapa += velocidadMario[X] * deltaT;
        }

        // HA LLEGADO AL 70% DE LA PANTALLA Y SE MUEVE EL FONDO
        if (posicionMario[X] <= maxX * 0.3 && movMapa >= 0) {
            posicionMario[X] = posMario;
            movMapa -= velocidadMario[X] * deltaT;
        }

        // TOCA EL SUELO
        if (posicionMario[Y] > posicionInicialMario[Y]) {
            posicionMario[Y] = posicionInicialMario[Y];
            gravedadMario[Y] = 0;
            velocidadMario[Y] = 0;
            saltando = false;

            salto.stop();
            try {
                salto.prepare();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // LIMITE PANTALLA izquierda
        if (posicionMario[X] <= 0) {
            posicionMario[X] = posMario;
        }

        // LIMITE PANTALLA DERECHA
        if (posicionMario[X] + (mario_w / 21) >= maxX) {
            posicionMario[X] = posMario;
        }

        // FIN JUEGO
        if (posicionMario[X] >= 1880 && posicionMario[X] <= 1880 + (mario_w / 21) / 2 && posicionMario[Y] >= posicionInicialMario[Y]) {
            mp.stop();
            finJuego = true;
            estado = 0;
            fin.start();
        }
    }

    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {
        if (canvas == null) {
            return;
        }

        canvas.drawColor(Color.BLACK);

        //pintar mensajes que nos ayudan
        Paint p=new Paint();
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.RED);
        p.setTextSize(50);
        canvas.drawText("Frame "+bucle.iteraciones+";"+"Tiempo "+bucle.tiempoTotal + " ["+bucle.maxX+","+bucle.maxY+"]" + " coordXMario: " + posicionMario[X],50,150,p);

        // MAPA
        Rect rectMapa = new Rect(0 + movMapa, 0, (int) maxX + movMapa, (int) mapa_h); // RECTANGULO QUE CORTO DEL BITMAP
        RectF rectDestinoPantallaMapa = new RectF(posicionMapa[X] , posicionMapa[Y], maxX, mapa_h+posicionMapa[Y]); // RECTANGULO DE LA PANTALLA DONDE DIBUJO
        canvas.drawBitmap(mapa,
                rectMapa,
                rectDestinoPantallaMapa,
                null
        );

        // MARIO
        Rect rectMario = new Rect((int)(estado * (mario_w / 21)), 0, (int) ((estado + 1) * (mario_w / 21)), (int) mario_h / 3 * 2) ;
        RectF rectDestinoPantallaMario = new RectF(posicionMario[X], posicionMario[Y], posicionMario[X] + mario_w / 21, posicionMario[Y] + mario_h / 3 * 2);
        canvas.drawBitmap(
                mario,
                rectMario,
                rectDestinoPantallaMario,
                null
        );

        // CONTROLES
        for (int i = 0; i < controles.length; i++) {
            controles[i].dibujar(canvas, p);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Juego destruido!");
        // cerrar el thread y esperar que acabe
        boolean retry = true;
        while (retry) {
            try {
                bucle.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int index;
        int x,y;

        // Obtener el pointer asociado con la acción
        index = event.getActionIndex();


        x = (int) event.getX(index);
        y = (int) event.getY(index);

        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                hayToque=true;

                synchronized(this) {
                    toques.add(index, new Toque(index, x, y));
                }

                //se comprueba si se ha pulsado
                for(int i=0;i<controles.length;i++)
                    controles[i].compruebaPulsado(x,y);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                synchronized(this) {
                    toques.remove(index);
                }

                //se comprueba si se ha soltado el botón
                for(int i=0;i<controles.length;i++)
                    controles[i].compruebaSoltado(toques);
                break;

            case MotionEvent.ACTION_UP:
                synchronized(this) {
                    toques.clear();
                }
                hayToque=false;
                //se comprueba si se ha soltado el botón
                for(int i=0;i<controles.length;i++)
                    controles[i].compruebaSoltado(toques);
                break;
        }

        return true;
    }
}
