package com.psa.hustlex.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class LogEntry {
    private final String message;
    private final LocalDateTime timestamp;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LogEntry(String message) {
        this.message = message;
            this.timestamp = LocalDateTime.now();

    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp + " - " + message;
    }
}
