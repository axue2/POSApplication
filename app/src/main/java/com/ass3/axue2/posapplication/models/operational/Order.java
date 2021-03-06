package com.ass3.axue2.posapplication.models.operational;

/**
 * Created by anthony on 5/1/2017.
 *
 */

public class Order {

    private long nOrderID;
    private long nTableID;
    private String sType;
    private String sStatus;
    private double nTotal;

    private long nTillID;
    private int nGuests;

    // Database Constants
    public static final String TABLE_NAME = "Orders";
    public static final String COLUMN_ID = "id";
    //TODO: Convert Table ID to Table Name or create a Table Name attribute
    public static final String COLUMN_TABLE_ID = "TABLE_ID";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_TOTAL = "TOTAL";

    // Constants for different Order types
    public static final String TYPE_EAT_IN = "EAT-IN";
    public static final String TYPE_TAKEAWAY = "TAKEAWAY";
    public static final String TYPE_DELIVERY = "DELIVERY";
    public static final String TYPE_BOOKING = "BOOKING";

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
            COLUMN_TOTAL + " REAL" +
            ")";

    public Order(long orderID, long tableID, String type, String status, double total){
        nOrderID = orderID;
        nTableID = tableID;
        sType = type;
        sStatus = status;
        nTotal = total;
        nGuests = 0;
        nTillID = 0;
    }

    public Order(long orderID, String type, String status, double total){
        nOrderID = orderID;
        nTableID = -1;
        sType = type;
        sStatus = status;
        nTotal = total;
        nGuests = 0;
        nTillID = 0;
    }

    public Order(String type, String status, double total){
        nOrderID = -1;
        nTableID = -1;
        sType = type;
        sStatus = status;
        nTotal = total;
        nGuests = 0;
        nTillID = 0;
    }

    public Order() {
        nOrderID = -1;
        nTableID = -1;
        sType = "";
        sStatus = "";
        nTotal = 0;
        nGuests = 0;
        nTillID = 0;
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

    public double getnTotal() {
        return nTotal;
    }

    public void setnTotal(double nTotal) {
        this.nTotal = nTotal;
    }


    public long getnTillID() {
        return nTillID;
    }

    public void setnTillID(long nTillID) {
        this.nTillID = nTillID;
    }

    public int getnGuests() {
        return nGuests;
    }

    public void setnGuests(int nGuests) {
        this.nGuests = nGuests;
    }


}
