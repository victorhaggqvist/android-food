package com.snilius.foodlife.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.snilius.foodlife.model.Food;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * @author Victor Häggqvist
 * @since 2/15/16
 */
public class CupboardDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food.db";
    private static final int DATABASE_VERSION = 1;

    private static SQLiteDatabase database;

    static {
        // register our models
        cupboard().register(Food.class);
    }

    public CupboardDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized SQLiteDatabase getConnection(Context context) {
        if (database == null) {
            // Construct the single helper and open the unique(!) db connection for the app
            database = new CupboardDbHelper(context.getApplicationContext()).getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // this will ensure that all tables are created
        cupboard().withDatabase(db).createTables();
        // add indexes and other database tweaks
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this will upgrade tables, adding columns and new tables.
        // Note that existing columns will not be converted
        cupboard().withDatabase(db).upgradeTables();
        // do migration work
    }
}
