package com.ass3.axue2.posapplication.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;

public class SettingsNetworkActivity extends AppCompatActivity {

    Switch mNetworkSwitch;
    ConfigurationDatabaseHelper mCDBHelper;
    EditText mIPAddress;
    EditText mDBName;
    EditText mUsername;
    EditText mPassword;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_network);
        setTitle("Network Settings");

        mNetworkSwitch = (Switch)  findViewById(R.id.network_mode_switch);
        Button networkConfigurationButton = (Button) findViewById(R.id.network_configuration_button);
        mIPAddress = (EditText) findViewById(R.id.network_ip_address_editText);
        mDBName = (EditText) findViewById(R.id.network_db_name_editText);
        mUsername = (EditText) findViewById(R.id.network_username_editText);
        mPassword = (EditText) findViewById(R.id.network_password_editText);

        mCDBHelper = new ConfigurationDatabaseHelper(getApplicationContext());
        mContext = this;

        networkModeConfiguration();

        if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 1){
            mNetworkSwitch.setChecked(true);
        } else if (mCDBHelper.GetNetworkSetting(1).getnNetworkMode() == 0){
            mNetworkSwitch.setChecked(false);
        }

        networkConfigurationButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NetworkSetting mNetworkSetting = new NetworkSetting(1,
                        mCDBHelper.GetNetworkSetting(1).getnNetworkMode(),
                        mIPAddress.getText().toString(), mDBName.getText().toString(),
                        mUsername.getText().toString(), mPassword.getText().toString());

                mCDBHelper.UpdateNetworkSetting(mNetworkSetting);

                System.out.println(mNetworkSetting.getsDBName() + " " + mNetworkSetting.getsIPAddress());

            }
        });

        mNetworkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);

                if (isChecked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Warning!").setMessage("Setting application to network mode will destroy all current data. Are you Sure?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            NetworkSetting mNetworkSetting = mCDBHelper.GetNetworkSetting(1);
                            mNetworkSetting.setnNetworkMode(1);
                            mCDBHelper.UpdateNetworkSetting(mNetworkSetting);
                        }
                    })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mNetworkSwitch.setChecked(false);
                                }
                            }).show();

                 } else {
                    NetworkSetting mNetworkSetting = mCDBHelper.GetNetworkSetting(1);
                    mNetworkSetting.setnNetworkMode(0);
                    mCDBHelper.UpdateNetworkSetting(mNetworkSetting);
                }

            }
        });

    }

    private void networkModeConfiguration(){
        NetworkSetting mNetworkSetting = mCDBHelper.GetNetworkSetting(1);
        mIPAddress.setText(mNetworkSetting.getsIPAddress());
        mDBName.setText(mNetworkSetting.getsDBName());
        mUsername.setText(mNetworkSetting.getsUsername());
        mPassword.setText(mNetworkSetting.getsPassword());
    }
}
