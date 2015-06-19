package com.example.jdraxler.arcadiaquestguildmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jdraxler on 2/27/2015.
 */
public class HerosDatabaseHandler extends SQLiteOpenHelper {

    private static final String LOGTAG = "AQ";

    private static final String DATABASE_NAME = "aq.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_HEROES            = "heroes";
    public static final String HEROES_COLUMN_ID        = "id";
    public static final String HEROES_COLUMN_NAME      = "name";
    public static final String HEROES_COLUMN_DEFENSE   = "defense";
    public static final String HEROES_COLUMN_HEALTH    = "health";
    public static final String HEROES_COLUMN_ABILITY   = "ability";
    public static final String HEROES_COLUMN_IMAGE     = "image";

    private static final String HEROES_TABLE_CREATE  =
            "CREATE TABLE " + TABLE_HEROES + " (" +
            HEROES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            HEROES_COLUMN_NAME + " TEXT, " +
            HEROES_COLUMN_DEFENSE + " NUMERIC, " +
            HEROES_COLUMN_HEALTH + " NUMERIC, " +
            HEROES_COLUMN_ABILITY + " TEXT, " +
            HEROES_COLUMN_IMAGE + " TEXT " +
            ")";


    private static final String TABLE_ITEMS = "items";

    public HerosDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HEROES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEROES);
        onCreate(db);
    }
}
