package com.ass3.axue2.posapplication.activities.Settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ass3.axue2.posapplication.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        // Setup buttons
        Button general = (Button) findViewById(R.id.settings_restaurant_button);
        general.setOnClickListener(this);
        Button tables = (Button) findViewById(R.id.settings_tables_button);
        tables.setOnClickListener(this);
        Button groups = (Button) findViewById(R.id.settings_groups_button);
        groups.setOnClickListener(this);
        Button products = (Button) findViewById(R.id.settings_products_button);
        products.setOnClickListener(this);
        Button drivers = (Button) findViewById(R.id.settings_drivers_button);
        drivers.setOnClickListener(this);
        Button database = (Button) findViewById(R.id.settings_database_button);
        database.setOnClickListener(this);
        Button networks = (Button) findViewById(R.id.settings_network_button);
        networks.setOnClickListener(this);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.settings_restaurant_button:
                Intent generalIntent = new Intent(this, SettingsRestaurantActivity.class);
                startActivity(generalIntent);
                break;
            case R.id.settings_tables_button:
                Intent tableIntent = new Intent(this, SettingsTableActivity.class);
                startActivity(tableIntent);
                break;
            case R.id.settings_groups_button:
                Intent settingIntent = new Intent(this, SettingsGroupActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.settings_products_button:
                Intent productIntent = new Intent(this, SettingsProductActivity.class);
                startActivity(productIntent);
                break;
            case R.id.settings_drivers_button:
                Intent driverIntent = new Intent(this, SettingsDriverActivity.class);
                startActivity(driverIntent);
                break;
            case R.id.settings_database_button:
                Intent databaseIntent = new Intent(this, SettingsDatabaseActivity.class);
                startActivity(databaseIntent);
                break;
            case R.id.settings_network_button:
                Intent networkIntent = new Intent(this, SettingsNetworkActivity.class);
                startActivity(networkIntent);
                break;
        }
    }
}
