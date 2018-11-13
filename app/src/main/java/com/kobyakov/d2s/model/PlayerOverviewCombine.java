package com.kobyakov.d2s.model;

public class PlayerOverviewCombine {
    private PlayerInfo playerInfo;
    private WinLose winLose;
    private long accountId;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public PlayerOverviewCombine(PlayerInfo playerInfo, WinLose winLose, /*List<MatchShortInfo> matches,*/ long accountId) {
        this.playerInfo = playerInfo;
        this.winLose = winLose;
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

    @Override
    public String toString() {
        return "PlayerOverviewCombine{" +
                "playerInfo=" + playerInfo +
                ", winLose=" + winLose +
                ", accountId=" + accountId +
                '}';
    }
}
