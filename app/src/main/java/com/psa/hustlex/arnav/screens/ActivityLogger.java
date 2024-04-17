package com.psa.hustlex.arnav.screens;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.psa.hustlex.R;
import com.psa.hustlex.arnav.datastructures.Bag;
import com.psa.hustlex.arnav.models.BagOfLogs;

import java.util.ArrayList;

public class ActivityLogger extends AppCompatActivity {
    private Bag<String> items;
    private BagOfLogs bagOfLogs;
    private ArrayList<String> itemList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        bagOfLogs = BagOfLogs.getInstance();
        items = bagOfLogs.getItems();
        listView = findViewById(R.id.list_view);
        itemList = new ArrayList<>();

        for (String entry : items) {
            itemList.add(entry);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(arrayAdapter);
    }
}
