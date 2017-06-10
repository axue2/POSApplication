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
 *  Database Strictly for settings local to the machine
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
        db.execSQL(NetworkSetting.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddNetworkSetting(NetworkSetting setting){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NetworkSetting.COLUMN_NETWORK_MODE, setting.getnNetworkMode());
        values.put(NetworkSetting.COLUMN_IP_ADDRESS, setting.getsIPAddress());
        values.put(NetworkSetting.COLUMN_DATABASE_NAME, setting.getsDBName());
        values.put(NetworkSetting.COLUMN_USERNAME, setting.getsUsername());
        values.put(NetworkSetting.COLUMN_PASSWORD, setting.getsPassword());
        db.insert(NetworkSetting.TABLE_NAME, null, values);
        db.close();
    }

    public void UpdateNetworkSetting(NetworkSetting setting){
        // Access Database
        SQLiteDatabase db = this.getWritableDatabase();
        // Add values
        ContentValues values = new ContentValues();
        values.put(NetworkSetting.COLUMN_NETWORK_MODE, setting.getnNetworkMode());
        values.put(NetworkSetting.COLUMN_IP_ADDRESS, setting.getsIPAddress());
        values.put(NetworkSetting.COLUMN_DATABASE_NAME, setting.getsDBName());
        values.put(NetworkSetting.COLUMN_USERNAME, setting.getsUsername());
        values.put(NetworkSetting.COLUMN_PASSWORD, setting.getsPassword());
        db.insert(NetworkSetting.TABLE_NAME, null, values);
        // Update values using table id
        db.update(NetworkSetting.TABLE_NAME, values, NetworkSetting.COLUMN_ID + " = " + setting.getnID(), null );
        db.close();
    }

    public NetworkSetting GetNetworkSetting(long id){
        // Access database
        SQLiteDatabase db = this.getReadableDatabase();
        // Database query
        Cursor cursor = db.query(NetworkSetting.TABLE_NAME, new String[] { NetworkSetting.COLUMN_ID,
                NetworkSetting.COLUMN_NETWORK_MODE, NetworkSetting.COLUMN_IP_ADDRESS,
                NetworkSetting.COLUMN_DATABASE_NAME, NetworkSetting.COLUMN_USERNAME,
                NetworkSetting.COLUMN_PASSWORD},
                NetworkSetting.COLUMN_ID + "=?",new String[] { String.valueOf(id) }, null, null, null, null);
        // Checks query
        if (cursor != null)
            cursor.moveToFirst();
        // Creates new order with query values
        NetworkSetting setting = new NetworkSetting(cursor.getLong(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );
        cursor.close();

        return setting;
    }

    public HashMap<Long, NetworkSetting> GetNetworkSettings(){
        HashMap<Long, NetworkSetting> settings = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NetworkSetting.TABLE_NAME, null);

        // Add all settings in db to hashmap
        if(cursor.moveToFirst()) {
            do {
                NetworkSetting setting = new NetworkSetting(cursor.getLong(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );
                settings.put(setting.getnID(), setting);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return settings;
    }
}
