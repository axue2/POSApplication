package com.ass3.axue2.posapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.Product;
import com.ass3.axue2.posapplication.views.adapters.OrderCurrentRecyclerViewAdapter;

import java.util.ArrayList;

public class OrderCurrentFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_recyclerview, container, false);
        rv.setHasFixedSize(true);

        // temporary Products
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Margherita Pizza", 16));
        productList.add(new Product(2, "Prosciutto Pizza", 21));
        productList.add(new Product(3, "Lamb Shoulder Pizza", 23));
        productList.add(new Product(4, "Napoletana Pizza", 17));
        productList.add(new Product(5, "Wild Mushroom Pizza", 18));
        productList.add(new Product(6, "Formaggio Quattro Pizza", 19));
        productList.add(new Product(7, "Super Veg Pizza", 18));
        productList.add(new Product(8, "Capricciosa Pizza", 18));
        productList.add(new Product(9, "Soppressa Pizza", 18));
        productList.add(new Product(10, "Cacciatore Pizza", 18));
        productList.add(new Product(11, "Chorizo Pizza", 18));
        productList.add(new Product(12, "Pancetta Pizza", 18));
        productList.add(new Product(12, "Pizza Royale 007", 4200));


        // Setup Adapter
        OrderCurrentRecyclerViewAdapter adapter = new OrderCurrentRecyclerViewAdapter(getActivity(), productList);
        rv.setAdapter(adapter);

        // Setup Layout Manager
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return rv;
    }

    
}
