package com.ass3.axue2.posapplication.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthony on 4/29/2017.
 *
 */

public class Group {

    // Database Constants
    static final String TABLE_NAME = "Groups";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "NAME";

    // Order Create Statement
    static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL" +
            ")";

    private long nGroupID;
    private String sGroupName;
    private ArrayList<Product> mProducts;

    public Group(long id, String name, ArrayList<Product> products){
        nGroupID = id;
        sGroupName = name;
        mProducts = products;

    }

    public Group(long id, String name){
        nGroupID = id;
        sGroupName = name;
    }

    public Group(String name){
        nGroupID = -1;
        sGroupName = name;
    }


    public long getnGroupID() {
        return nGroupID;
    }

    public void setnGroupID(long nGroupID) {
        this.nGroupID = nGroupID;
    }

    public String getsGroupName() {
        return sGroupName;
    }

    public void setsGroupName(String sGroupName) {
        this.sGroupName = sGroupName;
    }

    public ArrayList<Product> getmProducts() {
        return mProducts;
    }

    public void setmProducts(ArrayList<Product> mProducts) {
        this.mProducts = mProducts;
    }

}
