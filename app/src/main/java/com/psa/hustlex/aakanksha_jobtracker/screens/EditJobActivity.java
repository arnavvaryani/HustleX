package com.psa.hustlex.aakanksha_jobtracker.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.psa.hustlex.R;
import com.psa.hustlex.aakanksha_jobtracker.models.Job;
import com.psa.hustlex.aakanksha_jobtracker.tree_elements.JobStatusTree;
import com.psa.hustlex.aakanksha_jobtracker.tree_elements.Node;

public class EditJobActivity extends AppCompatActivity {


        /* BELOW THIS */

        private EditText editTextJobRole, editTextCompany, editTextLocation;
        private Spinner spinnerStatus;
        private Job job;
        private Button buttonSave;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_job);

            // Retrieve job ID from intent
            String jobId = getIntent().getStringExtra("jobId");
            Node workingNode = JobStatusTree.findNode(getIntent().getStringExtra("statusNode"));

            job=workingNode.searchJobById(jobId);

            // Initialize views
            editTextJobRole = findViewById(R.id.editTextJobRole);
            editTextCompany = findViewById(R.id.editTextCompany);
            editTextLocation = findViewById(R.id.editTextLocation);
            spinnerStatus = findViewById(R.id.spinnerStatus);

            // Set existing job data to the views
            editTextJobRole.setText(job.getJobRole());
            editTextCompany.setText(job.getCompanyName());
            editTextLocation.setText(job.getJobLocation());

            // Set up status spinner
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.job_statuses, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerStatus.setAdapter(adapter);
            spinnerStatus.setSelection(getStatusPosition(job.getJobStatus()));

            buttonSave = findViewById(R.id.buttonSave);

            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve job details
                    String jobRole = editTextJobRole.getText().toString();
                    String company = editTextCompany.getText().toString();
                    String location = editTextLocation.getText().toString();
                    String status = spinnerStatus.getSelectedItem().toString();

                    // Validate input
                    if (jobRole.isEmpty() || company.isEmpty() || location.isEmpty()) {
                        Toast.makeText(EditJobActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

//                    Job newJob = new Job(jobRole, status, company, location);
//                    JobStatusTree.addJob(newJob, status);
                    job.setJobRole(jobRole);
                    job.setCompanyName(company);
                    job.setJobLocation(location);
                    JobStatusTree.updateStatus(job, status);
                    //job.setJobStatus(status);

                    // Set result to indicate success and finish activity
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            });
        }



        // Method to find position of status in spinner
        private int getStatusPosition(String status) {
            String[] statusArray = getResources().getStringArray(R.array.job_statuses);
            for (int i = 0; i < statusArray.length; i++) {
                if (statusArray[i].equals(status)) {
                    return i;
                }
            }
            return 0; // Return 0 if status is not found
        }
    }
