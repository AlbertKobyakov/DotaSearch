package com.example.albert.dotasearch.model;

import java.util.List;

public class MatchDetailWithItems {
    private MatchFullInfo matchFullInfo;
    private List<Item> items;
    private long matchId;

    public MatchDetailWithItems(MatchFullInfo matchFullInfo, List<Item> items, long matchId) {
        this.matchFullInfo = matchFullInfo;
        this.items = items;
        this.matchId = matchId;
    }

    public MatchFullInfo getMatchFullInfo() {
        return matchFullInfo;
    }

    public void setMatchFullInfo(MatchFullInfo matchFullInfo) {
        this.matchFullInfo = matchFullInfo;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public String toString() {
        return "MatchDetailWithItems{" +
                "matchFullInfo=" + matchFullInfo +
                ", items=" + items +
                ", matchId=" + matchId +
                '}';
    }
}
