package com.example.jdraxler.arcadiaquestguildmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jdraxler.arcadiaquestguildmanager.Hero;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdraxler on 2/27/2015.
 */
public class HeroesDataSource {

    private static final String LOGTAG = "AQGM";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            AQDatabaseHandler.COLUMN_ID,
            AQDatabaseHandler.COLUMN_NAME,
            AQDatabaseHandler.COLUMN_DEFENSE,
            AQDatabaseHandler.COLUMN_HEALTH,
            AQDatabaseHandler.COLUMN_ABILITY,
            AQDatabaseHandler.COLUMN_IMAGE
    };

    public HeroesDataSource(Context context){
        dbhelper = new AQDatabaseHandler(context);
        open();
    }

    public void open(){
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public Hero create(Hero hero){
        ContentValues values = new ContentValues();
        //values.put(HerosDatabaseHandler.HEROES_COLUMN_ID, hero.getId());
        values.put(AQDatabaseHandler.COLUMN_NAME, hero.getName());

        long insertID = database.insert(AQDatabaseHandler.TABLE_HEROES, null, values);
        hero.setId(insertID);
        return hero;
    }

    public List<Hero> findAll() {
        List<Hero> heroes = new ArrayList<Hero>();

        Cursor cursor = database.query(AQDatabaseHandler.TABLE_HEROES, allColumns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Hero hero = new Hero();
                hero.setId(cursor.getLong(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_ID)));
                hero.setName(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_NAME)));
                hero.setDefense(cursor.getInt(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_DEFENSE)));
                hero.setHealth(cursor.getInt(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_HEALTH)));
                hero.setAbility(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_ABILITY)));
                hero.setImage(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_IMAGE)));
                heroes.add(hero);
            }
        }
        return heroes;
    }

    public List<String> getHeroNames(){

        List<String> names = new ArrayList<String>();
        Cursor cursor = database.query(AQDatabaseHandler.TABLE_HEROES, allColumns, null, null, null, null, null);
//        Cursor cursor = database.query(AQDatabaseHandler.TABLE_HEROES, new String[] {AQDatabaseHandler.COLUMN_NAME}, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                names.add(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_NAME)));
            }
        }
        return names;
    }

    public Hero getHeroByName(String name){

        String query = "SELECT * FROM " + AQDatabaseHandler.TABLE_HEROES + " WHERE " + AQDatabaseHandler.COLUMN_NAME + " = '" + name + "'";
        Log.d(LOGTAG, query);

        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();

        Hero hero = new Hero();
        hero.setId(cursor.getLong(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_ID)));
        hero.setName(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_NAME)));
        hero.setDefense(cursor.getInt(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_DEFENSE)));
        hero.setHealth(cursor.getInt(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_HEALTH)));
        hero.setAbility(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_ABILITY)));
        hero.setImage(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_IMAGE)));

        return hero;
    }
}
