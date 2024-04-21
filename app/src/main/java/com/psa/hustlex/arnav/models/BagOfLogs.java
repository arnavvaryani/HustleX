package com.psa.hustlex.arnav.models;

import com.psa.hustlex.arnav.datastructures.NodeBag;

//holds a list of activity logs
public class BagOfLogs {
    private static BagOfLogs instance;
    private NodeBag<String> items;

    private BagOfLogs() {
        items = new NodeBag<>();
    }

    public static BagOfLogs getInstance() {
        if (instance == null) {
            instance = new BagOfLogs();
        }
        return instance;
    }

    public NodeBag<String> getItems() {
        return items;
    }

    public void setItems(NodeBag<String> items) {
        this.items = items;
    }
}
