package com.ass3.axue2.posapplication.models;

/**
 * Created by anthony on 4/21/2017.
 */

public class Table {

    private long nTableID;
    private String sTableName;
    private int nGuests;
    private int nInvSum;
    private String sStatus;

    // Database Constants
    public static final String TABLE_NAME = "Tables";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_GUESTS = "NO_GUESTS";
    public static final String COLUMN_TOTAL = "INVOICE_TOTAL";
    public static final String COLUMN_STATUS = "STATUS";

    // Table Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_GUESTS + " INTEGER, " +
            COLUMN_TOTAL + " INTEGER, " +
            COLUMN_STATUS + " TEXT NOT NULL" +
            ")";

    public Table(long id, String name, int guests,
                 int total, String status) {
        nTableID = id;
        sTableName = name;
        nGuests = guests;
        nInvSum = total;
        sStatus = status;
    }

    public Table(){
        nTableID = -1;
        sTableName = "tmp";
        nGuests = 1;
        nInvSum = 1;
        sStatus = "Open";
    }
    public long getnTableID() {return nTableID;}

    public void setnTableID(long nTableID) {this.nTableID = nTableID;}

    public String getsTableName() {return sTableName;}

    public void setsTableName(String sTableName) {this.sTableName = sTableName;}

    public int getnGuests() {return nGuests;}

    public void setnGuests(int nGuests) {this.nGuests = nGuests;}

    public int getnInvSum() {return nInvSum;}

    public void setnInvSum(int nInvSum) {this.nInvSum = nInvSum;}

    public String getsStatus() {return sStatus;}

    public void setsStatus(String sStatus) {this.sStatus = sStatus;}
}
