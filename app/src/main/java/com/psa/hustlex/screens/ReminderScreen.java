package com.psa.hustlex.screens;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.psa.hustlex.R;
import com.psa.hustlex.datastructures.Bag;
import com.psa.hustlex.datastructures.CustomPriorityQueue;
import com.psa.hustlex.helpers.NotifierAlarm;
import com.psa.hustlex.helpers.AdapterReminders;
import com.psa.hustlex.models.BagOfEnteries;
import com.psa.hustlex.models.LogEntry;
import com.psa.hustlex.models.Reminders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class ReminderScreen extends AppCompatActivity implements AdapterReminders.OnDeleteClickListener {
    private FloatingActionButton add;
    private Dialog dialog;
    private RecyclerView recyclerView;
    private AdapterReminders adapter;
    private CustomPriorityQueue<Reminders> reminderQueue;
    private TextView empty;
    private SearchView searchView;
    private Bag<LogEntry> items;

    BagOfEnteries bagOfEnteries;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        bagOfEnteries = new BagOfEnteries();
        reminderQueue = new CustomPriorityQueue<>(5); // Assuming a capacity of 10 for demonstration
        adapter = new AdapterReminders(reminderQueue, (AdapterReminders.OnDeleteClickListener) this);
        add = findViewById(R.id.floatingButton);
        searchView = findViewById(R.id.searchView);
        empty = findViewById(R.id.empty);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        configureSearchView();
        add.setOnClickListener(v -> addReminder());

        Button gotoActivityLoggerButton = findViewById(R.id.goto_activity_logger_button);

        gotoActivityLoggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReminderScreen.this, ActivityLogger.class);
                startActivity(intent);
            }
        });
    }
    private void checkAndScheduleAlarm(Reminders reminder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (getSystemService(AlarmManager.class).canScheduleExactAlarms()) {
                scheduleAlarm(reminder);
            } else {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        } else {
            scheduleAlarm(reminder);
        }
    }

    private void scheduleAlarm(Reminders reminder) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        calendar.setTime(reminder.getRemindDate());
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, NotifierAlarm.class);
        intent.putExtra("Message", reminder.getMessage());
        intent.putExtra("RemindDate", reminder.getRemindDate().toString());
        intent.putExtra("id", reminder.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reminder.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Reminder Set Successfully", Toast.LENGTH_SHORT).show();
    }

    private void configureSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    private void addReminder() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.insertreminder_popup);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView dateText = dialog.findViewById(R.id.date);
        Button selectDate = dialog.findViewById(R.id.selectDate);
        Button addButton = dialog.findViewById(R.id.addButton);
        EditText messageEditText = dialog.findViewById(R.id.message);

        selectDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(ReminderScreen.this, (view, year, month, dayOfMonth) -> {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReminderScreen.this, (view1, hourOfDay, minute) -> {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String formattedDate = sdf.format(newDate.getTime());
                    dateText.setText(formattedDate);
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        addButton.setOnClickListener(v -> {
            try {
                String dateString = dateText.getText().toString().trim();
                Date remindDate = parseDate(dateString);
                if (remindDate == null || remindDate.before(new Date())) {
                    Toast.makeText(ReminderScreen.this, "Cannot set reminder in the past", Toast.LENGTH_SHORT).show();
                    return;
                }
                Reminders reminder = new Reminders();
                reminder.setMessage(messageEditText.getText().toString().trim());
                reminder.setRemindDate(remindDate);
                reminderQueue.enqueue(reminder);
                logReminderAddition(reminder);
                dialog.dismiss();
                refreshRecyclerView();
            } catch (Exception e) {
                Log.e("AddReminder", "Error adding reminder", e);
                Toast.makeText(ReminderScreen.this, "Failed to add reminder: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)

//    private void updateReminder() {
//        dialog = new Dialog(this);
//        dialog.setContentView(R.layout.updatereminder_popup);
//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//
//        TextView dateText = dialog.findViewById(R.id.date);
//        Button selectDate = dialog.findViewById(R.id.selectDate);
//        Button addButton = dialog.findViewById(R.id.addButton);
//        EditText messageEditText = dialog.findViewById(R.id.message);
//
//        selectDate.setOnClickListener(v -> {
//            Calendar calendar = Calendar.getInstance();
//            DatePickerDialog datePickerDialog = new DatePickerDialog(ReminderScreen.this, (view, year, month, dayOfMonth) -> {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(ReminderScreen.this, (view1, hourOfDay, minute) -> {
//                    Calendar newDate = Calendar.getInstance();
//                    newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//                    String formattedDate = sdf.format(newDate.getTime());
//                    dateText.setText(formattedDate);
//                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
//                timePickerDialog.show();
//            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//            datePickerDialog.show();
//        });
//
//        addButton.setOnClickListener(v -> {
//            try {
//                String dateString = dateText.getText().toString().trim();
//                Date remindDate = parseDate(dateString);
//                if (remindDate == null || remindDate.before(new Date())) {
//                    Toast.makeText(ReminderScreen.this, "Cannot set reminder in the past", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Reminders reminder = new Reminders();
//                reminder.setMessage(messageEditText.getText().toString().trim());
//                reminder.setRemindDate(remindDate);
//                reminderQueue.enqueueAt(position, reminder);
//                logReminderUpdation(reminder);
//                dialog.dismiss();
//                refreshRecyclerView();
//            } catch (Exception e) {
//                Log.e("AddReminder", "Error adding reminder", e);
//                Toast.makeText(ReminderScreen.this, "Failed to add reminder: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void logReminderAddition(Reminders reminder) {
        items = bagOfEnteries.getItems();
        LogEntry logEntry = new LogEntry("Added reminder: " + reminder.getMessage());
        items.add(logEntry);
        bagOfEnteries.setItems(items);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void logReminderUpdation(Reminders reminder) {

        LogEntry logEntry = new LogEntry("Updated reminder: " + reminder.getMessage());
        items.add(logEntry);
        bagOfEnteries.setItems(items);
        adapter.notifyDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void logReminderDeletion(Reminders reminder) {

        LogEntry logEntry = new LogEntry("Deleted reminder: " + reminder.getMessage());
        items.add(logEntry);
        bagOfEnteries.setItems(items);
        adapter.notifyDataSetChanged();

    }

    private Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            Log.e("MainPage", "Failed to parse date: " + dateString, e);
            return null;
        }
    }

    @Override
    public void onDeleteClick(int position) {
        reminderQueue.dequeueAt(position);
        refreshRecyclerView();
    }

    private void refreshRecyclerView() {
        boolean hasReminders = !reminderQueue.isEmpty();
        empty.setVisibility(hasReminders ? View.INVISIBLE : View.VISIBLE);
        recyclerView.setVisibility(hasReminders ? View.VISIBLE : View.INVISIBLE);
        adapter.notifyDataSetChanged();
    }

}
