package com.ass3.axue2.posapplication.activities.Settings;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Restaurant;

public class SettingsRestaurantActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mRestaurantName;
    private EditText mAddressLine1;
    private EditText mAddressLine2;
    private EditText mAddressLine3;
    private EditText mPostCode;
    private EditText mPhone;

    private DatabaseHelper mDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_restaurant);
        setTitle("Restaurant Settings");

        mDBHelper = new DatabaseHelper(this);

        // Setup EditTexts
        mRestaurantName = (EditText) findViewById(R.id.settings_restaurant_name_editText);
        mAddressLine1 = (EditText) findViewById(R.id.settings_restaurant_al1);
        mAddressLine2 = (EditText) findViewById(R.id.settings_restaurant_al2);
        mAddressLine3 = (EditText) findViewById(R.id.settings_restaurant_al3);
        mPostCode = (EditText) findViewById(R.id.settings_restaurant_postcode);
        mPhone = (EditText) findViewById(R.id.settings_restaurant_phone);

        updateText();

        // Setup Button
        Button mConfirmButton = (Button) findViewById(R.id.settings_restaurant_confirm_button);
        mConfirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_restaurant_confirm_button:
                Restaurant restaurant = mDBHelper.GetRestaurant(1);
                restaurant.setsRestaurantName(mRestaurantName.getText().toString());
                restaurant.setsAddressLine1(mAddressLine1.getText().toString());
                restaurant.setsAddressLine2(mAddressLine2.getText().toString());
                restaurant.setsAddressLine3(mAddressLine3.getText().toString());
                restaurant.setnPostCode(Integer.parseInt(mPostCode.getText().toString()));
                restaurant.setsPhone(mPhone.getText().toString());
                mDBHelper.UpdateRestaurant(restaurant);
                Snackbar.make(v, "Restaurant Settings Updated",
                        Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    private void updateText(){
        Restaurant restaurant = mDBHelper.GetRestaurant(1);
        mRestaurantName.setText(restaurant.getsRestaurantName());
        mAddressLine1.setText(restaurant.getsAddressLine1());
        mAddressLine2.setText(restaurant.getsAddressLine2());
        mAddressLine3.setText(restaurant.getsAddressLine3());
        mPostCode.setText(String.valueOf(restaurant.getnPostCode()));
        mPhone.setText(restaurant.getsPhone());
    }
}
