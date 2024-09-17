package com.ldnr.welovestephane;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AlerteActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerte);
        // On associe les données à l'AutocompliteTextView, on créé un tableau
        String[] lieux =getResources().getStringArray(R.array.alerte_lieu);
        // Va chercher l' objet :
        AutoCompleteTextView actv = findViewById(R.id.alerte_AutoC_lieu);
        // On lie le tableau et l'objet autocompletion avec ArrayAdapter (classe spe qui prend une string )
        // C'est une classe générique (il n'adapte que des chaines):
        // 1 er parametre = context (donc this), 2iem c'est le layout, le troisième c'est le tableau
        // lieux créé précedemment
        ArrayAdapter <String> aa = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                lieux
        );
        actv.setAdapter(aa);
        }
        public void envoyerClick(View v){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.alerte_titre);
            builder.setMessage(R.string.alerte_confirmé);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setPositiveButton(android.R.string.yes,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            envoyer();
                        }
                    });
            builder.setNegativeButton(android.R.string.no,null);
            builder.show();
        }
    // Comment lire une donnée d'un composant d'un formulaire :
        public void envoyer(){
            String text =null;
            // Trouve l'EditText dans le layout alerte en utilisant son ID
            EditText et = findViewById(R.id.alerte_editText_intitule);
            // Prends le texte dans et :
            text = et.getText().toString();
            // Trouve la CheckBox dans le layout alerte en utilisant son ID
            CheckBox cb = findViewById(R.id.alerte_checkBox_urgent);
            // si la checbox est coché envoyé le msg dont l'id correspond a alerte_envois_urgent
            if (cb.isChecked() == true){
                Toast.makeText(this,getString(R.string.alerte_envois_urgent, text)
                        , Toast.LENGTH_LONG).show();
            }
            Toast.makeText(this,getString(R.string.alerte_envois, text)
                    , Toast.LENGTH_LONG).show();
            //finish();
    }
}

