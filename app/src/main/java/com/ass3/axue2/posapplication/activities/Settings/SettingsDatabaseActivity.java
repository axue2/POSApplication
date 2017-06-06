package com.ass3.axue2.posapplication.activities.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;

public class SettingsDatabaseActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    DatabaseHelper mDBHelper;
    ConfigurationDatabaseHelper mCDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_database);
        setTitle("Database Settings");

        mContext = this;
        // Setup Databases
        mCDBHelper = new ConfigurationDatabaseHelper(getApplicationContext());
        mDBHelper = new DatabaseHelper(getApplicationContext());

        // Setup Delete Button
        Button deleteDbButton = (Button) findViewById(R.id.settings_database_delete_button);
        deleteDbButton.setOnClickListener(this);

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.settings_database_delete_button:
                // Warning Dialog in order to specify the dangers of this button
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Warning!").setMessage("All data in database will be erased." +
                        " Network Settings will also be erased. Are you sure?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete Databases
                                getApplicationContext().deleteDatabase(mDBHelper.DATABASE_NAME);
                                getApplicationContext().deleteDatabase(ConfigurationDatabaseHelper.DATABASE_NAME);

                                Snackbar.make(v, "Your Database has been Deleted! " +
                                                "Please RESTART the Application.",
                                        Snackbar.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
        }
    }
}
