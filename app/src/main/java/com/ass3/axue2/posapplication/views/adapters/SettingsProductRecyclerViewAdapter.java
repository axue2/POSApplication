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
import com.ass3.axue2.posapplication.activities.Settings.SettingsProductActivity;
import com.ass3.axue2.posapplication.models.operational.Product;

import java.util.List;

/**
 * Created by anthony on 4/21/2017.
 *
 */

public class SettingsProductRecyclerViewAdapter extends RecyclerView.Adapter<SettingsProductRecyclerViewAdapter.MyViewHolder> {

    private List<Product> mProducts;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private CardView mCardView;
        private TextView mProductName;
        private Button mDeleteButton;

        public MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.settings_cv);
            mProductName = (TextView) v.findViewById(R.id.settings_cv_name);
            mDeleteButton = (Button) v.findViewById(R.id.settings_cv_button);
        }


    }

    public SettingsProductRecyclerViewAdapter(Context context, List<Product> products) {
        mProducts = products;
        mContext = context;
    }

    @Override
    public SettingsProductRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Select CardView to be used
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_settings, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final Product product = mProducts.get(position);
        setTextViewValues(holder, product);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Cardview Clicked: " + String.valueOf(position));
                if (mContext instanceof SettingsProductActivity) {
                    ((SettingsProductActivity) mContext).updateText(mProducts.get(position).getnProductID());
                }
            }
        });
    }

    @Override
    public int getItemCount(){return mProducts.size();}

    private void setTextViewValues(MyViewHolder holder, Product product){
        holder.mProductName.setText(product.getsProductName());

    }
}
