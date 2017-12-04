package com.ass3.axue2.posapplication.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Customer;
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
import com.ass3.axue2.posapplication.network.Ab5ctlDAO;
import com.ass3.axue2.posapplication.network.CustomerDAO;
import com.ass3.axue2.posapplication.network.DeliveryDAO;
import com.ass3.axue2.posapplication.network.OrderItemDAO;
import com.ass3.axue2.posapplication.network.PoqapaDAO;
import com.ass3.axue2.posapplication.network.PoqapdDAO;
import com.ass3.axue2.posapplication.network.TableDAO;
import com.ass3.axue2.posapplication.views.adapters.OrderCurrentRecyclerViewAdapter;
import com.ass3.axue2.posapplication.views.adapters.OrderGroupRecyclerViewAdapter;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    // Extras all previous activities must contain
    public static final String EXTRA_TABLENAME = "Table Name";
    public static final String EXTRA_TABLEID = "Table ID";
    public static final String EXTRA_TABLEGUESTS = "Table Guests";
    public static final String EXTRA_ORDERID = "Order ID";
    public static final String EXTRA_ORDERTYPE = "Order Type";
    public static final String EXTRA_FROM = "From";
    // Delivery Extras
    public static final String EXTRA_CUSTOMERNAME = "Customer Name";
    public static final String EXTRA_AL1 = "Address Line 1";
    public static final String EXTRA_AL2 = "Address Line 2";
    public static final String EXTRA_AL3 = "Address Line 3";
    public static final String EXTRA_POSTCODE = "Postcode";
    public static final String EXTRA_PHONE = "Phone";
    public static final String EXTRA_DELIVERYFEE = "Delivery";
    // Order Variables
    private long nTableID;
    private long nOrderID;
    private long nQuantity = 0;
    private double nSubtotal = 0;
    private String sType;
    private String mTableName;
    private int nTableGuests;
    // Delivery Variables
    private String sCustomerName;
    private String sAL1;
    private String sAL2;
    private String sAL3;
    private int nPostcode;
    private int nPhone;
    private double nDeliveryFee;

    private Context mContext;
    private RecyclerView mRV;
    private TabLayout mTabLayout;
    private DatabaseHelper mDBHelper;
    private ConfigurationDatabaseHelper mCDBHelper;
    private Button mCurrentOrderButton;
    private TextView mOrderNumberTextView;
    private TextView mOrderQuantityTextView;
    private TextView mOrderSubtotalTextView;

    private ArrayList<OrderItem> mOrderItems = new ArrayList<>();
    private ArrayList<Group> mGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mContext = this;
        Intent intent = getIntent();

        // Get Order information
        mTableName = intent.getStringExtra(EXTRA_TABLENAME);
        nTableID = intent.getLongExtra(EXTRA_TABLEID, 0);
        nTableGuests = intent.getIntExtra(EXTRA_TABLEGUESTS, 0);
        nOrderID = intent.getLongExtra(EXTRA_ORDERID, 0);
        sType = intent.getStringExtra(EXTRA_ORDERTYPE);
        // if delivery transaction, get customer information
        if (intent.getStringExtra(EXTRA_FROM).equals("DeliveryDetailsActivity")){
            sCustomerName = intent.getStringExtra(EXTRA_CUSTOMERNAME);
            sAL1 = intent.getStringExtra(EXTRA_AL1);
            sAL2 = intent.getStringExtra(EXTRA_AL2);
            sAL3 = intent.getStringExtra(EXTRA_AL3);
            nPostcode = intent.getIntExtra(EXTRA_POSTCODE, 0);
            nPhone = intent.getIntExtra(EXTRA_POSTCODE, 0);
            nDeliveryFee = intent.getDoubleExtra(EXTRA_DELIVERYFEE,0);
        }

        // Setup DBs
        mDBHelper = new DatabaseHelper(getApplicationContext());
        mCDBHelper = new ConfigurationDatabaseHelper(getApplicationContext());

        // Setup RecyclerView
        mRV = (RecyclerView) findViewById(R.id.order_rv);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        //StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRV.setLayoutManager(llm);

        // Setup Divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,
                llm.getOrientation());
        mRV.addItemDecoration(itemDecoration);

        // If order already exists
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
        mGroups = new ArrayList<>(mDBHelper.GetAllGroups().values());

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get Buttons
        mCurrentOrderButton = (Button) findViewById(R.id.order_current_button);
        Button mConfirmButton = (Button) findViewById(R.id.order_confirm_button);

        // Title is set as table name
        // if takeaway or delivery transaction then table name is the transaction type
        setTitle(mTableName);

        // Setup TabLayout
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        // Create a new tab for each group
        for (Group group : mGroups) {
            if(group.getnGroupID() > 0) {
                // Add Group to tab
                mTabLayout.addTab(mTabLayout.newTab().setText(group.getsGroupName()));
            }
        }

        // If order exists then show current order
        if (nOrderID > 0){
            OrderCurrentRecyclerViewAdapter adapter =
                    new OrderCurrentRecyclerViewAdapter(mContext, mOrderItems);
            mRV.setAdapter(adapter);
            mCurrentOrderButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.OrderCurrentButtonClicked));
        }
        // Otherwise show first group
        else{
            updateCurrentOrderRV();
        }

        // Setup TextView
        mOrderNumberTextView = (TextView) findViewById(R.id.order_number);
        mOrderQuantityTextView = (TextView) findViewById(R.id.order_quantity);
        mOrderSubtotalTextView = (TextView) findViewById(R.id.order_subtotal);
        setTextViewValues();

        // Set Buttons depending on transaction type
        if(this.sType.equals(Order.TYPE_EAT_IN) || this.sType.equals(Order.TYPE_DELIVERY)) {
            mConfirmButton.setText(R.string.order_confirm_order);
        } else {
            mConfirmButton.setText(R.string.order_payment);
            mConfirmButton.setBackgroundColor(Color.RED);
        }
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If takeaway order go to Payment Activity
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
                        // Gets the current OrderItems in DB
                        ArrayList<OrderItem> OrderItems = new ArrayList<>(mDBHelper.GetOrderItems(nOrderID).values());
                        // Checks to see if any OrderItems have been deleted
                        for (OrderItem orderItem : OrderItems){
                            boolean isFound = false;
                            int i = 0;
                            // Check each OrderItem until it is found
                            while (i < mOrderItems.size() && !isFound){
                                OrderItem orderItem1 = mOrderItems.get(i);
                                // If found set isFound to true
                                if (orderItem1.getnOrderItemID() == orderItem.getnOrderItemID()){
                                    isFound = true;
                                }
                                i++;
                            }
                            // If OrderItem was not found then delete it from db
                            if (!isFound){
                                mDBHelper.DeleteOrderItem(orderItem);
                            }
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
                        // Return to MainActivity
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


        mCurrentOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Order Current Button Clicked");
                // Replace Adapter
                OrderCurrentRecyclerViewAdapter adapter =
                        new OrderCurrentRecyclerViewAdapter(mContext, mOrderItems);
                mRV.setAdapter(adapter);
                mCurrentOrderButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.OrderCurrentButtonClicked));

            }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateCurrentOrderRV();
                mCurrentOrderButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.OrderCurrentButton));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                updateCurrentOrderRV();
                mCurrentOrderButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.OrderCurrentButton));
            }
        });
    }

    private class ConfirmTask extends AsyncTask<Void, Void, Void>{
        private ProgressDialog mDialog;

        ConfirmTask(OrderActivity activity){
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
//            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Order order = new Order(nOrderID, nTableID, sType, Order.STATUS_UNPAID, nSubtotal);
            Poqapa poqapa = SaxposConverter.orderToPoqapa(order);
            // If status is not In-use create new order

            if (nOrderID <= 0) {
                try{
                    // Generate a new Invoice No
                    Ab5ctlDAO ab5ctlDAO = new Ab5ctlDAO(mContext);
                    BigDecimal invoiceNumber = ab5ctlDAO.getItemNo();
                    poqapa.setsID(SaxposConverter.convertToDigits(invoiceNumber.intValue(),7));


                    // Insert Order
                    //OrderDAO orderDAO = new OrderDAO(mContext);
                    //nOrderID = orderDAO.insertOrder(order);
                    PoqapaDAO poqapaDAO = new PoqapaDAO(mContext);
                    System.out.println("Old Order ID: " + nOrderID);
                    poqapaDAO.insertPoqapa(poqapa);
                    nOrderID = invoiceNumber.intValue();
                    System.out.println("New Order ID: " + nOrderID);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try{
                    // Update Order
                    //OrderDAO orderDAO = new OrderDAO(mContext);
                    //orderDAO.updateOrder(order);
                    PoqapaDAO poqapaDAO = new PoqapaDAO(mContext);
                    poqapaDAO.updatePoqapa(poqapa);

                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // Update Table details if its an eat-in order
            if (sType.equals(Order.TYPE_EAT_IN)) {
                Table table = new Table(nTableID, mTableName, nTableGuests, nOrderID, nSubtotal, Table.STATUS_INUSE);
                try {
                    TableDAO tableDAO = new TableDAO(mContext);
                    tableDAO.updateTable(table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // Otherwise add/update delivery details if delivery order
            else if (sType.equals(Order.TYPE_DELIVERY)) {
                CustomerDAO customerDAO = new CustomerDAO(mContext);
                DeliveryDAO deliveryDAO = new DeliveryDAO(mContext);
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

            // apd_detail_no Used for SAXPOS conversion
            int nItemPosition = 0;

            // Gets the current OrderItems in DB
            ArrayList<OrderItem> OrderItems = new ArrayList<>(mDBHelper.GetOrderItems(nOrderID).values());
            // Checks to see if any OrderItems have been deleted
            for (OrderItem orderItem : OrderItems){
                System.out.println("Position: " + orderItem.getnPosition());
                if (orderItem.getnPosition() > nItemPosition)
                    nItemPosition = orderItem.getnPosition();

                boolean isFound = false;
                int i = 0;
                // Check each OrderItem until it is found
                while (i < mOrderItems.size() && !isFound){
                    OrderItem orderItem1 = mOrderItems.get(i);
                    // If found set isFound to true
                    if (orderItem1.getnOrderItemID() == orderItem.getnOrderItemID()){
                        isFound = true;
                    }
                    i++;
                }
                // If OrderItem was not found then delete it from db
                if (!isFound){
                    try {
                        //OrderItemDAO orderItemDAO = new OrderItemDAO(mContext);
                        //orderItemDAO.deleteOrderItem(orderItem);

                        Poqapd poqapd = SaxposConverter.orderItemToPoqapd(orderItem);

                        System.out.println("Delete poqapd: " + poqapd.getsItemName());
                        PoqapdDAO poqapdDAO = new PoqapdDAO(mContext);
                        poqapdDAO.deletePoqapd(poqapd);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Adds/Updates OrderItems to db
            try {
                OrderItemDAO orderItemDAO = new OrderItemDAO(mContext);
                PoqapdDAO poqapdDAO = new PoqapdDAO(mContext);
                for (OrderItem orderItem : mOrderItems) {
                    orderItem.setnOrderID(nOrderID);
                    System.out.println("Order ID: " + nOrderID);
                    Poqapd poqapd = SaxposConverter.orderItemToPoqapd(orderItem);
                    System.out.println("Detail No: " + poqapd.getnDetailNo());
                    if (orderItem.getnOrderItemID() > 0) {
                        poqapdDAO.updatePoqapd(poqapd);
                        //orderItemDAO.updateOrderItem(orderItem);
                    } else {
                        nItemPosition += 1;
                        poqapd.setnDetailNo(nItemPosition);
                        poqapdDAO.insertPoqapd(poqapd);
                        //orderItemDAO.insertOrderItem(orderItem);
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

    private void updateCurrentOrderRV(){
        Group group = mGroups.get(mTabLayout.getSelectedTabPosition());

        // Get all products
        ArrayList<Product> productList = new ArrayList<>();
        if (group.getnGroupID() > 0){
            productList = new ArrayList<>(mDBHelper.GetProducts(group.getnGroupID()).values());
        }

        // Replace Adapter
        OrderGroupRecyclerViewAdapter adapter =
                new OrderGroupRecyclerViewAdapter(mContext, productList);
        mRV.setAdapter(adapter);
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
                found = true;
                break;
            }
        }

        if (!found) {
            // if OrderItem could not be found add one into OrderItems
            mOrderItems.add(orderItem);
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

    private void setTextViewValues(){
        if (nOrderID > 0){
            mOrderNumberTextView.setText(String.valueOf(nOrderID));
        }else{
            mOrderNumberTextView.setText(R.string.order_unconfirmed);
        }

        mOrderQuantityTextView.setText(String.valueOf(nQuantity));
        mOrderSubtotalTextView.setText(String.valueOf("$" + String.format("%.2f", nSubtotal)));
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

                    // If the quantity is now zero
                    if (checkItem.getnQuantity() == 0){
                        mOrderItems.remove(checkItem);
                    }

                    break;
                }
            }
        }
        setTextViewValues();
    }

}
