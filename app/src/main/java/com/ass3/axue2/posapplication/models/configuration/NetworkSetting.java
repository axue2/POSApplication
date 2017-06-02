package com.ass3.axue2.posapplication.models.configuration;

/**
 * Created by anthony on 5/22/2017.
 *
 */

public class NetworkSetting {
    // Database Constants
    static final String TABLE_NAME = "Groups";
    static final String COLUMN_ID = "ID";
    static final String COLUMN_NETWORK_MODE = "NETWORK_MODE";
    static final String COLUMN_IP_ADDRESS = "IP_ADDRESS";
    static final String COLUMN_DATABASE_NAME = "DATABASE_NAME";
    static final String COLUMN_USERNAME = "USERNAME";
    static final String COLUMN_PASSWORD = "PASSWORD";

    // Create Statement
    static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NETWORK_MODE + " INTEGER NOT NULL, " +
            COLUMN_IP_ADDRESS + " TEXT, " +
            COLUMN_DATABASE_NAME + " TEXT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_PASSWORD + " TEXT" +
            ")";

    private long nID;
    private int nNetworkMode;
    private String sIPAddress;
    private String sDBName;
    private String sUsername;
    private String sPassword;


    public NetworkSetting(long id, int network_mode){
        nID = id;
        nNetworkMode = network_mode;
        sIPAddress = "";
        sDBName = "";
        sUsername = "";
        sPassword = "";
    }

    public NetworkSetting(long id, int network_mode, String ipAddress,
                          String dbName, String username, String password){
        nID = id;
        nNetworkMode = network_mode;
        sIPAddress = ipAddress;
        sDBName = dbName;
        sUsername = username;
        sPassword = password;
    }


    public long getnID() {
        return nID;
    }

    public void setnID(long nID) {
        this.nID = nID;
    }

    public int getnNetworkMode() {
        return nNetworkMode;
    }

    public void setnNetworkMode(int nNetworkMode) {
        this.nNetworkMode = nNetworkMode;
    }

    public String getsIPAddress() {
        return sIPAddress;
    }

    public void setsIPAddress(String sIPAddress) {
        this.sIPAddress = sIPAddress;
    }

    public String getsDBName() {
        return sDBName;
    }

    public void setsDBName(String sDBName) {
        this.sDBName = sDBName;
    }

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public String getsPassword() {
        return sPassword;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

}
