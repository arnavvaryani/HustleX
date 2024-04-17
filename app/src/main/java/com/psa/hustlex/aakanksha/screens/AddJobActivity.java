package com.psa.hustlex.aakanksha.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.psa.hustlex.R;
import com.psa.hustlex.aakanksha.models.Job;
import com.psa.hustlex.aakanksha.tree_elements.JobStatusTree;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddJobActivity extends AppCompatActivity {
    private Spinner spinnerStatus;
    private EditText editTextJobRole;
    private EditText editTextCompany;
    private EditText editTextLocation;
    private Button buttonSave;
    private Button buttonPickDate;
    private EditText editTextAppliedOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        // Initialize views
        spinnerStatus = findViewById(R.id.spinnerStatus);
        editTextJobRole = findViewById(R.id.editTextJobRole);
        editTextCompany = findViewById(R.id.editTextCompany);
        editTextLocation = findViewById(R.id.editTextLocation);
        buttonSave = findViewById(R.id.buttonSave);
        buttonPickDate = findViewById(R.id.buttonPickDate);
        editTextAppliedOn = findViewById(R.id.editTextAppliedOn);

        // Set default date to today
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());
        editTextAppliedOn.setText(currentDate);

        // Set click listener for the date picker button
        buttonPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        // Populate the spinner with status options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.job_statuses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        // Set default status to "Saved"
        spinnerStatus.setSelection(adapter.getPosition("Saved"));

        // Set click listener for the save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Retrieve job details
                String jobRole = editTextJobRole.getText().toString();
                String company = editTextCompany.getText().toString();
                String location = editTextLocation.getText().toString();
                String status = spinnerStatus.getSelectedItem().toString();
                String dateStr = editTextAppliedOn.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date;
                try {
                    date = sdf.parse(dateStr);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                // Convert the Date object to LocalDate
                Instant instant = date.toInstant();
                ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
                LocalDate appliedOn = zonedDateTime.toLocalDate();


                // Validate input
                if (jobRole.isEmpty() || company.isEmpty() || location.isEmpty()) {
                    Toast.makeText(AddJobActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Job newJob = new Job(jobRole, status, company, location, appliedOn);
                JobStatusTree.addJob(newJob, status);

                // Set result to indicate success and finish activity
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddJobActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the Button text with the selected date
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        editTextAppliedOn.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}
