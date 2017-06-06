package com.ass3.axue2.posapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.fragments.OrderCurrentFragment;
import com.ass3.axue2.posapplication.fragments.OrderGroupFragment;
import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Customer;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Delivery;
import com.ass3.axue2.posapplication.models.operational.Group;
import com.ass3.axue2.posapplication.models.operational.Order;
import com.ass3.axue2.posapplication.models.operational.OrderItem;
import com.ass3.axue2.posapplication.models.operational.Product;
import com.ass3.axue2.posapplication.models.operational.Table;
import com.ass3.axue2.posapplication.network.CustomerDAO;
import com.ass3.axue2.posapplication.network.DeliveryDAO;
import com.ass3.axue2.posapplication.network.OrderDAO;
import com.ass3.axue2.posapplication.network.OrderItemDAO;
import com.ass3.axue2.posapplication.network.TableDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    public static final String EXTRA_TABLENAME = "Table Name";
    public static final String EXTRA_TABLEID = "Table ID";
    public static final String EXTRA_TABLEGUESTS = "Table Guests";
    public static final String EXTRA_ORDERID = "Order ID";
    public static final String EXTRA_ORDERTYPE = "Order Type";

    public static final String EXTRA_FROM = "From";

    public static final String EXTRA_CUSTOMERNAME = "Customer Name";
    public static final String EXTRA_AL1 = "Address Line 1";
    public static final String EXTRA_AL2 = "Address Line 2";
    public static final String EXTRA_AL3 = "Address Line 3";
    public static final String EXTRA_POSTCODE = "Postcode";
    public static final String EXTRA_PHONE = "Phone";
    public static final String EXTRA_DELIVERYFEE = "Delivery";

    private long nTableID;
    private long nOrderID;
    private long nQuantity = 0;
    private double nSubtotal = 0;
    private String sType;
    private String mTableName;
    private int nTableGuests;

    private String sCustomerName;
    private String sAL1;
    private String sAL2;
    private String sAL3;
    private int nPostcode;
    private int nPhone;
    private double nDeliveryFee;



    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private DatabaseHelper mDBHelper;
    private ConfigurationDatabaseHelper mCDBHelper;

    private TextView mOrderNumberTextView;
    private TextView mOrderQuantityTextView;
    private TextView mOrderSubtotalTextView;

    private ArrayList<OrderItem> mOrderItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();

        mTableName = intent.getStringExtra(EXTRA_TABLENAME);
        nTableID = intent.getLongExtra(EXTRA_TABLEID, 0);
        nTableGuests = intent.getIntExtra(EXTRA_TABLEGUESTS, 0);
        nOrderID = intent.getLongExtra(EXTRA_ORDERID, 0);
        sType = intent.getStringExtra(EXTRA_ORDERTYPE);

        if (intent.getStringExtra(EXTRA_FROM).equals("DeliveryDetailsActivity")){
            sCustomerName = intent.getStringExtra(EXTRA_CUSTOMERNAME);
            sAL1 = intent.getStringExtra(EXTRA_AL1);
            sAL2 = intent.getStringExtra(EXTRA_AL2);
            sAL3 = intent.getStringExtra(EXTRA_AL3);
            nPostcode = intent.getIntExtra(EXTRA_POSTCODE, 0);
            nPhone = intent.getIntExtra(EXTRA_POSTCODE, 0);
            nDeliveryFee = intent.getDoubleExtra(EXTRA_DELIVERYFEE,0);
        }

        Log.d("EXTRA_TABLENAME VALUE", mTableName);
        Log.d("EXTRA_TABLEID VALUE", String.valueOf(nTableID));

        mDBHelper = new DatabaseHelper(getApplicationContext());
        mCDBHelper = new ConfigurationDatabaseHelper(getApplicationContext());


        if (nOrderID > 0) {
            // Get all OrderItems
            mOrderItems = new ArrayList<>(mDBHelper.GetOrderItems(nOrderID).values());
            // Get total quantity of OrderItems
            for (OrderItem orderItem : mOrderItems) {
                nQuantity += orderItem.getnQuantity();
                nSubtotal += (orderItem.getnPrice() * orderItem.getnQuantity());
                System.out.println("ITEM PRICE: " + String.valueOf(orderItem.getnPrice()));
                System.out.println("ITEM QUANTITY: " + String.valueOf(orderItem.getnPrice()));
                System.out.println("ORDER SUBTOTAL: " + String.valueOf(nSubtotal));
            }
        }
        // Get all the Groups
        ArrayList<Group> groups = new ArrayList<>(mDBHelper.GetAllGroups().values());
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(mTableName);

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

            if(group.getnGroupID() > 0) {
                // Put GroupID in fragment
                Bundle args = new Bundle();
                args.putLong(OrderGroupFragment.BUNDLE_ITEM_GROUPID, group.getnGroupID());
                f.setArguments(args);

                mSectionsPagerAdapter.addFragment(f, group.getsGroupName());
            }
        }


        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);

        // Setup TextView
        mOrderNumberTextView = (TextView) findViewById(R.id.order_number);
        mOrderQuantityTextView = (TextView) findViewById(R.id.order_quantity);
        mOrderSubtotalTextView = (TextView) findViewById(R.id.order_subtotal);
        setTextViewValues();

        // Get Button
        Button mConfirmButton = (Button) findViewById(R.id.order_confirm_button);

        if(this.sType.equals(Order.TYPE_EAT_IN) || this.sType.equals(Order.TYPE_DELIVERY)) {
            mConfirmButton.setText(R.string.order_confirm_order);
        } else {
            mConfirmButton.setText(R.string.order_payment);
            mConfirmButton.setBackgroundColor(Color.RED);
        }
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnclickListener", "Click Successful");
                if (sType.equals(Order.TYPE_TAKEAWAY)) {

                    Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
                    intent.putExtra(PaymentActivity.EXTRA_ORDERTYPE, sType);
                    intent.putExtra(PaymentActivity.EXTRA_SUBTOTAL, nSubtotal);

                    startActivity(intent);
                }else {
                    // if standalone mode
                    if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 0) {
                        Intent intent = new Intent(OrderActivity.this, MainActivity.class);

                        Order order = new Order(nOrderID, nTableID, sType, Order.STATUS_UNPAID, nSubtotal);
                        // If status is not In-use create new order
                        if (nOrderID <= 0) {
                            nOrderID = mDBHelper.AddOrder(order);
                        } else {
                            // Update subtotal for Order
                            mDBHelper.UpdateOrder(order);
                        }
                        // Update Table details if its an eat-in order
                        if (sType.equals(Order.TYPE_EAT_IN)) {
                            Table table = new Table(nTableID, mTableName, nTableGuests, nOrderID, nSubtotal, Table.STATUS_INUSE);
                            mDBHelper.UpdateTable(table);
                        }
                        // Otherwise add/update delivery details if delivery order
                        else if (sType.equals(Order.TYPE_DELIVERY)) {
                            Customer customer = new Customer(sCustomerName, "", sAL1, sAL2, sAL3,
                                    nPostcode, nPhone);
                            long customerID = mDBHelper.AddCustomer(customer);

                            Delivery delivery = new Delivery(nOrderID, customerID, nDeliveryFee);
                            mDBHelper.AddDelivery(delivery);
                        }
                        // Adds/Updates OrderItems to db
                        for (OrderItem orderItem : mOrderItems) {
                            orderItem.setnOrderID(nOrderID);
                            if (orderItem.getnOrderItemID() > 0) {
                                mDBHelper.UpdateOrderItem(orderItem);
                            } else {
                                mDBHelper.AddOrderItem(orderItem);
                            }

                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        OrderActivity.this.finish();
                    }
                    // if network mode
                    else if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 1){
                        System.out.println("NETWORK MODE ON, CONFIRM TASK");
                        new ConfirmTask(OrderActivity.this).execute();
                    }
                }
            }
        });
    }

    private class ConfirmTask extends AsyncTask<Void, Void, Void>{
        private ProgressDialog mDialog;

        public ConfirmTask(OrderActivity activity){
            mDialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setTitle("Sending Data");
            mDialog.setMessage("Sending database information to server. Please Wait...");
            mDialog.setIndeterminate(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Order order = new Order(nOrderID, nTableID, sType, Order.STATUS_UNPAID, nSubtotal);
            // If status is not In-use create new order
            if (nOrderID <= 0) {
                try{
                    // Insert Order
                    OrderDAO orderDAO = new OrderDAO();
                    nOrderID = orderDAO.insertOrder(order);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try{
                    // Update Order
                    OrderDAO orderDAO = new OrderDAO();
                    orderDAO.updateOrder(order);

                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // Update Table details if its an eat-in order
            if (sType.equals(Order.TYPE_EAT_IN)) {
                Table table = new Table(nTableID, mTableName, nTableGuests, nOrderID, nSubtotal, Table.STATUS_INUSE);
                try {
                    TableDAO tableDAO = new TableDAO();
                    tableDAO.updateTable(table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // Otherwise add/update delivery details if delivery order
            else if (sType.equals(Order.TYPE_DELIVERY)) {
                CustomerDAO customerDAO = new CustomerDAO();
                DeliveryDAO deliveryDAO = new DeliveryDAO();
                Customer customer = new Customer(sCustomerName, "", sAL1, sAL2, sAL3,
                        nPostcode, nPhone);
                try {
                    long customerID = customerDAO.insertCustomer(customer);
                    Delivery delivery = new Delivery(nOrderID, customerID, nDeliveryFee);
                    deliveryDAO.insertDelivery(delivery);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // Adds/Updates OrderItems to db
            try {
                OrderItemDAO orderItemDAO = new OrderItemDAO();
                for (OrderItem orderItem : mOrderItems) {
                    orderItem.setnOrderID(nOrderID);
                    if (orderItem.getnOrderItemID() > 0) {
                        orderItemDAO.updateOrderItem(orderItem);
                    } else {
                        orderItemDAO.insertOrderItem(orderItem);
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
            mDialog.dismiss();
            Intent intent = new Intent(OrderActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
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

    public void AddOrderItem(OrderItem orderItem){

        boolean found = false;

        // Update Activity
        nQuantity += 1;
        nSubtotal += orderItem.getnPrice();
        setTextViewValues();

        // Check if OrderItem is currently in the list
        for (OrderItem checkItem : mOrderItems) {
            // If found in OrderItems then increase quantity by one
            if(checkItem.getnProductID() == orderItem.getnProductID()){
                checkItem.setnQuantity(checkItem.getnQuantity() + 1);
                Log.d("Current Quantity", String.valueOf(checkItem.getnQuantity()));
                updateCurrentFragment();
                found = true;
                break;
            }
        }

        if (!found) {
            // if OrderItem could not be found add one into OrderItems
            mOrderItems.add(orderItem);
            updateCurrentFragment();
            Log.d("Current Number of items", String.valueOf(mOrderItems.size()));
        }
    }

    public OrderItem ConvertProductToOrderItem(Product product){
        // Convert product into an OrderItem
        return new OrderItem(nOrderID, nTableID, product.getnProductID(),
                product.getsProductName(), product.getnPrice(), 1);
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

    private void setTextViewValues(){
        if (nOrderID > 0){
            mOrderNumberTextView.setText(String.valueOf(nOrderID));
        }else{
            mOrderNumberTextView.setText(R.string.order_unconfirmed);
        }

        mOrderQuantityTextView.setText(String.valueOf(nQuantity));
        mOrderSubtotalTextView.setText(String.valueOf("$" + nSubtotal));
    }

    public void RemoveOrderItem(OrderItem orderItem){

        if(nQuantity > 0 && orderItem.getnQuantity() > 0 && nSubtotal >= orderItem.getnPrice()){
            nQuantity -= 1;
            nSubtotal -= orderItem.getnPrice();

            for (OrderItem checkItem : mOrderItems) {
                // If found in OrderItems then decrease quantity by one
                if (checkItem.getnProductID() == orderItem.getnProductID()) {
                    checkItem.setnQuantity(checkItem.getnQuantity() - 1);
                    Log.d("Current Quantity", String.valueOf(checkItem.getnQuantity()));
                    updateCurrentFragment();
                    break;
                }
            }
        }
        setTextViewValues();
    }

}
