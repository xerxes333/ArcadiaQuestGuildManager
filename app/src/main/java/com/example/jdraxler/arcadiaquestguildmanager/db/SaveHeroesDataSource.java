package com.example.jdraxler.arcadiaquestguildmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jdraxler.arcadiaquestguildmanager.Hero;
import com.example.jdraxler.arcadiaquestguildmanager.Save;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdraxler on 3/25/2015.
 */
public class SaveHeroesDataSource {

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            AQDatabaseHandler.COLUMN_ID,
            AQDatabaseHandler.COLUMN_SAVE_ID ,
            AQDatabaseHandler.COLUMN_HERO_ID
    };

    public SaveHeroesDataSource(Context context){
        dbhelper = new AQDatabaseHandler(context);
        open();
    }

    public void open(){
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public void create(Save save){

        ContentValues values = new ContentValues();

        for (Hero hero: save.getHeroes()){
            values.put(AQDatabaseHandler.COLUMN_SAVE_ID, save.getId());
            values.put(AQDatabaseHandler.COLUMN_HERO_ID, hero.getId());
            long insertID = database.insert(AQDatabaseHandler.TABLE_SAVE_HEROES , null, values);
        }

    }

}
