package com.example.jdraxler.arcadiaquestguildmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jdraxler.arcadiaquestguildmanager.Save;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdraxler on 3/25/2015.
 */
public class SavesDataSource {


    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            AQDatabaseHandler.COLUMN_ID,
            AQDatabaseHandler.COLUMN_NAME,
            AQDatabaseHandler.COLUMN_GUILD
    };

    public SavesDataSource(Context context){
        dbhelper = new AQDatabaseHandler(context);
        open();
    }

    public void open(){
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public Save create(Save save){
        ContentValues values = new ContentValues();
        values.put(AQDatabaseHandler.COLUMN_NAME, save.getName());
        values.put(AQDatabaseHandler.COLUMN_GUILD, save.getGuild());

        long insertID = database.insert(AQDatabaseHandler.TABLE_SAVES, null, values);

        // now save the heroes to the save_heroes table

        save.setId(insertID);
        return save;
    }

    public List<Save> findAll() {
        List<Save> saves = new ArrayList<Save>();

        Cursor cursor = database.query(AQDatabaseHandler.TABLE_SAVES, allColumns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Save save = new Save();
                save.setId(cursor.getLong(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_ID)));
                save.setName(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_NAME)));
                save.setGuild(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_GUILD)));
                saves.add(save);
            }
        }
        return saves;
    }
}
