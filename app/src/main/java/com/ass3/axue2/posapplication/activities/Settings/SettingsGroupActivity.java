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
    SettingsGroupRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_group);
        setTitle("Group Settings");

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;
        mDBHelper = new DatabaseHelper(this);

        mGroups = new ArrayList<>(mDBHelper.GetAllGroups().values());

        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) findViewById(R.id.settings_group_rv);
        adapter = new SettingsGroupRecyclerViewAdapter(this, mGroups);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        // Setup Divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                llm.getOrientation());
        rv.addItemDecoration(itemDecoration);

        // Setup Button
        Button mConfirmButton = (Button) findViewById(R.id.settings_group_confirm_button);
        mConfirmButton.setOnClickListener(this);
        ImageButton mAddButton = (ImageButton) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(this);

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
        TextView id = (TextView) findViewById(R.id.settings_group_id_textview);
        EditText name = (EditText) findViewById(R.id.settings_group_name_editText);
        switch (v.getId()){
            case R.id.settings_group_confirm_button:
                if (!name.getText().toString().equals("")){
                    // If Group ID exists then update
                    if (!id.getText().toString().equals("")) {
                        long groupID = Long.parseLong(id.getText().toString());
                        Group group = mDBHelper.GetGroup(groupID);
                        group.setsGroupName(name.getText().toString());
                        mDBHelper.UpdateGroup(group);
                        adapter.updateItem(group);
                        Snackbar.make(v, "Group Updated",
                                Snackbar.LENGTH_SHORT).show();
                    } else {
                        Group group = new Group(0, name.getText().toString());
                        long groupID = mDBHelper.AddGroup(group);
                        group.setnGroupID(groupID);
                        adapter.addItem(group);
                        Snackbar.make(v, "Group Added",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.add_button:
                // Empty All Fields
                id.setText("");
                name.setText("");
                break;
        }
    }
}
