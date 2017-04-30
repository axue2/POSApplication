package com.ass3.axue2.posapplication.models;

/**
 * Created by anthony on 5/1/2017.
 * This class represents the item contents of the Order
 */

public class OrderItem {
    private long nOrderItemID;
    private long nOrderID;
    private long nTableID;
    private long nProductID;
    private int nPrice;
    private int nQuantity;

    // Database Constants
    public static final String TABLE_NAME = "Order_Item";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ORDER_ID = "ORDER_ID";
    public static final String COLUMN_TABLE_ID = "TABLE_ID";
    public static final String COLUMN_PRODUCT_ID = "PRODUCT_ID";
    public static final String COLUMN_PRODUCT_PRICE = "PRODUCT_PRICE";
    public static final String COLUMN_QUANTITY = "QUANTITY";

    // OrderItem Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_ORDER_ID + " INTEGER NOT NULL, " +
            COLUMN_TABLE_ID + " INTEGER NOT NULL, " +
            COLUMN_PRODUCT_ID + " INTEGER NOT NULL, " +
            COLUMN_PRODUCT_PRICE + " INTEGER, " +
            COLUMN_QUANTITY + " INTEGER" +
            ")";

    OrderItem(long orderItemID, long orderID, long tableID, long productID, int price, int quantity){
        nOrderItemID = orderItemID;
        nOrderID = orderID;
        nTableID = tableID;
        nProductID = productID;
        nPrice = price;
        nQuantity = quantity;
    }

    OrderItem(long orderID, long tableID, long productID, int price, int quantity){
        nOrderItemID = -1;
        nOrderID = orderID;
        nTableID = tableID;
        nProductID = productID;
        nPrice = price;
        nQuantity = quantity;
    }

}
