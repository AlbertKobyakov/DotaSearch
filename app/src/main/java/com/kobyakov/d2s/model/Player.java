package com.kobyakov.d2s.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Player {
    @SerializedName("match_id")
    @Expose
    private long matchId;
    @SerializedName("player_slot")
    @Expose
    private long playerSlot;
    @SerializedName("ability_upgrades_arr")
    @Expose
    private List<Long> abilityUpgradesArr = null;
    @SerializedName("account_id")
    @Expose
    private long accountId;
    @SerializedName("assists")
    @Expose
    private long assists;
    @SerializedName("backpack_0")
    @Expose
    private long backpack0;
    @SerializedName("backpack_1")
    @Expose
    private long backpack1;
    @SerializedName("backpack_2")
    @Expose
    private long backpack2;
    @SerializedName("deaths")
    @Expose
    private long deaths;
    @SerializedName("denies")
    @Expose
    private long denies;
    @SerializedName("gold")
    @Expose
    private long gold;
    @SerializedName("gold_per_min")
    @Expose
    private long goldPerMin;
    @SerializedName("gold_spent")
    @Expose
    private long goldSpent;
    @SerializedName("hero_damage")
    @Expose
    private long heroDamage;
    @SerializedName("hero_healing")
    @Expose
    private long heroHealing;
    @SerializedName("hero_id")
    @Expose
    private int heroId;
    @SerializedName("item_0")
    @Expose
    private long item0;
    @SerializedName("item_1")
    @Expose
    private long item1;
    @SerializedName("item_2")
    @Expose
    private long item2;
    @SerializedName("item_3")
    @Expose
    private long item3;
    @SerializedName("item_4")
    @Expose
    private long item4;
    @SerializedName("item_5")
    @Expose
    private long item5;
    @SerializedName("kills")
    @Expose
    private long kills;
    @SerializedName("last_hits")
    @Expose
    private long lastHits;
    @SerializedName("leaver_status")
    @Expose
    private long leaverStatus;
    @SerializedName("level")
    @Expose
    private long level;
    @SerializedName("tower_damage")
    @Expose
    private long towerDamage;
    @SerializedName("xp_per_min")
    @Expose
    private long xpPerMin;
    @SerializedName("personaname")
    @Expose
    private String personaname;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("radiant_win")
    @Expose
    private boolean radiantWin;
    @SerializedName("start_time")
    @Expose
    private long startTime;
    @SerializedName("duration")
    @Expose
    private long duration;
    @SerializedName("cluster")
    @Expose
    private long cluster;
    @SerializedName("lobby_type")
    @Expose
    private long lobbyType;
    @SerializedName("game_mode")
    @Expose
    private long gameMode;
    @SerializedName("patch")
    @Expose
    private long patch;
    @SerializedName("region")
    @Expose
    private long region;
    @SerializedName("isRadiant")
    @Expose
    private boolean isRadiant;
    @SerializedName("win")
    @Expose
    private long win;
    @SerializedName("lose")
    @Expose
    private long lose;
    @SerializedName("total_gold")
    @Expose
    private long totalGold;
    @SerializedName("total_xp")
    @Expose
    private long totalXp;
    @SerializedName("kills_per_min")
    @Expose
    private double killsPerMin;
    @SerializedName("kda")
    @Expose
    private long kda;
    @SerializedName("abandons")
    @Expose
    private long abandons;
    @SerializedName("rank_tier")
    @Expose
    private int rankTier;
    @SerializedName("randomed")
    @Expose
    private boolean randomed;

    @Override
    public String toString() {
        return "Player{" +
                "heroId=" + heroId +
                ", item0=" + item0 +
                ", item1=" + item1 +
                ", item2=" + item2 +
                ", item3=" + item3 +
                ", item4=" + item4 +
                ", item5=" + item5 +
                ", personaname='" + personaname + '\'' +
                ", name=" + name +
                '}';
    }

    public boolean isRandomed() {
        return randomed;
    }

    public void setRandomed(boolean randomed) {
        this.randomed = randomed;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(long playerSlot) {
        this.playerSlot = playerSlot;
    }

    public List<Long> getAbilityUpgradesArr() {
        return abilityUpgradesArr;
    }

    public void setAbilityUpgradesArr(List<Long> abilityUpgradesArr) {
        this.abilityUpgradesArr = abilityUpgradesArr;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getAssists() {
        return assists;
    }

    public void setAssists(long assists) {
        this.assists = assists;
    }

    public long getBackpack0() {
        return backpack0;
    }

    public void setBackpack0(long backpack0) {
        this.backpack0 = backpack0;
    }

    public long getBackpack1() {
        return backpack1;
    }

    public void setBackpack1(long backpack1) {
        this.backpack1 = backpack1;
    }

    public long getBackpack2() {
        return backpack2;
    }

    public void setBackpack2(long backpack2) {
        this.backpack2 = backpack2;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getDenies() {
        return denies;
    }

    public void setDenies(long denies) {
        this.denies = denies;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getGoldPerMin() {
        return goldPerMin;
    }

    public void setGoldPerMin(long goldPerMin) {
        this.goldPerMin = goldPerMin;
    }

    public long getGoldSpent() {
        return goldSpent;
    }

    public void setGoldSpent(long goldSpent) {
        this.goldSpent = goldSpent;
    }

    public long getHeroDamage() {
        return heroDamage;
    }

    public void setHeroDamage(long heroDamage) {
        this.heroDamage = heroDamage;
    }

    public long getHeroHealing() {
        return heroHealing;
    }

    public void setHeroHealing(long heroHealing) {
        this.heroHealing = heroHealing;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public long getItem0() {
        return item0;
    }

    public void setItem0(long item0) {
        this.item0 = item0;
    }

    public long getItem1() {
        return item1;
    }

    public void setItem1(long item1) {
        this.item1 = item1;
    }

    public long getItem2() {
        return item2;
    }

    public void setItem2(long item2) {
        this.item2 = item2;
    }

    public long getItem3() {
        return item3;
    }

    public void setItem3(long item3) {
        this.item3 = item3;
    }

    public long getItem4() {
        return item4;
    }

    public void setItem4(long item4) {
        this.item4 = item4;
    }

    public long getItem5() {
        return item5;
    }

    public void setItem5(long item5) {
        this.item5 = item5;
    }

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public long getLastHits() {
        return lastHits;
    }

    public void setLastHits(long lastHits) {
        this.lastHits = lastHits;
    }

    public long getLeaverStatus() {
        return leaverStatus;
    }

    public void setLeaverStatus(long leaverStatus) {
        this.leaverStatus = leaverStatus;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getTowerDamage() {
        return towerDamage;
    }

    public void setTowerDamage(long towerDamage) {
        this.towerDamage = towerDamage;
    }

    public long getXpPerMin() {
        return xpPerMin;
    }

    public void setXpPerMin(long xpPerMin) {
        this.xpPerMin = xpPerMin;
    }

    public String getPersonaname() {
        return personaname;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isRadiantWin() {
        return radiantWin;
    }

    public void setRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getCluster() {
        return cluster;
    }

    public void setCluster(long cluster) {
        this.cluster = cluster;
    }

    public long getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(long lobbyType) {
        this.lobbyType = lobbyType;
    }

    public long getGameMode() {
        return gameMode;
    }

    public void setGameMode(long gameMode) {
        this.gameMode = gameMode;
    }

    public long getPatch() {
        return patch;
    }

    public void setPatch(long patch) {
        this.patch = patch;
    }

    public long getRegion() {
        return region;
    }

    public void setRegion(long region) {
        this.region = region;
    }

    public boolean isIsRadiant() {
        return isRadiant;
    }

    public void setIsRadiant(boolean isRadiant) {
        this.isRadiant = isRadiant;
    }

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

    public long getTotalGold() {
        return totalGold;
    }

    public void setTotalGold(long totalGold) {
        this.totalGold = totalGold;
    }

    public long getTotalXp() {
        return totalXp;
    }

    public void setTotalXp(long totalXp) {
        this.totalXp = totalXp;
    }

    public double getKillsPerMin() {
        return killsPerMin;
    }

    public void setKillsPerMin(double killsPerMin) {
        this.killsPerMin = killsPerMin;
    }

    public long getKda() {
        return kda;
    }

    public void setKda(long kda) {
        this.kda = kda;
    }

    public long getAbandons() {
        return abandons;
    }

    public void setAbandons(long abandons) {
        this.abandons = abandons;
    }

    public int getRankTier() {
        return rankTier;
    }

    public void setRankTier(int rankTier) {
        this.rankTier = rankTier;
    }
}