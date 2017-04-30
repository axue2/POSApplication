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
        db.execSQL(Order.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Order.TABLE_NAME);
        onCreate(db);
    }

    public void AddTable(Table table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table.COLUMN_NAME, table.getsTableName());
        values.put(Table.COLUMN_GUESTS, table.getnGuests());
        values.put(Table.COLUMN_ORDER_ID, table.getnOrderID());
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
                        cursor.getLong(3),
                        cursor.getInt(4),
                        cursor.getString(5));
                tables.put(table.getnTableID(), table);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return tables;
    }

    public HashMap<Long, Table> GetInuseTables(){
        HashMap<Long, Table> tables = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table.TABLE_NAME + " WHERE " +
                        Table.COLUMN_STATUS + " = '" + Table.STATUS_INUSE + "'", null);

        // Add all Tables in db to hashmap
        if(cursor.moveToFirst()) {
            do {
                Table table = new Table(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getLong(3),
                        cursor.getInt(4),
                        cursor.getString(5));
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
                cursor.getLong(3),
                cursor.getInt(4),
                cursor.getString(5));
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
        values.put(Table.COLUMN_ORDER_ID, table.getnOrderID());
        values.put(Table.COLUMN_TOTAL, table.getnInvSum());
        values.put(Table.COLUMN_STATUS, table.getsStatus());
        // Update values using table id
        db.update(Table.TABLE_NAME, values, Table.COLUMN_ID + " = " + table.getnTableID(), null );
        db.close();
    }

    public long AddOrder(Order order){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Order.COLUMN_TABLE_ID, order.getnTableID());
        values.put(Order.COLUMN_TYPE, order.getsType());
        values.put(Order.COLUMN_TOTAL, order.getnTotal());
        values.put(Order.COLUMN_STATUS, order.getsStatus());
        long id = db.insert(Order.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public void UpdateOrder(Order order){
        // Access Database
        SQLiteDatabase db = this.getWritableDatabase();
        // Add values
        ContentValues values = new ContentValues();
        values.put(Order.COLUMN_TABLE_ID, order.getnTableID());
        values.put(Order.COLUMN_TYPE, order.getsType());
        values.put(Order.COLUMN_STATUS, order.getsStatus());
        values.put(Order.COLUMN_TOTAL, order.getnTotal());
        // Update values using table id
        db.update(Order.TABLE_NAME, values, Order.COLUMN_ID + " = " + order.getnOrderID(), null );
        db.close();
    }

    public Order GetOrder(long id){
        // Access database
        SQLiteDatabase db = this.getReadableDatabase();
        // Database query
        Cursor cursor = db.query(Order.TABLE_NAME, new String[] { Order.COLUMN_ID, Order.COLUMN_TABLE_ID,
                Order.COLUMN_TYPE, Order.COLUMN_STATUS, Order.COLUMN_TOTAL},
                Order.COLUMN_ID + "=?",new String[] { String.valueOf(id) }, null, null, null, null);
        // Checks query
        if (cursor != null)
            cursor.moveToFirst();
        // Creates new monster with query values
        Order order = new Order(cursor.getLong(0),
                cursor.getLong(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4));
        cursor.close();

        return order;
    }

    public void CreateDefaultTables(){
        AddTable(new Table("Table 1"));
        AddTable(new Table("Table 2"));
        AddTable(new Table("Table 3"));
        AddTable(new Table("Table 4"));
        AddTable(new Table("Table 5"));
        AddTable(new Table("Table 6"));
        AddTable(new Table("Table 7"));
        AddTable(new Table("Table 8"));
        AddTable(new Table("Table 9"));
        AddTable(new Table("Table 10"));
        AddTable(new Table("Table 11"));
        AddTable(new Table("Table 12"));

    }

    public void deleteTable(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(tableName, null, null);
        db.execSQL("delete * from " + tableName);
        db.execSQL("TRUNCATE table" + tableName);
        db.close();
    }
}
