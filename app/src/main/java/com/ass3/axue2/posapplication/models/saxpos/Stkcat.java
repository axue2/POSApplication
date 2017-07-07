package com.ass3.axue2.posapplication.models.saxpos;

/**
 * Created by anthonyxue on 6/07/2017.
 * SAXPOS Group Table
 */

public class Stkcat {

    // Database Constants
    public static final String TABLE_NAME = "stkcat";
    public static final String COLUMN_ID = "cat_id";
    public static final String COLUMN_NAME = "cat_name";
    public static final String COLUMN_STATUS = "cat_status";

    private String sID;
    private String sName;
    private String sStatus;

    public Stkcat(String id, String name, String status){
        sID = id;
        sName = name;
        sStatus = status;
    }

    public Stkcat(){
        sID = "";
        sName = "";
        sStatus = "";
    }


    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsStatus() {
        return sStatus;
    }

    public void setsStatus(String sStatus) {
        this.sStatus = sStatus;
    }


}
