package com.psa.hustlex.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.psa.hustlex.models.LogEntry;

import java.util.List;


public class BagRecyclerAdapter extends RecyclerView.Adapter<BagRecyclerAdapter.ViewHolder> {
    private List<LogEntry> itemList;

    public BagRecyclerAdapter(List<LogEntry> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LogEntry entry = itemList.get(position); // Now we can use get(index) safely
        holder.textView.setText(entry.getMessage());
    }

    public LogEntry getEntryByPosition(int position) {
        // Check if position is within the list size
        if (position >= 0 && position < itemList.size()) {
            return itemList.get(position);
        }
        return null; // Return null or throw an exception if position is out of bounds
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}

