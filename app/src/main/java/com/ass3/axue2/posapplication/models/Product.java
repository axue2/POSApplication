package com.ass3.axue2.posapplication.models;

/**
 * Created by anthony on 4/29/2017.
 *
 */

public class Product {

    // Database Constants
    static final String TABLE_NAME = "Products";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_GROUPID = "GROUP_ID";
    static final String COLUMN_NAME = "NAME";
    static final String COLUMN_PRICE = "PRICE";

    // Order Create Statement
    static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_GROUPID + " INTEGER NOT NULL," +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_PRICE + " REAL" +
            ")";

    private long nProductID;
    private long nGroupID;
    private String sProductName;
    private double nPrice;

    public Product(long productID, long groupID, String name, double price){
        nProductID = productID;
        nGroupID = groupID;
        sProductName = name;
        nPrice = price;
    }


    public Product(long groupID, String name, double price){
        nProductID = 0;
        nGroupID = groupID;
        sProductName = name;
        nPrice = price;
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

    public long getnGroupID() {
        return nGroupID;
    }

    public void setnGroupID(long nGroupID) {
        this.nGroupID = nGroupID;
    }

}
