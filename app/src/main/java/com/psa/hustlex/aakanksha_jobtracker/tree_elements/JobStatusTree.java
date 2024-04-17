package com.psa.hustlex.aakanksha_jobtracker.tree_elements;

import com.psa.hustlex.aakanksha_jobtracker.models.Job;

public class JobStatusTree{
    private static Node root;

    public JobStatusTree(){
        setRoot(new Node("Jobs"));
        getRoot().addChild(new Node("Saved"));
        getRoot().addChild(new Node("Applied"));
        getRoot().addChild(new Node("Screening"));
        getRoot().addChild(new Node("Rejected"));
        getRoot().addChild(new Node("Offer"));
    }
    //find node
    public static Node findNode(String status) {
        return getRoot().getChild(status);
    }

    //add job to status
    public static void addJob(Job job, String status) {
        Node workingNode = findNode(status);
        if(workingNode!=null) {
            workingNode.addJobToList(job);
            workingNode.sortJobList(workingNode);
        }
        else {
            System.out.println("Node Not Found");
        }
    }
    public static void updateStatus(Job job, String newStatus) {
        Node currentNode = findNode(job.getJobStatus());
        Node newNode = findNode(newStatus);

        if(currentNode!=null&&newNode!=null) {
            newNode.addJobToList(job);
            currentNode.removeJob(job);
            job.setJobStatus(newStatus);
        }
        else {
            System.out.println("Update Status Action - FAILED");
        }


    }


    public static Node getRoot() {
        return root;
    }
    public void setRoot(Node root) {
        JobStatusTree.root = root;
    }

}
