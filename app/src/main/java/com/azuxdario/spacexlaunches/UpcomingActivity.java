package com.azuxdario.spacexlaunches;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class UpcomingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        ArrayList<UpcomingItem> exampleList = new ArrayList<>();
        exampleList.add(new UpcomingItem("Line 1", "Line 2"));
        exampleList.add(new UpcomingItem("Line 3", "Line 4"));
        exampleList.add(new UpcomingItem("Line 5", "Line 6"));
        exampleList.add(new UpcomingItem("Line 7", "Line 8"));
        exampleList.add(new UpcomingItem("Line 9", "Line 10"));
        exampleList.add(new UpcomingItem("Line 11", "Line 12"));
        exampleList.add(new UpcomingItem("Line 13", "Line 14"));
        exampleList.add(new UpcomingItem("Line 15", "Line 16"));
        exampleList.add(new UpcomingItem("Line 17", "Line 18"));
        exampleList.add(new UpcomingItem("Line 19", "Line 20"));
        exampleList.add(new UpcomingItem("Line 21", "Line 22"));
        exampleList.add(new UpcomingItem("Line 23", "Line 24"));
        exampleList.add(new UpcomingItem("Line 25", "Line 26"));
        exampleList.add(new UpcomingItem("Line 27", "Line 28"));
        exampleList.add(new UpcomingItem("Line 29", "Line 30"));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new UpcomingAdapter(exampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}