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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.psa.hustlex.R;
import com.psa.hustlex.datastructures.CustomPriorityQueue;
import com.psa.hustlex.helpers.NotifierAlarm;
import com.psa.hustlex.reminders.AdapterReminders;
import com.psa.hustlex.reminders.Reminders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class MainPage extends AppCompatActivity {
    private FloatingActionButton add;
    private Dialog dialog;
    private RecyclerView recyclerView;
    private AdapterReminders adapter;
    private CustomPriorityQueue<Reminders> reminderQueue;
    private TextView empty;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        reminderQueue = new CustomPriorityQueue<>(5); // Assuming a capacity of 10 for demonstration
        adapter = new AdapterReminders(reminderQueue);
        add = findViewById(R.id.floatingButton);
        searchView = findViewById(R.id.searchView);
        empty = findViewById(R.id.empty);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        configureSearchView();
        add.setOnClickListener(v -> addReminder());
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
                searchInQueue(reminderQueue, newText);
                return true;
            }
        });
    }

    public <E extends Comparable<E>> void searchInQueue(CustomPriorityQueue<E> queue, String query) {
        CustomPriorityQueue<E> filteredQueue = new CustomPriorityQueue<>(5);
        Iterator<E> iterator = queue.iterator();

        while (iterator.hasNext()) {
            E item = iterator.next();
            if (item.toString().toLowerCase().contains(query.toLowerCase())) {
                filteredQueue.enqueue(item);
            }
        }
        refreshRecyclerView();
    }

    private void addReminder() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.floating_popup);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView dateText = dialog.findViewById(R.id.date);
        Button selectDate = dialog.findViewById(R.id.selectDate);
        Button addButton = dialog.findViewById(R.id.addButton);
        EditText messageEditText = dialog.findViewById(R.id.message);

        selectDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainPage.this, (view, year, month, dayOfMonth) -> {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainPage.this, (view1, hourOfDay, minute) -> {
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
                    Toast.makeText(MainPage.this, "Cannot set reminder in the past", Toast.LENGTH_SHORT).show();
                    return;
                }
                Reminders reminder = new Reminders();
                reminder.setMessage(messageEditText.getText().toString().trim());
                reminder.setRemindDate(remindDate);
                reminderQueue.enqueue(reminder);
                dialog.dismiss();
                refreshRecyclerView();
            } catch (Exception e) {
                Log.e("AddReminder", "Error adding reminder", e);
                Toast.makeText(MainPage.this, "Failed to add reminder: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

    private void refreshRecyclerView() {
        boolean hasReminders = !reminderQueue.isEmpty();
        empty.setVisibility(hasReminders ? View.INVISIBLE : View.VISIBLE);
        recyclerView.setVisibility(hasReminders ? View.VISIBLE : View.INVISIBLE);
        adapter.notifyDataSetChanged();
    }

}
