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
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

public class MainActivityAfficherImage extends AppCompatActivity {

    private ImageView imageViewChien;
    private ImageView imageViewColor;
    private Bitmap imageChien;
    private Bitmap imageColor;
    private Bitmap imageSpook;
    private Bitmap imageChienBackup;
    private Bitmap imageColorBackup;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_afficher_image);

        imageViewChien = findViewById(R.id.chien);
        imageViewColor = findViewById(R.id.color_spectrum);

        Button grayButton = findViewById(R.id.grayButton);
        Button spookyButton = findViewById(R.id.spookyButton);
        Button resetButton = findViewById(R.id.resetButton);
        Button randomHueButton = findViewById(R.id.randomHueButton);
        Button randomColorKeepButton = findViewById(R.id.randomColorKeepButton);
        Button invertButton = findViewById(R.id.invertButton);
        Button brightnessButton = findViewById(R.id.brightnessButton);
        TextView textHello = findViewById(R.id.helloworld);

        //option pour créer des bitmaps modifiables sans changement d'échelle
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;
        o.inMutable = true;

        //récupération bitmap depuis un fichier stocké dans le répertoire de ressources
        imageChien = BitmapFactory.decodeResource(getResources(), R.drawable.lumberjack_dog, o);
        imageColor = BitmapFactory.decodeResource(getResources(), R.drawable.color_spectrum, o);
        imageSpook = BitmapFactory.decodeResource(getResources(), R.drawable.spooky_dog, o);
        imageChienBackup = BitmapFactory.decodeResource(getResources(), R.drawable.lumberjack_dog, o);
        imageColorBackup = BitmapFactory.decodeResource(getResources(), R.drawable.color_spectrum, o);
        imageViewChien.setImageBitmap(imageChien);
        imageViewColor.setImageBitmap(imageColor);

        View.OnClickListener grayButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGreyRS(imageChien);
                toGreyRS(imageColor);
            }
        };

        View.OnClickListener spookyButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewChien.setImageBitmap(imageSpook);
                colorizeToSpook(imageSpook);
            }
        };

        View.OnClickListener randomHueButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewChien.setImageBitmap(imageChien);
                imageViewColor.setImageBitmap(imageColor);
                randomHue(imageChien);
                randomHue(imageColor);
            }
        };

        View.OnClickListener resetButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewChien.setImageBitmap(imageChien);
                imageViewColor.setImageBitmap(imageColor);
                toReset(imageChien, imageChienBackup);
                toReset(imageColor, imageColorBackup);
            }
        };

        View.OnClickListener randomColorKeepButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewChien.setImageBitmap(imageChien);
                imageViewColor.setImageBitmap(imageColor);
                float color = (float)Math.random();
                randomColorKeepRS(imageChien,color);
                randomColorKeepRS(imageColor,color);
            }
        };

        View.OnClickListener invertButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewChien.setImageBitmap(imageChien);
                imageViewColor.setImageBitmap(imageColor);
                invertRS(imageChien);
                invertRS(imageColor);
            }
        };

        View.OnClickListener brightnessButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewChien.setImageBitmap(imageChien);
                imageViewColor.setImageBitmap(imageColor);
                brightnessRS(imageChien);
                brightnessRS(imageColor);
            }
        };

        grayButton.setOnClickListener(grayButtonListener);
        spookyButton.setOnClickListener(spookyButtonListener);
        resetButton.setOnClickListener(resetButtonListener);
        randomHueButton.setOnClickListener(randomHueButtonListener);
        randomColorKeepButton.setOnClickListener(randomColorKeepButtonListener);
        invertButton.setOnClickListener(invertButtonListener);
        brightnessButton.setOnClickListener(brightnessButtonListener);


        textHello.setText("Image du chien :\nwidth : " + imageChien.getWidth() + " height : " + imageChien.getHeight());
    }

    /*
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
    */

    private void toGreyRS ( Bitmap bmp ) {
        // 1) Creer un contexte RenderScript
        RenderScript rs = RenderScript.create ( this ) ;

        // 2) Creer des Allocations pour passer les donnees
        Allocation input = Allocation . createFromBitmap ( rs , bmp ) ;
        Allocation output = Allocation . createTyped ( rs , input.getType () ) ;

        // 3) Creer le script
        ScriptC_grey greyScript = new ScriptC_grey ( rs ) ;

        // 4) Copier les donnees dans les Allocations
        // ...

        // 5) Initialiser les variables globales potentielles
        // ...

        // 6) Lancer le noyau
        greyScript.forEach_toGrey ( input , output ) ;

        // 7) Recuperer les donnees des Allocation (s)
        output.copyTo ( bmp ) ;

        // 8) Detruire le context , les Allocation (s) et le script
        input.destroy () ; output.destroy () ;
        greyScript.destroy () ; rs.destroy () ;
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

    // Colorise avec une nuance aléatoire

    void randomHue(Bitmap Bmp) {
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w * h];
        int c, r, g, b;
        float rand = (float) Math.random() * 360;
        float[] hsv = new float[3];
        Bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        for (int i = 0; i < pixels.length; i++) {
            c = pixels[i];
            r = Color.red(c);
            g = Color.green(c);
            b = Color.blue(c);
            Color.RGBToHSV(r, g, b, hsv);
            hsv[0] = rand;      //hsv[0] a la valeur de la nuance souhaitée (de 0 à 360, 0 = rouge)
            pixels[i] = Color.HSVToColor(hsv);
        }
        Bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    /*
    private void colorizeRandomRS ( Bitmap bmp ) {
        // 1) Creer un contexte RenderScript
        RenderScript rs = RenderScript.create ( this ) ;

        // 2) Creer des Allocations pour passer les donnees
        Allocation input = Allocation . createFromBitmap ( rs , bmp ) ;
        Allocation output = Allocation . createTyped ( rs , input.getType () ) ;

        // 3) Creer le script
        ScriptC_randomColor randomColorScript = new ScriptC_randomColor ( rs ) ;

        // 4) Copier les donnees dans les Allocations
        // ...

        // 5) Initialiser les variables globales potentielles
        // ...

        // 6) Lancer le noyau
        randomColorScript.forEach_randomColor ( input , output ); ;

        // 7) Recuperer les donnees des Allocation (s)
        output.copyTo ( bmp ) ;

        // 8) Detruire le context , les Allocation (s) et le script
        input.destroy () ; output.destroy () ;
        randomColorScript.destroy () ; rs.destroy () ;
    }
    */

    //Affiche l'image du chien effrayant en assombrissant l'image à chaque utilisation du bouton

    void colorizeToSpook(Bitmap Bmp){
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w*h];
        int c, r, g, b;
        float[] hsv = new float[3];
        Bmp.getPixels(pixels,0,w,0,0,w,h);
        for(int i = 0 ; i < pixels.length ; i++){
            c = pixels[i];
            r = (int)(Color.red(c) * 0.8);      //On diminue moins le rouge pour donner de l'effet
            g = (int)(Color.green(c) * 0.6);
            b = (int)(Color.blue(c) * 0.6);
            Color.RGBToHSV(r,g,b,hsv);
            hsv[0] = 0;
            pixels[i] = Color.HSVToColor(hsv);
        }
        Bmp.setPixels(pixels,0,w,0,0,w,h);
    }

    //Garde une plage de couleur aléatoire parmi le rouge, le vert ou le bleu
    void randomColorKeepRS(Bitmap bmp, float color) {
        // 1) Creer un contexte RenderScript
        RenderScript rs = RenderScript.create(this);

        // 2) Creer des Allocations pour passer les donnees
        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());

        // 3) Creer le script
        ScriptC_colorKeep colorKeepScript = new ScriptC_colorKeep(rs);

        // 4) Copier les donnees dans les Allocations
        // ...

        // 5) Initialiser les variables globales potentielles
        colorKeepScript.set_color(color);

        // 6) Lancer le noyau
        colorKeepScript.forEach_colorKeep(input, output);
        ;

        // 7) Recuperer les donnees des Allocation (s)
        output.copyTo(bmp);

        // 8) Detruire le context , les Allocation (s) et le script
        input.destroy();
        output.destroy();
        colorKeepScript.destroy();
        rs.destroy();
    }

    //Augmente la luminosité des images
    private void brightnessRS ( Bitmap bmp ) {
        // 1) Creer un contexte RenderScript
        RenderScript rs = RenderScript.create ( this ) ;

        // 2) Creer des Allocations pour passer les donnees
        Allocation input = Allocation . createFromBitmap ( rs , bmp ) ;
        Allocation output = Allocation . createTyped ( rs , input.getType () ) ;

        // 3) Creer le script
        ScriptC_brightness brightnessScript = new ScriptC_brightness ( rs ) ;

        // 4) Copier les donnees dans les Allocations
        // ...

        // 5) Initialiser les variables globales potentielles
        // ...

        // 6) Lancer le noyau
        brightnessScript.forEach_brightness ( input , output );

        // 7) Recuperer les donnees des Allocation (s)
        output.copyTo ( bmp ) ;

        // 8) Detruire le context , les Allocation (s) et le script
        input.destroy () ; output.destroy () ;
        brightnessScript.destroy () ; rs.destroy () ;
    }

    //Inverse les couleurs des images
    private void invertRS ( Bitmap bmp ) {
        // 1) Creer un contexte RenderScript
        RenderScript rs = RenderScript.create ( this ) ;

        // 2) Creer des Allocations pour passer les donnees
        Allocation input = Allocation . createFromBitmap ( rs , bmp ) ;
        Allocation output = Allocation . createTyped ( rs , input.getType () ) ;

        // 3) Creer le script
        ScriptC_invert invertScript = new ScriptC_invert ( rs ) ;

        // 4) Copier les donnees dans les Allocations
        // ...

        // 5) Initialiser les variables globales potentielles
        // ...

        // 6) Lancer le noyau
        invertScript.forEach_invert ( input , output ); ;

        // 7) Recuperer les donnees des Allocation (s)
        output.copyTo ( bmp ) ;

        // 8) Detruire le context , les Allocation (s) et le script
        input.destroy () ; output.destroy () ;
        invertScript.destroy () ; rs.destroy () ;
    }

    /*

    void contrast(Bitmap Bmp){
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w*h];
        int c, r, g, b;
        Bmp.getPixels(pixels,0,w,0,0,w,h);
        for(int i = 0 ; i < pixels.length ; i++){

        }
    }

    */

}