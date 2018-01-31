package com.example.albert.dotasearch.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchFullInfo {

    @SerializedName("match_id")
    @Expose
    private long matchId;
    @SerializedName("barracks_status_dire")
    @Expose
    private long barracksStatusDire;
    @SerializedName("barracks_status_radiant")
    @Expose
    private long barracksStatusRadiant;
    @SerializedName("chat")
    @Expose
    private Object chat;
    @SerializedName("cluster")
    @Expose
    private long cluster;
    @SerializedName("cosmetics")
    @Expose
    private Object cosmetics;
    @SerializedName("dire_score")
    @Expose
    private long direScore;
    @SerializedName("draft_timings")
    @Expose
    private Object draftTimings;
    @SerializedName("duration")
    @Expose
    private long duration;
    @SerializedName("engine")
    @Expose
    private long engine;
    @SerializedName("first_blood_time")
    @Expose
    private long firstBloodTime;
    @SerializedName("game_mode")
    @Expose
    private long gameMode;
    @SerializedName("human_players")
    @Expose
    private long humanPlayers;
    @SerializedName("leagueid")
    @Expose
    private long leagueid;
    @SerializedName("lobby_type")
    @Expose
    private long lobbyType;
    @SerializedName("match_seq_num")
    @Expose
    private long matchSeqNum;
    @SerializedName("negative_votes")
    @Expose
    private long negativeVotes;
    @SerializedName("objectives")
    @Expose
    private Object objectives;
    @SerializedName("picks_bans")
    @Expose
    private Object picksBans;
    @SerializedName("positive_votes")
    @Expose
    private long positiveVotes;
    @SerializedName("radiant_gold_adv")
    @Expose
    private Object radiantGoldAdv;
    @SerializedName("radiant_score")
    @Expose
    private long radiantScore;
    @SerializedName("radiant_win")
    @Expose
    private boolean radiantWin;
    @SerializedName("radiant_xp_adv")
    @Expose
    private Object radiantXpAdv;
    @SerializedName("skill")
    @Expose
    private long skill;
    @SerializedName("start_time")
    @Expose
    private long startTime;
    @SerializedName("teamfights")
    @Expose
    private Object teamfights;
    @SerializedName("tower_status_dire")
    @Expose
    private long towerStatusDire;
    @SerializedName("tower_status_radiant")
    @Expose
    private long towerStatusRadiant;
    @SerializedName("version")
    @Expose
    private Object version;
    @SerializedName("players")
    @Expose
    private List<Player> players = null;
    @SerializedName("patch")
    @Expose
    private long patch;
    @SerializedName("region")
    @Expose
    private long region;

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getBarracksStatusDire() {
        return barracksStatusDire;
    }

    public void setBarracksStatusDire(long barracksStatusDire) {
        this.barracksStatusDire = barracksStatusDire;
    }

    public long getBarracksStatusRadiant() {
        return barracksStatusRadiant;
    }

    public void setBarracksStatusRadiant(long barracksStatusRadiant) {
        this.barracksStatusRadiant = barracksStatusRadiant;
    }

    public Object getChat() {
        return chat;
    }

    public void setChat(Object chat) {
        this.chat = chat;
    }

    public long getCluster() {
        return cluster;
    }

    public void setCluster(long cluster) {
        this.cluster = cluster;
    }

    public Object getCosmetics() {
        return cosmetics;
    }

    public void setCosmetics(Object cosmetics) {
        this.cosmetics = cosmetics;
    }

    public long getDireScore() {
        return direScore;
    }

    public void setDireScore(long direScore) {
        this.direScore = direScore;
    }

    public Object getDraftTimings() {
        return draftTimings;
    }

    public void setDraftTimings(Object draftTimings) {
        this.draftTimings = draftTimings;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getEngine() {
        return engine;
    }

    public void setEngine(long engine) {
        this.engine = engine;
    }

    public long getFirstBloodTime() {
        return firstBloodTime;
    }

    public void setFirstBloodTime(long firstBloodTime) {
        this.firstBloodTime = firstBloodTime;
    }

    public long getGameMode() {
        return gameMode;
    }

    public void setGameMode(long gameMode) {
        this.gameMode = gameMode;
    }

    public long getHumanPlayers() {
        return humanPlayers;
    }

    public void setHumanPlayers(long humanPlayers) {
        this.humanPlayers = humanPlayers;
    }

    public long getLeagueid() {
        return leagueid;
    }

    public void setLeagueid(long leagueid) {
        this.leagueid = leagueid;
    }

    public long getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(long lobbyType) {
        this.lobbyType = lobbyType;
    }

    public long getMatchSeqNum() {
        return matchSeqNum;
    }

    public void setMatchSeqNum(long matchSeqNum) {
        this.matchSeqNum = matchSeqNum;
    }

    public long getNegativeVotes() {
        return negativeVotes;
    }

    public void setNegativeVotes(long negativeVotes) {
        this.negativeVotes = negativeVotes;
    }

    public Object getObjectives() {
        return objectives;
    }

    public void setObjectives(Object objectives) {
        this.objectives = objectives;
    }

    public Object getPicksBans() {
        return picksBans;
    }

    public void setPicksBans(Object picksBans) {
        this.picksBans = picksBans;
    }

    public long getPositiveVotes() {
        return positiveVotes;
    }

    public void setPositiveVotes(long positiveVotes) {
        this.positiveVotes = positiveVotes;
    }

    public Object getRadiantGoldAdv() {
        return radiantGoldAdv;
    }

    public void setRadiantGoldAdv(Object radiantGoldAdv) {
        this.radiantGoldAdv = radiantGoldAdv;
    }

    public long getRadiantScore() {
        return radiantScore;
    }

    public void setRadiantScore(long radiantScore) {
        this.radiantScore = radiantScore;
    }

    public boolean isRadiantWin() {
        return radiantWin;
    }

    public void setRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
    }

    public Object getRadiantXpAdv() {
        return radiantXpAdv;
    }

    public void setRadiantXpAdv(Object radiantXpAdv) {
        this.radiantXpAdv = radiantXpAdv;
    }

    public long getSkill() {
        return skill;
    }

    public void setSkill(long skill) {
        this.skill = skill;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Object getTeamfights() {
        return teamfights;
    }

    public void setTeamfights(Object teamfights) {
        this.teamfights = teamfights;
    }

    public long getTowerStatusDire() {
        return towerStatusDire;
    }

    public void setTowerStatusDire(long towerStatusDire) {
        this.towerStatusDire = towerStatusDire;
    }

    public long getTowerStatusRadiant() {
        return towerStatusRadiant;
    }

    public void setTowerStatusRadiant(long towerStatusRadiant) {
        this.towerStatusRadiant = towerStatusRadiant;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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

    class Benchmarks {

        @SerializedName("gold_per_min")
        @Expose
        private GoldPerMin goldPerMin;
        @SerializedName("xp_per_min")
        @Expose
        private XpPerMin xpPerMin;
        @SerializedName("kills_per_min")
        @Expose
        private KillsPerMin killsPerMin;
        @SerializedName("last_hits_per_min")
        @Expose
        private LastHitsPerMin lastHitsPerMin;
        @SerializedName("hero_damage_per_min")
        @Expose
        private HeroDamagePerMin heroDamagePerMin;
        @SerializedName("hero_healing_per_min")
        @Expose
        private HeroHealingPerMin heroHealingPerMin;
        @SerializedName("tower_damage")
        @Expose
        private TowerDamage towerDamage;

        public GoldPerMin getGoldPerMin() {
            return goldPerMin;
        }

        public void setGoldPerMin(GoldPerMin goldPerMin) {
            this.goldPerMin = goldPerMin;
        }

        public XpPerMin getXpPerMin() {
            return xpPerMin;
        }

        public void setXpPerMin(XpPerMin xpPerMin) {
            this.xpPerMin = xpPerMin;
        }

        public KillsPerMin getKillsPerMin() {
            return killsPerMin;
        }

        public void setKillsPerMin(KillsPerMin killsPerMin) {
            this.killsPerMin = killsPerMin;
        }

        public LastHitsPerMin getLastHitsPerMin() {
            return lastHitsPerMin;
        }

        public void setLastHitsPerMin(LastHitsPerMin lastHitsPerMin) {
            this.lastHitsPerMin = lastHitsPerMin;
        }

        public HeroDamagePerMin getHeroDamagePerMin() {
            return heroDamagePerMin;
        }

        public void setHeroDamagePerMin(HeroDamagePerMin heroDamagePerMin) {
            this.heroDamagePerMin = heroDamagePerMin;
        }

        public HeroHealingPerMin getHeroHealingPerMin() {
            return heroHealingPerMin;
        }

        public void setHeroHealingPerMin(HeroHealingPerMin heroHealingPerMin) {
            this.heroHealingPerMin = heroHealingPerMin;
        }

        public TowerDamage getTowerDamage() {
            return towerDamage;
        }

        public void setTowerDamage(TowerDamage towerDamage) {
            this.towerDamage = towerDamage;
        }

    }
    class GoldPerMin {

        @SerializedName("raw")
        @Expose
        private long raw;
        @SerializedName("pct")
        @Expose
        private double pct;

        public long getRaw() {
            return raw;
        }

        public void setRaw(long raw) {
            this.raw = raw;
        }

        public double getPct() {
            return pct;
        }

        public void setPct(double pct) {
            this.pct = pct;
        }

    }
    class HeroDamagePerMin {

        @SerializedName("raw")
        @Expose
        private double raw;
        @SerializedName("pct")
        @Expose
        private double pct;

        public double getRaw() {
            return raw;
        }

        public void setRaw(double raw) {
            this.raw = raw;
        }

        public double getPct() {
            return pct;
        }

        public void setPct(double pct) {
            this.pct = pct;
        }

    }
    class HeroHealingPerMin {

        @SerializedName("raw")
        @Expose
        private double raw;
        @SerializedName("pct")
        @Expose
        private double pct;

        public double getRaw() {
            return raw;
        }

        public void setRaw(long raw) {
            this.raw = raw;
        }

        public double getPct() {
            return pct;
        }

        public void setPct(double pct) {
            this.pct = pct;
        }

    }
    class KillsPerMin {

        @SerializedName("raw")
        @Expose
        private double raw;
        @SerializedName("pct")
        @Expose
        private double pct;

        public double getRaw() {
            return raw;
        }

        public void setRaw(double raw) {
            this.raw = raw;
        }

        public double getPct() {
            return pct;
        }

        public void setPct(double pct) {
            this.pct = pct;
        }

    }
    class LastHitsPerMin {

        @SerializedName("raw")
        @Expose
        private double raw;
        @SerializedName("pct")
        @Expose
        private double pct;

        public double getRaw() {
            return raw;
        }

        public void setRaw(double raw) {
            this.raw = raw;
        }

        public double getPct() {
            return pct;
        }

        public void setPct(double pct) {
            this.pct = pct;
        }

    }

    class TowerDamage {

        @SerializedName("raw")
        @Expose
        private long raw;
        @SerializedName("pct")
        @Expose
        private double pct;

        public long getRaw() {
            return raw;
        }

        public void setRaw(long raw) {
            this.raw = raw;
        }

        public double getPct() {
            return pct;
        }

        public void setPct(double pct) {
            this.pct = pct;
        }

    }
    class XpPerMin {

        @SerializedName("raw")
        @Expose
        private long raw;
        @SerializedName("pct")
        @Expose
        private double pct;

        public long getRaw() {
            return raw;
        }

        public void setRaw(long raw) {
            this.raw = raw;
        }

        public double getPct() {
            return pct;
        }

        public void setPct(double pct) {
            this.pct = pct;
        }

    }

}

