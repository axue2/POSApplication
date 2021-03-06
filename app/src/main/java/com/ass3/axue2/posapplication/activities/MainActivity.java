package com.ass3.axue2.posapplication.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.activities.Settings.SettingsActivity;
import com.ass3.axue2.posapplication.fragments.MainOrdersFragment;
import com.ass3.axue2.posapplication.fragments.MainTableFragment;
import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Delivery;
import com.ass3.axue2.posapplication.models.operational.Group;
import com.ass3.axue2.posapplication.models.operational.Order;
import com.ass3.axue2.posapplication.models.operational.OrderItem;
import com.ass3.axue2.posapplication.models.operational.Product;
import com.ass3.axue2.posapplication.models.operational.Table;
import com.ass3.axue2.posapplication.models.saxpos.Poqapa;
import com.ass3.axue2.posapplication.models.saxpos.Poqapd;
import com.ass3.axue2.posapplication.models.saxpos.SaxposConverter;
import com.ass3.axue2.posapplication.models.saxpos.Stkcat;
import com.ass3.axue2.posapplication.models.saxpos.Stkite;
import com.ass3.axue2.posapplication.network.PoqapaDAO;
import com.ass3.axue2.posapplication.network.PoqapdDAO;
import com.ass3.axue2.posapplication.network.StkcatDAO;
import com.ass3.axue2.posapplication.network.StkiteDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by anthony on 4/21/2017.
 *
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper mDBHelper;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //this.deleteDatabase(mDBHelper.DATABASE_NAME);
        //this.deleteDatabase(ConfigurationDatabaseHelper.DATABASE_NAME);

        // Get database handler
        mContext = this;
        mDBHelper = new DatabaseHelper(this);
        ConfigurationDatabaseHelper mCDBHelper = new ConfigurationDatabaseHelper(this);


        // Checks to see if there are Network Settings
        if (mCDBHelper.GetNetworkSettings().size() == 0){
            // Set Default Network Values
            mCDBHelper.AddNetworkSetting(new NetworkSetting(1,0,
                    "192.168.56.1", "posdb", "root", "123"));
        }

        // Check to see if tables are empty
        checkTablesEmpty();

        // Checks to see if its in network mode
        if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 1){
            new SynchroniseTask(MainActivity.this).execute();
        } else if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 0){
            createTabLayouts();
        }

        // If Restaurant Name has not been set
        if (mDBHelper.GetRestaurant(1).getsRestaurantName().equals("")){
            setTitle(getString(R.string.main_title));
        }
        // If Restaurant Name has been set
        else{
            setTitle(mDBHelper.GetRestaurant(1).getsRestaurantName());
        }

        // Setup Toolbar Buttons
        ImageButton sync_button = (ImageButton) findViewById(R.id.sync_button);
        if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 1) {
            sync_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SynchroniseTask(MainActivity.this).execute();
                }
            });

        } else {
            sync_button.setVisibility(View.INVISIBLE);
        }

        ImageButton delivery_button = (ImageButton) findViewById(R.id.delivery_button);
        delivery_button.setOnClickListener(this);
        ImageButton eatin_button = (ImageButton) findViewById(R.id.eatin_button);
        eatin_button.setOnClickListener(this);
        ImageButton takeaway_button = (ImageButton) findViewById(R.id.takeaway_button);
        takeaway_button.setOnClickListener(this);


        // Setup Floating Action Buttons
        FloatingActionButton mainFAB = (FloatingActionButton) findViewById(R.id.fab);
        mainFAB.setOnClickListener(this);
        FloatingActionButton fabTakeaway = (FloatingActionButton) findViewById(R.id.fab_takeaway);
        fabTakeaway.setOnClickListener(this);
        FloatingActionButton fabDelivery = (FloatingActionButton) findViewById(R.id.fab_delivery);
        fabDelivery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // If main fab clicked
            case R.id.fab:
                // Setup FAB
                final FloatingActionButton mainFAB = (FloatingActionButton) findViewById(R.id.fab);
                // Setup Animations
                final Animation showAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.show_button);
                final Animation hideAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.hide_button);
                final Animation showLayout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.show_layout);
                final Animation hideLayout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.hide_layout);
                // Setup LinearLayouts
                final LinearLayout takeawayLayout = (LinearLayout) findViewById(R.id.main_takeaway_layout);
                final LinearLayout deliveryLayout = (LinearLayout) findViewById(R.id.main_delivery_layout);

                // If main fab has been clicked then hide the other fabs
                if(takeawayLayout.getVisibility() == View.VISIBLE &&
                        deliveryLayout.getVisibility() == View.VISIBLE){
                    takeawayLayout.setVisibility(View.GONE);
                    deliveryLayout.setVisibility(View.GONE);
                    takeawayLayout.startAnimation(hideLayout);
                    deliveryLayout.startAnimation(hideLayout);
                    mainFAB.startAnimation(hideAnimation);
                }
                // Otherwise show the other fabs
                else{
                    takeawayLayout.setVisibility(View.VISIBLE);
                    deliveryLayout.setVisibility(View.VISIBLE);
                    takeawayLayout.startAnimation(showLayout);
                    deliveryLayout.startAnimation(showLayout);
                    mainFAB.startAnimation(showAnimation);
                }
                break;
            // If takeaway fab clicked
            case R.id.takeaway_button:
            case R.id.fab_takeaway:
                Intent takeawayIntent = new Intent(v.getContext(), OrderActivity.class);
                takeawayIntent.putExtra(OrderActivity.EXTRA_ORDERTYPE, Order.TYPE_TAKEAWAY);
                takeawayIntent.putExtra(OrderActivity.EXTRA_TABLENAME, "Takeaway");
                takeawayIntent.putExtra(OrderActivity.EXTRA_TABLEGUESTS, 0);
                takeawayIntent.putExtra(OrderActivity.EXTRA_TABLEID, 0);
                takeawayIntent.putExtra(OrderActivity.EXTRA_ORDERID, 0);
                takeawayIntent.putExtra(OrderActivity.EXTRA_FROM, "MainActivity");
                startActivity(takeawayIntent);
                break;
            // If delivery fab clicked
            case R.id.delivery_button:
            case R.id.fab_delivery:
                Intent deliveryIntent = new Intent(v.getContext(), DeliveryDetailsActivity.class);
                startActivity(deliveryIntent);
                break;
            case R.id.eatin_button:
                // Eatin order with custom Table Number
                Toast.makeText(mContext, "TODO: Add Button "
                        , Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_table_no, null);
                builder.setView(dialogView);

                final EditText sTableNo = (EditText) dialogView.findViewById(R.id.table_number);

                builder.setTitle("Table Number")
                        .setMessage("Enter a Table Number: ")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                long nTableNo;
                                try {
                                    nTableNo = Long.parseLong(sTableNo.getText().toString());
                                } catch (NumberFormatException e) {
                                    nTableNo = 0;
                                }
                                System.out.println("Table no: " + nTableNo);
                                Intent intent = new Intent(mContext, OrderActivity.class);
                                intent.putExtra(OrderActivity.EXTRA_TABLENAME, String.valueOf(nTableNo));
                                intent.putExtra(OrderActivity.EXTRA_TABLEGUESTS, 0);
                                intent.putExtra(OrderActivity.EXTRA_ORDERTYPE, Order.TYPE_EAT_IN);
                                intent.putExtra(OrderActivity.EXTRA_TABLEID, nTableNo);
                                intent.putExtra(OrderActivity.EXTRA_ORDERID, 0);
                                intent.putExtra(OrderActivity.EXTRA_FROM, "MainRecyclerViewAdapter");

                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                builder.show();
                break;
        }
    }

    private class SynchroniseTask extends AsyncTask<Object, Object, Boolean> {
        private ProgressDialog mDialog;
        private DatabaseHelper dbHelper;

        SynchroniseTask(MainActivity activity){
            mDialog = new ProgressDialog(activity);
            dbHelper = new DatabaseHelper(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setTitle("Synchronising Data");
            mDialog.setMessage("Getting database information from server. Please Wait...");
            mDialog.setIndeterminate(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected Boolean doInBackground(Object... params) {

            PoqapaDAO poqapaDAO = new PoqapaDAO(mContext);
            PoqapdDAO poqapdDAO = new PoqapdDAO(mContext);
            StkcatDAO stkcatDAO = new StkcatDAO(mContext);
            StkiteDAO stkiteDAO = new StkiteDAO(mContext);

            Collection<Table> tables;
            List<Order> orders;
            List<OrderItem> orderItems;
            List<Product> products;
            List<Group> groups;
            List<Poqapa> poqapas;
            List<Poqapd> poqapds;
            List<Stkcat> stkcats;
            List<Stkite> stkites;

            try {
                // Check connection
                if (poqapaDAO.testConnection()) {
                    // Reset Tables
                    tables = dbHelper.GetAllTables().values();
                    for (Table table:tables) {
                        dbHelper.ClearTable(table);
                    }
                    // Sync Orders
                    poqapas = poqapaDAO.getEatInPoqapas();
                    orders = SaxposConverter.poqapaToOrders(poqapas);
                    dbHelper.dropTable(Order.TABLE_NAME);
                    dbHelper.createTable(Order.CREATE_STATEMENT);
                    dbHelper.dropTable(OrderItem.TABLE_NAME);
                    dbHelper.createTable(OrderItem.CREATE_STATEMENT);

                    for (Order order : orders) {
                        dbHelper.AddOrder(order);
                        // Sync OrderItems
                        poqapds = poqapdDAO.getPoqapdsFromOrderID(order.getnOrderID());
                        System.out.println(poqapds.size());
                        orderItems = SaxposConverter.poqapdToOrderItems(poqapds);
                        for (OrderItem orderItem : orderItems) {
                                dbHelper.AddOrderItem(orderItem);
                        }
                        // Sync Tables
                        for (Table table : tables) {
                            if (order.getnTableID() == table.getnTableID()) {
                                table.setsStatus(Table.STATUS_INUSE);
                                table.setnOrderID(order.getnOrderID());
                                table.setnTableID(order.getnTableID());
                                table.setnInvSum(order.getnTotal());

                                dbHelper.UpdateTable(table);
                            }
                        }
                    }

                    // Sync Products
                    stkites = stkiteDAO.getStkites();
                    dbHelper.dropTable(Product.TABLE_NAME);
                    dbHelper.createTable(Product.CREATE_STATEMENT);
                    products = SaxposConverter.stkiteToProducts(stkites);
                    for (Product product : products) {
                        dbHelper.AddProduct(product);
                    }
                    // Sync Groups
                    stkcats = stkcatDAO.getStkcats();
                    dbHelper.dropTable(Group.TABLE_NAME);
                    dbHelper.createTable(Group.CREATE_STATEMENT);
                    groups = SaxposConverter.stkcatToGroups(stkcats);
                    for (Group group : groups) {
                        dbHelper.AddGroup(group);
                    }
                } else{
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

            if (!aVoid){
                Toast.makeText(mContext, "Error, Could not Connect to Db. " +
                                "Please make sure your network settings are correct and try again!"
                        , Toast.LENGTH_LONG).show();
            }
            createTabLayouts();
            mDialog.dismiss();
        }
    }

    private void createTabLayouts(){
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);

        assert mViewPager != null;
        mSectionsPagerAdapter.addFragment(new MainOrdersFragment(), "Orders");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Tables");

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
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

    private void checkTablesEmpty(){
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

        if (mDBHelper.GetAllRestaurants().size() == 0)
            mDBHelper.CreateDefaultRestaurant();
    }
}