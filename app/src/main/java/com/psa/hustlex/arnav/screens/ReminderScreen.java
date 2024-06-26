package com.psa.hustlex.arnav.screens;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.psa.hustlex.R;
import com.psa.hustlex.arnav.datastructures.NodeBag;
import com.psa.hustlex.arnav.helpers.AdapterReminders;
import com.psa.hustlex.arnav.helpers.NotifierAlarm;
import com.psa.hustlex.arnav.models.Reminders;
import com.psa.hustlex.arnav.datastructures.HeapPriorityQueue;
import com.psa.hustlex.arnav.models.BagOfLogs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

//reminder screen ui
public class ReminderScreen extends AppCompatActivity implements AdapterReminders.OnDeleteClickListener, AdapterReminders.OnUpdateClickListener {
    private static final String TAG = "ReminderScreen";
    private FloatingActionButton add;
    private Dialog dialog;
    private RecyclerView recyclerView;
    private AdapterReminders adapter;
    private HeapPriorityQueue<Reminders> reminderQueue;
    private TextView empty;

    private NodeBag<String> items;

    BagOfLogs bagOfLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        bagOfLogs = BagOfLogs.getInstance();
        reminderQueue = new HeapPriorityQueue<>(10);
        adapter = new AdapterReminders(reminderQueue, this, this);
        add = findViewById(R.id.floatingButton);
        empty = findViewById(R.id.empty);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        add.setOnClickListener(v -> addReminder());

       addDummyReminder();

        FloatingActionButton gotoActivityLoggerButton = findViewById(R.id.goto_activity_logger_button);

        gotoActivityLoggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReminderScreen.this, ActivityLogger.class);
                startActivity(intent);
            }
        });
    }
//adds reminder
    private void addDummyReminder() {
        Reminders reminders = new Reminders();
        reminders.setMessage("Do homework");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.APRIL, 20, 10, 0, 0);  // April 20, 2024, at 10:00:00 AM
        Date remindDate = calendar.getTime();

        String formattedDate = sdf.format(remindDate);
        reminders.setRemindDate(remindDate);

        reminderQueue.enqueue(reminders);

        System.out.println("Reminder set for: " + formattedDate); // Just for demonstration
    }

    //schedules notifications
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

    //schedule alarm to show in ui
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

//adds reminder
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
                checkAndScheduleAlarm(reminder);
                logReminderAddition(reminder);
                dialog.dismiss();
                refreshRecyclerView();
            } catch (Exception e) {
                Log.e("AddReminder", "Error adding reminder", e);
                Toast.makeText(ReminderScreen.this, "Failed to add reminder: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //updates reminder
    private void updateReminder(int position) {
        Reminders reminderToUpdate = reminderQueue.get(position);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.updatereminder_popup);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView dateText = dialog.findViewById(R.id.date);
        Button selectDate = dialog.findViewById(R.id.selectDate);
        Button addButton = dialog.findViewById(R.id.addButton);
        EditText messageEditText = dialog.findViewById(R.id.message);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateText.setText(sdf.format(reminderToUpdate.getRemindDate()));
        messageEditText.setText(reminderToUpdate.getMessage());

        selectDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(ReminderScreen.this, (view, year, month, dayOfMonth) -> {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReminderScreen.this, (view1, hourOfDay, minute) -> {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);
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
                reminderQueue.enqueueAt(position, reminder);
                checkAndScheduleAlarm(reminder);
                logReminderUpdation(reminder);
                dialog.dismiss();
                refreshRecyclerView();
            } catch (Exception e) {
                Log.e("AddReminder", "Error adding reminder", e);
                Toast.makeText(ReminderScreen.this, "Failed to add reminder: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

//logs adding new reminder in bag
    private void logReminderAddition(@NonNull Reminders reminder) {
        items = bagOfLogs.getItems();
        String logEntry = "Added reminder: " + reminder.getMessage();
        items.add(logEntry);
        bagOfLogs.setItems(items);
        Log.d(TAG, "" + items.size());
        refreshRecyclerView();
    }

    //logs updating existing reminder in bag
    private void logReminderUpdation(@NonNull Reminders reminder) {
        items = bagOfLogs.getItems();
        String logEntry = "Updated reminder: " + reminder.getMessage();
        items.add(logEntry);
        bagOfLogs.setItems(items);
        refreshRecyclerView();
    }

    //logs deleting reminder in bag
   private void logReminderDeletion(@NonNull Reminders reminder) {
       items = bagOfLogs.getItems();
        String logEntry = "Deleted reminder: " + reminder.getMessage();
        items.add(logEntry);
        bagOfLogs.setItems(items);
        refreshRecyclerView();
    }

    //used to parse date from calendar
    private Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            Log.d("MainPage", "Failed to parse date: " + dateString, e);
            return null;
        }
    }

    //delete click button callback handler
    @Override
    public void onDeleteClick(int position) {
        Reminders reminders = reminderQueue.get(position);
        reminderQueue.dequeueAt(position);
        logReminderDeletion(reminders);
        refreshRecyclerView();
    }

    //refresh ui
    private void refreshRecyclerView() {
        boolean hasReminders = !reminderQueue.isEmpty();
        empty.setVisibility(hasReminders ? View.INVISIBLE : View.VISIBLE);
        recyclerView.setVisibility(hasReminders ? View.VISIBLE : View.INVISIBLE);
        adapter.notifyDataSetChanged();
    }
//update click button callback handler
    @Override
    public void onUpdateClick(int position) {
        updateReminder(position);
    }
}
