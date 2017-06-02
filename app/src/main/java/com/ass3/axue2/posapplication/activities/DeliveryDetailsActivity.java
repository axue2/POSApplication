package com.ass3.axue2.posapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.operational.Order;

public class DeliveryDetailsActivity extends AppCompatActivity {

    private Button mConfirmButton;
    private EditText mCustomerName;
    private EditText mCustomerAddressLine1;
    private EditText mCustomerAddressLine2;
    private EditText mCustomerAddressLine3;
    private EditText mCustomerPostCode;
    private EditText mCustomerPhone;
    private EditText mCustomerDeliveryFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        mConfirmButton = (Button) findViewById(R.id.delivery_details_confirm_button);
        mCustomerName = (EditText) findViewById(R.id.delivery_details_name_edit);
        mCustomerAddressLine1 = (EditText) findViewById(R.id.delivery_details_address_1_edit);
        mCustomerAddressLine2 = (EditText) findViewById(R.id.delivery_details_address_2_edit);
        mCustomerAddressLine3 = (EditText) findViewById(R.id.delivery_details_address_3_edit);
        mCustomerPostCode = (EditText) findViewById(R.id.delivery_details_postcode_edit);
        mCustomerPhone = (EditText) findViewById(R.id.delivery_details_phone_edit);
        mCustomerDeliveryFee = (EditText) findViewById(R.id.delivery_details_fee_edit);


        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Check if address is valid
                Context context = v.getContext();

                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra(OrderActivity.EXTRA_ORDERTYPE, Order.TYPE_DELIVERY);
                intent.putExtra(OrderActivity.EXTRA_TABLENAME, "Delivery");
                long i = 0;
                intent.putExtra(OrderActivity.EXTRA_TABLEID, i);
                intent.putExtra(OrderActivity.EXTRA_ORDERID, i);

                intent.putExtra(OrderActivity.EXTRA_CUSTOMERNAME, mCustomerName.getText().toString());
                intent.putExtra(OrderActivity.EXTRA_AL1, mCustomerAddressLine1.getText().toString());
                intent.putExtra(OrderActivity.EXTRA_AL2, mCustomerAddressLine2.getText().toString());
                intent.putExtra(OrderActivity.EXTRA_AL3, mCustomerAddressLine3.getText().toString());
                if (!mCustomerPostCode.getText().toString().equals(""))
                    intent.putExtra(OrderActivity.EXTRA_POSTCODE, Integer.parseInt(mCustomerPostCode.getText().toString()));
                else
                    intent.putExtra(OrderActivity.EXTRA_POSTCODE, 0);
                if (!mCustomerPhone.getText().toString().equals(""))
                    intent.putExtra(OrderActivity.EXTRA_PHONE, Integer.parseInt(mCustomerPhone.getText().toString()));
                else
                    intent.putExtra(OrderActivity.EXTRA_PHONE, 0);
                if (!mCustomerDeliveryFee.getText().toString().equals(""))
                    intent.putExtra(OrderActivity.EXTRA_DELIVERYFEE,Double.parseDouble(mCustomerDeliveryFee.getText().toString()));
                else
                    intent.putExtra(OrderActivity.EXTRA_DELIVERYFEE, 0);


                intent.putExtra(OrderActivity.EXTRA_FROM, "DeliveryDetailsActivity");

                context.startActivity(intent);
            }
        });
    }
}
