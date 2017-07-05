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
import com.ass3.axue2.posapplication.activities.Settings.SettingsDriverActivity;
import com.ass3.axue2.posapplication.models.operational.Driver;

import java.util.List;

public class SettingsDriverRecyclerViewAdapter extends RecyclerView.Adapter<SettingsDriverRecyclerViewAdapter.MyViewHolder> {

    private List<Driver> mDrivers;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private CardView mCardView;
        private TextView mName;
        private Button mDeleteButton;

        public MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.settings_cv);
            mName = (TextView) v.findViewById(R.id.settings_cv_name);
            mDeleteButton = (Button) v.findViewById(R.id.settings_cv_button);
        }
    }

    public SettingsDriverRecyclerViewAdapter(Context context, List<Driver> drivers) {
        mDrivers = drivers;
        mContext = context;
    }

    @Override
    public SettingsDriverRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Select CardView to be used
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_settings, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final Driver driver = mDrivers.get(position);
        setTextViewValues(holder, driver);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof SettingsDriverActivity) {
                    ((SettingsDriverActivity) mContext).updateText(mDrivers.get(position).getnDriverID());
                }
            }
        });
    }

    @Override
    public int getItemCount(){return mDrivers.size();}

    private void setTextViewValues(MyViewHolder holder, Driver driver){
        holder.mName.setText(driver.getnFirstName() + " " + driver.getnLastName());

    }

    public void updateItem(Driver driver){
        for (int i = 0; i < mDrivers.size(); i++){
            if (mDrivers.get(i).getnDriverID() == driver.getnDriverID()){
                mDrivers.set(i,driver);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void addItem(Driver driver){
        mDrivers.add(driver);
        notifyItemInserted(mDrivers.size() - 1);
    }
}