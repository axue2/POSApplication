package com.ass3.axue2.posapplication.activities;

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
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.fragments.DeliveryManagerFragment;
import com.ass3.axue2.posapplication.models.DatabaseHelper;
import com.ass3.axue2.posapplication.models.Delivery;
import com.ass3.axue2.posapplication.models.Driver;

import java.util.ArrayList;
import java.util.List;

public class DeliveryManagerActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private String sDriverName = Driver.NAME_ALL;

    private long nDriverID;
    private ArrayList<Driver> mDrivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.delivery_manager_title);

        // Get database handler
        mDBHelper = new DatabaseHelper(getApplicationContext());

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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Get All Drivers
        mDrivers = new ArrayList<>(mDBHelper.GetAllDrivers().values());
        String[] driverNames = new String[mDrivers.size() + 1];
        driverNames[0] = Driver.NAME_ALL;
        Log.d("Number of drivers", String.valueOf(mDrivers.size()));
        int count = 1;
        for (Driver driver : mDrivers){
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

                int tabPosition = mViewPager.getCurrentItem();
                int spinnerPosition = parent.getSelectedItemPosition();
                Log.d("Selected item position", String.valueOf(spinnerPosition));
                if(parent.getSelectedItemPosition() > 0) {
                    // Driver position should be position-1 due to all taking first position
                    nDriverID = mDrivers.get(spinnerPosition - 1).getnDriverID();
                    Log.d("position > 0", String.valueOf(nDriverID));
                } else{
                    nDriverID = 0;
                }
                Log.d("Driver ID", String.valueOf(nDriverID));
                sDriverName = parent.getSelectedItem().toString();
                Log.d("Driver Name", sDriverName);

                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                createFragment(Delivery.STATUS_UNALLOCATED);
                createFragment(Delivery.STATUS_ALLOCATED);
                createFragment(Delivery.STATUS_COMPLETE);
                mViewPager.setAdapter(mSectionsPagerAdapter);

                // Set current item to allocated
                mViewPager.setCurrentItem(tabPosition);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void createFragment(String status){
        Bundle bundle = new Bundle();
        bundle.putString(DeliveryManagerFragment.BUNDLE_DELIVERY_STATUS, status);
        bundle.putString(DeliveryManagerFragment.BUNDLE_DRIVER_NAME, sDriverName);
        bundle.putLong(DeliveryManagerFragment.BUNDLE_DRIVER_ID, nDriverID);
        Log.d("Driver Name", sDriverName);
        Log.d("Driver ID", String.valueOf(nDriverID));
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
}
