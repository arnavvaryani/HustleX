package com.psa.hustlex.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.psa.hustlex.R;
import com.psa.hustlex.models.Reminders;
import com.psa.hustlex.datastructures.CustomPriorityQueue;

public class AdapterReminders extends RecyclerView.Adapter<AdapterReminders.MyViewHolder> {
    private final CustomPriorityQueue<Reminders> reminderQueue;
    private OnDeleteClickListener onDeleteClickListener;

    // Interface for the delete button click listener
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    // Constructor to set the reminder queue and the delete button click listener
    public AdapterReminders(CustomPriorityQueue<Reminders> reminderQueue, OnDeleteClickListener onDeleteClickListener) {
        this.reminderQueue = reminderQueue;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item, parent, false);
        return new MyViewHolder(view, onDeleteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Reminders reminder = reminderQueue.get(position);
        if (reminder != null) {
            holder.message.setText(reminder.getMessage());
            holder.time.setText(reminder.getRemindDate().toString());
        }
    }

    @Override
    public int getItemCount() {
        return reminderQueue.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView message;
        final TextView time;
        final Button buttonDelete;

        MyViewHolder(@NonNull View itemView, final OnDeleteClickListener onDeleteClickListener) {
            super(itemView);
            message = itemView.findViewById(R.id.textView1);
            time = itemView.findViewById(R.id.textView2);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);

            buttonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(position);
                }
            });
        }
    }
}
