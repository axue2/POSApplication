package com.ass3.axue2.posapplication.models.saxpos;

/**
 * Created by anthonyxue on 6/07/2017.
 * SAXPOS Product Table
 */

public class Stkite {

    // Database Constants
    public static final String TABLE_NAME = "stkite";
    public static final String COLUMN_ID = "ite_id";
    public static final String COLUMN_NAME = "ite_name";
    public static final String COLUMN_STATUS = "ite_status";
    public static final String COLUMN_GROUP_BY = "ite_group_by";
    public static final String COLUMN_SALES_PRICE = "ite_sales_price";


    private String sID;
    private String sName;
    private String sStatus;
    private String sGroupBy;
    private double sSalesPrice;


    public Stkite(String id, String status, String groupBy, double salesPrice, String name){
        sID = id;
        sName = name;
        sStatus = status;
        sGroupBy = groupBy;
        sSalesPrice = salesPrice;
    }

    public Stkite(){
        sID = "";
        sName = "";
        sStatus = "";
        sGroupBy = "";
        sSalesPrice = 0;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsStatus() {
        return sStatus;
    }

    public void setsStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    public String getsGroupBy() {
        return sGroupBy;
    }

    public void setsGroupBy(String sGroupBy) {
        this.sGroupBy = sGroupBy;
    }

    public double getsSalesPrice() {
        return sSalesPrice;
    }

    public void setsSalesPrice(double sSalesPrice) {
        this.sSalesPrice = sSalesPrice;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }



}
