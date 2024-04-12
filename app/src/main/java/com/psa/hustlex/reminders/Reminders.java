package com.psa.hustlex.reminders;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Reminders implements Comparable<Reminders> {
    @PrimaryKey
    private int id;
    private String message;
    private Date remindDate;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Reminders other) {
        return this.remindDate.compareTo(other.getRemindDate());
    }
}
