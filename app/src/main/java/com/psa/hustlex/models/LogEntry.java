package com.psa.hustlex.models;

import java.io.Serializable;

public class LogEntry implements Serializable {
    private final String message;


    public LogEntry(String message) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }



    @Override
    public String toString() {
        return  message;
    }
}
