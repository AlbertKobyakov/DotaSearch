package com.example.albert.dotasearch.model;

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
    @SerializedName("ability_targets")
    @Expose
    private Object abilityTargets;
    @SerializedName("ability_upgrades_arr")
    @Expose
    private List<Long> abilityUpgradesArr = null;
    @SerializedName("ability_uses")
    @Expose
    private Object abilityUses;
    @SerializedName("account_id")
    @Expose
    private long accountId;
    @SerializedName("actions")
    @Expose
    private Object actions;
    @SerializedName("additional_units")
    @Expose
    private Object additionalUnits;
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
    @SerializedName("buyback_log")
    @Expose
    private Object buybackLog;
    @SerializedName("camps_stacked")
    @Expose
    private Object campsStacked;
    @SerializedName("creeps_stacked")
    @Expose
    private Object creepsStacked;
    @SerializedName("damage")
    @Expose
    private Object damage;
    @SerializedName("damage_inflictor")
    @Expose
    private Object damageInflictor;
    @SerializedName("damage_inflictor_received")
    @Expose
    private Object damageInflictorReceived;
    @SerializedName("damage_taken")
    @Expose
    private Object damageTaken;
    @SerializedName("deaths")
    @Expose
    private long deaths;
    @SerializedName("denies")
    @Expose
    private long denies;
    @SerializedName("dn_t")
    @Expose
    private Object dnT;
    @SerializedName("firstblood_claimed")
    @Expose
    private Object firstbloodClaimed;
    @SerializedName("gold")
    @Expose
    private long gold;
    @SerializedName("gold_per_min")
    @Expose
    private long goldPerMin;
    @SerializedName("gold_reasons")
    @Expose
    private Object goldReasons;
    @SerializedName("gold_spent")
    @Expose
    private long goldSpent;
    @SerializedName("gold_t")
    @Expose
    private Object goldT;
    @SerializedName("hero_damage")
    @Expose
    private long heroDamage;
    @SerializedName("hero_healing")
    @Expose
    private long heroHealing;
    @SerializedName("hero_hits")
    @Expose
    private Object heroHits;
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
    @SerializedName("item_uses")
    @Expose
    private Object itemUses;
    @SerializedName("kill_streaks")
    @Expose
    private Object killStreaks;
    @SerializedName("killed")
    @Expose
    private Object killed;
    @SerializedName("killed_by")
    @Expose
    private Object killedBy;
    @SerializedName("kills")
    @Expose
    private long kills;
    @SerializedName("kills_log")
    @Expose
    private Object killsLog;
    @SerializedName("lane_pos")
    @Expose
    private Object lanePos;
    @SerializedName("last_hits")
    @Expose
    private long lastHits;
    @SerializedName("leaver_status")
    @Expose
    private long leaverStatus;
    @SerializedName("level")
    @Expose
    private long level;
    @SerializedName("lh_t")
    @Expose
    private Object lhT;
    @SerializedName("life_state")
    @Expose
    private Object lifeState;
    @SerializedName("max_hero_hit")
    @Expose
    private Object maxHeroHit;
    @SerializedName("multi_kills")
    @Expose
    private Object multiKills;
    @SerializedName("obs")
    @Expose
    private Object obs;
    @SerializedName("obs_left_log")
    @Expose
    private Object obsLeftLog;
    @SerializedName("obs_log")
    @Expose
    private Object obsLog;
    @SerializedName("obs_placed")
    @Expose
    private Object obsPlaced;
    @SerializedName("party_id")
    @Expose
    private Object partyId;
    @SerializedName("party_size")
    @Expose
    private Object partySize;
    @SerializedName("performance_others")
    @Expose
    private Object performanceOthers;
    @SerializedName("permanent_buffs")
    @Expose
    private Object permanentBuffs;
    @SerializedName("pings")
    @Expose
    private Object pings;
    @SerializedName("pred_vict")
    @Expose
    private Object predVict;
    @SerializedName("purchase")
    @Expose
    private Object purchase;
    @SerializedName("purchase_log")
    @Expose
    private Object purchaseLog;
    @SerializedName("randomed")
    @Expose
    private Object randomed;
    @SerializedName("repicked")
    @Expose
    private Object repicked;
    @SerializedName("roshans_killed")
    @Expose
    private Object roshansKilled;
    @SerializedName("rune_pickups")
    @Expose
    private Object runePickups;
    @SerializedName("runes")
    @Expose
    private Object runes;
    @SerializedName("runes_log")
    @Expose
    private Object runesLog;
    @SerializedName("sen")
    @Expose
    private Object sen;
    @SerializedName("sen_left_log")
    @Expose
    private Object senLeftLog;
    @SerializedName("sen_log")
    @Expose
    private Object senLog;
    @SerializedName("sen_placed")
    @Expose
    private Object senPlaced;
    @SerializedName("stuns")
    @Expose
    private Object stuns;
    @SerializedName("teamfight_participation")
    @Expose
    private Object teamfightParticipation;
    @SerializedName("times")
    @Expose
    private Object times;
    @SerializedName("tower_damage")
    @Expose
    private long towerDamage;
    @SerializedName("towers_killed")
    @Expose
    private Object towersKilled;
    @SerializedName("xp_per_min")
    @Expose
    private long xpPerMin;
    @SerializedName("xp_reasons")
    @Expose
    private Object xpReasons;
    @SerializedName("xp_t")
    @Expose
    private Object xpT;
    @SerializedName("personaname")
    @Expose
    private String personaname;
    @SerializedName("name")
    @Expose
    private Object name;
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
    @SerializedName("cosmetics")
    @Expose
    private List<Object> cosmetics = null;
    @SerializedName("benchmarks")
    @Expose
    private MatchFullInfo.Benchmarks benchmarks;

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

    public Object getAbilityTargets() {
        return abilityTargets;
    }

    public void setAbilityTargets(Object abilityTargets) {
        this.abilityTargets = abilityTargets;
    }

    public List<Long> getAbilityUpgradesArr() {
        return abilityUpgradesArr;
    }

    public void setAbilityUpgradesArr(List<Long> abilityUpgradesArr) {
        this.abilityUpgradesArr = abilityUpgradesArr;
    }

    public Object getAbilityUses() {
        return abilityUses;
    }

    public void setAbilityUses(Object abilityUses) {
        this.abilityUses = abilityUses;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Object getActions() {
        return actions;
    }

    public void setActions(Object actions) {
        this.actions = actions;
    }

    public Object getAdditionalUnits() {
        return additionalUnits;
    }

    public void setAdditionalUnits(Object additionalUnits) {
        this.additionalUnits = additionalUnits;
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

    public Object getBuybackLog() {
        return buybackLog;
    }

    public void setBuybackLog(Object buybackLog) {
        this.buybackLog = buybackLog;
    }

    public Object getCampsStacked() {
        return campsStacked;
    }

    public void setCampsStacked(Object campsStacked) {
        this.campsStacked = campsStacked;
    }

    public Object getCreepsStacked() {
        return creepsStacked;
    }

    public void setCreepsStacked(Object creepsStacked) {
        this.creepsStacked = creepsStacked;
    }

    public Object getDamage() {
        return damage;
    }

    public void setDamage(Object damage) {
        this.damage = damage;
    }

    public Object getDamageInflictor() {
        return damageInflictor;
    }

    public void setDamageInflictor(Object damageInflictor) {
        this.damageInflictor = damageInflictor;
    }

    public Object getDamageInflictorReceived() {
        return damageInflictorReceived;
    }

    public void setDamageInflictorReceived(Object damageInflictorReceived) {
        this.damageInflictorReceived = damageInflictorReceived;
    }

    public Object getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(Object damageTaken) {
        this.damageTaken = damageTaken;
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

    public Object getDnT() {
        return dnT;
    }

    public void setDnT(Object dnT) {
        this.dnT = dnT;
    }

    public Object getFirstbloodClaimed() {
        return firstbloodClaimed;
    }

    public void setFirstbloodClaimed(Object firstbloodClaimed) {
        this.firstbloodClaimed = firstbloodClaimed;
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

    public Object getGoldReasons() {
        return goldReasons;
    }

    public void setGoldReasons(Object goldReasons) {
        this.goldReasons = goldReasons;
    }

    public long getGoldSpent() {
        return goldSpent;
    }

    public void setGoldSpent(long goldSpent) {
        this.goldSpent = goldSpent;
    }

    public Object getGoldT() {
        return goldT;
    }

    public void setGoldT(Object goldT) {
        this.goldT = goldT;
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

    public Object getHeroHits() {
        return heroHits;
    }

    public void setHeroHits(Object heroHits) {
        this.heroHits = heroHits;
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

    public Object getItemUses() {
        return itemUses;
    }

    public void setItemUses(Object itemUses) {
        this.itemUses = itemUses;
    }

    public Object getKillStreaks() {
        return killStreaks;
    }

    public void setKillStreaks(Object killStreaks) {
        this.killStreaks = killStreaks;
    }

    public Object getKilled() {
        return killed;
    }

    public void setKilled(Object killed) {
        this.killed = killed;
    }

    public Object getKilledBy() {
        return killedBy;
    }

    public void setKilledBy(Object killedBy) {
        this.killedBy = killedBy;
    }

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public Object getKillsLog() {
        return killsLog;
    }

    public void setKillsLog(Object killsLog) {
        this.killsLog = killsLog;
    }

    public Object getLanePos() {
        return lanePos;
    }

    public void setLanePos(Object lanePos) {
        this.lanePos = lanePos;
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

    public Object getLhT() {
        return lhT;
    }

    public void setLhT(Object lhT) {
        this.lhT = lhT;
    }

    public Object getLifeState() {
        return lifeState;
    }

    public void setLifeState(Object lifeState) {
        this.lifeState = lifeState;
    }

    public Object getMaxHeroHit() {
        return maxHeroHit;
    }

    public void setMaxHeroHit(Object maxHeroHit) {
        this.maxHeroHit = maxHeroHit;
    }

    public Object getMultiKills() {
        return multiKills;
    }

    public void setMultiKills(Object multiKills) {
        this.multiKills = multiKills;
    }

    public Object getObs() {
        return obs;
    }

    public void setObs(Object obs) {
        this.obs = obs;
    }

    public Object getObsLeftLog() {
        return obsLeftLog;
    }

    public void setObsLeftLog(Object obsLeftLog) {
        this.obsLeftLog = obsLeftLog;
    }

    public Object getObsLog() {
        return obsLog;
    }

    public void setObsLog(Object obsLog) {
        this.obsLog = obsLog;
    }

    public Object getObsPlaced() {
        return obsPlaced;
    }

    public void setObsPlaced(Object obsPlaced) {
        this.obsPlaced = obsPlaced;
    }

    public Object getPartyId() {
        return partyId;
    }

    public void setPartyId(Object partyId) {
        this.partyId = partyId;
    }

    public Object getPartySize() {
        return partySize;
    }

    public void setPartySize(Object partySize) {
        this.partySize = partySize;
    }

    public Object getPerformanceOthers() {
        return performanceOthers;
    }

    public void setPerformanceOthers(Object performanceOthers) {
        this.performanceOthers = performanceOthers;
    }

    public Object getPermanentBuffs() {
        return permanentBuffs;
    }

    public void setPermanentBuffs(Object permanentBuffs) {
        this.permanentBuffs = permanentBuffs;
    }

    public Object getPings() {
        return pings;
    }

    public void setPings(Object pings) {
        this.pings = pings;
    }

    public Object getPredVict() {
        return predVict;
    }

    public void setPredVict(Object predVict) {
        this.predVict = predVict;
    }

    public Object getPurchase() {
        return purchase;
    }

    public void setPurchase(Object purchase) {
        this.purchase = purchase;
    }

    public Object getPurchaseLog() {
        return purchaseLog;
    }

    public void setPurchaseLog(Object purchaseLog) {
        this.purchaseLog = purchaseLog;
    }

    public Object getRandomed() {
        return randomed;
    }

    public void setRandomed(Object randomed) {
        this.randomed = randomed;
    }

    public Object getRepicked() {
        return repicked;
    }

    public void setRepicked(Object repicked) {
        this.repicked = repicked;
    }

    public Object getRoshansKilled() {
        return roshansKilled;
    }

    public void setRoshansKilled(Object roshansKilled) {
        this.roshansKilled = roshansKilled;
    }

    public Object getRunePickups() {
        return runePickups;
    }

    public void setRunePickups(Object runePickups) {
        this.runePickups = runePickups;
    }

    public Object getRunes() {
        return runes;
    }

    public void setRunes(Object runes) {
        this.runes = runes;
    }

    public Object getRunesLog() {
        return runesLog;
    }

    public void setRunesLog(Object runesLog) {
        this.runesLog = runesLog;
    }

    public Object getSen() {
        return sen;
    }

    public void setSen(Object sen) {
        this.sen = sen;
    }

    public Object getSenLeftLog() {
        return senLeftLog;
    }

    public void setSenLeftLog(Object senLeftLog) {
        this.senLeftLog = senLeftLog;
    }

    public Object getSenLog() {
        return senLog;
    }

    public void setSenLog(Object senLog) {
        this.senLog = senLog;
    }

    public Object getSenPlaced() {
        return senPlaced;
    }

    public void setSenPlaced(Object senPlaced) {
        this.senPlaced = senPlaced;
    }

    public Object getStuns() {
        return stuns;
    }

    public void setStuns(Object stuns) {
        this.stuns = stuns;
    }

    public Object getTeamfightParticipation() {
        return teamfightParticipation;
    }

    public void setTeamfightParticipation(Object teamfightParticipation) {
        this.teamfightParticipation = teamfightParticipation;
    }

    public Object getTimes() {
        return times;
    }

    public void setTimes(Object times) {
        this.times = times;
    }

    public long getTowerDamage() {
        return towerDamage;
    }

    public void setTowerDamage(long towerDamage) {
        this.towerDamage = towerDamage;
    }

    public Object getTowersKilled() {
        return towersKilled;
    }

    public void setTowersKilled(Object towersKilled) {
        this.towersKilled = towersKilled;
    }

    public long getXpPerMin() {
        return xpPerMin;
    }

    public void setXpPerMin(long xpPerMin) {
        this.xpPerMin = xpPerMin;
    }

    public Object getXpReasons() {
        return xpReasons;
    }

    public void setXpReasons(Object xpReasons) {
        this.xpReasons = xpReasons;
    }

    public Object getXpT() {
        return xpT;
    }

    public void setXpT(Object xpT) {
        this.xpT = xpT;
    }

    public String getPersonaname() {
        return personaname;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
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

    public List<Object> getCosmetics() {
        return cosmetics;
    }

    public void setCosmetics(List<Object> cosmetics) {
        this.cosmetics = cosmetics;
    }

    public MatchFullInfo.Benchmarks getBenchmarks() {
        return benchmarks;
    }

    public void setBenchmarks(MatchFullInfo.Benchmarks benchmarks) {
        this.benchmarks = benchmarks;
    }

}
