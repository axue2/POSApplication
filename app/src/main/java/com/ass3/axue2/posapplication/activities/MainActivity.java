package com.ass3.axue2.posapplication.activities;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ass3.axue2.posapplication.R;
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
import com.ass3.axue2.posapplication.network.GroupDAO;
import com.ass3.axue2.posapplication.network.OrderDAO;
import com.ass3.axue2.posapplication.network.OrderItemDAO;
import com.ass3.axue2.posapplication.network.ProductDAO;
import com.ass3.axue2.posapplication.network.TableDAO;

import java.sql.SQLException;
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

        //this.deleteDatabase(mDBHelper.DATABASE_NAME);
        //this.deleteDatabase(ConfigurationDatabaseHelper.DATABASE_NAME);

        // Get database handler
        mDBHelper = new DatabaseHelper(getApplicationContext());
        ConfigurationDatabaseHelper mCDBHelper = new ConfigurationDatabaseHelper(getApplicationContext());


        if (mCDBHelper.GetNetworkSettings().size() == 0){
            mCDBHelper.AddNetworkSetting(new NetworkSetting(1,0));
        }

        // Checks to see if its in network mode
        if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 1){
            //TODO Create empty tables if tables do not exist
            new SynchroniseTask(MainActivity.this).execute();
        } else if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 0){
            checkTablesEmpty();
            createTabLayouts();
        }

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

                intent.putExtra(OrderActivity.EXTRA_FROM, "MainActivity");

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

    private class SynchroniseTask extends AsyncTask<Void, Void, Void>{
        private ProgressDialog mDialog;
        private DatabaseHelper dbHelper;

        public SynchroniseTask(MainActivity activity){
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
        protected Void doInBackground(Void... params) {
            TableDAO tableDAO= new TableDAO();
            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            ProductDAO productDAO = new ProductDAO();
            GroupDAO groupDAO = new GroupDAO();
            List<Table> tables;
            List<Order> orders;
            List<OrderItem> orderItems;
            List<Product> products;
            List<Group> groups;
            try {
                // Sync Tables
                tables = tableDAO.getTables();
                dbHelper.dropTable(Table.TABLE_NAME);
                dbHelper.createTable(Table.CREATE_STATEMENT);
                for (Table table : tables){
                    dbHelper.AddTable(table);
//                    System.out.println("Table ID: " + String.valueOf(table.getnTableID()));
//                    System.out.println("Table Name: " + table.getsTableName());
//                    System.out.println();
                }
                // Sync Orders
                orders = orderDAO.getOrders();
                dbHelper.dropTable(Order.TABLE_NAME);
                dbHelper.createTable(Order.CREATE_STATEMENT);
                for (Order order: orders){
                    dbHelper.AddOrder(order);
//                    System.out.println("Order ID: " + String.valueOf(order.getnOrderID()));
//                    System.out.println("Order Status: " + order.getsStatus());
//                    System.out.println();
                }
                // Sync OrderItems
                orderItems = orderItemDAO.getOrderItems();
                dbHelper.dropTable(OrderItem.TABLE_NAME);
                dbHelper.createTable(OrderItem.CREATE_STATEMENT);
                for (OrderItem orderItem : orderItems){
                    dbHelper.AddOrderItem(orderItem);
//                    System.out.println("OrderItem ID: " + String.valueOf(orderItem.getnOrderItemID()));
//                    System.out.println("Order Status: " + orderItem.getsProductName());
//                    System.out.println("OrderITEM PRICE: " + orderItem.getnPrice());
//                    System.out.println("OrderITEM QUANTITY: " + orderItem.getnQuantity());

                }
                // Sync Products
                products = productDAO.getProducts();
                dbHelper.dropTable(Product.TABLE_NAME);
                dbHelper.createTable(Product.CREATE_STATEMENT);
                for (Product product : products){
                    dbHelper.AddProduct(product);
//                    System.out.println("Product ID: " + String.valueOf(product.getnProductID()));
//                    System.out.println("Product Name: " + product.getsProductName());
//                    System.out.println();
                }
                // Sync Groups
                groups = groupDAO.getGroups();
                dbHelper.dropTable(Group.TABLE_NAME);
                dbHelper.createTable(Group.CREATE_STATEMENT);
                for (Group group : groups){
                    dbHelper.AddGroup(group);
//                    System.out.println("Group ID: " + String.valueOf(group.getnGroupID()));
//                    System.out.println("Group Name: " + group.getsGroupName());
//                    System.out.println();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");

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
        Log.d("Checking tables", "checkTablesEmpty: ");
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
    }
}
