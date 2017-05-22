package com.ass3.axue2.posapplication.models.operational;

/**
 * Created by anthony on 4/21/2017.
 *
 */

public class Table {

    private long nTableID;
    private String sTableName;
    private int nGuests;
    private long nOrderID;
    private double nInvSum;
    private String sStatus;

    // Database Constants
    public static final String TABLE_NAME = "Tables";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_GUESTS = "NO_GUESTS";
    public static final String COLUMN_ORDER_ID = "ORDER_ID";
    public static final String COLUMN_TOTAL = "INVOICE_TOTAL";
    public static final String COLUMN_STATUS = "STATUS";

    // Constants for Table status
    public static final String STATUS_OPEN = "OPEN";
    public static final String STATUS_INUSE = "IN-USE";
    public static final String STATUS_BOOKED = "BOOKED";


    // Table Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_GUESTS + " INTEGER, " +
            COLUMN_ORDER_ID + " INTEGER, " +
            COLUMN_TOTAL + " REAL, " +
            COLUMN_STATUS + " TEXT NOT NULL" +
            ")";

    public Table(long id, String name, int guests, long orderID,
                 double total, String status) {
        nTableID = id;
        sTableName = name;
        nGuests = guests;
        nOrderID = orderID;
        nInvSum = total;
        sStatus = status;
    }

    public Table(String name) {
        nTableID = -1;
        sTableName = name;
        nGuests = 0;
        nOrderID = -1;
        nInvSum = 0;
        sStatus = STATUS_OPEN;
    }

    public Table(){
        nTableID = -1;
        sTableName = "tmp";
        nGuests = 1;
        nInvSum = 1;
        sStatus = STATUS_OPEN;
    }
    public long getnTableID() {return nTableID;}

    public void setnTableID(long nTableID) {this.nTableID = nTableID;}

    public String getsTableName() {return sTableName;}

    public void setsTableName(String sTableName) {this.sTableName = sTableName;}

    public int getnGuests() {return nGuests;}

    public void setnGuests(int nGuests) {this.nGuests = nGuests;}

    public double getnInvSum() {return nInvSum;}

    public void setnInvSum(double nInvSum) {this.nInvSum = nInvSum;}

    public String getsStatus() {return sStatus;}

    public void setsStatus(String sStatus) {this.sStatus = sStatus;}

    public long getnOrderID() {return nOrderID;}

    public void setnOrderID(long nOrderID) {this.nOrderID = nOrderID;}


}
