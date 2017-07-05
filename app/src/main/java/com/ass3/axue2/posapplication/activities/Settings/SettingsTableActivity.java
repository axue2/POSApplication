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
import com.ass3.axue2.posapplication.models.operational.Table;
import com.ass3.axue2.posapplication.views.adapters.SettingsTableRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsTableActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    DatabaseHelper mDBHelper;
    List<Table> mTables;
    SettingsTableRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_table);
        setTitle("Table Settings");

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;
        mDBHelper = new DatabaseHelper(this);

        mTables = new ArrayList<>(mDBHelper.GetAllTables().values());

        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) findViewById(R.id.settings_table_rv);
        mAdapter = new SettingsTableRecyclerViewAdapter(this, mTables);
        rv.setAdapter(mAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        // Setup Divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                llm.getOrientation());
        rv.addItemDecoration(itemDecoration);

        // Setup Buttons
        Button mConfirmButton = (Button) findViewById(R.id.settings_table_confirm_button);
        mConfirmButton.setOnClickListener(this);
        ImageButton mAddButton = (ImageButton) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(this);
    }

    public void updateText(long tableID){
        Table table = mDBHelper.GetTable(tableID);
        TextView id = (TextView) findViewById(R.id.settings_table_id_textview);
        EditText name = (EditText) findViewById(R.id.settings_table_name_editText);

        id.setText(String.valueOf(tableID));
        name.setText(table.getsTableName());
    }

    @Override
    public void onClick(View v) {
        TextView id = (TextView) findViewById(R.id.settings_table_id_textview);
        EditText name = (EditText) findViewById(R.id.settings_table_name_editText);
        switch (v.getId()){
            case R.id.settings_table_confirm_button:
                // If table name has been filled
                if (!name.getText().toString().equals("")){
                    // If tableID has been set then update table
                    if (!id.getText().toString().equals("")) {
                        long tableID = Long.parseLong(id.getText().toString());
                        Table table = mDBHelper.GetTable(tableID);
                        table.setsTableName(name.getText().toString());
                        mDBHelper.UpdateTable(table);
                        mAdapter.updateItem(table);

                        Snackbar.make(v, "Table Updated",
                                Snackbar.LENGTH_SHORT).show();
                    }
                    // Otherwise create new Table
                    else {
                        Table table = new Table(name.getText().toString());
                        long tableID = mDBHelper.AddTable(table);
                        table.setnTableID(tableID);
                        mAdapter.addItem(table);
                        Snackbar.make(v, "Driver Added",
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
