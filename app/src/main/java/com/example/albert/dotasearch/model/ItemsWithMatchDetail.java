package com.example.albert.dotasearch.model;

import android.util.SparseArray;

public class ItemsWithMatchDetail {
    private MatchFullInfo matchFullInfo;
    private SparseArray<Item> items;
    private SparseArray<Hero> heroes;

    public ItemsWithMatchDetail(MatchFullInfo matchFullInfo, SparseArray<Item> items, SparseArray<Hero> heroes) {
        this.matchFullInfo = matchFullInfo;
        this.items = items;
        this.heroes = heroes;
    }

    public SparseArray<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(SparseArray<Hero> heroes) {
        this.heroes = heroes;
    }

    public MatchFullInfo getMatchFullInfo() {
        return matchFullInfo;
    }

    public void setMatchFullInfo(MatchFullInfo matchFullInfo) {
        this.matchFullInfo = matchFullInfo;
    }

    public SparseArray<Item> getItems() {
        return items;
    }

    public void setItems(SparseArray<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ItemsWithMatchDetail{" +
                "matchFullInfo=" + matchFullInfo +
                ", items=" + items +
                ", heroes=" + heroes +
                '}';
    }
}