package com.ass3.axue2.posapplication.models;

import java.util.ArrayList;

/**
 * Created by anthony on 4/21/2017.
 */

public class Table {

    private int nTableID;
    private String sTableName;
    private int nGuests;
    private int nInvSum;
    private String sStatus;
    private ArrayList<Product> mProducts;


    public Table(int id, String name, int guests,
                 int total, String status) {
        nTableID = id;
        sTableName = name;
        nGuests = guests;
        nInvSum = total;
        sStatus = status;
    }

    public Table(){
        nTableID = -1;
        sTableName = "tmp";
        nGuests = 1;
        nInvSum = 1;
        sStatus = "Open";
    }
    public int getnTableID() {return nTableID;}

    public void setnTableID(int nTableID) {this.nTableID = nTableID;}

    public String getsTableName() {return sTableName;}

    public void setsTableName(String sTableName) {this.sTableName = sTableName;}

    public int getnGuests() {return nGuests;}

    public void setnGuests(int nGuests) {this.nGuests = nGuests;}

    public int getnInvSum() {return nInvSum;}

    public void setnInvSum(int nInvSum) {this.nInvSum = nInvSum;}

    public String getsStatus() {return sStatus;}

    public void setsStatus(String sStatus) {this.sStatus = sStatus;}
}
