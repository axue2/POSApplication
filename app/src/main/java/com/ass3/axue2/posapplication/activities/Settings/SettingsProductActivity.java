package com.ass3.axue2.posapplication.activities.Settings;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
import android.widget.ImageButton;
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
    long mGroupID;

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

        // Setup Products & Groups
        mGroups = new ArrayList<>(mDBHelper.GetAllGroups().values());
        mProducts = new ArrayList<>(mDBHelper.GetProducts(mGroups.get(0).getnGroupID()).values());

        // Setup RecyclerView
        mRV = (RecyclerView) findViewById(R.id.settings_product_rv);
        mAdapter = new SettingsProductRecyclerViewAdapter(this, mProducts);
        mRV.setAdapter(mAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRV.setLayoutManager(llm);

        // Setup Divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                llm.getOrientation());
        mRV.addItemDecoration(itemDecoration);

        // Setup Buttons
        Button mConfirmButton = (Button) findViewById(R.id.settings_product_confirm_button);
        mConfirmButton.setOnClickListener(this);
        ImageButton mAddButton = (ImageButton) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(this);

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
                mGroupID = mGroups.get(position).getnGroupID();
                mProducts = new ArrayList<>(mDBHelper.GetProducts(mGroupID).values());
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
        TextView id = (TextView) findViewById(R.id.settings_product_id_textview);
        EditText name = (EditText) findViewById(R.id.settings_product_name_editText);
        EditText price = (EditText) findViewById(R.id.settings_product_price_editText);
        switch (v.getId()){
            case R.id.settings_product_confirm_button:
                // If Product ID is not empty
                if (!id.getText().toString().equals("")) {
                    long productID = Long.parseLong(id.getText().toString());
                    // Get product
                    Product product = mDBHelper.GetProduct(productID);
                    // Update Values
                    product.setsProductName(name.getText().toString());
                    if (!price.getText().toString().equals("")) {
                        product.setnPrice(Double.parseDouble(price.getText().toString()));
                    }
                    long groupID = mGroups.get(mProductSpinner.getSelectedItemPosition()).getnGroupID();
                    // If group id has changed then remove it from RV
                    if (mGroupID != groupID){
                        product.setnGroupID(groupID);
                        mAdapter.removeItem(product);
                    }
                    // Otherwise update the product in RV
                    else{
                        mAdapter.updateItem(product);
                    }
                    mDBHelper.UpdateProduct(product);

                    Snackbar.make(v, "Product Updated",
                            Snackbar.LENGTH_SHORT).show();
                }
                // Otherwise create new product
                else {
                    long groupID = mGroups.get(mProductSpinner.getSelectedItemPosition()).getnGroupID();
                    double productPrice = 0;

                    // If there is a price set
                    if (!price.getText().toString().equals("")) {
                        productPrice = Double.parseDouble(price.getText().toString());
                    }

                    Product product = new Product(0, groupID, name.getText().toString(),
                            productPrice);
                    long productID = mDBHelper.AddProduct(product);
                    product.setnProductID(productID);
                    // If product in current group displayed then add it to RV
                    if (groupID == mGroupID){
                        mAdapter.addItem(product);
                    }

                    Snackbar.make(v, "Product Added",
                            Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_button:
                // Empty All Fields
                id.setText("");
                name.setText("");
                price.setText("");
                break;
        }
    }


    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        MyAdapter(Context context, String[] objects) {
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
