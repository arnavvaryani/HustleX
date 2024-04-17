package com.psa.hustlex.chirag;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.psa.hustlex.MainActivity;
import com.psa.hustlex.R;
import com.psa.hustlex.chirag.datastructure.linksequence.LinkSequence;
import com.psa.hustlex.chirag.datastructure.tree.Tree;
import com.psa.hustlex.chirag.datastructure.tree.TreeInterface;
import com.psa.hustlex.chirag.model.KanbanTasks;

import java.util.Date;
import java.util.Objects;

public class DailyTaskTracker extends AppCompatActivity {
    TreeInterface<KanbanTasks> tree;

//    Declaring the notStarted tasks list LinkSequence.
    LinkSequence<Tree<KanbanTasks>.Node> notStartedTasksLinkSequence;

//    Declaring the inProgress tasks list LinkSequence.
    LinkSequence<Tree<KanbanTasks>.Node> inProgressTasksLinkSequence;

//    Declaring the completed tasks list LinkSequence.
    LinkSequence<Tree<KanbanTasks>.Node> completedTasksLinkSequence;

//    Declaring the adapter for notStarted tasks.
    CustomAdapterNotStarted csAdapterNotStarted;

//    Declaring the adapter for inProgress tasks.
    CustomAdapterInProgress csAdapterInProgress;

//    Declaring the adapter for completed tasks.
    CustomAdapterCompleted csAdapterCompleted;

//    Declaring the dialog to open onClick on Add New Task button in UI.
    Dialog addNewTaskDialog;

//    Declaring the home button to go to HomePage in the app.
    Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.daily_task_tracker);

//        Initializing the dialog.
        addNewTaskDialog = new Dialog(this);
//        Set the content view for the dialog created.
        addNewTaskDialog.setContentView(R.layout.add_new_task_dialog);
//        Dialog will not be cancelable by clicking else where on the screen or performing back on the UI.
        addNewTaskDialog.setCancelable(false);

//        Find and initialize the home button using id.
        homeBtn = findViewById(R.id.homeButton);
//        Defining the onClickListener on the home button.
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Define and create an intent to switch between activities.
                Intent intent = new Intent(DailyTaskTracker.this, MainActivity.class);
//                Start the activity.
                startActivity(intent);
            }
        });

//        Find and initialize the Add New Task button using it's id.
        Button addNewTask = findViewById(R.id.btnAddNewTask);
//        Defining the onClickListener on the Add New Task button.
        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Show the dialog.
                addNewTaskDialog.show();
            }
        });

//        Find and initialize the cancel button shown in the dialog.
        Button dialogCancelBtn = addNewTaskDialog.findViewById(R.id.cancel);
//        Find and initialize the add button shown in the dialog.
        Button dialogAddBtn = addNewTaskDialog.findViewById(R.id.addNew);

//        Defining the onClickListener on the cancel button shown in the dialog.
        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Close the dialog.
                addNewTaskDialog.dismiss();
            }
        });

//        Defining the onClickListener on the add button shown in the dialog.
        dialogAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Find and initialize the title EditText from the dialog.
                EditText titleText = addNewTaskDialog.findViewById(R.id.dialogAddNewTitle);
//                Find and initialize the content EditText from the dialog.
                EditText contentText = addNewTaskDialog.findViewById(R.id.dialogAddNewContent);

//                Fetch the title text value from the title EditText.
                String title = titleText.getText().toString();
//                Fetch the content text value from the content EditText.
                String content = contentText.getText().toString();

//                If the title is Empty,
                if (title.isEmpty()) {
//                    Show a Toast informing user about the title field can not be empty.
                    Toast.makeText(DailyTaskTracker.this, "Title field is empty!", Toast.LENGTH_LONG).show();
                } else {
//                    Else, create a new Task.
                    KanbanTasks newTask = new KanbanTasks(title, content, "notStarted", new Date(), null, null);
//                    Create a new tree node.
                    Tree<KanbanTasks>.Node newTreeNode = tree.createNode("task", "status", newTask);
//                    Add it to the notStarted tasks list.
                    notStartedTasksLinkSequence.add(newTreeNode);
//                    update on UI using the adapter.
                    csAdapterNotStarted.notifyItemInserted(notStartedTasksLinkSequence.length() - 1);
//                    close the dialog as the task is created and added successfully.
                    addNewTaskDialog.dismiss();
                }
            }
        });


