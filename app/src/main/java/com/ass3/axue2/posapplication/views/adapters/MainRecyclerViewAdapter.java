package com.ass3.axue2.posapplication.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ass3.axue2.posapplication.R;

import java.util.List;

/**
 * Created by anthony on 4/21/2017.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {

    private List<String> mValues;

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        public View mView;
        public CardView mCardView;
        public TextView mNameTextView;


        public MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.main_table_list_cv);
            mNameTextView = (TextView) v.findViewById(R.id.main_table_list_cv_text);
        }


    }

    public MainRecyclerViewAdapter(Context context, List<String> tableList) {
        mValues = tableList;
    }

    @Override
    public MainRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_main_table_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.mNameTextView.setText(mValues.get(position));
    }

    @Override
    public int getItemCount(){return mValues.size();}
}
