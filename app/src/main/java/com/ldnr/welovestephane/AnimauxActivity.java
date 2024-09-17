package com.ldnr.welovestephane;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnimauxActivity extends Activity {

    // recois les evenements thread
    private Handler mainHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal);

        Button btsave =findViewById((R.id.btAddAnimal));
        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Enregistrer();
            }
        });

        // CREATION DU BOUTON RECHERCHE
        Button btrecherche = findViewById(R.id.btChercher);
        btrecherche.setOnClickListener(new View.OnClickListener() {
            @Override //
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.INTERNET)==
                        PackageManager.PERMISSION_GRANTED){
                EditText etEsp = findViewById(R.id.etEspece);
                // On crée 2 caissiers (en gros) un peu comme des cores ou processeur :
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                // APPEL RECHERCHER DANS LEXECUTOR
                executorService.execute(AnimauxActivity.this::rechercher);
                } else {
                    // FENETRE DE DEMANDE DE PERMISSION SI ON NE LA PAS RECU ALORS JE DEMANDE
                    requestPermissions(new String[]{Manifest.permission.INTERNET}
                    ,0);
                }
            }
});
        // TROUVE UNE BOUCLE D'EVENEMENT
        mainHandler = new Handler(Looper.getMainLooper());
    }
    // FONCTION APPELER QUAND USAGE A ACCEPTER OU REFUSER LA PERMISSION
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // On crée 2 caissiers (en gros) un peu comme des cores ou processeur :
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            // APPEL RECHERCHE DANS LEXECUTOR
            executorService.execute(AnimauxActivity.this::rechercher);
        } else
            Log.i("AnimalActivity","L'usager refuse l'accès à internet");
    }

    // CREATION FCT DE RECHERCHE
    public void rechercher () {
        try {
            afficher("....");// on peut aussi desactivée le bouton pour pas que l'usager appuis pleins
            //de fois et lance plein de thread
            EditText etEsp = findViewById(R.id.etEspece);
            String url = null;
            String espece = etEsp.getText().toString();

            url = "https://fr.wikipedia.org/w/api.php?action=query&prop=extracts&exsentences=3&format=json&titles="
                    + URLEncoder.encode(espece,"UTF-8");
            URLConnection conn = new URL(url).openConnection();
            //1er partie envois (ici rien car on a tout mis avant, rien de plus à envoyer)
            //2iem parti lecture des données
            InputStream is =conn.getInputStream();
            Log.i("AnnimalActivity", "Recherche de " + url);

            // RECUPERE LES INFOS DE WIKIPEDIA
             Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name());
             String contenuJson = scanner.useDelimiter("\\A").next();

            is.close();
            String message = formatJson(contenuJson);
            afficher(Html.fromHtml(message));
            
        } catch (Exception e) {
            Log.e("AnimalActivity","Echec recherche", e);
        }
    }

    private void afficher(CharSequence message){
        mainHandler.post(()->{
            TextView tvResultat = findViewById(R.id.tvresultat);
            tvResultat.setText(message);
        });

    }
    public void Enregistrer () {

        EditText etNom = findViewById(R.id.edNom);
        EditText etEsp = findViewById(R.id.etEspece);
        EditText etAge = findViewById(R.id.etAge);

        String nom = etNom.getText().toString();
        String sage = etAge.getText().toString();
        String espece = etEsp.getText().toString();
        int age = Integer.parseInt(sage);

        validation(nom, sage, espece);

        Log.i("animauxActivity",
                "Nouvel animal "+ nom + " " +" "+ sage + " "+espece);

        animalHelper ah = new animalHelper(this);

        if (ah.getIdEspece(espece)==(-1)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AnimauxActivity.this);
            builder.setTitle(R.string.alerte_titre);
            builder.setMessage(R.string.animal_espece_dont_exist);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.show();
        } else {
        int resultat = ah.insererAnimal(nom,age,espece);
        Log.i("ActivityAnimal", "il y a "+ resultat + " n'animaux de cette espèce");

        List<String> animaux = ah.ListeAnimals();
        for(String animal : animaux)
            Log.i("AnnimalActivity", animal);

        etAge.setText("");
        etNom.setText("");
        etEsp.setText(""); }

    }

    public void validation (String nom, String sage, String espece){
        if ( nom.isEmpty() || sage.isEmpty() || espece.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AnimauxActivity.this);
            builder.setTitle(R.string.alerte_titre);
            builder.setMessage(R.string.animal_validation);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.show();
        } else {
            int age = Integer.parseInt(sage);
            Log.d("nom : ", nom);
            Log.d("age : ", sage);
            Log.d("espèce : ", espece);
        }
    }

    // FCT POUR CONVERTIR UN JSON EN HTML
    private String formatJson(String json) {
        String messageHtml =":(";
        try {
            JSONObject racine = new JSONObject(json);
            JSONObject query = racine.getJSONObject("query");
            JSONObject pages = query.getJSONObject("pages");
            String numeropage = pages.keys().next();
            JSONObject page = pages.getJSONObject(numeropage);
            messageHtml = page.getString("extract");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return messageHtml;
    }
}