//        Initialize the tree.
        tree = new Tree<>();

//        Creating Initial value to be added in the tree.
//              - Creates tree nodes and add it in the tree.
        tree.addNode(tree.createNode("treeRoot", "root", null), "");
        tree.addNode(tree.createNode("notStarted", "status", null), "");
        tree.addNode(tree.createNode("inProgress", "status", null), "");
        tree.addNode(tree.createNode("completed", "status", null), "");

//        Creating Initial value to be added in the tree.
//              - Creates KanbanTasks (tasks) with certain title, content, status of the task, startTime, endTime, and duration.
        KanbanTasks kt1 = new KanbanTasks("task-1", "content-1", "notStarted", new Date(), null, null);
        KanbanTasks kt2 = new KanbanTasks("task-2", "content-2", "notStarted", new Date(), null, null);
        KanbanTasks kt3 = new KanbanTasks("task-3", "content-3", "inProgress", new Date(), new Date(), null);
        KanbanTasks kt4 = new KanbanTasks("task-4", "content-4", "inProgress", new Date(), new Date(), null);
        KanbanTasks kt5 = new KanbanTasks("task-5", "content-5", "inProgress", new Date(), new Date(), null);

//        Firstly, creating nodes using the above created tasks and adding them in the tree.
        tree.addNode(tree.createNode("task", "status", kt1), "notStarted");
        tree.addNode(tree.createNode("task", "status", kt2), "notStarted");
        tree.addNode(tree.createNode("task", "status", kt3), "inProgress");
        tree.addNode(tree.createNode("task", "status", kt4), "inProgress");
        tree.addNode(tree.createNode("task", "status", kt5), "inProgress");

//        Fetching the list of all the tasks with status "notStarted".
        notStartedTasksLinkSequence = tree.getChildrenOf("notStarted");
//        Declaring and initializing the notStarted tasks RecyclerView.
        RecyclerView cvNotStarted = (RecyclerView) findViewById(R.id.recycleViewNotStarted);
        cvNotStarted.setLayoutManager(new LinearLayoutManager(this));
//        Initializing the notStarted adapter.
        csAdapterNotStarted = new CustomAdapterNotStarted(this, notStartedTasksLinkSequence);
//        Displaying the tree structure.
        Log.d("Custom Message: ", " " + tree.displayTree());
//        Initializing the adapter on the notStarted tasks list.
        cvNotStarted.setAdapter(csAdapterNotStarted);

//        Fetching the list of all the tasks with status "inProgress".
        inProgressTasksLinkSequence = tree.getChildrenOf("inProgress");
//        Declaring and initializing the inProgress tasks RecyclerView.
        RecyclerView cvInprogress = (RecyclerView) findViewById(R.id.recycleViewInProgress);
        cvInprogress.setLayoutManager(new LinearLayoutManager(this));
//        Initializing the inProgress adapter.
        csAdapterInProgress = new CustomAdapterInProgress(this, inProgressTasksLinkSequence);
//        Displaying the tree structure.
        Log.d("Custom Message: ", " " + tree.displayTree());
//        Initializing the adapter on the inProgress tasks list.
        cvInprogress.setAdapter(csAdapterInProgress);

//        Fetching the list of all the tasks with status "completed".
        completedTasksLinkSequence = tree.getChildrenOf("completed");
//        Declaring and initializing the completed tasks RecyclerView.
        RecyclerView cvCompleted = (RecyclerView) findViewById(R.id.recycleViewCompleted);
        cvCompleted.setLayoutManager(new LinearLayoutManager(this));
//        Initializing the completed adapter.
        csAdapterCompleted = new CustomAdapterCompleted(DailyTaskTracker.this, completedTasksLinkSequence);
//        Displaying the tree structure.
        Log.d("Custom Message: ", " " + tree.displayTree());
