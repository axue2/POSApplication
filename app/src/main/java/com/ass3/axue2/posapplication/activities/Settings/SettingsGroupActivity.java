package com.ass3.axue2.posapplication.activities.Settings;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Group;
import com.ass3.axue2.posapplication.views.adapters.SettingsGroupRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsGroupActivity extends AppCompatActivity implements View.OnClickListener {

    Button mAddButton;
    Context mContext;
    DatabaseHelper mDBHelper;
    List<Group> mGroups;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_group);
        setTitle("Group Settings");
        mContext = this;
        mDBHelper = new DatabaseHelper(this);

        mGroups = new ArrayList<>(mDBHelper.GetAllGroups().values());

        RecyclerView rv = (RecyclerView) findViewById(R.id.settings_group_rv);

        SettingsGroupRecyclerViewAdapter adapter = new SettingsGroupRecyclerViewAdapter(this, mGroups);

        rv.setAdapter(adapter);

        rv.setLayoutManager(new LinearLayoutManager(this));

        Button mConfirmButton = (Button) findViewById(R.id.settings_group_confirm_button);
        mConfirmButton.setOnClickListener(this);


    }

    public void updateText(long groupID){

        Group group = mDBHelper.GetGroup(groupID);
        TextView id = (TextView) findViewById(R.id.settings_group_id_textview);
        EditText name = (EditText) findViewById(R.id.settings_group_name_editText);

        id.setText(String.valueOf(groupID));
        name.setText(group.getsGroupName());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_group_confirm_button:
                TextView id = (TextView) findViewById(R.id.settings_group_id_textview);
                EditText name = (EditText) findViewById(R.id.settings_group_name_editText);
                if (!id.getText().toString().equals("")) {
                    long groupID = Long.parseLong(id.getText().toString());
                    Group group = mDBHelper.GetGroup(groupID);
                    group.setsGroupName(name.getText().toString());
                    mDBHelper.UpdateGroup(group);
                }
                break;
        }
    }
}
