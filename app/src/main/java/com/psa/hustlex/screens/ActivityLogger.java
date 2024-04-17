package com.psa.hustlex.screens;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.psa.hustlex.R;
import com.psa.hustlex.datastructures.Bag;
import com.psa.hustlex.helpers.BagRecyclerAdapter;
import com.psa.hustlex.models.BagOfLogs;
import com.psa.hustlex.models.LogEntry;

import java.util.ArrayList;

public class ActivityLogger extends AppCompatActivity {
    private Bag<LogEntry> items;
    private BagOfLogs bagOfLogs;
    private ArrayList<LogEntry> itemList;
    private BagRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        bagOfLogs = new BagOfLogs();
        items = bagOfLogs.getItems();

        itemList = new ArrayList<>();
        adapter = new BagRecyclerAdapter(itemList);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateItemList();
    }

    private void updateItemList() {
        itemList.clear();
        for (LogEntry entry : items) {
            itemList.add(entry);
        }
        adapter.notifyDataSetChanged();
    }


    public void addLogEntry(LogEntry entry) {
        bagOfLogs.getItems().add(entry);
        updateItemList();
    }
}
