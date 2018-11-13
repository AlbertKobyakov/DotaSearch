package com.kobyakov.d2s.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MmrEstimate {

    @SerializedName("estimate")
    @Expose
    private long estimate;

    public long getEstimate() {
        return estimate;
    }

    public void setEstimate(long estimate) {
        this.estimate = estimate;
    }

}
