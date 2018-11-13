package com.kobyakov.d2s.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class MatchFullInfo {
    @SerializedName("match_id")
    @Expose
    @PrimaryKey
    private long matchId;
    @SerializedName("barracks_status_dire")
    @Expose
    private long barracksStatusDire;
    @SerializedName("barracks_status_radiant")
    @Expose
    private long barracksStatusRadiant;
    @SerializedName("dire_score")
    @Expose
    private long direScore;
    @SerializedName("duration")
    @Expose
    private long duration;
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
    @SerializedName("picks_bans")
    @Expose
    private List<PicksBan> picksBans;
    @SerializedName("positive_votes")
    @Expose
    private long positiveVotes;
    @SerializedName("radiant_gold_adv")
    @Expose
    private List<Long> radiantGoldAdv;
    @SerializedName("radiant_score")
    @Expose
    private long radiantScore;
    @SerializedName("radiant_win")
    @Expose
    private boolean radiantWin;
    @SerializedName("radiant_xp_adv")
    @Expose
    private List<Long> radiantXpAdv;
    @SerializedName("skill")
    @Expose
    private long skill;
    @SerializedName("start_time")
    @Expose
    private long startTime;
    @SerializedName("tower_status_dire")
    @Expose
    private long towerStatusDire;
    @SerializedName("tower_status_radiant")
    @Expose
    private long towerStatusRadiant;
    @SerializedName("version")
    @Expose
    private long version;
    @SerializedName("players")
    @Expose
    private List<Player> players = null;
    @SerializedName("patch")
    @Expose
    private long patch;
    @SerializedName("region")
    @Expose
    private long region;
    @SerializedName("radiant_team")
    @Expose
    private RadiantTeam radiantTeam;
    @SerializedName("dire_team")
    @Expose
    private DireTeam direTeam;
    @SerializedName("league")
    @Expose
    private League league;
    @SerializedName("objectives")
    @Expose
    private List<Objective> objectives = null;
    @SerializedName("series_id")
    @Expose
    private long seriesId;
    @SerializedName("series_type")
    @Expose
    private long seriesType;

    public long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(long seriesId) {
        this.seriesId = seriesId;
    }

    public long getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(long seriesType) {
        this.seriesType = seriesType;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public List<PicksBan> getPicksBans() {
        return picksBans;
    }

    public void setPicksBans(List<PicksBan> picksBans) {
        this.picksBans = picksBans;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public RadiantTeam getRadiantTeam() {
        return radiantTeam;
    }

    public void setRadiantTeam(RadiantTeam radiantTeam) {
        this.radiantTeam = radiantTeam;
    }

    public DireTeam getDireTeam() {
        return direTeam;
    }

    public void setDireTeam(DireTeam direTeam) {
        this.direTeam = direTeam;
    }


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

    public long getDireScore() {
        return direScore;
    }

    public void setDireScore(long direScore) {
        this.direScore = direScore;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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

    public long getPositiveVotes() {
        return positiveVotes;
    }

    public void setPositiveVotes(long positiveVotes) {
        this.positiveVotes = positiveVotes;
    }

    public List<Long> getRadiantGoldAdv() {
        return radiantGoldAdv;
    }

    public void setRadiantGoldAdv(List<Long> radiantGoldAdv) {
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

    public List<Long> getRadiantXpAdv() {
        return radiantXpAdv;
    }

    public void setRadiantXpAdv(List<Long> radiantXpAdv) {
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

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
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

    public class KillsPerMin {
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

    public class LastHitsPerMin {
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

    public class TowerDamage {
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

    public class XpPerMin {
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

    public class DireTeam {
        @SerializedName("team_id")
        @Expose
        private long teamId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("tag")
        @Expose
        private String tag;
        @SerializedName("logo_url")
        @Expose
        private String logoUrl;

        public long getTeamId() {
            return teamId;
        }

        public void setTeamId(long teamId) {
            this.teamId = teamId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

    }

    public class RadiantTeam {
        @SerializedName("team_id")
        @Expose
        private long teamId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("tag")
        @Expose
        private String tag;
        @SerializedName("logo_url")
        @Expose
        private String logoUrl;

        public long getTeamId() {
            return teamId;
        }

        public void setTeamId(long teamId) {
            this.teamId = teamId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

    }
}