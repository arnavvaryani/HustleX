package com.psa.hustlex.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.psa.hustlex.R;
import com.psa.hustlex.models.Reminders;
import com.psa.hustlex.datastructures.CustomPriorityQueue;

import java.util.Iterator;

public class AdapterReminders extends RecyclerView.Adapter<AdapterReminders.MyViewHolder> {
    private final CustomPriorityQueue<Reminders> reminderQueue;
    private CustomPriorityQueue<Reminders> displayQueue; // Temporary cache for display

    public AdapterReminders(CustomPriorityQueue<Reminders> reminderQueue) {
        this.reminderQueue = reminderQueue;
        this.displayQueue = new CustomPriorityQueue<>(reminderQueue.size());
        cacheReminders(); // Populate the display cache
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Ensure cache is up to date
        if (reminderQueue.size() != displayQueue.size()) {
            cacheReminders();
        }

        // Access reminders directly by polling, which respects their priority order
        Reminders reminder = null;
        CustomPriorityQueue<Reminders> tempQueue = new CustomPriorityQueue<>(displayQueue.size());
        for (int i = 0; i <= position; i++) {
            if (!displayQueue.isEmpty()) {
                reminder = displayQueue.dequeue();
                tempQueue.enqueue(reminder);
            }
        }

        // Restore the displayQueue from tempQueue
        displayQueue = tempQueue;

        if (reminder != null) {
            holder.message.setText(reminder.getMessage());
            holder.time.setText(reminder.getRemindDate().toString());
        }
    }

    private void cacheReminders() {
        displayQueue.clear();
        Iterator<Reminders> iterator = reminderQueue.iterator();
        while (iterator.hasNext()) {
            displayQueue.enqueue(iterator.next());
        }
    }

    @Override
    public int getItemCount() {
        return reminderQueue.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView message;
        final TextView time;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.textView1);
            time = itemView.findViewById(R.id.textView2);
        }
    }
}
