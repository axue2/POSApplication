package com.ass3.axue2.posapplication.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by anthony on 5/1/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Set Database Properties
    public static final String DATABASE_NAME = "OperationDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table.TABLE_NAME);
        onCreate(db);
    }

    public void AddTable(Table table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table.COLUMN_NAME, table.getsTableName());
        values.put(Table.COLUMN_GUESTS, table.getnGuests());
        values.put(Table.COLUMN_TOTAL, table.getnInvSum());
        values.put(Table.COLUMN_STATUS, table.getsStatus());
        db.insert(Table.TABLE_NAME, null, values);
        db.close();
    }

    public HashMap<Long, Table> GetAllTables(){
        HashMap<Long, Table> tables = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table.TABLE_NAME, null);

        // Add all Tables in db to hashmap
        if(cursor.moveToFirst()) {
            do {
                Table table = new Table(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4));
                tables.put(table.getnTableID(), table);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return tables;
    }

    public Table GetTable(long id){
        // Access database
        SQLiteDatabase db = this.getReadableDatabase();
        // Database query
        Cursor cursor = db.query(Table.TABLE_NAME, new String[] { Table.COLUMN_ID,
                        Table.COLUMN_NAME, Table.COLUMN_GUESTS,
                        Table.COLUMN_TOTAL, Table.COLUMN_STATUS},
                Table.COLUMN_ID + "=?",new String[] { String.valueOf(id) }, null, null, null, null);
        // Checks query
        if (cursor != null)
            cursor.moveToFirst();
        // Creates new monster with query values
        Table table = new Table(cursor.getLong(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getString(4));
        cursor.close();

        return table;
    }

    public void RemoveTable(Table table){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table.TABLE_NAME,
                Table.COLUMN_ID + " = ?",
                new String[] {String.valueOf(table.getnTableID())});
    }

    public void UpdateTable(Table table){
        // Access Database
        SQLiteDatabase db = this.getWritableDatabase();
        // Add values
        ContentValues values = new ContentValues();
        values.put(Table.COLUMN_NAME, table.getsTableName());
        values.put(Table.COLUMN_GUESTS, table.getnGuests());
        values.put(Table.COLUMN_TOTAL, table.getnInvSum());
        values.put(Table.COLUMN_STATUS, table.getsStatus());
        // Update values using table id
        db.update(Table.TABLE_NAME, values, Table.COLUMN_ID + " = " + table.getnTableID(), null );
        db.close();
    }

    public void CreateDefaultTables(){
        AddTable(new Table(1, "Table 1", -1, -1, "Open"));
        AddTable(new Table(2, "Table 2", -1, -1, "Open"));
        AddTable(new Table(3, "Table 3", -1, -1, "Open"));
        AddTable(new Table(4, "Table 4", -1, -1, "Open"));
        AddTable(new Table(5, "Table 5", -1, -1, "Open"));
        AddTable(new Table(6, "Table 6", -1, -1, "Open"));
        AddTable(new Table(7, "Table 7", -1, -1, "Open"));
        AddTable(new Table(8, "Table 8", -1, -1, "Open"));
        AddTable(new Table(9, "Table 9", -1, -1, "Open"));
        AddTable(new Table(10, "Table 10", -1, -1, "Open"));
        AddTable(new Table(11, "Table 11", -1, -1, "Open"));
        AddTable(new Table(12, "Table 12", -1, -1, "Open"));

    }

    public void deleteTable(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(tableName, null, null);
        db.execSQL("delete * from " + tableName);
        db.execSQL("TRUNCATE table" + tableName);
        db.close();
    }
}
