package com.ass3.axue2.posapplication.activities;

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
        Button general = (Button) findViewById(R.id.settings_general_button);
        general.setOnClickListener(this);
        Button tables = (Button) findViewById(R.id.settings_tables_button);
        tables.setOnClickListener(this);
        Button products = (Button) findViewById(R.id.settings_products_button);
        products.setOnClickListener(this);
        Button networks = (Button) findViewById(R.id.settings_network_button);
        networks.setOnClickListener(this);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.settings_general_button:
                System.out.println("General Button");
                break;
            case R.id.settings_tables_button:
                System.out.println("Tables Button");
                break;
            case R.id.settings_products_button:
                System.out.println("Products Button");
                break;
            case R.id.settings_network_button:
                System.out.println("Network Button");
                Intent intent = new Intent(this, SettingsNetworkActivity.class);
                startActivity(intent);
                break;
        }
    }
}
