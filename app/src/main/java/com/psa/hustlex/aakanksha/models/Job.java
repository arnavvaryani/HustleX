package com.psa.hustlex.aakanksha.models;

import android.annotation.SuppressLint;

import java.time.LocalDate;
import java.util.UUID;

public class Job {
    private String id;
    private String jobRole;
    private String jobStatus;
    private String companyName;
    private String jobLocation;
    private LocalDate appliedOn;

    // default
    @SuppressLint("NewApi")
    public Job(String jobRole, String companyName, String jobLocation) {
        this(jobRole, "Saved", companyName, jobLocation, LocalDate.now()); // Default status is Saved, appliedOn is current date
    }
    //
    @SuppressLint("NewApi")
    public Job(String jobRole, String jobStatus, String companyName, String jobLocation) {
        this(jobRole, jobStatus, companyName, jobLocation, LocalDate.now()); // Default appliedOn is current date
    }

    public Job(String jobRole, String jobStatus, String companyName, String jobLocation, LocalDate appliedOn) {
        this.jobRole = jobRole;
        this.jobStatus = jobStatus;
        this.companyName = companyName;
        this.jobLocation = jobLocation;
        this.appliedOn = appliedOn;
        this.id = UUID.randomUUID().toString();
    }


    // Getters and Setters
    public String getJobId() {
        return id;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public LocalDate getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(LocalDate appliedOn) {
        this.appliedOn = appliedOn;
    }

    // CRUD methods
    public void updateStatus(String status) {
        this.jobStatus = status;
    }

    @Override
    public String toString() {
        return "Job: " + jobRole +
                "\nCompany: "+ companyName +
                "\nLocation: " + jobLocation +
                "\nStatus: " + jobStatus +
                "\nApplied On: " + appliedOn;

    }

}
