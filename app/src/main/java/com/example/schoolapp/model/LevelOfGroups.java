package com.example.schoolapp.model;


public class LevelOfGroups {

    private int lvl;
    private String description;

    public LevelOfGroups(int level, String description) {

        this.lvl = level;
        this.description = description;
    }

    // Getters and setters

    public int getLvl() { return lvl; }
    public String getDescription() { return description; }
}