package com.example.albert.dotasearch.model;

import java.util.List;

public class PlayerOverviewCombine {
    private PlayerInfo playerInfo;
    private WinLose winLose;
    private List<MatchShortInfo> matches;
    private long accountId;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public PlayerOverviewCombine(PlayerInfo playerInfo, WinLose winLose, List<MatchShortInfo> matches, long accountId) {
        this.playerInfo = playerInfo;
        this.winLose = winLose;
        this.matches = matches;
        this.accountId = accountId;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public WinLose getWinLose() {
        return winLose;
    }

    public void setWinLose(WinLose winLose) {
        this.winLose = winLose;
    }

    public List<MatchShortInfo> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchShortInfo> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "PlayerOverviewCombine{" +
                "playerInfo=" + playerInfo +
                ", winLose=" + winLose +
                ", matches=" + matches +
                ", accountId=" + accountId +
                '}';
    }
}
