package com.ass3.axue2.posapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.models.Table;
import com.ass3.axue2.posapplication.views.adapters.MainRecyclerViewAdapter;

import java.util.ArrayList;

public class MainOrdersFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_recyclerview, container, false);
        rv.setHasFixedSize(true);

        // temporary tables
        ArrayList<Table> tableList = new ArrayList<>();
        tableList.add(new Table(1, "Table 1", 5, 500, "In-use" ));
        tableList.add(new Table(2, "Table 2", 2, 1000, "In-use"));
        tableList.add(new Table(3, "Table 3", 5, 12312, "Open"));
        tableList.add(new Table(4, "Table 4", 0, 0, "Open"));
        tableList.add(new Table(5, "Table 5", 0, 0, "Open"));
        tableList.add(new Table(6, "Table 6", 7, 40, "In-use"));
        tableList.add(new Table(7, "Table 7", 0, 0, "Open"));
        tableList.add(new Table(8, "Table 8", 2, 90, "In-use"));
        tableList.add(new Table(9, "Table 9", 0, 0, "Open"));

        // Setup Adapter
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(getActivity(), tableList);
        rv.setAdapter(adapter);
        // Setup Layout Manager
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return rv;
    }

    
}
