package com.ass3.axue2.posapplication.models.operational;

/**
 * Created by anthony on 5/1/2017.
 * This class represents the item contents of the Order
 */

public class OrderItem {
    private long nOrderItemID;
    private long nOrderID;
    private long nTableID;
    private long nProductID;
    private String sProductName;
    private double nPrice;
    private int nQuantity;

    // Database Constants
    public static final String TABLE_NAME = "Order_Item";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ORDER_ID = "ORDER_ID";
    public static final String COLUMN_TABLE_ID = "TABLE_ID";
    public static final String COLUMN_PRODUCT_ID = "PRODUCT_ID";
    public static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String COLUMN_PRODUCT_PRICE = "PRODUCT_PRICE";
    public static final String COLUMN_QUANTITY = "QUANTITY";

    // OrderItem Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_ORDER_ID + " INTEGER NOT NULL, " +
            COLUMN_TABLE_ID + " INTEGER NOT NULL, " +
            COLUMN_PRODUCT_ID + " INTEGER NOT NULL, " +
            COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
            COLUMN_PRODUCT_PRICE + " REAL, " +
            COLUMN_QUANTITY + " INTEGER" +
            ")";

    public OrderItem(long orderItemID, long orderID, long tableID, long productID, String name,
              double price, int quantity){
        nOrderItemID = orderItemID;
        nOrderID = orderID;
        nTableID = tableID;
        nProductID = productID;
        sProductName = name;
        nPrice = price;
        nQuantity = quantity;
    }

    public OrderItem(long orderID, long tableID, long productID, String name,
              double price, int quantity){
        nOrderItemID = -1;
        nOrderID = orderID;
        nTableID = tableID;
        nProductID = productID;
        sProductName = name;
        nPrice = price;
        nQuantity = quantity;
    }

    public OrderItem() {
        nOrderItemID = 0;
        nOrderID = 0;
        nTableID = 0;
        nProductID = 0;
        sProductName = "";
        nPrice = 0;
        nQuantity = 0;
    }

    public void increaseQuantityByOne(){setnQuantity(this.nQuantity + 1);}

    public void decreaseQuantityByOne(){setnQuantity(this.nQuantity - 1);}

    public long getnOrderItemID() {
        return nOrderItemID;
    }

    public void setnOrderItemID(long nOrderItemID) {
        this.nOrderItemID = nOrderItemID;
    }

    public long getnOrderID() {
        return nOrderID;
    }

    public void setnOrderID(long nOrderID) {
        this.nOrderID = nOrderID;
    }

    public long getnTableID() {
        return nTableID;
    }

    public void setnTableID(long nTableID) {
        this.nTableID = nTableID;
    }

    public long getnProductID() {
        return nProductID;
    }

    public void setnProductID(long nProductID) {
        this.nProductID = nProductID;
    }

    public String getsProductName() {
        return sProductName;
    }

    public void setsProductName(String sProductName) {
        this.sProductName = sProductName;
    }

    public double getnPrice() {
        return nPrice;
    }

    public void setnPrice(double nPrice) {
        this.nPrice = nPrice;
    }

    public int getnQuantity() {
        return nQuantity;
    }

    public void setnQuantity(int nQuantity) {
        this.nQuantity = nQuantity;
    }
}
