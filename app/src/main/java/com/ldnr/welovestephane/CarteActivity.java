package com.ldnr.welovestephane;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CarteActivity extends Activity {

    //SE RELIER A UNE VUE
    // 1 RELIER OBJET VUE à L'ACTIVITE :
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CarteView(this));
        // Fait apparaitre un log
        Log.i("CarteActivity","Activity Create");
        // Fait apparaitre un toast donc un message quand on lance l'application
        Toast.makeText(this,
                getString(R.string.map_welcome_msg)
                ,Toast.LENGTH_LONG).show();
    }
    // Fonction pour changer d'activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            // pour que ça marche que quand on clique en haut à droite
            int largeur = findViewById(android.R.id.content).getWidth();
            int longueur = findViewById(android.R.id.content).getHeight();
            if ((event.getX() > largeur / 2) && (event.getY() < longueur / 2)) {
                // Pas le droit d'instancier une activity donc on laisse android le faire
                Intent i = new Intent(this, AquariumActivity.class); // voila mon attention
                //je part de this pour aller vers l'activity Aquarium
                startActivity(i);
            } else {
                Intent i = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://fr.wikipedia.org/wiki/St%C3%A9phane_Bern#:~:text=St%C3%A9phane%20Bern%2C%20n%C3%A9%20le%2014,acteur%20et%20%C3%A9crivain%20franco%2Dluxembourgeois.&text=St%C3%A9phane%20Bern%20en%202012%20lors,et%20de%20St%C3%A9phanie%20de%20Lannoy.")
                );
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    Log.e("CarteActivity", "pas de navigateur ?", e);
                }
            }
            }
            return true;
        }


        // 2 CREER L'OBJET VU // PREPARATION DE LA VUE / OBJET VUE -----------------------------------
        // En Android on met une classe dans une classe
        public class CarteView extends View {

            // Pas oublier d'appeler le constructeur de classe mère
            public CarteView(Context context) {
                super(context);
            }
            @Override // le compilateur verifie qu'on hérite bien cette fonction de la classe mère
            // ça évite de se tromper

            // la fonction onDraw prend un objet Canvas en entrée, c'est un objet sur lequel on va
            // pouvoir dessiner la on affiche juste une image
            protected void onDraw(@NonNull Canvas canvas) {
                // super.onDraw(canvas); pas utile ici
                // Obtient un objet image, le R c'est pour ressources
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.carte);
                // Prends l'image pour la mettre à la position 0 0
                canvas.drawBitmap(bmp, 0, 0, null);
            }
        }
    }
