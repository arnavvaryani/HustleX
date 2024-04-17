package com.psa.hustlex.chirag;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.psa.hustlex.R;
import com.psa.hustlex.chirag.datastructure.linksequence.LinkSequence;
import com.psa.hustlex.chirag.datastructure.tree.Tree;
import com.psa.hustlex.chirag.model.KanbanTasks;

public class CustomAdapterNotStarted extends RecyclerView.Adapter {

    DailyTaskTracker dailyTaskTracker;
    LinkSequence<Tree<KanbanTasks>.Node> tasksList;
    LayoutInflater layoutInflater;

    CustomAdapterNotStarted(DailyTaskTracker ctx, LinkSequence<Tree<KanbanTasks>.Node> tasksList){
        this.dailyTaskTracker = ctx;
        this.tasksList = tasksList;
        layoutInflater = LayoutInflater.from(dailyTaskTracker);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txTitle;
        public TextView txContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txTitle = itemView.findViewById(R.id.notStartedTitle);
            txContent = itemView.findViewById(R.id.notStartedContent);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.not_started_task_card, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Tree<KanbanTasks>.Node dataNode = (Tree<KanbanTasks>.Node) this.tasksList.get(position);
        TextView txTitle = holder.itemView.findViewById(R.id.notStartedTitle);
        txTitle.setText(dataNode.getKanbanTask().getTitle());
        TextView txContent = holder.itemView.findViewById(R.id.notStartedContent);
        txContent.setText(dataNode.getKanbanTask().getContent());

//        Start Button
        Button btnStartTask = holder.itemView.findViewById(R.id.notStartedStartTaskButton);
        btnStartTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPosition(holder.getAdapterPosition(), "notStarted", "inProgress");
                Log.d("ANS - Custom Message: ", ""+tasksList.length());
            }
        });

//        End Button
        Button btnEndTask = holder.itemView.findViewById(R.id.notStartedEndTaskButton);
        btnEndTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPosition(holder.getAdapterPosition(), "notStarted", "completed");
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.tasksList.length();
    }

    public void sendPosition(int position, String fromStatus, String toStatus){
        dailyTaskTracker.updateLists(position, fromStatus, toStatus);
    }

//    @Override
//    public int getCount() {
//        return this.tasksList.length();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = layoutInflater.inflate(R.layout.not_started_task_card, null);
//        TextView txTitle = convertView.findViewById(R.id.notStartedTitle);
//        TextView txContent = convertView.findViewById(R.id.notStartedContent);
//        Tree<KanbanTasks>.Node dataNode = this.tasksList.get(position);
//        txTitle.setText("hello-title");
//        txContent.setText("hello-content");
//        return convertView;
//    }
}
