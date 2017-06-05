package com.ass3.axue2.posapplication.activities.Settings;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Group;
import com.ass3.axue2.posapplication.models.operational.Product;
import com.ass3.axue2.posapplication.views.adapters.SettingsProductRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsProductActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    DatabaseHelper mDBHelper;
    List<Group> mGroups;
    List<Product> mProducts;

    RecyclerView mRV;
    SettingsProductRecyclerViewAdapter mAdapter;

    Spinner mToolbarSpinner;
    Spinner mProductSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Product Settings");

        mContext = this;
        mDBHelper = new DatabaseHelper(this);

        mGroups = new ArrayList<>(mDBHelper.GetAllGroups().values());

        mRV = (RecyclerView) findViewById(R.id.settings_product_rv);

        mProducts = new ArrayList<>(mDBHelper.GetProducts(mGroups.get(0).getnGroupID()).values());
        mAdapter = new SettingsProductRecyclerViewAdapter(this, mProducts);

        mRV.setAdapter(mAdapter);

        mRV.setLayoutManager(new LinearLayoutManager(this));

        Button mConfirmButton = (Button) findViewById(R.id.settings_product_confirm_button);
        mConfirmButton.setOnClickListener(this);

        String[] values = new String[mGroups.size()];
        int count = 0;
        for (Group group: mGroups){
            System.out.println(String.valueOf(mGroups.get(count).getsGroupName() + String.valueOf(mGroups.get(count).getnGroupID())));
            values[count] = group.getsGroupName();
            count++;
        }

        // Setup spinner
        mToolbarSpinner = (Spinner) findViewById(R.id.spinner);
        mToolbarSpinner.setAdapter(new MyAdapter(
                toolbar.getContext(), values
                ));

        mToolbarSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                System.out.println(String.valueOf(position));
                System.out.println(mGroups.get(position).getsGroupName());
                mProducts = new ArrayList<>(mDBHelper.GetProducts(mGroups.get(position).getnGroupID()).values());
                mAdapter = new SettingsProductRecyclerViewAdapter(mContext, mProducts);
                mRV.setAdapter(mAdapter);
                mRV.setLayoutManager(new LinearLayoutManager(mContext));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mProductSpinner = (Spinner) findViewById(R.id.spinner2);
        mProductSpinner.setAdapter(new MyAdapter(
                this, values
        ));


    }
    public void updateText(long productID){

        Product product = mDBHelper.GetProduct(productID);
        TextView id = (TextView) findViewById(R.id.settings_product_id_textview);
        EditText name = (EditText) findViewById(R.id.settings_product_name_editText);
        EditText price = (EditText) findViewById(R.id.settings_product_price_editText);

        mProductSpinner.setSelection(mToolbarSpinner.getSelectedItemPosition());

        id.setText(String.valueOf(productID));
        name.setText(product.getsProductName());
        price.setText(String.valueOf(product.getnPrice()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_product_confirm_button:
                TextView id = (TextView) findViewById(R.id.settings_product_id_textview);
                EditText name = (EditText) findViewById(R.id.settings_product_name_editText);
                EditText price = (EditText) findViewById(R.id.settings_product_price_editText);
                if (!id.getText().toString().equals("")) {
                    long productID = Long.parseLong(id.getText().toString());
                    Product product = mDBHelper.GetProduct(productID);
                    product.setsProductName(name.getText().toString());
                    if (!price.getText().toString().equals("")) {
                        product.setnPrice(Double.parseDouble(price.getText().toString()));
                    }
                    product.setnGroupID(mGroups.get(mProductSpinner.getSelectedItemPosition()).getnGroupID());
                    mDBHelper.UpdateProduct(product);
                }
                break;
        }
    }


    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }
}
