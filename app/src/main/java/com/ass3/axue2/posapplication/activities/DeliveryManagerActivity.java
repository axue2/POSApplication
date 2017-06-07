package com.ass3.axue2.posapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.fragments.DeliveryManagerFragment;
import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Customer;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Delivery;
import com.ass3.axue2.posapplication.models.operational.Driver;
import com.ass3.axue2.posapplication.network.CustomerDAO;
import com.ass3.axue2.posapplication.network.DeliveryDAO;
import com.ass3.axue2.posapplication.network.DriverDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// TODO: FIX MAJOR DELIVERY MANAGER BUG
public class DeliveryManagerActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private String sDriverName = Driver.NAME_ALL;

    private long nDriverID;
    private ArrayList<Driver> mDrivers;

    private ArrayList<Long> mSelectedDeliveries = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_manager);

        // Get database handler
        mDBHelper = new DatabaseHelper(getApplicationContext());
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

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        assert mViewPager != null;
        createFragment(Delivery.STATUS_UNALLOCATED);
        createFragment(Delivery.STATUS_ALLOCATED);
        createFragment(Delivery.STATUS_COMPLETE);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Setup Tab layouts
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mDrivers = new ArrayList<>(mDBHelper.GetAllDrivers().values());
        String[] driverNames = new String[mDrivers.size() + 1];

        // Add in all drivers
        driverNames[0] = Driver.NAME_ALL;
        int count = 1;
        for (Driver driver : mDrivers) {
            driverNames[count] = driver.getnFirstName() + " " + driver.getnLastName();
            count++;
        }

        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(toolbar.getContext(), driverNames));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.

                int spinnerPosition = parent.getSelectedItemPosition();
                if(parent.getSelectedItemPosition() > 0) {
                    // Driver position should be position-1 due to all taking first position
                    nDriverID = mDrivers.get(spinnerPosition - 1).getnDriverID();
                } else{
                    nDriverID = 0;
                }
                sDriverName = parent.getSelectedItem().toString();
                createTabs();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.location_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // https://developers.google.com/maps/documentation/urls/android-intents
                //Uri gmmIntentUri = Uri.parse("google.navigation:q=34 Clyde St, Box Hill North VIC 3129(Google+Sydney)");
                //Intent intent = new Intent(v.getContext(), DeliveryLocationActivity.class);
                //Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                //intent.setPackage("com.google.android.apps.maps");

                String mapUrl = "https://maps.google.com/maps?";
                mapUrl += "saddr=34 Clyde St, Box Hill North VIC 3129";
                System.out.println(String.valueOf("Selected Deliveries: " + mSelectedDeliveries.size()));
                if (mSelectedDeliveries.size() > 0){
                    String url = "";
                    int size = mSelectedDeliveries.size();
                    for (int i = 0; i < size; i++){
                        if (i == 0){
                            url += "&daddr=";
                        } else{
                            url += "+to:";
                        }
                        Customer customer = mDBHelper.GetCustomer(mDBHelper.GetDelivery(mSelectedDeliveries.get(i)).getnCustomerID());
                        url += customer.getsAddressLine1();
                        url += ", ";
                        url += customer.getsAddressLine2();
                        url += " ";
                        url+= "VIC";
                        url += " ";
                        url += customer.getnPostCode();
                        System.out.println(url);
                    }
                    System.out.println(url);
                    mapUrl += url;
                    //mapUrl += "&daddr=34 Shannon St, Box Hill North VIC 3129";
                    System.out.println(mapUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                    startActivity(intent);

                }
            }
        });
    }

    private class SynchroniseTask extends AsyncTask<Void, Void, Void> {
/*        private ProgressDialog mDialog;*/
        private DatabaseHelper dbHelper;


        public SynchroniseTask(DeliveryManagerActivity activity){
/*            mDialog = new ProgressDialog(activity);*/
            dbHelper = new DatabaseHelper(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
/*            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setTitle("Synchronising Data");
            mDialog.setMessage("Getting database information from server. Please Wait...");
            mDialog.setIndeterminate(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... params) {
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

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initialSetup();
/*            mDialog.dismiss();*/
        }
    }

    private void createTabs(){
        //mSectionsPagerAdapter.destroyFragments();

        int tabPosition = mViewPager.getCurrentItem();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        createFragment(Delivery.STATUS_UNALLOCATED);
        createFragment(Delivery.STATUS_ALLOCATED);
        createFragment(Delivery.STATUS_COMPLETE);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Set current item to allocated
        mViewPager.setCurrentItem(tabPosition);
    }

    private void createFragment(String status){
        Bundle bundle = new Bundle();
        bundle.putString(DeliveryManagerFragment.BUNDLE_DELIVERY_STATUS, status);
        DeliveryManagerFragment fragment = new DeliveryManagerFragment();
        fragment.setArguments(bundle);
        mSectionsPagerAdapter.addFragment(fragment, status);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delivery_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments = new ArrayList<>();
        private  List<String> mFragmentTitles = new ArrayList<>();

        SectionsPagerAdapter(FragmentManager fm) {super(fm);}

        void addFragment(Fragment fragment, String title){
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {return mFragments.get(position);}

        @Override
        public int getCount() {return mFragments.size();}

        @Override
        public CharSequence getPageTitle(int position) {return mFragmentTitles.get(position);}
    }

    public String getsDriverName() {
        return sDriverName;
    }

    public long getnDriverID() {
        return nDriverID;
    }

    public ArrayList<Long> getmSelectedDeliveries() {
        return mSelectedDeliveries;
    }

    public void setmSelectedDeliveries(ArrayList<Long> mSelectedDeliveries) {
        this.mSelectedDeliveries = mSelectedDeliveries;
    }

}
