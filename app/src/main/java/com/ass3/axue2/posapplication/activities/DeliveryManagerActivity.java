package com.ass3.axue2.posapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Customer;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Delivery;
import com.ass3.axue2.posapplication.models.operational.Driver;
import com.ass3.axue2.posapplication.models.operational.Restaurant;
import com.ass3.axue2.posapplication.network.CustomerDAO;
import com.ass3.axue2.posapplication.network.DeliveryDAO;
import com.ass3.axue2.posapplication.network.DriverDAO;
import com.ass3.axue2.posapplication.views.adapters.DeliveryManagerRecyclerViewAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryManagerActivity extends AppCompatActivity {

    public static final String BUTTON_UNALLOCATE = "Allocate Driver";
    public static final String BUTTON_ALLOCATE = "Complete Delivery";
    public static final String BUTTON_COMPLETE = "FALSE";

    private DatabaseHelper mDBHelper;
    private ConfigurationDatabaseHelper mCDBHelper;

    private long nDriverID;
    private String sStatus;

    private ArrayList<Driver> mDrivers;

    private ArrayList<Delivery> mDeliveries = new ArrayList<>();
    private ArrayList<Delivery> mSelectedDeliveries = new ArrayList<>();

    private RecyclerView mRV;
    private DeliveryManagerRecyclerViewAdapter mAdapter;

    private Button mConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_manager);

        // Get database handler
        mDBHelper = new DatabaseHelper(getApplicationContext());
        mCDBHelper = new ConfigurationDatabaseHelper(getApplicationContext());
        ConfigurationDatabaseHelper mCDBHelper = new ConfigurationDatabaseHelper(getApplicationContext());

        // Get All Drivers
        if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 1){
            new SynchroniseTask(DeliveryManagerActivity.this).execute();
        } else if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 0){
            initialSetup();
        }
    }

    private void initialSetup(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.delivery_manager_title);

        // Get Drivers
        mDrivers = new ArrayList<>(mDBHelper.GetAllDrivers().values());
        String[] driverNames = new String[mDrivers.size() + 1];

        // Add in 'all' drivers
        driverNames[0] = Driver.NAME_ALL;
        int count = 1;
        // Add all drivers
        for (Driver driver : mDrivers) {
            driverNames[count] = driver.getnFirstName() + " " + driver.getnLastName();
            count++;
        }

        // Set initial status as unallocated
        sStatus = Delivery.STATUS_UNALLOCATED;

        // Setup RecyclerView
        mRV = (RecyclerView) findViewById(R.id.delivery_manager_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRV.setLayoutManager(llm);

        // Setup Divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                llm.getOrientation());
        mRV.addItemDecoration(itemDecoration);

        // Setup Confirm Button
        mConfirmButton = (Button) findViewById(R.id.delivery_manager_confirm_button);

        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(toolbar.getContext(), driverNames));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int spinnerPosition = parent.getSelectedItemPosition();
                if(parent.getSelectedItemPosition() > 0) {
                    // Driver position should be position-1 due to all taking first position
                    nDriverID = mDrivers.get(spinnerPosition - 1).getnDriverID();
                } else{
                    nDriverID = 0;
                }
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Setup TabLayouts
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText(Delivery.STATUS_UNALLOCATED));
        mTabLayout.addTab(mTabLayout.newTab().setText(Delivery.STATUS_ALLOCATED));
        mTabLayout.addTab(mTabLayout.newTab().setText(Delivery.STATUS_COMPLETE));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Set status and 'refresh' RecyclerView
                sStatus = (String) tab.getText();
                refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Set status and 'refresh' RecyclerView
                sStatus = (String) tab.getText();
                refresh();
            }
        });

        // Setup Location Button
        ImageButton imageButton = (ImageButton) findViewById(R.id.location_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Restaurant Setting in order to get state
                Restaurant restaurant = mDBHelper.GetRestaurant(1);

                String mapUrl = "https://maps.google.com/maps?";
                // If Restaurant Address exists then use it as a starting point
                // Otherwise Google Maps will just use the GPS location
                if (!restaurant.getsAddressLine1().equals("")){
                    mapUrl += "saddr=";
                    mapUrl += restaurant.getsAddressLine1();
                    mapUrl += ", ";
                    mapUrl += restaurant.getsAddressLine2();
                    mapUrl += " ";
                    mapUrl += restaurant.getsState();
                    mapUrl += " ";
                    mapUrl += restaurant.getnPostCode();
                }
                System.out.println(String.valueOf("Selected Deliveries: " + mSelectedDeliveries.size()));
                // If deliveries have been clicked
                if (mSelectedDeliveries.size() > 0){
                    String url = "";
                    int size = mSelectedDeliveries.size();
                    // Cycle through all deliveries and get their addresses
                    for (int i = 0; i < size; i++){
                        if (i == 0){
                            url += "&daddr=";
                        } else{
                            url += "+to:";
                        }
                        // get Customer address
                        Customer customer = mDBHelper.GetCustomer(
                                mSelectedDeliveries.get(i).getnCustomerID());
                        // Add Customer address as a destination point
                        url += customer.getsAddressLine1();
                        url += ", ";
                        url += customer.getsAddressLine2();
                        url += " ";
                        url+= restaurant.getsState();
                        url += " ";
                        url += customer.getnPostCode();
                    }
                    mapUrl += url;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                    startActivity(intent);
                }
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delivery status the delivery is to be updated to
                String updatedStatus = "";

                // Checks if valid driver
                if (nDriverID > 0 && mSelectedDeliveries.size() > 0){
                    // Checks which tab
                    if (sStatus.equals(Delivery.STATUS_UNALLOCATED)) {
                        updatedStatus = Delivery.STATUS_ALLOCATED;
                    } else if (sStatus.equals(Delivery.STATUS_ALLOCATED)) {
                        updatedStatus = Delivery.STATUS_COMPLETE;
                    }
                    // Checks to see if updatedStatus has text
                    if(updatedStatus.equals(Delivery.STATUS_ALLOCATED) || updatedStatus.equals(Delivery.STATUS_COMPLETE)) {
                        // If not network mode
                        if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 0) {
                            for (int i = 0; i < mSelectedDeliveries.size(); i++) {
                                Delivery delivery = mSelectedDeliveries.get(i);
                                // checks delivery to see if it hasn't already be allocated
                                if (delivery.getsStatus().equals(sStatus)) {
                                    delivery.setsStatus(updatedStatus);
                                    delivery.setnDriverID(nDriverID);

                                    mDBHelper.UpdateDelivery(delivery);
                                    mAdapter.removeItem(delivery);
                                }
                            }
                            mSelectedDeliveries.clear();
                        }
                        // Otherwise if network mode
                        else if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 1){
                            new UpdateTask(updatedStatus, DeliveryManagerActivity.this).execute();
                        }

                    }

                }
            }
        });
    }

    private class UpdateTask extends AsyncTask<Object, Object, ArrayList<Delivery>> {
        private DatabaseHelper dbHelper;
        private String mStatus;

        UpdateTask(String status, DeliveryManagerActivity activity){
            dbHelper = new DatabaseHelper(activity);
            mStatus = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Delivery> doInBackground(Object... params) {
            DeliveryDAO deliveryDAO = new DeliveryDAO();
            ArrayList<Delivery> deliveries = new ArrayList<>();
            try {
                for (Delivery del : mDeliveries) {
                    // Get delivery from server db
                    Delivery delivery = deliveryDAO.getDelivery(del.getnDeliveryID());
                    // check to see if delivery hasn't already be allocated
                    if (delivery.getsStatus().equals(sStatus)) {
                        // update delivery
                        delivery.setsStatus(mStatus);
                        delivery.setnDriverID(nDriverID);
                        deliveryDAO.updateDelivery(delivery);
                        deliveries.add(delivery);
                    }
                }
                // synchronise with server db
                synchronise(dbHelper);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return deliveries;
        }

        @Override
        protected void onPostExecute(ArrayList<Delivery> deliveries) {
            super.onPostExecute(deliveries);
            for (Delivery delivery : deliveries){
                mDeliveries.remove(delivery);
/*                mAdapter.removeItem(delivery);*/
                refresh();
            }

        }
    }

    private class SynchroniseTask extends AsyncTask<Void, Void, Void> {
        private DatabaseHelper dbHelper;


        SynchroniseTask(DeliveryManagerActivity activity){
            dbHelper = new DatabaseHelper(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            synchronise(dbHelper);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initialSetup();
        }
    }

    private void synchronise(DatabaseHelper dbHelper){
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        DriverDAO driverDAO = new DriverDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        List<Delivery> deliveries;
        List<Driver> drivers;
        List<Customer> customers;
        try{
            deliveries = deliveryDAO.getDeliveries();
            drivers = driverDAO.getDrivers();
            customers = customerDAO.getCustomers();
            // Sync Deliveries
            dbHelper.dropTable(Delivery.TABLE_NAME);
            dbHelper.createTable(Delivery.CREATE_STATEMENT);
            for (Delivery delivery : deliveries){
                dbHelper.AddDelivery(delivery);
            }
            // Sync Drivers
            dbHelper.dropTable(Driver.TABLE_NAME);
            dbHelper.createTable(Driver.CREATE_STATEMENT);
            for (Driver driver : drivers){
                dbHelper.AddDriver(driver);
            }
            // Sync Customers
            dbHelper.dropTable(Customer.TABLE_NAME);
            dbHelper.createTable(Customer.CREATE_STATEMENT);
            for (Customer customer : customers){
                dbHelper.AddCustomer(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void refresh(){
        // Clear all selected deliveries
        mSelectedDeliveries.clear();
        // Checks delivery status and sets buttons accordingly
        switch (sStatus){
            case Delivery.STATUS_UNALLOCATED:
                mConfirmButton.setText(BUTTON_UNALLOCATE);
                mConfirmButton.setVisibility(View.VISIBLE);
                break;
            case Delivery.STATUS_ALLOCATED:
                mConfirmButton.setText(BUTTON_ALLOCATE);
                mConfirmButton.setVisibility(View.VISIBLE);
                break;
            default:
                mConfirmButton.setText(BUTTON_COMPLETE);
                mConfirmButton.setVisibility(View.GONE);
        }
        // If status unallocated or 'all' drivers
        if (sStatus.equals(Delivery.STATUS_UNALLOCATED) || nDriverID <= 0){
            mDeliveries = new ArrayList<>(mDBHelper.GetDeliveriesByStatus(sStatus).values());
        }
        // Otherwise driverID > 0 and not unallocated status
        else{
            mDeliveries = new ArrayList<>(mDBHelper.GetDeliveriesByDriverAndStatus(sStatus, nDriverID).values());
        }
        mAdapter = new DeliveryManagerRecyclerViewAdapter(this, mDeliveries);
        mRV.setAdapter(mAdapter);
    }

    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }

    public ArrayList<Delivery> getmSelectedDeliveries() {
        return mSelectedDeliveries;
    }

    public void addmSelectedDelivery(Delivery delivery) {
        this.mSelectedDeliveries.add(delivery);
    }

    public void removemSelectedDelivery(Delivery delivery) {
        this.mSelectedDeliveries.remove(delivery);
    }

}
