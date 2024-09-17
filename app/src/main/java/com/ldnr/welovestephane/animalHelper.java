package com.ldnr.welovestephane;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class animalHelper extends SQLiteOpenHelper {

    // simplification constructeur de la mère :
    public animalHelper(@Nullable Context context) {
        super(context, "animal.sqlite", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE espece(id INTEGER PRIMARY KEY, nom TEXT)");
        String insertionEspece ="INSERT INTO espece(nom) VALUES (?)";
        db.execSQL(insertionEspece,new Object[]{"singe"});
        db.execSQL(insertionEspece,new Object[]{"hippopotame"});
        db.execSQL(insertionEspece,new Object[]{"lion"});
        db.execSQL(insertionEspece,new Object[]{"girafe"});
        db.execSQL(insertionEspece,new Object[]{"cacatoes"});
        db.execSQL("CREATE TABLE animal(id INTEGER PRIMARY KEY, nom TEXT, " +
                "age INTEGER, " +
                "id_espece INTEGER," +
                "FOREIGN KEY (id_espece) REFERENCES espece(id))");
    }

    // dans le cas d'une évolution de base de donnée :
    @Override
    public void onUpgrade(SQLiteDatabase db, int ancien, int nouveau) {
    }

    public int getIdEspece(String espece){
        int idEspece = -1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM espece WHERE nom LIKE ?",
                new String[]{ String.valueOf(espece)});
        if (c.moveToFirst()) {
            idEspece = c.getInt(0);
        }
        c.close();
        return idEspece;
    }

    public int insererAnimal (String nom, Integer age, String espece) {
        int id_espece = getIdEspece(espece);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO animal (nom, age, id_espece) VALUES (?,?,?)",
            new Object[]{nom, age, id_espece});
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM animal WHERE id_espece = ?",
                new String[]{String.valueOf(id_espece)});
        c.moveToNext();
        int resultat = c.getInt(0);
        c.close();
        db.close();
        return resultat;
    }

    public List<String> ListeAnimals (){
        List<String> animaux = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM animal JOIN espece ON id_espece=espece.id ORDER BY age";
        Cursor c = db.rawQuery(sql,null);
        while (c.moveToNext()) {
            animaux.add(c.getString(1)+"|"+c.getInt(2)+"|"
                    +c.getString(5));
        }
        c.close();
        db.close();
        return animaux;
    }
}
