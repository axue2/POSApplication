package com.ass3.axue2.posapplication.models.configuration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by anthony on 5/22/2017.
 */

public class ConfigurationDatabaseHelper extends SQLiteOpenHelper{
    // Set Database Properties
    public static final String DATABASE_NAME = "ConfigurationDB";
    public static final int DATABASE_VERSION = 1;

    public ConfigurationDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConfigurationSetting.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddConfigurationSetting(ConfigurationSetting setting){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ConfigurationSetting.COLUMN_NETWORK_MODE, setting.getnNetworkMode());
        db.insert(ConfigurationSetting.TABLE_NAME, null, values);
        db.close();
    }

    public ConfigurationSetting GetConfigurationSetting(long id){
        // Access database
        SQLiteDatabase db = this.getReadableDatabase();
        // Database query
        Cursor cursor = db.query(ConfigurationSetting.TABLE_NAME, new String[] { ConfigurationSetting.COLUMN_ID,
                ConfigurationSetting.COLUMN_NETWORK_MODE},
                ConfigurationSetting.COLUMN_ID + "=?",new String[] { String.valueOf(id) }, null, null, null, null);
        // Checks query
        if (cursor != null)
            cursor.moveToFirst();
        // Creates new order with query values
        ConfigurationSetting setting = new ConfigurationSetting(cursor.getLong(0),
                cursor.getInt(1)
        );
        cursor.close();

        return setting;
    }

    public HashMap<Long, ConfigurationSetting> GetConfigurationSettings(){
        HashMap<Long, ConfigurationSetting> settings = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ConfigurationSetting.TABLE_NAME, null);

        // Add all settings in db to hashmap
        if(cursor.moveToFirst()) {
            do {
                ConfigurationSetting setting = new ConfigurationSetting(cursor.getLong(0),
                        cursor.getInt(1)
                );
                settings.put(setting.getnID(), setting);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return settings;
    }
}
