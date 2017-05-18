package com.ass3.axue2.posapplication.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.activities.OrderActivity;
import com.ass3.axue2.posapplication.models.Customer;
import com.ass3.axue2.posapplication.models.DatabaseHelper;
import com.ass3.axue2.posapplication.models.Delivery;
import com.ass3.axue2.posapplication.models.Order;
import com.ass3.axue2.posapplication.models.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthony on 4/21/2017.
 *
 */

public class DeliveryManagerRecyclerViewAdapter extends RecyclerView.Adapter<DeliveryManagerRecyclerViewAdapter.MyViewHolder> {

    private List<Delivery> mDeliveries;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private CardView mCardView;
        private TextView mDeliveryIDTextView;
        private TextView mCustomerNameTextView;
        private TextView mStatusTextView;
        private TextView mInvoiceTextView;

        public MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.delivery_manager_list_cv);
            mDeliveryIDTextView = (TextView) v.findViewById(R.id.delivery_manager_list_cv_id_text);
            mCustomerNameTextView = (TextView) v.findViewById(R.id.delivery_manager_list_cv_customer_text);
            mStatusTextView = (TextView) v.findViewById(R.id.delivery_manager_list_cv_status_text);
            mInvoiceTextView = (TextView) v.findViewById(R.id.delivery_manager_list_cv_total_text);
        }


    }

    public DeliveryManagerRecyclerViewAdapter(Context context, List<Delivery> deliveries) {
        mDeliveries = deliveries;
        mContext = context;
    }

    @Override
    public DeliveryManagerRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Select CardView to be used
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_delivery_manager_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        final Delivery currentDelivery = mDeliveries.get(position);

        // Set values in the CardView
        setTextViewValues(holder, currentDelivery);

        // OnClickListener for CardView Press
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            //TODO: Add expandable recyclerview

            }
        });

    }

    @Override
    public int getItemCount(){return mDeliveries.size();}

    private void setTextViewValues(MyViewHolder holder, Delivery delivery){
        DatabaseHelper db = new DatabaseHelper(mContext);

        if(delivery.getnCustomerID() > 0) {
            Customer customer = db.GetCustomer(delivery.getnCustomerID());
            holder.mCustomerNameTextView.setText(customer.getsFirstName() + " " + customer.getsLastName());
        }

        holder.mDeliveryIDTextView.setText(String.valueOf(delivery.getnDeliveryID()));
        holder.mStatusTextView.setText(delivery.getsStatus());
        holder.mInvoiceTextView.setText("$" + String.valueOf(delivery.getnDeliveryFee()));

    }
}
