package com.psa.hustlex.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.psa.hustlex.R;
import com.psa.hustlex.datastructures.Bag;
import com.psa.hustlex.models.BagOfEnteries;
import com.psa.hustlex.models.LogEntry;

import java.util.Iterator;
import android.util.Log;

public class ActivityLogger extends AppCompatActivity {
    Bag<LogEntry> items;
    BagOfEnteries bagOfEnteries;

    private static final String TAG = "ActivityLogger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bagOfEnteries = new BagOfEnteries();
        setContentView(R.layout.list_view);
        items = bagOfEnteries.getItems();
        Log.d(TAG, items.toString());

        // Setup ListView and Adapter
        ListView listView = findViewById(R.id.list_view);

        LogEntry[] itemsArray = new LogEntry[items.size()];
        int index = 0;
        for (LogEntry item : items) {
            itemsArray[index++] = item;
        }

        ArrayAdapter<LogEntry> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsArray);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Iterator<LogEntry> iterator = items.iterator();
                for (int i = 0; i < position; i++) {
                    iterator.next();
                }
                LogEntry item = iterator.next();
                Toast.makeText(getApplicationContext(), item.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
