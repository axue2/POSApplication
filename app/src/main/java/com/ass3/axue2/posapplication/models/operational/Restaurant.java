package com.ass3.axue2.posapplication.models.operational;

/**
 * Created by anthony on 6/7/2017.
 */

public class Restaurant {

    private long nRestaurantID;
    private String sRestaurantName;
    private String sAddressLine1;
    private String sAddressLine2;
    private String sAddressLine3;
    private int nPostCode;
    private String sPhone;


    // Database Constants
    public static final String TABLE_NAME = "Restaurant";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
    public static final String COLUMN_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
    public static final String COLUMN_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
    public static final String COLUMN_POST_CODE = "POST_CODE";
    public static final String COLUMN_PHONE = "PHONE";

    // Restaurant Setting
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_ADDRESS_LINE_1 + " TEXT NOT NULL, " +
            COLUMN_ADDRESS_LINE_2 + " TEXT, " +
            COLUMN_ADDRESS_LINE_3 + " TEXT, " +
            COLUMN_POST_CODE + " INTEGER NOT NULL, " +
            COLUMN_PHONE + " STRING" +
            ")";

    public Restaurant(long id, String name, String al1, String al2, String al3, int postcode, String phone){
        nRestaurantID = id;
        sRestaurantName = name;
        sAddressLine1 = al1;
        sAddressLine2 = al2;
        sAddressLine3 = al3;
        nPostCode = postcode;
        sPhone = phone;
    }

    public long getnRestaurantID() {
        return nRestaurantID;
    }

    public void setnRestaurantID(long nRestaurantID) {
        this.nRestaurantID = nRestaurantID;
    }

    public String getsRestaurantName() {
        return sRestaurantName;
    }

    public void setsRestaurantName(String sRestaurantName) {
        this.sRestaurantName = sRestaurantName;
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

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }
}
