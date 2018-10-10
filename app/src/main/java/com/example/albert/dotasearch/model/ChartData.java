package com.example.albert.dotasearch.model;

public class ChartData {
    private String title;
    private long value;
    private boolean isRadiant;

    public ChartData(String title, long value, boolean isRadiant) {
        this.title = title;
        this.value = value;
        this.isRadiant = isRadiant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public boolean isRadiant() {
        return isRadiant;
    }

    public void setRadiant(boolean radiant) {
        isRadiant = radiant;
    }
}
