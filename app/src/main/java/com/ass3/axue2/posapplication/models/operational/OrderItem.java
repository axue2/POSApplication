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

    // OrderItem Variables to be implemented in offline version of app
    // TODO: Item Position, Multiple Restaurants (chains), Tills, Printers, GST
    private int nPosition;
    private long nDepartmentID;
    private long nTillID;
    private long nPrinterID;
    private long nPrinter2ID;
    private int nQuantityPrinted;
    private double nGSTPercent;


    // Database Constants
    public static final String TABLE_NAME = "Order_Item";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ORDER_ID = "ORDER_ID";
    public static final String COLUMN_TABLE_ID = "TABLE_ID";
    public static final String COLUMN_PRODUCT_ID = "PRODUCT_ID";
    public static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String COLUMN_PRODUCT_PRICE = "PRODUCT_PRICE";
    public static final String COLUMN_QUANTITY = "QUANTITY";
    public static final String COLUMN_POSITION = "POSITION";
    public static final String COLUMN_DEPARTMENT_ID = "DEPARTMENT_ID";
    public static final String COLUMN_TILL_ID = "TILL_ID";
    public static final String COLUMN_PRINTER_ID = "PRINTER_ID";
    public static final String COLUMN_PRINTER_2_ID = "PRINTER_2_ID";
    public static final String COLUMN_QUANTITY_PRINTED = "QUANTITY_PRINTED";
    public static final String COLUMN_GST_PERCENT = "GST_PERCENT";


    // OrderItem Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_ORDER_ID + " INTEGER NOT NULL, " +
            COLUMN_TABLE_ID + " INTEGER NOT NULL, " +
            COLUMN_PRODUCT_ID + " INTEGER NOT NULL, " +
            COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
            COLUMN_PRODUCT_PRICE + " REAL, " +
            COLUMN_QUANTITY + " INTEGER, " +
            COLUMN_POSITION + " INTEGER, " +
            COLUMN_DEPARTMENT_ID + " INTEGER, " +
            COLUMN_TILL_ID + " INTEGER, " +
            COLUMN_PRINTER_ID + " INTEGER, " +
            COLUMN_PRINTER_2_ID + " INTEGER, " +
            COLUMN_QUANTITY_PRINTED + " INTEGER, " +
            COLUMN_GST_PERCENT + " REAL" +
            ")";

    public OrderItem(long orderItemID, long orderID, long tableID, long productID, String name,
                     double price, int quantity, int position, int tillID, long printerID,
                     long printer2ID, int quantityPrinted, double gstPercent){
        nOrderItemID = orderItemID;
        nOrderID = orderID;
        nTableID = tableID;
        nProductID = productID;
        sProductName = name;
        nPrice = price;
        nQuantity = quantity;
        nPosition = position;
        nTillID = tillID;
        nPrinterID = printerID;
        nPrinter2ID = printer2ID;
        nQuantityPrinted = quantityPrinted;
        nGSTPercent = gstPercent;
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
        nPosition = -1;
        nDepartmentID = 0;
        nTillID = 0;
        nPrinterID = 0;
        nPrinter2ID = 0;
        nQuantityPrinted = 0;
        nGSTPercent = -0.1;
    }

    public OrderItem() {
        nOrderItemID = 0;
        nOrderID = 0;
        nTableID = 0;
        nProductID = 0;
        sProductName = "";
        nPrice = 0;
        nQuantity = 0;
        nPosition = -1;
        nDepartmentID = 0;
        nTillID = 0;
        nPrinterID = 0;
        nPrinter2ID = 0;
        nQuantityPrinted = 0;
        nGSTPercent = -0.1;
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

    public int getnPosition() {
        return nPosition;
    }

    public void setnPosition(int nPosition) {
        this.nPosition = nPosition;
    }

    public long getnDepartmentID() {
        return nDepartmentID;
    }

    public void setnDepartmentID(long nDepartmentID) {
        this.nDepartmentID = nDepartmentID;
    }

    public long getnTillID() {
        return nTillID;
    }

    public void setnTillID(long nTillID) {
        this.nTillID = nTillID;
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

    public int getnQuantityPrinted() {
        return nQuantityPrinted;
    }

    public void setnQuantityPrinted(int nQuantityPrinted) {
        this.nQuantityPrinted = nQuantityPrinted;
    }

    public double getnGSTPercent() {
        return nGSTPercent;
    }

    public void setnGSTPercent(double nGSTPercent) {
        this.nGSTPercent = nGSTPercent;
    }



}
