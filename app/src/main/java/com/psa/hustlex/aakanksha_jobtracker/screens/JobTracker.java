// JobTracker.java

package com.psa.hustlex.aakanksha_jobtracker.screens;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.psa.hustlex.R;
import com.psa.hustlex.aakanksha_jobtracker.models.Job;
import com.psa.hustlex.aakanksha_jobtracker.tree_elements.JobStatusTree;
import com.psa.hustlex.aakanksha_jobtracker.tree_elements.Node;

import java.time.LocalDate;

public class JobTracker extends AppCompatActivity {

    private JobStatusTree jobStatusTree;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_tracker);

        // Initialize job status tree
        jobStatusTree = new JobStatusTree();

        // Sample job data
        Job job1 = new Job("Software Engineer", "Applied", "Google", "Mountain View", LocalDate.of(2024, 3, 16));
        Job job2 = new Job("Product Manager", "Screening", "Facebook", "Menlo Park");
        Job job3 = new Job("Data Scientist", "Screening", "Amazon", "Seattle", LocalDate.of(2024, 3, 16));

        // Add jobs to different lists
        jobStatusTree.addJob(job1, job1.getJobStatus());
        jobStatusTree.addJob(job2, job2.getJobStatus());
        jobStatusTree.addJob(job3, job3.getJobStatus());

        // Display all job lists
        displayJobList("Saved", R.id.listSavedJobs);
        displayJobList("Applied", R.id.listAppliedJobs);
        displayJobList("Screening", R.id.listScreeningJobs);
        displayJobList("Rejected", R.id.listRejectedJobs);
        displayJobList("Offer", R.id.listOfferJobs);

        // Set onClickListener for Add buttons
        Button buttonAddJob = findViewById(R.id.buttonAddJob);
        buttonAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to AddJobActivity and expect a result
                Intent intent = new Intent(JobTracker.this, AddJobActivity.class);
                startActivityForResult(intent, 1); // 1 is a request code to identify the activity
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 || requestCode == 2) { // Check if the result is from AddJobActivity
            if (resultCode == RESULT_OK) { // Check if the result indicates success
                // Refresh the displayed job lists
                displayJobList("Saved", R.id.listSavedJobs);
                displayJobList("Applied", R.id.listAppliedJobs);
                displayJobList("Screening", R.id.listScreeningJobs);
                displayJobList("Rejected", R.id.listRejectedJobs);
                displayJobList("Offer", R.id.listOfferJobs);
            }
        }
    }

    private void displayJobList(String status, int layoutId) {
        Node node = jobStatusTree.findNode(status);
        if (node != null) {
            LinearLayout layout = findViewById(layoutId);
            layout.removeAllViews(); // Clear existing views

            for (final Job job : node.getJobs()) {
                View jobView = getLayoutInflater().inflate(R.layout.job_item, null);
                TextView textJobRole = jobView.findViewById(R.id.textJobRole);
                final LinearLayout layoutDetails = jobView.findViewById(R.id.layoutJobDetails);
                final TextView textJobLocation = jobView.findViewById(R.id.textJobLocation);
                final TextView textAppliedOn = jobView.findViewById(R.id.textAppliedOn);
                //final TextView textJobId = jobView.findViewById(R.id.textJobId);
                final TextView textCompany = jobView.findViewById(R.id.textCompany);
                // Set job details
                textJobRole.setText(job.getJobRole());
               // textJobId.setText("ID: " + job.getJobId());
                textCompany.setText("Company: " + job.getCompanyName());
                textJobLocation.setText("Location: " + job.getJobLocation());
                textAppliedOn.setText("Applied on: " + job.getAppliedOn());

                // Set OnClickListener for delete button
                ImageButton btnDelete = jobView.findViewById(R.id.btnDelete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Remove the job from the Node
                        node.removeJob(job);
                        // Remove the jobView from the layout
                        layout.removeView(jobView);
                        // Update the job count text view
                        TextView textCount = findViewById(getCountTextViewId(status));
                        textCount.setText("Count: " + node.getJobCount());
                        // Show toast message indicating success
                        Toast.makeText(JobTracker.this, "Job removed successfully", Toast.LENGTH_SHORT).show();
                    }
                });

                // Set OnClickListener for job item to toggle details visibility
                jobView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int visibility = layoutDetails.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                        layoutDetails.setVisibility(visibility);
                    }
                });
                ImageButton btnEdit = jobView.findViewById(R.id.btnEdit);

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Redirect to EditJobActivity and pass the job ID
                        Intent intent = new Intent(JobTracker.this, EditJobActivity.class);
                        intent.putExtra("jobId", job.getJobId());
                        intent.putExtra("statusNode", job.getJobStatus());
                        startActivityForResult(intent, 2); // 2 is a request code to identify the activity
                    }
                });
                // Add jobView to layout
                layout.addView(jobView);
            }

            // Set count of jobs in the list
            TextView textCount = findViewById(getCountTextViewId(status));
            textCount.setText("Count: " + node.getJobCount());
        }
    }

    // Helper method to get TextView ID for job count
    private int getCountTextViewId(String status) {
        switch (status) {
            case "Saved":
                return R.id.textSavedCount;
            case "Applied":
                return R.id.textAppliedCount;
            case "Screening":
                return R.id.textScreeningCount;
            case "Rejected":
                return R.id.textRejectedCount;
            case "Offer":
                return R.id.textOfferCount;
            default:
                return 0;
        }
    }
}
