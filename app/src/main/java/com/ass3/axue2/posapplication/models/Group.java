package com.ass3.axue2.posapplication.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthony on 4/29/2017.
 */

public class Group {



    private int nGroupID;
    private String sGroupName;
    private ArrayList<Product> mProducts;

    public Group(int id, String name, ArrayList<Product> products){
        nGroupID = id;
        sGroupName = name;
        mProducts = products;

    }

    public ArrayList<Product> testProducts(ArrayList<Product> products){
        Product product = new Product(1, "Salt and Pepper Calamari", 14, 0);
        products.add(product);
        product = new Product(2, "Margherita Pizza", 16, 0);
        products.add(product);
        product = new Product(3, "Prosciutto Pizza", 21, 0);
        products.add(product);
        product = new Product(4, "Lamb Pizza", 19, 0);
        products.add(product);
        product = new Product(5, "Nutella Calzone", 11, 0);
        products.add(product);
        product = new Product(6, "Napoletana Pizza", 16, 0);
        products.add(product);
        product = new Product(7, "Vegan Pizza", 16, 0);
        products.add(product);
        product = new Product(8, "Capricciosa Pizza", 17, 0);
        products.add(product);
        product = new Product(9, "Chorizo Pizza", 18, 0);
        products.add(product);

        return products;
    }

    public int getnGroupID() {
        return nGroupID;
    }

    public void setnGroupID(int nGroupID) {
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
