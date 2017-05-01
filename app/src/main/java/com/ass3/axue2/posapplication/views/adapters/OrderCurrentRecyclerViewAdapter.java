package com.ass3.axue2.posapplication.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.activities.OrderActivity;
import com.ass3.axue2.posapplication.models.OrderItem;
import com.ass3.axue2.posapplication.models.Product;

import java.util.List;

/**
 * Created by anthony on 4/21/2017.
 */

public class OrderCurrentRecyclerViewAdapter extends RecyclerView.Adapter<OrderCurrentRecyclerViewAdapter.MyViewHolder> {

    private List<OrderItem> mOrderItems;

    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public View mView;
        public CardView mCardView;
        public TextView mNameTextView;
        public TextView mPriceTextView;



        public MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.order_current_list_cv);
            mNameTextView = (TextView) v.findViewById(R.id.order_current_product_name);
            mPriceTextView = (TextView) v.findViewById(R.id.order_current_product_price);
        }
    }

    public OrderCurrentRecyclerViewAdapter(Context context, List<OrderItem> orderItems) {
        mContext = context;
        mOrderItems = orderItems;
    }

    @Override
    public OrderCurrentRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Select CardView to be used
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_order_current_product_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        final OrderItem orderItem = mOrderItems.get(position);
        // Set values in the CardView
        setTextViewValues(holder, orderItem);

        // OnClickListener for CardView Press
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


            }
        });

    }

    @Override
    public int getItemCount(){return mOrderItems.size();}

    private void setTextViewValues(MyViewHolder holder, OrderItem item){

        holder.mNameTextView.setText(item.getsProductName());
        String price = "$" + String.valueOf(item.getnPrice());
        holder.mPriceTextView.setText(price);

    }

    public void update(){
        if(mContext instanceof OrderActivity){
            mOrderItems.clear();
            mOrderItems.addAll(((OrderActivity) mContext).getmOrderItems());
            Log.d("OrderItems updated", String.valueOf(mOrderItems.size()));
            //mOrderItems = ((OrderActivity) mContext).getmOrderItems();
            notifyDataSetChanged();
        }
    }

}
