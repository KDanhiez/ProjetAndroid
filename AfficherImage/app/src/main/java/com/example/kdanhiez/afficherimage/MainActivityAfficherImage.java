package com.example.kdanhiez.afficherimage;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityAfficherImage extends AppCompatActivity {

    private Button grayButton;
    private Button spookyButton;
    private Button resetButton;
    private ImageView imageViewChien;
    private Bitmap imageChien;
    private Bitmap imageSpook;
    private Bitmap imageBackup;
    private TextView textHello;
    private BitmapFactory.Options o;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_afficher_image);

        imageViewChien = findViewById(R.id.chien);

        grayButton = findViewById(R.id.grayButton);
        spookyButton = findViewById(R.id.spookyButton);
        resetButton = findViewById(R.id.resetButton);
        textHello = findViewById(R.id.helloworld);

        //option pour créer des bitmaps modifiables sans changement d'échelle
        o = new BitmapFactory.Options();
        o.inScaled = false;
        o.inMutable = true;

        //récupération bitmap depuis un fichier stocké dans le répertoire de ressources
        imageChien = BitmapFactory.decodeResource(getResources(), R.drawable.lumberjack_dog, o);
        imageSpook = BitmapFactory.decodeResource(getResources(), R.drawable.spooky_dog, o);
        imageBackup = BitmapFactory.decodeResource(getResources(), R.drawable.lumberjack_dog, o);
        imageViewChien.setImageBitmap(imageChien);

        View.OnClickListener grayButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGray(imageChien);
            }
        };

        View.OnClickListener spookyButtonListener = new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                imageViewChien.setImageBitmap(imageSpook);
                colorizeToSpook(imageSpook);
            }
        };

        View.OnClickListener resetButtonListener = new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                imageViewChien.setImageBitmap(imageChien);
                toReset(imageChien,imageBackup);
            }
        };

        grayButton.setOnClickListener(grayButtonListener);
        spookyButton.setOnClickListener(spookyButtonListener);
        resetButton.setOnClickListener(resetButtonListener);


        textHello.setText("width : " + imageChien.getWidth() + " height : " + imageChien.getHeight() + "\nWoof woof");
    }

    private void toGray(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getWidth();
        for (int x = 0; x < w; ++x){
            for (int y = 0; y < h; ++y){
                int col = bmp.getPixel(x,y);
                int r = Color.red(col);
                int g = Color.green(col);
                int b = Color.blue(col);
                int gl = (int) (r*0.3 + g*0.59 + b*0.11);
                bmp.setPixel(x,y,Color.rgb(gl,gl,gl));
            }
        }
    }

    private void toReset(Bitmap bmp,Bitmap backup){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int col = backup.getPixel(i, j);
                bmp.setPixel(i, j, col);
            }
        }
    }

    /* Colorise avec une nuance aléatoire

    void colorize(Bitmap Bmp){
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w*h];
        int c = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        int avgcolor = 0;
        float rand = (float) Math.random() * 360;
        float[] hsv = new float[3];
        Bmp.getPixels(pixels,0,w,0,0,w,h);
        for(int i = 0 ; i < pixels.length ; i++){
            c = pixels[i];
            r = Color.red(c);
            g = Color.green(c);
            b = Color.blue(c);
            Color.RGBToHSV(r,g,b,hsv);
            hsv[0] = rand;      //hsv[0] a la valeur de la nuance souhaitée (de 0 à 360, 0 = rouge)
            pixels[i] = Color.HSVToColor(hsv);
        }
        Bmp.setPixels(pixels,0,w,0,0,w,h);
    }
    */

    void colorizeToSpook(Bitmap Bmp){
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w*h];
        int c = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        int avgcolor = 0;
        float[] hsv = new float[3];
        Bmp.getPixels(pixels,0,w,0,0,w,h);
        for(int i = 0 ; i < pixels.length ; i++){
            c = pixels[i];
            r = (int)(Color.red(c) * 0.8);
            g = (int)(Color.green(c) * 0.6);
            b = (int)(Color.blue(c) * 0.6);
            Color.RGBToHSV(r,g,b,hsv);
            hsv[0] = 0;
            pixels[i] = Color.HSVToColor(hsv);
        }
        Bmp.setPixels(pixels,0,w,0,0,w,h);
    }
}