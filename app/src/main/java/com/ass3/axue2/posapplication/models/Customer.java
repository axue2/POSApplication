package com.ass3.axue2.posapplication.models;

/**
 * Created by Anthony on 9/05/2017.
 */

public class Customer {
    private long nCustomerID;
    private String sAddressLine1;
    private String sAddressLine2;
    private String sAddressLine3;
    private int nPostCode;
    private int nPhone;


    // Database Constants
    static final String TABLE_NAME = "Customers";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
    static final String COLUMN_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
    static final String COLUMN_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
    static final String COLUMN_POST_CODE = "POST_CODE";
    static final String COLUMN_PHONE = "PHONE";

    // Customer Create Statement
    static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_ADDRESS_LINE_1 + " TEXT NOT NULL, " +
            COLUMN_ADDRESS_LINE_2 + " TEXT NOT NULL, " +
            COLUMN_ADDRESS_LINE_3 + " TEXT, " +
            COLUMN_POST_CODE + " INTEGER NOT NULL, " +
            COLUMN_PHONE + " INTEGER" +
            ")";
}
