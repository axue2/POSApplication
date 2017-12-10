package com.ass3.axue2.posapplication.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Order;
import com.ass3.axue2.posapplication.models.operational.Table;
import com.ass3.axue2.posapplication.models.saxpos.Ab5ctl;
import com.ass3.axue2.posapplication.models.saxpos.Poqapa;
import com.ass3.axue2.posapplication.models.saxpos.SaxposConverter;
import com.ass3.axue2.posapplication.network.Ab5ctlDAO;
import com.ass3.axue2.posapplication.network.OrderDAO;
import com.ass3.axue2.posapplication.network.PoqapaDAO;
import com.ass3.axue2.posapplication.network.TableDAO;

import java.sql.SQLException;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_SUBTOTAL = "Subtotal";
    public static final String EXTRA_ORDERID = "OrderID";
    public static final String EXTRA_TABLEID = "TableID";
    public static final String EXTRA_TABLENAME = "TableName";
    public static final String EXTRA_ORDERTYPE = "OrderType";

    private DatabaseHelper mDBHelper;
    private Context mContext;

    private TextView mSubtotalTextView;
    private TextView mPaidTextView;
    private TextView mChangeTextView;

    private double nSubtotal;
    private String sPaid = "";
    private double nChange = 0;

    private boolean bDecimalUsed = false;
    private boolean bDecimalActive = false;

    private long nTableID = 0;
    private long nOrderID = 0;
    private String sTableName = "";
    private String sType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Get Intent
        Intent intent = getIntent();
        sType = intent.getStringExtra(EXTRA_ORDERTYPE);
        nSubtotal = intent.getDoubleExtra(EXTRA_SUBTOTAL, 0);

        if (sType.equals(Order.TYPE_EAT_IN)) {
            sTableName = intent.getStringExtra(EXTRA_TABLENAME);
            nTableID = intent.getLongExtra(EXTRA_TABLEID, 0);
            nOrderID = intent.getLongExtra(EXTRA_ORDERID, 0);
            setTitle(getString(R.string.payment_title) + " " + sTableName);
        } else if (sType.equals(Order.TYPE_TAKEAWAY)){
            setTitle(getString(R.string.payment_title) + " " + sType);
        }

        mDBHelper = new DatabaseHelper(getApplicationContext());
        mContext = this;

        // Setup TextView
        mSubtotalTextView = (TextView) findViewById(R.id.payment_subtotal);
        mPaidTextView = (TextView) findViewById(R.id.payment_paid_amount);
        mChangeTextView = (TextView) findViewById(R.id.payment_change_amount);

        // Set TextView
        mSubtotalTextView.setText(String.format("%.2f", nSubtotal));
        mChangeTextView.setText(String.valueOf(nChange));
        mPaidTextView.setText("0");

        // Get each button
        Button btn_1 = (Button)findViewById(R.id.payment_numberpad_1);
        Button btn_2 = (Button)findViewById(R.id.payment_numberpad_2);
        Button btn_3 = (Button)findViewById(R.id.payment_numberpad_3);
        Button btn_4 = (Button)findViewById(R.id.payment_numberpad_4);
        Button btn_5 = (Button)findViewById(R.id.payment_numberpad_5);
        Button btn_6 = (Button)findViewById(R.id.payment_numberpad_6);
        Button btn_7 = (Button)findViewById(R.id.payment_numberpad_7);
        Button btn_8 = (Button)findViewById(R.id.payment_numberpad_8);
        Button btn_9 = (Button)findViewById(R.id.payment_numberpad_9);
        Button btn_0 = (Button)findViewById(R.id.payment_numberpad_0);
        Button btn_00 = (Button)findViewById(R.id.payment_numberpad_00);
        Button btn_decimal = (Button)findViewById(R.id.payment_numberpad_decimal);
        Button btn_clear = (Button)findViewById(R.id.payment_numberpad_clear);
        Button btn_back = (Button)findViewById(R.id.payment_numberpad_back);
        Button btn_payment = (Button)findViewById(R.id.payment_payment_button);

        // Set onClickListeners for each button
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_00.setOnClickListener(this);
        btn_decimal.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_payment.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.payment_numberpad_1:
                calculate("1");
                break;
            case R.id.payment_numberpad_2:
                calculate("2");
                break;
            case R.id.payment_numberpad_3:
                calculate("3");
                break;
            case R.id.payment_numberpad_4:
                calculate("4");
                break;
            case R.id.payment_numberpad_5:
                calculate("5");
                break;
            case R.id.payment_numberpad_6:
                calculate("6");
                break;
            case R.id.payment_numberpad_7:
                calculate("7");
                break;
            case R.id.payment_numberpad_8:
                calculate("8");
                break;
            case R.id.payment_numberpad_9:
                calculate("9");
                break;
            case R.id.payment_numberpad_0:
                calculate("0");
                break;
            case R.id.payment_numberpad_00:
                calculate("00");
                break;
            case R.id.payment_numberpad_decimal:
                if(!bDecimalUsed){
                    bDecimalActive = true;
                    String newString = sPaid + getString(R.string.decimal);
                    mPaidTextView.setText(newString);
                }
                break;
            case R.id.payment_numberpad_back:
                // If the current value is suppose to be a decimal then remove the decimal
                if (bDecimalActive){
                    bDecimalActive = false;
                    bDecimalUsed = false;
                    mPaidTextView.setText(sPaid);
                    mChangeTextView.setText(String.valueOf(nChange));
                }
                // If the current value is not empty
                else if(!sPaid.equals("")) {
                    // If there are more than one value
                    if(sPaid.length() > 1){
                        // If the value before the current value is a decimal
                        if (sPaid.substring(sPaid.length()-2,sPaid.length() - 1).equals(getString(R.string.decimal))){
                            bDecimalActive = true;
                            bDecimalUsed = false;
                            sPaid = sPaid.substring(0, sPaid.length() - 2);
                            // If sPaid is not empty after removing the decimal
                            if (!sPaid.equals("")){
                                double paid = Double.parseDouble(sPaid);
                                String change = String.format("%.2f" ,(nSubtotal - paid) * -1);
                                nChange = Double.parseDouble(change);
                                String newString = sPaid + getString(R.string.decimal);
                                mPaidTextView.setText(newString);
                                mChangeTextView.setText(String.valueOf(change));

                            }else{
                                sPaid = "";
                                nChange = 0;
                                String newString = sPaid + getString(R.string.decimal);
                                mPaidTextView.setText(newString);
                                mChangeTextView.setText(String.valueOf(nChange));
                            }
                        }else{
                            sPaid = sPaid.substring(0, sPaid.length() - 1);
                            double paid = Double.parseDouble(sPaid);
                            String change = String.format("%.2f" ,(nSubtotal - paid) * -1);
                            nChange = Double.parseDouble(change);

                            mPaidTextView.setText(sPaid);
                            mChangeTextView.setText(String.valueOf(change));
                        }
                    }
                    // If there is only one value
                    else{
                        sPaid = "";
                        nChange = 0;
                        mPaidTextView.setText(sPaid);
                        mChangeTextView.setText(String.valueOf(nChange));
                    }

                }
                break;
            case R.id.payment_numberpad_clear:
                sPaid = "";
                nChange = 0;
                bDecimalUsed = false;
                bDecimalActive = false;

                mPaidTextView.setText(sPaid);
                mChangeTextView.setText(String.valueOf(nChange));
                break;
            case R.id.payment_payment_button:

                // Amount paid must be at least the subtotal
                if(nChange >= 0) {
                    ConfigurationDatabaseHelper mCDBHelper = new ConfigurationDatabaseHelper(getApplicationContext());
                    if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 0){
                        if (sType.equals(Order.TYPE_TAKEAWAY)){
                            // Create a new takeaway order
                            Order currentOrder = new Order(sType, Order.STATUS_PAID, nSubtotal);
                            mDBHelper.AddOrder(currentOrder);
                        }
                        // Assumes that if not takeaway order then it is a table order
                        // May be rewritten if additional order types use this payment activity
                        else{
                            // Setup Order data with status now PAID
                            Order currentOrder = mDBHelper.GetOrder(nOrderID);
                            Order newOrder = new Order(currentOrder.getnOrderID(), currentOrder.getnTableID(),
                                    currentOrder.getsType(), Order.STATUS_PAID, currentOrder.getnTotal());

                            // Setup Table data as refreshed
                            Table newTable = new Table(nTableID, sTableName,
                                    0, -1, 0, Table.STATUS_OPEN);

                            // Update db
                            mDBHelper.UpdateTable(newTable);
                            mDBHelper.UpdateOrder(newOrder);

                        }
                        // Return to MainActivity
                        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        PaymentActivity.this.finish();
                    } else if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 1){
                        new PaymentTask(PaymentActivity.this).execute();
                    }

                }
                break;
            default:
                break;
        }
    }

    // Network Mode Payment
    private class PaymentTask extends AsyncTask<Void, Void, Boolean>{
        private ProgressDialog mDialog;

        PaymentTask(PaymentActivity activity){
            mDialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setTitle("Sending Data");
            mDialog.setMessage("Sending data to server. Please Wait...");
            mDialog.setIndeterminate(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Ab5ctlDAO ab5ctlDAO = new Ab5ctlDAO(mContext);
            PoqapaDAO poqapaDAO = new PoqapaDAO(mContext);

            try{
                if (poqapaDAO.testConnection()){
                    if (sType.equals(Order.TYPE_TAKEAWAY)) {
                        // Create a new takeaway order
                        Poqapa poqapa = new Poqapa();
                        poqapa.setsID(String.valueOf(ab5ctlDAO.getItemNo()));
                        poqapaDAO.insertPoqapa(poqapa);
                        // TODO: Insert OrderItems, create another tmp table?
                    }
                    // Assumes that if not takeaway order then it is a table order
                    // May be rewritten if additional order types use this payment activity
                    else {
                        // Setup Table data as refreshed
                        Table newTable = new Table(nTableID, sTableName,
                                0, -1, 0, Table.STATUS_OPEN);
                        Poqapa poqapa = poqapaDAO.getPoqapa(SaxposConverter.convertToDigits((int) nOrderID, 7));
                        poqapa.setsThisPayStatus("P");
                        poqapa.setsNextTransaction("C");

                        mDBHelper.UpdateTable(newTable);
                        poqapaDAO.updatePoqapa(poqapa);
                    }
                } else{
                    return false;
                }
            return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            mDialog.dismiss();

            if (aVoid){
                // Return to MainActivity
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                PaymentActivity.this.finish();
                Toast.makeText(mContext, "Payment successful!"
                        , Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "Error, there was an issue in updating the db. " +
                                "Please make sure your network is stable and try again!"
                        , Toast.LENGTH_LONG).show();
            }

        }
    }

    private void calculate(String n){
        if(bDecimalActive){
            bDecimalActive = false;
            bDecimalUsed = true;
            sPaid += ".";
        }
        sPaid += n;
        double paid = Double.parseDouble(sPaid);
        String change = String.format("%.2f" ,(nSubtotal - paid) * -1);
        nChange = Double.parseDouble(change);

        mPaidTextView.setText(sPaid);
        mChangeTextView.setText(change);
    }
}

