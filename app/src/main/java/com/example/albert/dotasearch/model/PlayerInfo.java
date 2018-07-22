package com.example.albert.dotasearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerInfo {

    @SerializedName("tracked_until")
    @Expose
    private String trackedUntil;
    @SerializedName("solo_competitive_rank")
    @Expose
    private long soloCompetitiveRank;
    @SerializedName("rank_tier")
    @Expose
    private long rankTier;
    @SerializedName("leaderboard_rank")
    @Expose
    private long leaderboardRank;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("competitive_rank")
    @Expose
    private long competitiveRank;
    @SerializedName("mmr_estimate")
    @Expose
    private MmrEstimate mmrEstimate;

    public String getTrackedUntil() {
        return trackedUntil;
    }

    public void setTrackedUntil(String trackedUntil) {
        this.trackedUntil = trackedUntil;
    }

    public long getSoloCompetitiveRank() {
        return soloCompetitiveRank;
    }

    public void setSoloCompetitiveRank(long soloCompetitiveRank) {
        this.soloCompetitiveRank = soloCompetitiveRank;
    }

    public long getRankTier() {
        return rankTier;
    }

    public void setRankTier(long rankTier) {
        this.rankTier = rankTier;
    }

    public long getLeaderboardRank() {
        return leaderboardRank;
    }

    public void setLeaderboardRank(long leaderboardRank) {
        this.leaderboardRank = leaderboardRank;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public long getCompetitiveRank() {
        return competitiveRank;
    }

    public void setCompetitiveRank(long competitiveRank) {
        this.competitiveRank = competitiveRank;
    }

    public MmrEstimate getMmrEstimate() {
        return mmrEstimate;
    }

    public void setMmrEstimate(MmrEstimate mmrEstimate) {
        this.mmrEstimate = mmrEstimate;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "rankTier=" + rankTier +
                ", profile=" + profile +
                '}';
    }
}