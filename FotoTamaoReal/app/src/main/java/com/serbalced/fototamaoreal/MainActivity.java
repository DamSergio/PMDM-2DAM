package com.serbalced.fototamaoreal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public final int CAPTURA = 1;
    public final int PERMISO_STORAGE = 2;
    public String mRutaDefinitiva;
    Uri foto_uri;
    SeekBar sb;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFoto = findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Compruebo los permisos
                if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISO_STORAGE);
                } else {
                    hacerFoto();
                }
            }
        });

        sb = findViewById(R.id.seekBar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //resizeDecoder();
                resizeScaled();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void resizeScaled() {
        // Calcular factor de escalado en porcentaje: 100%->tamaño original
        int progreso = sb.getProgress();
        // cargar el bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(mRutaDefinitiva, bmOptions);
        int anchoOriginal = bmOptions.outWidth;
        int altoOriginal = bmOptions.outHeight;
        try {
            Bitmap nuevoBitmap = bitmap.createScaledBitmap(
                    bitmap,
                    anchoOriginal * progreso / 100,
                    altoOriginal * progreso / 100, false);
            int altoFinal = nuevoBitmap.getHeight();
            int anchoFinal = nuevoBitmap.getWidth();
            imageView.getLayoutParams().height = altoFinal;
            imageView.getLayoutParams().width = anchoFinal;
            imageView.setImageBitmap(nuevoBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resizeDecoder() {
        int scaleFactor=0;
        // Calcular factor de escalado: 1->100, 2->50, 4->25, ...
        int progreso=sb.getProgress();
        if(progreso!=0)
            scaleFactor = 100/sb.getProgress();
        else
            scaleFactor=Integer.MAX_VALUE;
        BitmapFactory.Options bmOptions =
                new BitmapFactory.Options();
        // El decoder solo escala en potencias de 2
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap =
                BitmapFactory.decodeFile(mRutaDefinitiva, bmOptions);
        // Calculamos el alto y ancho final de la imagen
        int anchoFinal=bmOptions.outWidth;
        int altoFinal=bmOptions.outHeight;
        // Redimensionamos el view donde se mostrará la imagen
        imageView.getLayoutParams().height=altoFinal;
        imageView.getLayoutParams().width=anchoFinal;
        imageView.setImageBitmap(bitmap);
    }

    public void addToGallery(){
        File f = new File(mRutaDefinitiva);
        MediaScannerConnection.scanFile(this,
                new String[]{f.toString()},
                null, null);
    }

    public void hacerFoto() {
        // Crear el intent
        Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Crear un archivo temporal
        File fichero_temporal;
        try {
            fichero_temporal = crearFichero();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (fichero_temporal != null) {
            foto_uri =  FileProvider.getUriForFile(getApplicationContext(),
                    getBaseContext().getPackageName() + ".provider",
                    fichero_temporal
            );

            // Cargar en los extras el intent
            intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, foto_uri);
            startActivityForResult(intentFoto, CAPTURA);
        }
    }

    public File crearFichero() throws IOException {
        String tiempo=new
                SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombre_fichero="JPEG_"+tiempo+"_";
        File directorio_almacenaje=
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
        File image=File.createTempFile(nombre_fichero,".jpg",directorio_almacenaje);
        mRutaDefinitiva=image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISO_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hacerFoto();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), foto_uri);
            Bitmap bitmap = ImageDecoder.decodeBitmap(source);
            imageView=findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        addToGallery();
    }
}