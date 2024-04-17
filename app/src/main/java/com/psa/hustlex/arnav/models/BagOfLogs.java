package com.psa.hustlex.arnav.models;

import com.psa.hustlex.arnav.datastructures.Bag;

public class BagOfLogs {
    private static BagOfLogs instance;
    private Bag<String> items;

    private BagOfLogs() {
        items = new Bag<>();
    }

    public static BagOfLogs getInstance() {
        if (instance == null) {
            instance = new BagOfLogs();
        }
        return instance;
    }

    public Bag<String> getItems() {
        return items;
    }

    public void setItems(Bag<String> items) {
        this.items = items;
    }
}
