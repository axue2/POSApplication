package com.ass3.axue2.posapplication.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.activities.OrderActivity;
import com.ass3.axue2.posapplication.models.OrderItem;

import java.util.List;

/**
 * Created by anthony on 4/21/2017.
 *
 */

public class OrderCurrentRecyclerViewAdapter extends RecyclerView.Adapter<OrderCurrentRecyclerViewAdapter.MyViewHolder> {

    private List<OrderItem> mOrderItems;

    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private CardView mCardView;
        private TextView mNameTextView;
        private TextView mPriceTextView;
        private TextView mQuantityTextView;
        private TextView mSubtotalTextView;
        private Button mMinusButton;
        private Button mPlusButton;



        public MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.order_current_list_cv);
            mNameTextView = (TextView) v.findViewById(R.id.order_current_product_name);
            mPriceTextView = (TextView) v.findViewById(R.id.order_current_product_price);
            mQuantityTextView = (TextView) v.findViewById(R.id.order_current_product_quantity);
            mSubtotalTextView = (TextView) v.findViewById(R.id.order_current_product_subtotal);
            mMinusButton = (Button) v.findViewById(R.id.order_current_minus_button);
            mPlusButton = (Button) v.findViewById(R.id.order_current_plus_button);
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
    public void onBindViewHolder(final MyViewHolder holder, int position){
        final OrderItem orderItem = mOrderItems.get(position);
        // Set values in the CardView
        setTextViewValues(holder, orderItem);

        holder.mMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderItem.getnQuantity() > 0) {
                    if (mContext instanceof OrderActivity) {
                        ((OrderActivity) mContext).RemoveOrderItem(orderItem);
                    }
                }
            }
        });

        holder.mPlusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mContext instanceof OrderActivity){
                    ((OrderActivity) mContext).AddOrderItem(orderItem);
                }
            }
        });

    }

    @Override
    public int getItemCount(){return mOrderItems.size();}

    private void setTextViewValues(MyViewHolder holder, OrderItem item){

        holder.mNameTextView.setText(item.getsProductName());
        String price = "$" + String.valueOf(item.getnPrice());
        holder.mPriceTextView.setText(price);
        String quantity = "x" + item.getnQuantity();
        holder.mQuantityTextView.setText(quantity);
        String subtotal = "$" + String.valueOf(item.getnQuantity() * item.getnPrice());
        holder.mSubtotalTextView.setText(subtotal);

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
