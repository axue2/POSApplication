package com.ass3.axue2.posapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.Order;

public class DeliveryDetailsActivity extends AppCompatActivity {

    private Button mConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        mConfirmButton = (Button) findViewById(R.id.delivery_details_confirm_button);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra(OrderActivity.EXTRA_ORDERTYPE, Order.TYPE_EAT_IN);
                intent.putExtra(OrderActivity.EXTRA_TABLENAME, "Delivery");
                intent.putExtra(OrderActivity.EXTRA_TABLEGUESTS, 0);
                intent.putExtra(OrderActivity.EXTRA_TABLEID, 0);
                intent.putExtra(OrderActivity.EXTRA_ORDERID, 0);

                context.startActivity(intent);
            }
        });
    }
}
