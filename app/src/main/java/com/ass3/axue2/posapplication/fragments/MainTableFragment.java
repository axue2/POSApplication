package com.ass3.axue2.posapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.views.adapters.MainRecyclerViewAdapter;

import java.util.ArrayList;

public class MainTableFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Setup RecyclerView
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_main_table_list, container, false);
        rv.setHasFixedSize(true);

        ArrayList<String> tmpList = new ArrayList<>();
        tmpList.add("D");
        tmpList.add("B");
        tmpList.add("C");
        tmpList.add("D");
        tmpList.add("D");
        tmpList.add("B");
        tmpList.add("C");
        tmpList.add("D");
        tmpList.add("D");
        tmpList.add("B");
        tmpList.add("C");
        tmpList.add("D");
        tmpList.add("D");
        tmpList.add("B");
        tmpList.add("C");
        tmpList.add("D");

        // Setup Adapter
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(getActivity(), tmpList);
        rv.setAdapter(adapter);
        // Setup Layout Manager
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return rv;
    }


}
