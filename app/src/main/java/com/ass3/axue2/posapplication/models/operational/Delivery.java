package com.ass3.axue2.posapplication.models.operational;

/**
 * Created by Anthony on 9/05/2017.
 *
 */

public class Delivery {
    private long nDeliveryID;
    private long nOrderID;
    private long nDriverID;
    private long nCustomerID;
    private String sStatus;
    private double nDeliveryFee;

    // Constants for different Delivery status
    public static final String STATUS_UNALLOCATED = "UNALLOCATED";
    public static final String STATUS_ALLOCATED = "ALLOCATED";
    public static final String STATUS_DELIVERED = "DELIVERED";
    public static final String STATUS_COMPLETE = "COMPLETE";

    // Database Constants
    public static final String TABLE_NAME = "Delivery";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ORDER_ID = "ORDER_ID";
    public static final String COLUMN_DRIVER_ID = "DRIVER_ID";
    public static final String COLUMN_CUSTOMER_ID = "CUSTOMER_ID";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_DELIVERY_FEE = "DELIVERY_FEE";

    // Customer Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_ORDER_ID + " INTEGER NOT NULL, " +
            COLUMN_DRIVER_ID + " INTEGER, " +
            COLUMN_CUSTOMER_ID + " INTEGER, " +
            COLUMN_STATUS + " TEXT NOT NULL, " +
            COLUMN_DELIVERY_FEE + " REAL" +
            ")";

    public Delivery(long deliveryID, long orderID, long driverID, long customerID,
                    String status, double deliveryFee){
        nDeliveryID = deliveryID;
        nOrderID = orderID;
        nDriverID = driverID;
        nCustomerID = customerID;
        sStatus = status;
        nDeliveryFee = deliveryFee;
    }

    public Delivery(long driverID, double deliveryFee, String status){
        nDeliveryID = -1;
        nOrderID = -1;
        nDriverID = driverID;
        nCustomerID = -1;
        sStatus = status;
        nDeliveryFee = deliveryFee;
    }

    public Delivery(long orderID, long customerID, double deliveryFee){
        nDeliveryID = 0;
        nOrderID = orderID;
        nDriverID = 0;
        nCustomerID = customerID;
        sStatus = STATUS_UNALLOCATED;
        nDeliveryFee = deliveryFee;
    }

    public Delivery() {
        nDeliveryID = 0;
        nOrderID = 0;
        nDriverID = 0;
        nCustomerID = 0;
        sStatus = "";
        nDeliveryFee = 0;
    }


    public long getnDeliveryID() {
        return nDeliveryID;
    }

    public void setnDeliveryID(long nDeliveryID) {
        this.nDeliveryID = nDeliveryID;
    }

    public long getnOrderID() {
        return nOrderID;
    }

    public void setnOrderID(long nOrderID) {
        this.nOrderID = nOrderID;
    }

    public long getnCustomerID() {
        return nCustomerID;
    }

    public void setnCustomerID(long nCustomerID) {
        this.nCustomerID = nCustomerID;
    }

    public long getnDriverID() {
        return nDriverID;
    }

    public void setnDriverID(long nDriverID) {
        this.nDriverID = nDriverID;
    }

    public String getsStatus() {
        return sStatus;
    }

    public void setsStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    public double getnDeliveryFee() {
        return nDeliveryFee;
    }

    public void setnDeliveryFee(double nDeliveryFee) {
        this.nDeliveryFee = nDeliveryFee;
    }
}
