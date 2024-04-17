package com.psa.hustlex.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.psa.hustlex.R;
import com.psa.hustlex.datastructures.Bag;
import com.psa.hustlex.models.BagOfLogs;
import com.psa.hustlex.models.LogEntry;
import com.psa.hustlex.helpers.BagRecyclerAdapter;

import java.util.ArrayList;

public class ActivityLogger extends AppCompatActivity {
    private Bag<LogEntry> items;
    private BagOfLogs bagOfLogs;
    private ArrayList<LogEntry> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        bagOfLogs = new BagOfLogs();
        items = bagOfLogs.getItems();

        // Convert Bag to List
        itemList = new ArrayList<>();
        for (LogEntry entry : items) {
            itemList.add(entry);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BagRecyclerAdapter adapter = new BagRecyclerAdapter(itemList); // Adjust the adapter to accept a List
        recyclerView.setAdapter(adapter);

        // Optional: if you want to handle item clicks
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && e.getAction() == MotionEvent.ACTION_UP) {
                    int position = rv.getChildAdapterPosition(child);
                    LogEntry item = adapter.getEntryByPosition(position);
                    Toast.makeText(getApplicationContext(), item.getMessage(), Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }
}
