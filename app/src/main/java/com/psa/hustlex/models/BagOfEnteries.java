package com.psa.hustlex.models;

import android.os.Build;

import com.psa.hustlex.datastructures.Bag;

public class BagOfEnteries {
    private Bag<LogEntry> items = new Bag<LogEntry>();

    public BagOfEnteries() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            items.add(new LogEntry("Entry message 1"));
        }
    }

    public Bag<LogEntry> getItems() {
        return items;
    }

    public void setItems(Bag<LogEntry> items) {
        this.items = items;
    }
}
