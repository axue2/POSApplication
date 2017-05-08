package com.ass3.axue2.posapplication.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by anthony on 5/1/2017.
 *
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
        db.execSQL(Group.CREATE_STATEMENT);
        db.execSQL(Product.CREATE_STATEMENT);
        db.execSQL(Order.CREATE_STATEMENT);
        db.execSQL(OrderItem.CREATE_STATEMENT);
        db.execSQL(Customer.CREATE_STATEMENT);
        db.execSQL(Delivery.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Order.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OrderItem.TABLE_NAME);
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

    public long AddProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_GROUPID, product.getnGroupID());
        values.put(Product.COLUMN_NAME, product.getsProductName());
        values.put(Product.COLUMN_PRICE, product.getnPrice());
        long id = db.insert(Product.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public HashMap<Long, Product> GetProducts(long groupID){
        HashMap<Long, Product> products = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Product.TABLE_NAME + " WHERE " +
                Product.COLUMN_GROUPID + " = '" + groupID + "'", null);

        // Add all products in db to hashmap
        if(cursor.moveToFirst()) {
            do {
                Product product = new Product(cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getInt(3)
                );
                products.put(product.getnProductID(), product);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return products;
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

    public long AddOrderItem(OrderItem orderItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OrderItem.COLUMN_ORDER_ID, orderItem.getnOrderID());
        values.put(OrderItem.COLUMN_TABLE_ID, orderItem.getnTableID());
        values.put(OrderItem.COLUMN_PRODUCT_ID, orderItem.getnProductID());
        values.put(OrderItem.COLUMN_PRODUCT_NAME, orderItem.getsProductName());
        values.put(OrderItem.COLUMN_PRODUCT_PRICE, orderItem.getnPrice());
        values.put(OrderItem.COLUMN_QUANTITY, orderItem.getnQuantity());
        long id = db.insert(OrderItem.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public void UpdateOrderItem(OrderItem orderItem){
        // Access Database
        SQLiteDatabase db = this.getWritableDatabase();
        // Add values
        ContentValues values = new ContentValues();
        values.put(OrderItem.COLUMN_ORDER_ID, orderItem.getnOrderID());
        values.put(OrderItem.COLUMN_TABLE_ID, orderItem.getnTableID());
        values.put(OrderItem.COLUMN_PRODUCT_ID, orderItem.getnProductID());
        values.put(OrderItem.COLUMN_PRODUCT_NAME, orderItem.getsProductName());
        values.put(OrderItem.COLUMN_PRODUCT_PRICE, orderItem.getnPrice());
        values.put(OrderItem.COLUMN_QUANTITY, orderItem.getnQuantity());
        // Update values using table id
        db.update(OrderItem.TABLE_NAME, values, OrderItem.COLUMN_ID + " = " + orderItem.getnOrderItemID(), null );
        db.close();
    }

    public HashMap<Long, OrderItem> GetOrderItems(long orderID){
        HashMap<Long, OrderItem> orderItems = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + OrderItem.TABLE_NAME + " WHERE " +
                OrderItem.COLUMN_ORDER_ID + " = '" + orderID + "'", null);

        // Add all OrderItems in db to hashmap
        if(cursor.moveToFirst()) {
            do {
                OrderItem orderItem = new OrderItem(cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getLong(2),
                        cursor.getLong(3),
                        cursor.getString(4),
                        cursor.getDouble(5),
                        cursor.getInt(6)
                );
                orderItems.put(orderItem.getnOrderItemID(), orderItem);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return orderItems;
    }

    public void AddGroup(Group group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Group.COLUMN_NAME, group.getsGroupName());
        db.insert(Group.TABLE_NAME, null, values);
        db.close();
    }

    public HashMap<Long, Group> GetAllGroups(){
        HashMap<Long, Group> groups = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Group.TABLE_NAME, null);

        // Add all groups in db to hashmap
        if(cursor.moveToFirst()) {
            do {
                Group group = new Group(cursor.getLong(0),
                        cursor.getString(1));
                groups.put(group.getnGroupID(), group);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return groups;
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

    public long AddCustomer(Customer customer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Customer.COLUMN_ADDRESS_LINE_1, customer.getsAddressLine1());
        values.put(Customer.COLUMN_ADDRESS_LINE_2, customer.getsAddressLine2());
        values.put(Customer.COLUMN_ADDRESS_LINE_3, customer.getsAddressLine3());
        values.put(Customer.COLUMN_POST_CODE, customer.getnPostCode());
        values.put(Customer.COLUMN_PHONE, customer.getnPhone());
        long id = db.insert(Customer.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public long AddDelivery(Delivery delivery){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Delivery.COLUMN_ORDER_ID, delivery.getnOrderID());
        values.put(Delivery.COLUMN_DRIVER_ID, delivery.getnDriverID());
        values.put(Delivery.COLUMN_CUSTOMER_ID, delivery.getnCustomerID());
        values.put(Delivery.COLUMN_STATUS, delivery.getsStatus());
        values.put(Delivery.COLUMN_DELIVERY_FEE, delivery.getnDeliveryFee());
        long id = db.insert(Delivery.TABLE_NAME, null, values);
        db.close();
        return id;
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

    public void CreateDefaultGroups(){
        AddGroup(new Group("Appetizers"));
        AddGroup(new Group("Main"));
        AddGroup(new Group("Pizza"));
        AddGroup(new Group("Desserts"));
        AddGroup(new Group("Drinks"));
        AddGroup(new Group("Salads"));
    }

    public void CreateDefaultProducts(){
        AddProduct(new Product(1, "Ultimate Garlic Bread", 5.5));
        AddProduct(new Product(1, "Artisan Bread Basket", 4.5));
        AddProduct(new Product(1, "Caprese Salad", 14.5));
        AddProduct(new Product(1, "Arancini Margherita", 15));
        AddProduct(new Product(1, "Tomato Bruschetta", 12.5));
        AddProduct(new Product(1, "Crunchy Italian Nachos", 13.5));
        AddProduct(new Product(1, "Primavera Bruschetta", 12.5));
        AddProduct(new Product(1, "World's Best Olives", 9));
        AddProduct(new Product(1, "Crispy Squid", 15));
        AddProduct(new Product(1, "Mushroom Fritti", 13));

        AddProduct(new Product(2, "Chicken Milanese", 30));
        AddProduct(new Product(2, "Italian Steak & Fries", 28.5));
        AddProduct(new Product(2, "Super Special Awesome Burger", 24));
        AddProduct(new Product(2, "Strip Loin Steak", 34));
        AddProduct(new Product(2, "Grilled Pork Chop", 32));
        AddProduct(new Product(2, "Herby Lamb Steak", 32));
        AddProduct(new Product(2, "Sicilian-Spiced Eggplant", 19));
        AddProduct(new Product(2, "Mulloway Acqua Pazza", 31));
        AddProduct(new Product(2, "Fish of the Day", 23));
        AddProduct(new Product(2, "Gennaro's Chicken Primavera", 32));

        AddProduct(new Product(3, "Margarita Pizza", 16));
        AddProduct(new Product(3, "Napoletana Pizza", 17));
        AddProduct(new Product(3, "Wild Mushroom Pizza", 19));
        AddProduct(new Product(3, "Onion Pizza", 16));
        AddProduct(new Product(3, "Hawaiian Pizza", 17));
        AddProduct(new Product(3, "Aussie Pizza", 16));
        AddProduct(new Product(3, "Lamb Shoulder Pizza", 19));
        AddProduct(new Product(3, "Smoked Salmon Pizza", 19));
        AddProduct(new Product(3, "Pizza Royale 007", 4200));

        AddProduct(new Product(4, "Epic Chocolate Brownie", 12.5));
        AddProduct(new Product(4, "Molten Chocolate Pudding of DOOM", 13));
        AddProduct(new Product(4, "Madolche Tiaramisu", 11));
        AddProduct(new Product(4, "Organic Yoghurt Panna Cotta", 9));
        AddProduct(new Product(4, "Orange Blossom Polenta Cake", 10.5));
        AddProduct(new Product(4, "Amalfi Lemon Meringue Cheesecake", 14));

        AddProduct(new Product(5, "Rossini", 9.95));
        AddProduct(new Product(5, "Belini", 9.95));
        AddProduct(new Product(5, "Aperol Spritz", 11));
        AddProduct(new Product(5, "Grande Reserve", 16.5));
        AddProduct(new Product(5, "Special Mojito", 16.34));
        AddProduct(new Product(5, "Ginger Martini", 9.96));
        AddProduct(new Product(5, "Espresso Martini", 9.99));
        AddProduct(new Product(5, "Vanilla & Lemon Martini", 16));
        AddProduct(new Product(5, "Soft Drink", 3));
        AddProduct(new Product(5, "Fresh Juice", 5));

        AddProduct(new Product(6, "Classic Super Food Salad", 18));
        AddProduct(new Product(6, "Bresaola Salad", 18));
        AddProduct(new Product(6, "Prosciutto & Pear Salad", 18));

        Log.d("Product Table 1 Size", String.valueOf(GetProducts(1).size()));
        Log.d("Product Table 2 Size", String.valueOf(GetProducts(1).size()));
        Log.d("Product Table 3 Size", String.valueOf(GetProducts(1).size()));
        Log.d("Product Table 4 Size", String.valueOf(GetProducts(1).size()));
        Log.d("Product Table 5 Size", String.valueOf(GetProducts(1).size()));
        Log.d("Product Table 6 Size", String.valueOf(GetProducts(1).size()));
    }

    public void deleteTable(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(tableName, null, null);
        db.execSQL("delete * from " + tableName);
        db.execSQL("TRUNCATE table" + tableName);
        db.close();
    }
}
