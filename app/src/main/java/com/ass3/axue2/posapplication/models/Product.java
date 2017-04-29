package com.ass3.axue2.posapplication.models;

/**
 * Created by anthony on 4/29/2017.
 */

public class Product {

    private int nProductID;
    private String sProductName;
    private int nPrice;
    private int nQuantity;

    public Product(int id, String name, int price, int quantity){
        nProductID = id;
        sProductName = name;
        nPrice = price;
        nQuantity = quantity;
    }

    public Product(int id, String name, int price){
        nProductID = id;
        sProductName = name;
        nPrice = price;
        nQuantity = 1;
    }

    public int getnProductID() {
        return nProductID;
    }

    public void setnProductID(int nProductID) {
        this.nProductID = nProductID;
    }

    public String getsProductName() {
        return sProductName;
    }

    public void setsProductName(String sProductName) {
        this.sProductName = sProductName;
    }

    public int getnPrice() {
        return nPrice;
    }

    public void setnPrice(int nPrice) {
        this.nPrice = nPrice;
    }

    public int getnQuantity() {
        return nQuantity;
    }

    public void setnQuantity(int nQuantity) {
        this.nQuantity = nQuantity;
    }
}
