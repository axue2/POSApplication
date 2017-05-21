package com.ass3.axue2.posapplication.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.fragments.MainOrdersFragment;
import com.ass3.axue2.posapplication.fragments.MainTableFragment;
import com.ass3.axue2.posapplication.models.DatabaseHelper;
import com.ass3.axue2.posapplication.models.Delivery;
import com.ass3.axue2.posapplication.models.Order;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.main_title));

        this.deleteDatabase(mDBHelper.DATABASE_NAME);

        // Get database handler
        mDBHelper = new DatabaseHelper(getApplicationContext());

        // If tables are empty add default values
        if(mDBHelper.GetAllTables().size() == 0)
            mDBHelper.CreateDefaultTables();

        if(mDBHelper.GetAllGroups().size() == 0)
            mDBHelper.CreateDefaultGroups();

        if(mDBHelper.GetProducts(1).size() == 0)
            mDBHelper.CreateDefaultProducts();

        if(mDBHelper.GetAllDrivers().size() == 0)
            mDBHelper.CreateDefaultDrivers();

        if(mDBHelper.GetDeliveriesByStatus(Delivery.STATUS_COMPLETE).size() == 0)
            mDBHelper.CreateTestDeliveries();

        if(mDBHelper.GetAllCustomers().size() == 0)
            mDBHelper.CreateTestCustomers();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);

        assert mViewPager != null;
        mSectionsPagerAdapter.addFragment(new MainOrdersFragment(), "Orders");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //TODO: Add animations to floating action buttons
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fabTakeaway = (FloatingActionButton) findViewById(R.id.fab_takeaway);
        FloatingActionButton fabDelivery = (FloatingActionButton) findViewById(R.id.fab_delivery);
        final LinearLayout takeawayLayout = (LinearLayout) findViewById(R.id.main_takeaway_layout);
        final LinearLayout deliveryLayout = (LinearLayout) findViewById(R.id.main_delivery_layout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(takeawayLayout.getVisibility() == View.VISIBLE &&
                        deliveryLayout.getVisibility() == View.VISIBLE){
                    takeawayLayout.setVisibility(View.GONE);
                    deliveryLayout.setVisibility(View.GONE);
                } else{
                    takeawayLayout.setVisibility(View.VISIBLE);
                    deliveryLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        fabTakeaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), OrderActivity.class);
                intent.putExtra(OrderActivity.EXTRA_ORDERTYPE, Order.TYPE_TAKEAWAY);
                intent.putExtra(OrderActivity.EXTRA_TABLENAME, Order.TYPE_TAKEAWAY);
                intent.putExtra(OrderActivity.EXTRA_TABLEGUESTS, 0);

                intent.putExtra(OrderActivity.EXTRA_TABLEID, -1);
                intent.putExtra(OrderActivity.EXTRA_ORDERID, -1);

                startActivity(intent);

            }
        });

        fabDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), DeliveryDetailsActivity.class);

                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delivery_manager) {
            Intent intent = new Intent(this, DeliveryManagerActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private  List<Fragment> mFragments = new ArrayList<>();
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
}
