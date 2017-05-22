package com.ass3.axue2.posapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.activities.OrderActivity;
import com.ass3.axue2.posapplication.models.operational.OrderItem;
import com.ass3.axue2.posapplication.views.adapters.OrderCurrentRecyclerViewAdapter;

import java.util.ArrayList;

public class OrderCurrentFragment extends android.support.v4.app.Fragment {

    OrderCurrentRecyclerViewAdapter mAdaper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_recyclerview, container, false);
        rv.setHasFixedSize(true);

        // Get OrderItems
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        Context context= getContext();
        if (context instanceof OrderActivity){
            ((OrderActivity) context).getmOrderItems();
        }

        // Setup Adapter
        mAdaper = new OrderCurrentRecyclerViewAdapter(getActivity(), orderItems);
        rv.setAdapter(mAdaper);
        // Setup Layout Manager
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return rv;
    }

    public void updateRecyclerView(){
        mAdaper.update();
    }

    
}
