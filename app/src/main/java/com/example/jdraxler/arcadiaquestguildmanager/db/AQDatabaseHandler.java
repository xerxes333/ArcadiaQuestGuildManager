package com.example.jdraxler.arcadiaquestguildmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jdraxler on 2/27/2015.
 */
public class AQDatabaseHandler extends SQLiteOpenHelper {

    private static final String LOGTAG = "AQGM";

    private static final String DATABASE_NAME = "aq.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_HEROES     = "heroes";
    public static final String TABLE_ITEMS      = "items";
    public static final String TABLE_SAVES      = "saves";
    public static final String TABLE_SAVE_HEROES= "save_heroes";
    public static final String TABLE_HERO_ITEMS = "hero_items";

    // Common Columns
    public static final String COLUMN_ID    = "id";
    public static final String COLUMN_NAME  = "name";
    public static final String COLUMN_IMAGE = "image";

    // Hero columns
    public static final String COLUMN_DEFENSE   = "defense";
    public static final String COLUMN_HEALTH    = "health";
    public static final String COLUMN_ABILITY   = "ability";

    // Item columns
    public static final String COLUMN_COST  = "cost";
    public static final String COLUMN_TYPE  = "type";   // melee, ranged, boost, permanent
    public static final String COLUMN_GROUP = "groupp";  // sword, hammer, magic, etc.
    public static final String COLUMN_DICE  = "dice";
    public static final String COLUMN_EFFECT= "effect";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_NUMBER= "number";

    // Saves columns
    public static final String COLUMN_GUILD = "guild";

    // Cross Ref columns
    public static final String COLUMN_SAVE_ID = "save_id";
    public static final String COLUMN_HERO_ID = "hero_id";
    public static final String COLUMN_ITEM_ID = "item_id";



    // Table creation queries
    private static final String CREATE_TABLE_HEROES  =
        "CREATE TABLE " + TABLE_HEROES + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_NAME + " TEXT, " +
        COLUMN_DEFENSE + " INTEGER, " +
        COLUMN_HEALTH + " INTEGER, " +
        COLUMN_ABILITY + " TEXT, " +
        COLUMN_IMAGE + " TEXT " +
        ")";

    private static final String CREATE_TABLE_ITEMS  =
        "CREATE TABLE " + TABLE_ITEMS + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_NAME + " TEXT, " +
        COLUMN_COST + " INTEGER, " +
        COLUMN_TYPE + " TEXT, " +
        COLUMN_GROUP + " TEXT, " +
        COLUMN_DICE + " INTEGER, " +
        COLUMN_EFFECT + " TEXT, " +
        COLUMN_LEVEL + " TEXT, " +
        COLUMN_NUMBER + " INTEGER, " +
        COLUMN_IMAGE + " TEXT " +
        ")";

    private static final String CREATE_TABLE_SAVES  =
        "CREATE TABLE " + TABLE_SAVES + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_NAME + " TEXT, " +
        COLUMN_GUILD + " TEXT " +
        ")";

    private static final String CREATE_TABLE_SAVE_HEROES  =
        "CREATE TABLE " + TABLE_SAVE_HEROES + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_SAVE_ID + " INTEGER, " +
        COLUMN_HERO_ID + " INTEGER " +
        ")";

    private static final String CREATE_TABLE_HERO_ITEMS  =
        "CREATE TABLE " + TABLE_HERO_ITEMS + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_HERO_ID + " INTEGER, " +
        COLUMN_ITEM_ID + " INTEGER " +
        ")";

    public AQDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HEROES);
        db.execSQL(CREATE_TABLE_ITEMS);
        db.execSQL(CREATE_TABLE_SAVES);
        db.execSQL(CREATE_TABLE_SAVE_HEROES);
        db.execSQL(CREATE_TABLE_HERO_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEROES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE_HEROES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HERO_ITEMS);
        onCreate(db);
    }
}
