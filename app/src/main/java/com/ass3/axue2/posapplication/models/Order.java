package com.ass3.axue2.posapplication.models;

/**
 * Created by anthony on 5/1/2017.
 */

public class Order {

    private long nOrderID;
    private long nTableID;
    private String sType;
    private String sStatus;
    private int nTotal;

    // Database Constants
    public static final String TABLE_NAME = "Orders";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TABLE_ID = "TABLE_ID";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_TOTAL = "TOTAL";

    // Constants for different Order types
    public static final String TYPE_EAT_IN = "EAT-IN";
    public static final String TYPE_TAKEAWAY = "TAKEAWAY";
    public static final String TYPE_DELIVERY = "DELIVERY";

    // Constants for different Order status
    public static final String STATUS_UNPAID = "UNPAID";
    public static final String STATUS_PAID = "PAID";
    public static final String STATUS_REFUND = "REFUND";

    // Order Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_TABLE_ID + " INTEGER NOT NULL, " +
            COLUMN_TYPE + " TEXT NOT NULL, " +
            COLUMN_STATUS + " TEXT NOT NULL, " +
            COLUMN_TOTAL + " INTEGER" +
            ")";

    public Order(long orderID, long tableID, String type, String status, int total){
        nOrderID = orderID;
        nTableID = tableID;
        sType = type;
        sStatus = status;
        nTotal = total;
    }

    public Order(long tableID, String type, String status, int total){
        nOrderID = -1;
        nTableID = tableID;
        sType = type;
        sStatus = status;
        nTotal = total;
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

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public String getsStatus() {
        return sStatus;
    }

    public void setsStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    public int getnTotal() {
        return nTotal;
    }

    public void setnTotal(int nTotal) {
        this.nTotal = nTotal;
    }
}
