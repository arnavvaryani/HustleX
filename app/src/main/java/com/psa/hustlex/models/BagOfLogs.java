package com.psa.hustlex.models;

import com.psa.hustlex.datastructures.Bag;

public class BagOfLogs {
    private Bag<LogEntry> items = new Bag<>();

    public Bag<LogEntry> getItems() {
        return items;
    }

    public void setItems(Bag<LogEntry> items) {
        this.items = items;
    }
}
