package com.ass3.axue2.posapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.activities.DeliveryManagerActivity;
import com.ass3.axue2.posapplication.models.DatabaseHelper;
import com.ass3.axue2.posapplication.models.Delivery;
import com.ass3.axue2.posapplication.views.adapters.DeliveryManagerRecyclerViewAdapter;

import java.util.ArrayList;

public class DeliveryManagerFragment extends android.support.v4.app.Fragment {

    DatabaseHelper mDBHelper;

    public final static String BUNDLE_DELIVERY_STATUS = "Delivery Status";
    public final static String BUNDLE_DRIVER_NAME = "Driver Name";
    public final static String BUNDLE_DRIVER_ID = "Driver ID";

    private String sDeliveryStatus;
    private long nDriverID;
    private ArrayList<Delivery> mDeliveries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get database handler
        mDBHelper = new DatabaseHelper(getActivity());

        // get arguments from activity
        sDeliveryStatus = getArguments().getString(BUNDLE_DELIVERY_STATUS);
        //nDriverID = getArguments().getLong(BUNDLE_DRIVER_ID);

        if(getContext() instanceof DeliveryManagerActivity){
            nDriverID = ((DeliveryManagerActivity) getContext()).getnDriverID();
        }

        Log.d("Delivery Status", sDeliveryStatus);
        Log.d("Driver ID", String.valueOf(nDriverID));

        if(nDriverID > 0 && !sDeliveryStatus.equals(Delivery.STATUS_UNALLOCATED)) {
            // Get Deliveries
            mDeliveries = new ArrayList<>(mDBHelper.GetDeliveriesByDriverAndStatus(sDeliveryStatus, nDriverID).values());
        } else{
            mDeliveries = new ArrayList<>(mDBHelper.GetDeliveriesByStatus(sDeliveryStatus).values());
        }
        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_recyclerview, container, false);
        rv.setHasFixedSize(true);

        // Setup Adapter
        DeliveryManagerRecyclerViewAdapter adapter = new DeliveryManagerRecyclerViewAdapter(getActivity(), mDeliveries);
        rv.setAdapter(adapter);

        // Setup Layout Manager
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return rv;
    }

    
}
