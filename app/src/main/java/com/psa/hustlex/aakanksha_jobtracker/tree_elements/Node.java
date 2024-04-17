package com.psa.hustlex.aakanksha_jobtracker.tree_elements;

import android.annotation.SuppressLint;

import com.psa.hustlex.aakanksha_jobtracker.data_structures.JobList;
import com.psa.hustlex.aakanksha_jobtracker.data_structures.NodeMap;
import com.psa.hustlex.aakanksha_jobtracker.models.Job;

public class Node {
    String status;
    JobList<Job> jobs;
    NodeMap<String, Node> children;

    //ctor
    public Node(String status) {
        this.status=status;
        this.jobs= new JobList<>();
        this.children=new NodeMap<>();
    }
    //add child node
    public void addChild(Node node) {
        children.put(node.status, node);
    }
    //get child node
    public Node getChild(String status) {
        return children.get(status);
    }

    //add job to a status node (adding to list)
    public void addJobToList(Job job) {
        jobs.add(job);
    }
    //remove job from a list
    public boolean removeJob(Job job) {
        return jobs.remove(job);
    }
    //returns the number of jobs in the node (status)
    public int getJobCount() {
        return jobs.size();
    }
    public JobList<Job> getJobs(){
        return jobs;
    }

    public Job searchJobById(String jobId) {
        for (Job job : jobs) {
            if (job.getJobId().equals(jobId)) {
                return job;
            }
        }
        return null; // Job not found
    }

    //Insertion Sort for JobList
    @SuppressLint("NewApi")
    public void sortJobList(Node node) {
        JobList<Job> jobs = node.jobs;
        int n = jobs.size();

        for (int i = 1; i < n; ++i) {
            Job current = jobs.get(i);
            int j = i - 1;

            // Move elements of jobs[0..i-1], that are greater than key.timeCreated, to one position ahead of their current position
            while (j >= 0 && jobs.get(j).getAppliedOn().isAfter(current.getAppliedOn())) {
                jobs.set(j + 1, jobs.get(j));
                j = j - 1;
            }
            jobs.set(j + 1, current);
        }
    }



}
