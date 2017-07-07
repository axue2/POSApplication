package com.ass3.axue2.posapplication.models.saxpos;

/**
 * Created by anthonyxue on 6/07/2017.
 * SAXPOS Customer Table
 */

public class Popcum {
    // Database Constants
    public static final String TABLE_NAME = "popcum";
    public static final String COLUMN_ID = "cum_cid";
    // Account No set to 0000
    public static final String COLUMN_ACCOUNT_NO = "cum_account_no";
    public static final String COLUMN_NAME = "cum_store_name";
    public static final String COLUMN_ADDRESS_LINE_1 = "cum_store_add_1";
    public static final String COLUMN_ADDRESS_LINE_2 = "cum_store_add_2";
    public static final String COLUMN_CITY = "cum_store_city";
    public static final String COLUMN_STATE = "cum_store_state";
    public static final String COLUMN_ZIP = "cum_store_zip";
    public static final String COLUMN_PHONE = "cum_store_tele";

    private String sID;
    private String sAccountNo;
    private String sName;
    private String sAL1;
    private String sAL2;
    private String sCity;
    private String sState;
    private String sZip;
    private String sPhone;

    public Popcum(String id, String accountNo, String name,
                  String al1, String al2, String city,
                  String state, String zip, String phone){
        sID = id;
        sAccountNo = accountNo;
        sName = name;
        sAL1 = al1;
        sAL2 = al2;
        sCity = city;
        sState = state;
        sZip = zip;
        sPhone = phone;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsAccountNo() {
        return sAccountNo;
    }

    public void setsAccountNo(String sAccountNo) {
        this.sAccountNo = sAccountNo;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsAL1() {
        return sAL1;
    }

    public void setsAL1(String sAL1) {
        this.sAL1 = sAL1;
    }

    public String getsAL2() {
        return sAL2;
    }

    public void setsAL2(String sAL2) {
        this.sAL2 = sAL2;
    }

    public String getsCity() {
        return sCity;
    }

    public void setsCity(String sCity) {
        this.sCity = sCity;
    }

    public String getsState() {
        return sState;
    }

    public void setsState(String sState) {
        this.sState = sState;
    }

    public String getsZip() {
        return sZip;
    }

    public void setsZip(String sZip) {
        this.sZip = sZip;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }
}
