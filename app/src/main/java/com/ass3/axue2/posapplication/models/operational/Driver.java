package com.ass3.axue2.posapplication.models.operational;

/**
 * Created by anthony on 5/13/2017.
 *
 */

public class Driver {

    // Database Constants
    public static final String TABLE_NAME = "Drivers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";

    public static final String NAME_ALL = "ALL";

    // Driver Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_FIRST_NAME + " TEXT, " +
            COLUMN_LAST_NAME + " TEXT" +
            ")";


    private long nDriverID;
    private String nFirstName;
    private String nLastName;



    public Driver(long driverID, String firstName, String lastName){
        nDriverID = driverID;
        nFirstName = firstName;
        nLastName = lastName;
    }

    public Driver(String firstName, String lastName){
        nDriverID = -1;
        nFirstName = firstName;
        nLastName = lastName;
    }

    public Driver() {
        nDriverID = 0;
        nFirstName = "";
        nLastName = "";
    }

    public long getnDriverID() {
        return nDriverID;
    }

    public void setnDriverID(long nDriverID) {
        this.nDriverID = nDriverID;
    }

    public String getnFirstName() {
        return nFirstName;
    }

    public void setnFirstName(String nFirstName) {
        this.nFirstName = nFirstName;
    }

    public String getnLastName() {
        return nLastName;
    }

    public void setnLastName(String nLastName) {
        this.nLastName = nLastName;
    }
}
