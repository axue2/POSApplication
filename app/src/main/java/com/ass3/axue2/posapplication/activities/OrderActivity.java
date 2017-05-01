package com.ass3.axue2.posapplication.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.fragments.OrderCurrentFragment;
import com.ass3.axue2.posapplication.fragments.OrderGroupFragment;
import com.ass3.axue2.posapplication.models.DatabaseHelper;
import com.ass3.axue2.posapplication.models.Group;
import com.ass3.axue2.posapplication.models.Order;
import com.ass3.axue2.posapplication.models.OrderItem;
import com.ass3.axue2.posapplication.models.Product;
import com.ass3.axue2.posapplication.models.Table;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    public static final String EXTRA_TABLENAME = "Table Name";
    public static final String EXTRA_TABLEID = "Table ID";
    public static final String EXTRA_TABLEGUESTS = "Table Guests";
    public static final String EXTRA_ORDERID = "Order ID";
    public static final String EXTRA_ORDERTYPE = "Order Type";

    private long nTableID;
    private long nOrderID;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private Button mConfirmButton;

    private ArrayList<OrderItem> mOrderItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Find out which table was clicked in previous activity
        Intent intent = getIntent();
        final String tableName = intent.getStringExtra(EXTRA_TABLENAME);
        nTableID = intent.getLongExtra(EXTRA_TABLEID, 0);
        final int tableGuests = intent.getIntExtra(EXTRA_TABLEGUESTS, 0);
        nOrderID = intent.getLongExtra(EXTRA_ORDERID, 0);
        final String orderType = intent.getStringExtra(EXTRA_ORDERTYPE);

        Log.d("EXTRA_TABLENAME VALUE", tableName);
        Log.d("EXTRA_TABLEID VALUE", String.valueOf(nTableID));

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        
        // Get all the Groups
        ArrayList<Group> groups = new ArrayList<>(db.GetAllGroups().values());
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(tableName);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        assert mViewPager != null;
        mSectionsPagerAdapter.addFragment(new OrderCurrentFragment(), "Current Order");

        // Create a new tab for each group
        for (Group group : groups) {
            OrderGroupFragment f = new OrderGroupFragment();

            // Put GroupID in fragment
            Bundle args = new Bundle();
            args.putLong(OrderGroupFragment.BUNDLE_ITEM_GROUPID, group.getnGroupID());
            f.setArguments(args);

            mSectionsPagerAdapter.addFragment(f, group.getsGroupName());
        }


        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);

        // Get Button
        mConfirmButton = (Button) findViewById(R.id.order_confirm_button);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnclickListener", "Click Successful");
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                // If status is not In-use create new order
                if(nOrderID <= 0){
                    Log.d("OrderID", "Less than 0");
                    DatabaseHelper db =  new DatabaseHelper(getApplicationContext());
                    // TODO: Calculate total invoice
                    Order order = new Order(nTableID, orderType, Order.STATUS_UNPAID, 0);
                    long newID = db.AddOrder(order);
                    Log.d("newID", String.valueOf(newID));
                    // Set Table with new Order
                    Table table = new Table(nTableID, tableName, tableGuests, newID, 0, Table.STATUS_INUSE);
                    db.UpdateTable(table);
                }
                else{

                }
                // Add Products to order
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                OrderActivity.this.finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order, menu);
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

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

    public void AddOrderItem(Product product){

        boolean found = false;

        // Convert product into an OrderItem
        OrderItem item = new OrderItem(nOrderID, nTableID, product.getnProductID(),
                product.getsProductName(), product.getnPrice(), 1);
        // Check if OrderItem is currently in the list
        for (OrderItem checkItem : mOrderItems) {
            // If found in OrderItems then increase quantity by one
            if(checkItem.getnProductID() == item.getnProductID()){
                checkItem.setnQuantity(checkItem.getnQuantity() + 1);
                Log.d("Current Quantity", String.valueOf(checkItem.getnQuantity()));
                updateCurrentFragment();
                found = true;
                break;
            }
        }

        if (!found) {
            // if OrderItem could not be found add one into OrderItems
            mOrderItems.add(item);
            updateCurrentFragment();
            Log.d("Current Number of items", String.valueOf(mOrderItems.size()));
        }
    }

    public ArrayList<OrderItem> getmOrderItems() {
        return mOrderItems;
    }

    public void updateCurrentFragment(){
        // OrderCurrentFragment MUST BE FIRST FRAGMENT
        Fragment fragment = mSectionsPagerAdapter.getItem(0);
        if (fragment instanceof OrderCurrentFragment){
            ((OrderCurrentFragment) fragment).updateRecyclerView();
        }
    }
}
