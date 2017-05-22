package com.ass3.axue2.posapplication.models.configuration;

/**
 * Created by anthony on 5/22/2017.
 *
 */

public class ConfigurationSetting {
    // Database Constants
    static final String TABLE_NAME = "Groups";
    static final String COLUMN_ID = "ID";
    static final String COLUMN_NETWORK_MODE = "NETWORK_MODE";

    // Create Statement
    static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NETWORK_MODE + " BOOLEAN NOT NULL" +
            ")";

    private long nID;
    private int nNetworkMode;

    public ConfigurationSetting(long id, int network_mode){
        nID = id;
        nNetworkMode = network_mode;
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

}
