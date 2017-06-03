package com.ass3.axue2.posapplication.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.activities.Settings.SettingsTableActivity;
import com.ass3.axue2.posapplication.models.operational.Table;


import java.util.List;

/**
 * Created by anthony on 4/21/2017.
 *
 */

public class SettingsTableRecyclerViewAdapter extends RecyclerView.Adapter<SettingsTableRecyclerViewAdapter.MyViewHolder> {

    private List<Table> mTables;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private CardView mCardView;
        private TextView mTableName;
        private Button mDeleteButton;


        public MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.settings_cv);
            mTableName = (TextView) v.findViewById(R.id.settings_cv_name);
            mDeleteButton = (Button) v.findViewById(R.id.settings_cv_button);
        }


    }

    public SettingsTableRecyclerViewAdapter(Context context, List<Table> tables) {
        mTables = tables;
        mContext = context;
    }

    @Override
    public SettingsTableRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Select CardView to be used
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_settings, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final Table table = mTables.get(position);
        setTextViewValues(holder, table);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Cardview Clicked: " + String.valueOf(position));
                if (mContext instanceof SettingsTableActivity) {
                    ((SettingsTableActivity) mContext).updateText(mTables.get(position).getnTableID());
                }
            }
        });
    }

    @Override
    public int getItemCount(){return mTables.size();}

    private void setTextViewValues(MyViewHolder holder, Table table){
        holder.mTableName.setText(table.getsTableName());

    }
}
