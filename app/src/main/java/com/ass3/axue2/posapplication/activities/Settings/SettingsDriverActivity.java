package com.ass3.axue2.posapplication.activities.Settings;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Driver;
import com.ass3.axue2.posapplication.views.adapters.SettingsDriverRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsDriverActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    DatabaseHelper mDBHelper;
    List<Driver> mDrivers;
    SettingsDriverRecyclerViewAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_driver);
        setTitle("Driver Settings");

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;
        mDBHelper = new DatabaseHelper(this);

        mDrivers = new ArrayList<>(mDBHelper.GetAllDrivers().values());

        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) findViewById(R.id.settings_driver_rv);
        mAdapter = new SettingsDriverRecyclerViewAdapter(this, mDrivers);
        rv.setAdapter(mAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        // Setup Divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                llm.getOrientation());
        rv.addItemDecoration(itemDecoration);

        // Setup Buttons
        Button mConfirmButton = (Button) findViewById(R.id.settings_driver_confirm_button);
        mConfirmButton.setOnClickListener(this);
        ImageButton mAddButton = (ImageButton) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(this);
    }

    public void updateText(long driverID){
        Driver driver = mDBHelper.GetDriver(driverID);
        TextView id = (TextView) findViewById(R.id.settings_driver_id_textview);
        EditText name = (EditText) findViewById(R.id.settings_driver_first_name_editText);
        EditText surname = (EditText) findViewById(R.id.settings_driver_last_name_editText);

        id.setText(String.valueOf(driverID));
        name.setText(driver.getnFirstName());
        surname.setText(driver.getnLastName());
    }

    @Override
    public void onClick(View v) {
        TextView id = (TextView) findViewById(R.id.settings_driver_id_textview);
        EditText name = (EditText) findViewById(R.id.settings_driver_first_name_editText);
        EditText surname = (EditText) findViewById(R.id.settings_driver_last_name_editText);
        switch (v.getId()){
            case R.id.settings_driver_confirm_button:
                // If name has been filled
                if (!name.getText().toString().equals("")) {
                    // If driverID has been set then update driver
                    if (!id.getText().toString().equals("")){
                        long driverID = Long.parseLong(id.getText().toString());
                        Driver driver = mDBHelper.GetDriver(driverID);
                        driver.setnFirstName(name.getText().toString());
                        driver.setnLastName(surname.getText().toString());
                        mDBHelper.UpdateDriver(driver);
                        mAdapter.updateItem(driver);
                        Snackbar.make(v, "Driver Updated",
                                Snackbar.LENGTH_SHORT).show();
                    }
                    // Otherwise create new driver
                    else {
                        Driver driver = new Driver(0, name.getText().toString(),
                                surname.getText().toString());
                        long driverID = mDBHelper.AddDriver(driver);
                        driver.setnDriverID(driverID);
                        mAdapter.addItem(driver);
                        Snackbar.make(v, "Driver Added",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.add_button:
                // Empty All Fields
                id.setText("");
                name.setText("");
                surname.setText("");
                break;
        }
    }
}
