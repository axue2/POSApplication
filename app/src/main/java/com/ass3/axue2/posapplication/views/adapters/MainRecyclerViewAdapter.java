package com.ass3.axue2.posapplication.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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
import com.ass3.axue2.posapplication.models.Table;

import java.util.List;

/**
 * Created by anthony on 4/21/2017.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {

    private List<Table> mTables;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public View mView;
        public CardView mCardView;
        public TextView mNameTextView;
        public TextView mStatusTextView;
        public TextView mGuestsTextView;
        public TextView mInvoiceTextView;
        public Button mPaymentButton;
        public int nID;


        public MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.main_table_list_cv);
            mNameTextView = (TextView) v.findViewById(R.id.main_table_list_cv_name_text);
            mStatusTextView = (TextView) v.findViewById(R.id.main_table_list_cv_status_text);
            mGuestsTextView = (TextView) v.findViewById(R.id.main_table_list_cv_guests_text);
            mInvoiceTextView = (TextView) v.findViewById(R.id.main_table_list_cv_total_text);
            mPaymentButton = (Button) v.findViewById(R.id.main_table_list_cv_button);
        }


    }

    public MainRecyclerViewAdapter(Context context, List<Table> tableList) {
        mTables = tableList;
    }

    @Override
    public MainRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Select CardView to be used
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_main_table_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        final Table currentTable = mTables.get(position);
        // Set values in the CardView
        setTextViewValues(holder, currentTable);

        // OnClickListener for Payment Button Press
        holder.mPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Payment Button Press");
            }
        });


        // OnClickListener for CardView Press
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = v.getContext();
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra(OrderActivity.EXTRA_TABLE, currentTable.getsTableName());
                intent.putExtra(OrderActivity.EXTRA_ID, currentTable.getnTableID());
                context.startActivity(intent);
                Log.d("Button", "Main CardView Pressed");
            }
        });

    }

    @Override
    public int getItemCount(){return mTables.size();}

    private void setTextViewValues(MyViewHolder holder, Table currentTable){

        // Set Table values on TextViews
        holder.mNameTextView.setText(currentTable.getsTableName());
        // Tables that are open should not show addition details
        if (!currentTable.getsStatus().equals("Open")) {
            // If table is in-use then payment button should appear
            if (currentTable.getsStatus().equals("In-use"))
                holder.mPaymentButton.setVisibility(View.VISIBLE);
            else
                holder.mPaymentButton.setVisibility(View.INVISIBLE);
            // Set TextViews to visible
            holder.mStatusTextView.setVisibility(View.VISIBLE);
            holder.mGuestsTextView.setVisibility(View.VISIBLE);
            holder.mInvoiceTextView.setVisibility(View.VISIBLE);

            // Set values for TextViews
            holder.mStatusTextView.setText(currentTable.getsStatus());
            holder.mGuestsTextView.setText(String.valueOf(currentTable.getnGuests()));
            String invString = "$" + String.valueOf(currentTable.getnInvSum());
            holder.mInvoiceTextView.setText(invString);
            // Set CardView color
            holder.mCardView.setCardBackgroundColor(Color.parseColor("#ffea00"));
        } else{
            // Set TextViews to invisible
            holder.mStatusTextView.setVisibility(View.INVISIBLE);
            holder.mGuestsTextView.setVisibility(View.INVISIBLE);
            holder.mInvoiceTextView.setVisibility(View.INVISIBLE);
            holder.mPaymentButton.setVisibility(View.INVISIBLE);
            // TODO: Figure out how to dymanically change colors
            holder.mCardView.setCardBackgroundColor(Color.parseColor("#80deea"));
        }
    }
}
