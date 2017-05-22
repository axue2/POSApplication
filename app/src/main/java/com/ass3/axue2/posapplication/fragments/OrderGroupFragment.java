package com.ass3.axue2.posapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Product;
import com.ass3.axue2.posapplication.views.adapters.OrderGroupRecyclerViewAdapter;

import java.util.ArrayList;

public class OrderGroupFragment extends android.support.v4.app.Fragment {

    public static final String BUNDLE_ITEM_GROUPID = "Group ID";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get groupID
        Bundle args = this.getArguments();
        long groupID = 0;
        if (args != null){
            groupID = args.getLong(BUNDLE_ITEM_GROUPID, 0);
        }

        // Setup Database
        DatabaseHelper db = new DatabaseHelper(getContext());

        // Get all products
        ArrayList<Product> productList = new ArrayList<>();
        if (groupID > 0){
            productList = new ArrayList<>(db.GetProducts(groupID).values());
        }

        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_recyclerview, container, false);
        rv.setHasFixedSize(true);

        // Setup Adapter
        OrderGroupRecyclerViewAdapter adapter = new OrderGroupRecyclerViewAdapter(getActivity(), productList);
        rv.setAdapter(adapter);

        // Setup Layout Manager
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return rv;
    }


}
