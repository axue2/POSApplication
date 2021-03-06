package com.ass3.axue2.posapplication.fragments;

import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.operational.DatabaseHelper;
import com.ass3.axue2.posapplication.models.operational.Order;
import com.ass3.axue2.posapplication.models.operational.Table;
import com.ass3.axue2.posapplication.views.adapters.MainRecyclerViewAdapter;

import java.util.ArrayList;

public class MainOrdersFragment extends android.support.v4.app.Fragment {

    DatabaseHelper mDBHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get database handler
        mDBHelper = new DatabaseHelper(getActivity());

        // Get Tables
        ArrayList<Order> orders = new ArrayList<>(mDBHelper.GetEatInOrders().values());
        ArrayList<Table> tableList = new ArrayList<>();
        //TODO: GET MAIN ORDERS FRAGMENT WORKING

        for (Order order : orders){
            Table table;
            try{
                mDBHelper.GetTable(order.getnTableID());
                table = new Table(order.getnTableID(),String.valueOf(mDBHelper.GetTable(order.getnTableID()).getsTableName()),
                        0, order.getnOrderID(), order.getnTotal(), Table.STATUS_INUSE);
            } catch (CursorIndexOutOfBoundsException e) {
                table = new Table(order.getnTableID(),String.valueOf(order.getnTableID()),
                        0, order.getnOrderID(), order.getnTotal(), Table.STATUS_INUSE);
            }

            tableList.add(table);
        }
        //ArrayList<Table> tableList = new ArrayList<>(mDBHelper.GetInuseTables().values());

        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_recyclerview, container, false);
        rv.setHasFixedSize(true);

        // Setup Adapter
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(getActivity(), tableList);
        rv.setAdapter(adapter);

        // Setup Layout Manager
        //LinearLayoutManager llm = new LinearLayoutManager(getContext());
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        // Setup Divider
        /*DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                llm.getOrientation());
        rv.addItemDecoration(itemDecoration);*/

        return rv;
    }

    
}
