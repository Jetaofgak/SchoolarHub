package com.example.schoolapp;

import java.util.Date;

public class Homework {
    private Date dueDate;
    private String description;



    public Homework(Date due, String desc) {
        this.dueDate = due;
        this.description = desc; // Fixed: Assign desc to description
    }

    public Homework(Homework hw) {
        this.dueDate = hw.dueDate;
        this.description = hw.description;
    }

    public Date getDueDate() {
        return new Date(dueDate.getTime());
    }

    public String getDescription() {
        return description;
    }


}