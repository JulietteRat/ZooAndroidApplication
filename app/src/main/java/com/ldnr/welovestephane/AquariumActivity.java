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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AquariumActivity extends Activity {

    private long debut, fin;

    static final String CLE_RETOUR ="messageAffiche";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new AquariumView(this));
        Log.i("Aquarium","Activity Create");
        debut = System.currentTimeMillis();
        // Fait apparaitre un toast donc un message quand on lance l'application
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                fin = System.currentTimeMillis();
                long duree =fin-debut;
                Intent i = new Intent(this, PopcornActivity.class); // voila mon attention
                i.putExtra("temps",duree); // envois de la donnée
                //je part de this pour aller vers l'activity Aquarium

                // 1 Procédure standart
                //startActivity(i);
                //}

                // 2 Si on veux recevoir des données d'une autre activité :
                startActivityForResult(i, 0);
            }
            return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!= null && data.getBooleanExtra(CLE_RETOUR,false))
            Log.i("Aquarium", "Message Popcorn Affiché");
        else
            Log.i("Aquarium","Message du popcorn non Affiché ");
    }

    public class AquariumView extends View {

        public AquariumView(Context context) {
            super(context);
        }
        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.aquarium);
            canvas.drawBitmap(bmp, 0, 0, null);
        }
    }
}
