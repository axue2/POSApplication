package com.ass3.axue2.posapplication.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.activities.DeliveryLocationActivity;
import com.ass3.axue2.posapplication.activities.DeliveryManagerActivity;
import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Delivery;
import com.ass3.axue2.posapplication.network.DeliveryDAO;
import com.ass3.axue2.posapplication.views.adapters.DeliveryManagerRecyclerViewAdapter;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryManagerFragment extends android.support.v4.app.Fragment {

    DatabaseHelper mDBHelper;

    public static final String BUTTON_UNALLOCATE = "Allocate Driver";
    public static final String BUTTON_ALLOCATE = "Complete Delivery";
    public static final String BUTTON_COMPLETE = "FALSE";

    public final static String BUNDLE_DELIVERY_STATUS = "Delivery Status";

    public String sDeliveryStatus;
    public long nDriverID;
    private ArrayList<Delivery> mDeliveries = new ArrayList<>();
    private ArrayList<Long> mSelectedDeliveries = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView rv;
    DeliveryManagerRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get database handler
        mDBHelper = new DatabaseHelper(getActivity());

        // get arguments from activity
        sDeliveryStatus = getArguments().getString(BUNDLE_DELIVERY_STATUS);

        // Get current driver selected
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

        final View v = inflater.inflate(R.layout.fragment_delivery_manager, container, false);

        //setup button
        Button mButton = (Button) v.findViewById(R.id.fragment_delivery_manager_button);
        switch (sDeliveryStatus){
            case Delivery.STATUS_UNALLOCATED:
                mButton.setText(BUTTON_UNALLOCATE);
                mButton.setVisibility(View.VISIBLE);
                break;
            case Delivery.STATUS_ALLOCATED:
                mButton.setText(BUTTON_ALLOCATE);
                mButton.setVisibility(View.VISIBLE);
                break;
            default:
                mButton.setText(BUTTON_COMPLETE);
                mButton.setVisibility(View.GONE);
        }

        // Setup RecyclerView
        rv = (RecyclerView) v.findViewById(R.id.fragment_delivery_manager_rv);
        rv.setHasFixedSize(true);

        // Setup Adapter
        adapter = new DeliveryManagerRecyclerViewAdapter(getActivity(), mDeliveries, mSelectedDeliveries);
        rv.setAdapter(adapter);

        // Setup Layout Manager
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLinearLayoutManager);

        rv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    View v = rv.findChildViewUnder(e.getX(), e.getY());
                    if (v != null && rv.getChildAdapterPosition(v) >= 0) {
                        toggleSelect(rv.getChildAdapterPosition(v));
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button Clicked", "onClick: ");
                updateDeliveries();
            }
        });

        ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.location_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeliveryLocationActivity.class);

                startActivity(intent);
            }
        });


        return v;
    }

    public void updateDeliveries(){
        Log.d("Number of deliveries", String.valueOf(mSelectedDeliveries.size()));
        Log.d("DriverID", String.valueOf(nDriverID));
        String updatedStatus = "";

        // Checks if valid driver
        if (nDriverID > 0 && mSelectedDeliveries.size() > 0){
            // Checks which tab
            if (sDeliveryStatus.equals(Delivery.STATUS_UNALLOCATED)) {
                updatedStatus = Delivery.STATUS_ALLOCATED;
            } else if (sDeliveryStatus.equals(Delivery.STATUS_ALLOCATED)) {
                updatedStatus = Delivery.STATUS_COMPLETE;
            }

            if(updatedStatus.equals(Delivery.STATUS_ALLOCATED) || updatedStatus.equals(Delivery.STATUS_COMPLETE)) {
                ConfigurationDatabaseHelper mCDBHelper = new ConfigurationDatabaseHelper(getActivity());
                if (mCDBHelper.GetConfigurationSetting(1).getnNetworkMode() == 0) {
                    for (int i = 0; i < mSelectedDeliveries.size(); i++) {
                        Delivery delivery = mDBHelper.GetDelivery(mSelectedDeliveries.get(i));
                        // checks delivery to see if it hasn't already be allocated
                        if (delivery.getsStatus().equals(sDeliveryStatus)) {
                            delivery.setsStatus(updatedStatus);
                            delivery.setnDriverID(nDriverID);

                            mDBHelper.UpdateDelivery(delivery);
                        }
                    }
                    mSelectedDeliveries.clear();
                    update();
                } else if (mCDBHelper.GetConfigurationSetting(1).getnNetworkMode() == 1){
                    new InsertTask((DeliveryManagerActivity)getActivity(), updatedStatus, mSelectedDeliveries).execute();

                }

            }

        }
    }


    private class InsertTask extends AsyncTask<Void, Void, Void>{
        private  ProgressDialog mDialog;
        private DatabaseHelper dbHelper;
        private String mStatus;
        private ArrayList<Long> deliveryIDs;

        public InsertTask(DeliveryManagerActivity activity, String status, ArrayList<Long> selectedDeliveries){
            mDialog = new ProgressDialog(activity);
            mStatus = status;
            deliveryIDs = selectedDeliveries;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setTitle("Sending Data");
            mDialog.setMessage("Sending information to server. Please Wait...");
            mDialog.setIndeterminate(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            System.out.println("DOINBACKGROUND.............................");
            DeliveryDAO deliveryDAO = new DeliveryDAO();
            System.out.println("DOINBACKGROUND.............................");

            try {
                for (int i = 0; i < deliveryIDs.size(); i++) {
                    System.out.println("DELIVERY ID = " + String.valueOf(deliveryIDs.get(i)));
                    Delivery delivery = deliveryDAO.getDelivery(deliveryIDs.get(i));
                    if (delivery.getsStatus().equals(sDeliveryStatus)) {
                        // checks delivery to see if it hasn't already be allocated

                        delivery.setsStatus(mStatus);
                        delivery.setnDriverID(nDriverID);
                        System.out.println(mStatus);



                        deliveryDAO.updateDelivery(delivery);
                        for (Delivery chk : mDeliveries){
                            if (chk.getnDeliveryID() == delivery.getnDeliveryID()){
                                mDeliveries.remove(chk);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            deliveryIDs.clear();
            update();
            mDialog.dismiss();

        }
    }

    public void toggleSelect(int position){

        if(mSelectedDeliveries.contains(mDeliveries.get(position).getnDeliveryID())){
            mSelectedDeliveries.remove(mDeliveries.get(position).getnDeliveryID());
            Log.d("", "removed");
        } else{
            mSelectedDeliveries.add(mDeliveries.get(position).getnDeliveryID());
            Log.d("", "added");
        }

        Log.d("selected delivery size", String.valueOf(mSelectedDeliveries.size()));

        update();
    }

    public void update(){
/*        adapter.mSelectedDeliveries = mSelectedDeliveries;
        adapter.mDeliveries = mDeliveries;
        adapter.notifyDataSetChanged();*/

        adapter.updateDataset(mDeliveries,mSelectedDeliveries);

    }
}
