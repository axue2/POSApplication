package com.ass3.axue2.posapplication.models.operational;

/**
 * Created by Anthony on 9/05/2017.
 *
 */

public class Customer {

    private long nCustomerID;
    private String sFirstName;
    private String sLastName;
    private String sAddressLine1;
    private String sAddressLine2;
    private String sAddressLine3;
    private int nPostCode;
    private int nPhone;

    // Database Constants
    public static final String TABLE_NAME = "Customers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
    public static final String COLUMN_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
    public static final String COLUMN_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
    public static final String COLUMN_POST_CODE = "POST_CODE";
    public static final String COLUMN_PHONE = "PHONE";

    // Customer Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_FIRST_NAME + " TEXT, " +
            COLUMN_LAST_NAME + " TEXT, " +
            COLUMN_ADDRESS_LINE_1 + " TEXT NOT NULL, " +
            COLUMN_ADDRESS_LINE_2 + " TEXT, " +
            COLUMN_ADDRESS_LINE_3 + " TEXT, " +
            COLUMN_POST_CODE + " INTEGER NOT NULL, " +
            COLUMN_PHONE + " INTEGER" +
            ")";

    public Customer(long id, String firstName, String lastName, String al1, String al2, String al3, int postcode, int phone){
        nCustomerID = id;
        sFirstName = firstName;
        sLastName = lastName;
        sAddressLine1 = al1;
        sAddressLine2 = al2;
        sAddressLine3 = al3;
        nPostCode = postcode;
        nPhone = phone;
    }

    public Customer(String firstName, String lastName, String al1, String al2, String al3, int postcode, int phone){
        nCustomerID = -1;
        sFirstName = firstName;
        sLastName = lastName;
        sAddressLine1 = al1;
        sAddressLine2 = al2;
        sAddressLine3 = al3;
        nPostCode = postcode;
        nPhone = phone;
    }

    public Customer(){
        nCustomerID = -1;
        sFirstName = "";
        sLastName = "";
        sAddressLine1 = "";
        sAddressLine2 = "";
        sAddressLine3 = "";
        nPostCode = -1;
        nPhone = -1;
    }

    public long getnCustomerID() {
        return nCustomerID;
    }

    public void setnCustomerID(long nCustomerID) {
        this.nCustomerID = nCustomerID;
    }

    public String getsAddressLine1() {
        return sAddressLine1;
    }

    public void setsAddressLine1(String sAddressLine1) {
        this.sAddressLine1 = sAddressLine1;
    }

    public String getsAddressLine2() {
        return sAddressLine2;
    }

    public void setsAddressLine2(String sAddressLine2) {
        this.sAddressLine2 = sAddressLine2;
    }

    public String getsAddressLine3() {
        return sAddressLine3;
    }

    public void setsAddressLine3(String sAddressLine3) {
        this.sAddressLine3 = sAddressLine3;
    }

    public int getnPostCode() {
        return nPostCode;
    }

    public void setnPostCode(int nPostCode) {
        this.nPostCode = nPostCode;
    }

    public int getnPhone() {
        return nPhone;
    }

    public void setnPhone(int nPhone) {
        this.nPhone = nPhone;
    }

    public String getsFirstName() {
        return sFirstName;
    }

    public void setsFirstName(String sFirstName) {
        this.sFirstName = sFirstName;
    }

    public String getsLastName() {
        return sLastName;
    }

    public void setsLastName(String sLastName) {
        this.sLastName = sLastName;
    }
}
