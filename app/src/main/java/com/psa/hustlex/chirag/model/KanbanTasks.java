package com.psa.hustlex.chirag.model;

import java.util.Date;
import java.util.UUID;
public class KanbanTasks {
    UUID taskId;
    String title;
    String content;
    String status; // notStarted, inProgress, completed
    Date date; // createdDate
    Date startTime;
    Date endTime;
    long duration;

    public KanbanTasks(String title, String content, String status, Date date, Date startTime, Date endTime) {
        this.taskId = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.status = status;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getTotalMinsWorked(){
        return (endTime.getTime() - startTime.getTime());
    }

    @Override
    public String toString() {
        return "KanbanTasks{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
