package com.ass3.axue2.posapplication.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.activities.DeliveryManagerActivity;
import com.ass3.axue2.posapplication.models.operational.Customer;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Delivery;

import java.util.ArrayList;

/**
 * Created by anthony on 4/21/2017.
 *
 */

public class DeliveryManagerRecyclerViewAdapter extends RecyclerView.Adapter<DeliveryManagerRecyclerViewAdapter.MyViewHolder> {

    public ArrayList<Delivery> mDeliveries;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private CardView mCardView;
        private TextView mDeliveryIDTextView;
        private TextView mCustomerNameTextView;
        private TextView mStatusTextView;
        private TextView mInvoiceTextView;
        private LinearLayout mLinearLayout;

        MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.delivery_manager_list_cv);
            mDeliveryIDTextView = (TextView) v.findViewById(R.id.delivery_manager_list_cv_id_text);
            mCustomerNameTextView = (TextView) v.findViewById(R.id.delivery_manager_list_cv_customer_text);
            mStatusTextView = (TextView) v.findViewById(R.id.delivery_manager_list_cv_status_text);
            mInvoiceTextView = (TextView) v.findViewById(R.id.delivery_manager_list_cv_total_text);
            mLinearLayout = (LinearLayout) v.findViewById(R.id.delivery_manager_ll);
        }
    }

    public DeliveryManagerRecyclerViewAdapter(Context context, ArrayList<Delivery> deliveries) {
        this.mDeliveries = deliveries;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_delivery_manager_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        final Delivery currentDelivery = mDeliveries.get(position);

        // Set values in the CardView
        setTextViewValues(holder, currentDelivery);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof DeliveryManagerActivity) {
                    ArrayList<Delivery> selectedDeliveries = ((DeliveryManagerActivity) mContext).getmSelectedDeliveries();
                    System.out.println("Selected Deliveries: " + selectedDeliveries.size());
                    if (selectedDeliveries.contains(currentDelivery)) {
                        ((DeliveryManagerActivity) mContext).removemSelectedDelivery(currentDelivery);
                    } else {
                        ((DeliveryManagerActivity) mContext).addmSelectedDelivery(currentDelivery);
                    }
                    System.out.println("Selected Deliveries Now: " + selectedDeliveries.size());
                    notifyDataSetChanged();
                }
            }
        });

        // Set background colour depending on whether or not CardView has been clicked
        if (mContext instanceof DeliveryManagerActivity) {
            ArrayList<Delivery> selectedDeliveries = ((DeliveryManagerActivity) mContext).getmSelectedDeliveries();
            if (selectedDeliveries.contains(mDeliveries.get(position))){
                holder.mCardView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.deliverySelected));
            } else{
                holder.mCardView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.WHITE));
            }
        }
    }

    @Override
    public int getItemCount(){return mDeliveries.size();}

    private void setTextViewValues(MyViewHolder holder, Delivery delivery){
        DatabaseHelper db = new DatabaseHelper(mContext);

        if(delivery.getnCustomerID() > 0) {
            Customer customer = db.GetCustomer(delivery.getnCustomerID());
            holder.mCustomerNameTextView.setText(customer.getsFirstName() + " " + customer.getsLastName());
            holder.mStatusTextView.setText(String.valueOf(customer.getnPostCode()));
        }

        holder.mDeliveryIDTextView.setText(String.valueOf(delivery.getnDeliveryID()));
        //TODO: Set Delivery fee for total fee
        String str = "$" + String.valueOf(delivery.getnDeliveryFee());
        holder.mInvoiceTextView.setText(str);
    }

    public void removeItem(Delivery delivery){
        int pos = mDeliveries.indexOf(delivery);
        mDeliveries.remove(delivery);
        notifyItemRemoved(pos);
    }
}
