package com.example.skeletune.data.model;

public class DayStatus {
    private String name;
    private boolean completed;

    public DayStatus(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
    }
    public String getName() { return name; }
    public boolean isCompleted() { return completed; }
}