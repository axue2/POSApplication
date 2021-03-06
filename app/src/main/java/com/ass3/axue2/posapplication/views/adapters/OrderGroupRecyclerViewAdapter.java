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
import com.ass3.axue2.posapplication.models.operational.Product;

import java.util.List;

/**
 * Created by anthony on 4/21/2017.
 *
 */

public class OrderGroupRecyclerViewAdapter extends RecyclerView.Adapter<OrderGroupRecyclerViewAdapter.MyViewHolder> {

    private List<Product> mProducts;

    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private CardView mCardView;
        private TextView mNameTextView;
        private TextView mPriceTextView;


        public MyViewHolder(View v){
            super(v);
            mView = v;
            mCardView = (CardView) v.findViewById(R.id.order_product_list_cv);
            mNameTextView = (TextView) v.findViewById(R.id.order_add_product_name);
            mPriceTextView = (TextView) v.findViewById(R.id.order_add_product_price);
        }
    }

    public OrderGroupRecyclerViewAdapter(Context context, List<Product> productList) {
        mContext = context;
        mProducts = productList;
    }

    @Override
    public OrderGroupRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){


        // Select CardView to be used
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_order_add_product_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        final Product currentProduct = mProducts.get(position);
        // Set values in the CardView
        setTextViewValues(holder, currentProduct);

        // OnClickListener for CardView Press
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(mContext instanceof OrderActivity){
                    Log.d("AddOrderItem started", currentProduct.getsProductName());
                    ((OrderActivity) mContext).AddOrderItem(((OrderActivity) mContext).ConvertProductToOrderItem(currentProduct));
                    Log.d("AddOrderItem finished", "");
                }

            }
        });

    }

    @Override
    public int getItemCount(){return mProducts.size();}

    private void setTextViewValues(MyViewHolder holder, Product product){

        holder.mNameTextView.setText(product.getsProductName());
        String price = "$" + String.format("%.2f", product.getnPrice());
        holder.mPriceTextView.setText(price);

    }


}
