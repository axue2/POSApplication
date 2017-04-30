package com.ass3.axue2.posapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.DatabaseHelper;
import com.ass3.axue2.posapplication.models.Table;
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
        ArrayList<Table> tableList = new ArrayList<>(mDBHelper.GetAllTables().values());

        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_recyclerview, container, false);
        rv.setHasFixedSize(true);

        // Setup Adapter
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(getActivity(), tableList);
        rv.setAdapter(adapter);
        // Setup Layout Manager
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return rv;
    }

    
}
