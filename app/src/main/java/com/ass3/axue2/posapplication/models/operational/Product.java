package com.ass3.axue2.posapplication.models.operational;

/**
 * Created by anthony on 4/29/2017.
 *
 */

public class Product {

    // Database Constants
    public static final String TABLE_NAME = "Products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_GROUPID = "GROUP_ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PRICE = "PRICE";
    public static final String COLUMN_GST_PERCENT = "GST_PERCENT";
    public static final String COLUMN_PRINTER_ID = "PRINTER";
    public static final String COLUMN_PRINTER_2_ID = "PRINTER_2_ID";

    // Order Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_GROUPID + " INTEGER NOT NULL," +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_GST_PERCENT + " REAL, " +
            COLUMN_PRINTER_ID + " INTEGER, " +
            COLUMN_PRINTER_2_ID + " INTEGER" +
            ")";

    private long nProductID;
    private long nGroupID;
    private String sProductName;
    private double nPrice;
    private double nGSTPercent;
    private long nPrinterID;
    private long nPrinter2ID;

    public Product(long productID, long groupID, String name, double price, double gstPercent,
                   long printerID, long printer2ID){
        nProductID = productID;
        nGroupID = groupID;
        sProductName = name;
        nPrice = price;
        nGSTPercent = gstPercent;
        nPrinterID = printerID;
        nPrinter2ID = printer2ID;
    }


    public Product(long productID, long groupID, String name, double price){
        nProductID = productID;
        nGroupID = groupID;
        sProductName = name;
        nPrice = price;
        nGSTPercent = -0.1;
        nPrinterID = 0;
        nPrinter2ID = 0;
    }


    public Product(long groupID, String name, double price){
        nProductID = 0;
        nGroupID = groupID;
        sProductName = name;
        nPrice = price;
        nGSTPercent = -0.1;
        nPrinterID = 0;
        nPrinter2ID = 0;
    }

    public Product() {
        nProductID = 0;
        nGroupID = 0;
        sProductName = "";
        nPrice = 0;
        nGSTPercent = -0.1;
        nPrinterID = 0;
        nPrinter2ID = 0;
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

    public long getnPrinterID() {
        return nPrinterID;
    }

    public void setnPrinterID(long nPrinterID) {
        this.nPrinterID = nPrinterID;
    }

    public long getnPrinter2ID() {
        return nPrinter2ID;
    }

    public void setnPrinter2ID(long nPrinter2ID) {
        this.nPrinter2ID = nPrinter2ID;
    }


    public double getnGSTPercent() {
        return nGSTPercent;
    }

    public void setnGSTPercent(double nGSTPercent) {
        this.nGSTPercent = nGSTPercent;
    }


}
