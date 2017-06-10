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
import com.ass3.axue2.posapplication.activities.Settings.SettingsGroupActivity;
import com.ass3.axue2.posapplication.models.operational.Driver;
import com.ass3.axue2.posapplication.models.operational.Group;

import java.util.List;

/**
 * Created by anthony on 4/21/2017.
 *
 */

public class SettingsGroupRecyclerViewAdapter extends RecyclerView.Adapter<SettingsGroupRecyclerViewAdapter.MyViewHolder> {

    private List<Group> mGroups;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private CardView mCardView;
        private TextView mGroupName;
        private Button mDeleteButton;

        public MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.settings_cv);
            mGroupName = (TextView) v.findViewById(R.id.settings_cv_name);
            mDeleteButton = (Button) v.findViewById(R.id.settings_cv_button);
        }


    }

    public SettingsGroupRecyclerViewAdapter(Context context, List<Group> groups) {
        mGroups = groups;
        mContext = context;
    }

    @Override
    public SettingsGroupRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Select CardView to be used
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_settings, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final Group group = mGroups.get(position);
        setTextViewValues(holder, group);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Cardview Clicked: " + String.valueOf(position));
                if (mContext instanceof  SettingsGroupActivity) {
                    ((SettingsGroupActivity) mContext).updateText(mGroups.get(position).getnGroupID());
                }
            }
        });
    }

    @Override
    public int getItemCount(){return mGroups.size();}

    private void setTextViewValues(MyViewHolder holder, Group group){
        holder.mGroupName.setText(group.getsGroupName());

    }

    public void updateItem(Group group){
        for (int i = 0; i < mGroups.size(); i++){
            if (mGroups.get(i).getnGroupID() == group.getnGroupID()){
                mGroups.set(i,group);
                notifyItemChanged(i);
            }
        }
    }
}