//        Initializing the adapter on the completed tasks list.
        cvCompleted.setAdapter(csAdapterCompleted);
    }

    /**
     * Updates the 3 different tasks list and moves the tasks from one status to another.
     * @param position: Specify the index from the fromStatus's tasks lists.
     * @param fromStatus: Specify the status of the task list in which the node is currently present and is to be moved to another list.
     * @param toStatus: Specify the status of the task list to which the node at position in fromStatus's tasks list is to be moved from to toStatus's tasks list.
     * */
    public void updateLists(int position, String fromStatus, String toStatus) {

        switch (fromStatus) {
//            Case: to move the task from notStarted status to - inProgress or completed.
            case "notStarted": {
//                Get the node that will be removed from notStarted tasks list.
                Tree<KanbanTasks>.Node toRemove = notStartedTasksLinkSequence.get(position);
//                Remove the node from the notStarted tasks list.
                notStartedTasksLinkSequence.removeAt(position);
//                Notify and update the adapter to update the UI.
                csAdapterNotStarted.notifyItemRemoved(position);
//                To move the task to inProgress status.
                if (toStatus.equals("inProgress")) {
//                    As the task is moved from notStarted status to inProgress status, add the task's start time.
                    toRemove.getKanbanTask().setStartTime(new Date());
//                    add the task to inProgress tasks list.
                    inProgressTasksLinkSequence.add(toRemove);
//                    Update the tree to move the node from parent node with notStarted status to inProgress status.
                    tree.transferTo(toRemove, "notStarted", "inProgress");
//                    Notify and update the adapter to update the UI.
                    csAdapterInProgress.notifyItemInserted(inProgressTasksLinkSequence.length() - 1);
                }
//                To move to completed status.
                else if (toStatus.equals("completed")) {
//                    If the task is moved directly from notStarted to completed status, det the duration of task to 0.
                    toRemove.getKanbanTask().setDuration(0);
//                    add the task to the completed tasks list.
                    completedTasksLinkSequence.add(toRemove);
//                    Update the tree to move the node from parent node with notStarted status to completed status.
                    tree.transferTo(toRemove, "notStarted", "completed");
//                    Notify and update the adapter to update the UI.
                    csAdapterCompleted.notifyItemInserted(completedTasksLinkSequence.length() - 1);
                }
                break;
            }
//            Case: to move the task from inProgress status to - completed.
            case "inProgress": {
//                Get the node that will be removed from inProgress tasks list.
                Tree<KanbanTasks>.Node toRemove = inProgressTasksLinkSequence.get(position);
//                Remove the node from the inProgress tasks list.
                inProgressTasksLinkSequence.removeAt(position);
//                Notify and update the adapter to update the UI.
                csAdapterInProgress.notifyItemRemoved(position);
//                To move the task to completed status.
                if (toStatus.equals("completed")) {
//                    Set the end time for the task
                    toRemove.getKanbanTask().setEndTime(new Date());
                    Log.d("Custom Message - inProgressSDate: ", "" + toRemove.getKanbanTask().getStartTime().getTime());
                    Log.d("Custom Message - inProgressEDate: ", "" + toRemove.getKanbanTask().getEndTime().getTime());
                    Log.d("Custom Message - Duration: ", "" + ((toRemove.getKanbanTask().getEndTime().getTime() - toRemove.getKanbanTask().getStartTime().getTime()) / 60000));
//                    Set the duration field in the task to know, how much time did the user spent on this task.
                    toRemove.getKanbanTask().setDuration(((toRemove.getKanbanTask().getEndTime().getTime() - toRemove.getKanbanTask().getStartTime().getTime()) / 60000));
//                    add the task to the completed tasks list.
                    completedTasksLinkSequence.add(toRemove);
//                    Update the tree to move the node from parent node with inProgress status to completed status.
                    tree.transferTo(toRemove, "inProgress", "completed");
//                    Notify and update the adapter to update the UI.
                    csAdapterCompleted.notifyItemInserted(completedTasksLinkSequence.length() - 1);
                }
                break;
            }
//            Case: to move the task out of the tasks View.
            case "completed":
//                Get the node that will be removed from completed tasks list.
                Tree<KanbanTasks>.Node toRemove = completedTasksLinkSequence.get(position);
//                Remove the node from the completed tasks list.
                completedTasksLinkSequence.removeAt(position);
//                Remove the node from the tree.
                tree.removeNode(toRemove, "completed");
//                Notify and update the adapter to update the UI.
                csAdapterCompleted.notifyItemRemoved(position);
                break;
        }
//        View the tree after update.
        Log.d("Custom Message: Visualization of tree", tree.displayTree());
    }

}