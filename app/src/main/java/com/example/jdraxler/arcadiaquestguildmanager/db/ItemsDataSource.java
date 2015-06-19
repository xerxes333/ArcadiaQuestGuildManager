package com.example.jdraxler.arcadiaquestguildmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jdraxler.arcadiaquestguildmanager.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdraxler on 3/24/2015.
 */
public class ItemsDataSource {

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            AQDatabaseHandler.COLUMN_ID,
            AQDatabaseHandler.COLUMN_NAME,
            AQDatabaseHandler.COLUMN_COST,
            AQDatabaseHandler.COLUMN_TYPE,
            AQDatabaseHandler.COLUMN_GROUP,
            AQDatabaseHandler.COLUMN_DICE,
            AQDatabaseHandler.COLUMN_EFFECT,
            AQDatabaseHandler.COLUMN_LEVEL,
            AQDatabaseHandler.COLUMN_NUMBER,
            AQDatabaseHandler.COLUMN_IMAGE
    };

    public ItemsDataSource(Context context){
        dbhelper = new AQDatabaseHandler(context);
        open();
    }

    public void open(){
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public Item create(Item item){
        ContentValues values = new ContentValues();
        values.put(AQDatabaseHandler.COLUMN_NAME, item.getName());
        values.put(AQDatabaseHandler.COLUMN_COST, item.getCost());
        values.put(AQDatabaseHandler.COLUMN_TYPE, item.getType());
        values.put(AQDatabaseHandler.COLUMN_GROUP, item.getGroup());
        values.put(AQDatabaseHandler.COLUMN_DICE, item.getDice());
        values.put(AQDatabaseHandler.COLUMN_EFFECT, item.getEffect());
        values.put(AQDatabaseHandler.COLUMN_LEVEL, item.getLevel());
        values.put(AQDatabaseHandler.COLUMN_NUMBER, item.getNumber());
        values.put(AQDatabaseHandler.COLUMN_IMAGE, item.getImage());

        long insertID = database.insert(AQDatabaseHandler.TABLE_ITEMS, null, values);
        item.setId(insertID);
        return item;
    }

    public List<Item> findAll() {
        List<Item> items = new ArrayList<Item>();

        Cursor cursor = database.query(AQDatabaseHandler.TABLE_ITEMS, allColumns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.setId(cursor.getLong(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_NAME)));
                item.setCost(cursor.getInt(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_COST)));
                item.setType(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_TYPE)));
                item.setGroup(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_GROUP)));
                item.setDice(cursor.getInt(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_DICE)));
                item.setEffect(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_EFFECT)));
                item.setLevel(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_LEVEL)));
                item.setNumber(cursor.getInt(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_NUMBER)));
                item.setImage(cursor.getString(cursor.getColumnIndex(AQDatabaseHandler.COLUMN_IMAGE)));
                items.add(item);
            }
        }
        return items;
    }
}
