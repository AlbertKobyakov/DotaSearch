package com.example.albert.dotasearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WinLose {

    @SerializedName("win")
    @Expose
    private long win;
    @SerializedName("lose")
    @Expose
    private long lose;

    public long getWin() {
        return win;
    }

    public void setWin(long win) {
        this.win = win;
    }

    public long getLose() {
        return lose;
    }

    public void setLose(long lose) {
        this.lose = lose;
    }

    @Override
    public String toString() {
        return "WinLose{" +
                "win=" + win +
                ", lose=" + lose +
                '}';
    }
}
