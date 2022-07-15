package com.cookandroid.jye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BusAdapter mAdapter;
    private ArrayList<Item> mBusList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        mBusList = new ArrayList<Item>();

        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));
        mBusList.add(new Item("222488"));

        mAdapter = new BusAdapter(mBusList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
