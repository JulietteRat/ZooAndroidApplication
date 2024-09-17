package com.ldnr.welovestephane;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AccueilActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil); // on va chercher le layout accueil
        readNews();
        // Bouton :
        Button btmap = findViewById(R.id.btmap);
        btmap.setOnClickListener(this);
        // Button 2 :
        Button btalerte = findViewById(R.id.btWarning);
        btalerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // attention au this caché !
                Intent i = new Intent(AccueilActivity.this, AlerteActivity.class);
                startActivity(i);
            }
        });
        //Button 3
        Button btannuaire = findViewById(R.id.btannuaire);
        btannuaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v ){
                Intent i =new Intent(AccueilActivity.this, AnnuaireActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // charger un menu avec l'objet getMenuInflater // decompresse sous forme d'objet :
        getMenuInflater().inflate(R.menu.accueil, menu);
        // Va chercher l'info si la case à été coché avant ou pas :
        SharedPreferences sp = getSharedPreferences("zoo", MODE_PRIVATE);
        menu.findItem((R.id.menu_envoyer))
                .setChecked(sp.getBoolean("envoyer",true));
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, @NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_carte){
            // Ouvre la carte
            startActivity(new Intent(this,CarteActivity.class));
        } if (item.getItemId()==R.id.menu_animaux){
            startActivity(new Intent(this, AnimauxActivity.class));
        }
        if (item.getItemId()==R.id.menu_envoyer){
            item.setChecked(!item.isChecked());
        // Cree un fichier de préference dans lequel on va pouvoir écrire :
        SharedPreferences sp = getSharedPreferences("zoo", MODE_PRIVATE);
        // pour indiquer les modification voulu (celle qu'on veut enregistrer)
        SharedPreferences.Editor e =sp.edit();
        e.putBoolean("envoyer",item.isChecked());
        e.commit();
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, CarteActivity.class); // voila mon attention
        //je part de this pour aller vers l'activity Carte
        startActivity(i);
    }
    private void readNews() {

        try {
            // Ouvre le fichier "news.txt" dans le dossier assets de l'application
            InputStream is = getAssets().open("news.txt");

            // Initialise un Scanner pour lire l'InputStream, en spécifiant l'encodage UTF-8
            Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name());

            // Utilise le délimiteur "\\A" pour lire tout le contenu du fichier en une seule opération
            // "\\A" est une expression régulière qui correspond au début de l'entrée
            String content = scanner.useDelimiter("\\A").next();

            // Ferme le Scanner pour libérer les ressources associées
            scanner.close();

            // Trouve le TextView avec l'ID textView3 dans le layout actuel
            TextView tvNews = findViewById(R.id.textView3);

            // Met à jour le TextView avec le contenu lu depuis le fichier
            tvNews.setText(content);  // Utilise le contenu du fichier pour mettre à jour le TextView

        } catch (Exception e) {
            Log.e("AccueilActivity","Erreur lecture news");
        }
    }


}
