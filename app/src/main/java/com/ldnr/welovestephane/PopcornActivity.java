package com.ldnr.welovestephane;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PopcornActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PopcornActivity.PopcornView(this));
        Log.i("Popcorn","Activity Create");
        // La clef "temps" doit correspondre à la clef dans aquariumAquariumActivity
        long duree = getIntent().getLongExtra("temps",0);
        // si on est resté plus de 5s sur aquarium :
        if(duree > 500) {
            // on récupère la string des seconde dans le fichier xml en fonction du temps écoulé
            String second = getResources().getQuantityString(R.plurals.popcorn_seconds,(int)duree/1000);
            // on ajoute la string seconde dans la string text
            String text =getString(R.string.popcorn_warning, (int)duree/1000,
                    second);
            // on affiche ce toast :
            Toast.makeText(this,text,
                    Toast.LENGTH_LONG).show();
            // Enregistre l'info si on a affiché le message ou non :
            Intent resultat = new Intent();
            resultat.putExtra(AquariumActivity.CLE_RETOUR,true);
            setResult(0,resultat);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            Intent i = new Intent(this, CarteActivity.class);
            // Les flags pour neutraliser une annimation
            //i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            //i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        return true;
    }

    public class PopcornView extends View {

        public PopcornView(Context context) {
            super(context);
        }
        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.popcorn);
            canvas.drawBitmap(bmp, 0, 0, null);
        }
    }
}
