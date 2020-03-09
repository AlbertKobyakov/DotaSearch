package com.kobyakov.d2s.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Hero {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("localized_name")
    @Expose
    private String localizedName;
    @SerializedName("primary_attr")
    @Expose
    private String primaryAttr;
    @SerializedName("attack_type")
    @Expose
    private String attackType;
    @SerializedName("roles")
    @Expose
    @Ignore
    private List<String> roles = null;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("base_health")
    @Expose
    private long baseHealth;
    @SerializedName("base_health_regen")
    @Expose
    private double baseHealthRegen;
    @SerializedName("base_mana")
    @Expose
    private long baseMana;
    @SerializedName("base_mana_regen")
    @Expose
    private double baseManaRegen;
    @SerializedName("base_armor")
    @Expose
    private double baseArmor;
    @SerializedName("base_mr")
    @Expose
    private long baseMr;
    @SerializedName("base_attack_min")
    @Expose
    private long baseAttackMin;
    @SerializedName("base_attack_max")
    @Expose
    private long baseAttackMax;
    @SerializedName("base_str")
    @Expose
    private long baseStr;
    @SerializedName("base_agi")
    @Expose
    private long baseAgi;
    @SerializedName("base_int")
    @Expose
    private long baseInt;
    @SerializedName("str_gain")
    @Expose
    private double strGain;
    @SerializedName("agi_gain")
    @Expose
    private double agiGain;
    @SerializedName("int_gain")
    @Expose
    private double intGain;
    @SerializedName("attack_range")
    @Expose
    private double attackRange;
    @SerializedName("projectile_speed")
    @Expose
    private long projectileSpeed;
    @SerializedName("attack_rate")
    @Expose
    private double attackRate;
    @SerializedName("move_speed")
    @Expose
    private long moveSpeed;
    @SerializedName("turn_rate")
    @Expose
    private double turnRate;
    @SerializedName("cm_enabled")
    @Expose
    private boolean cmEnabled;
    @SerializedName("legs")
    @Expose
    private long legs;
    @SerializedName("pro_ban")
    @Expose
    private long proBan;
    @SerializedName("hero_id")
    @Expose
    private long heroId;
    @SerializedName("pro_win")
    @Expose
    private long proWin;
    @SerializedName("pro_pick")
    @Expose
    private long proPick;
    @SerializedName("4_pick")
    @Expose
    private long _4Pick;
    @SerializedName("4_win")
    @Expose
    private long _4Win;
    @SerializedName("6_pick")
    @Expose
    private long _6Pick;
    @SerializedName("6_win")
    @Expose
    private long _6Win;
    @SerializedName("2_pick")
    @Expose
    private long _2Pick;
    @SerializedName("2_win")
    @Expose
    private long _2Win;
    @SerializedName("7_pick")
    @Expose
    private long _7Pick;
    @SerializedName("7_win")
    @Expose
    private long _7Win;
    @SerializedName("5_pick")
    @Expose
    private long _5Pick;
    @SerializedName("5_win")
    @Expose
    private long _5Win;
    @SerializedName("3_pick")
    @Expose
    private long _3Pick;
    @SerializedName("3_win")
    @Expose
    private long _3Win;
    @SerializedName("1_pick")
    @Expose
    private long _1Pick;
    @SerializedName("1_win")
    @Expose
    private long _1Win;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hero withName(String name) {
        this.name = name;
        return this;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public Hero withLocalizedName(String localizedName) {
        this.localizedName = localizedName;
        return this;
    }

    public String getPrimaryAttr() {
        return primaryAttr;
    }

    public void setPrimaryAttr(String primaryAttr) {
        this.primaryAttr = primaryAttr;
    }

    public Hero withPrimaryAttr(String primaryAttr) {
        this.primaryAttr = primaryAttr;
        return this;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public Hero withAttackType(String attackType) {
        this.attackType = attackType;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Hero withRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Hero withImg(String img) {
        this.img = img;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Hero withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public long getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(long baseHealth) {
        this.baseHealth = baseHealth;
    }

    public Hero withBaseHealth(long baseHealth) {
        this.baseHealth = baseHealth;
        return this;
    }

    public double getBaseHealthRegen() {
        return baseHealthRegen;
    }

    public void setBaseHealthRegen(double baseHealthRegen) {
        this.baseHealthRegen = baseHealthRegen;
    }

    public Hero withBaseHealthRegen(double baseHealthRegen) {
        this.baseHealthRegen = baseHealthRegen;
        return this;
    }

    public long getBaseMana() {
        return baseMana;
    }

    public void setBaseMana(long baseMana) {
        this.baseMana = baseMana;
    }

    public Hero withBaseMana(long baseMana) {
        this.baseMana = baseMana;
        return this;
    }

    public double getBaseManaRegen() {
        return baseManaRegen;
    }

    public void setBaseManaRegen(double baseManaRegen) {
        this.baseManaRegen = baseManaRegen;
    }

    public Hero withBaseManaRegen(double baseManaRegen) {
        this.baseManaRegen = baseManaRegen;
        return this;
    }

    public double getBaseArmor() {
        return baseArmor;
    }

    public void setBaseArmor(double baseArmor) {
        this.baseArmor = baseArmor;
    }

    public Hero withBaseArmor(long baseArmor) {
        this.baseArmor = baseArmor;
        return this;
    }

    public long getBaseMr() {
        return baseMr;
    }

    public void setBaseMr(long baseMr) {
        this.baseMr = baseMr;
    }

    public Hero withBaseMr(long baseMr) {
        this.baseMr = baseMr;
        return this;
    }

    public long getBaseAttackMin() {
        return baseAttackMin;
    }

    public void setBaseAttackMin(long baseAttackMin) {
        this.baseAttackMin = baseAttackMin;
    }

    public Hero withBaseAttackMin(long baseAttackMin) {
        this.baseAttackMin = baseAttackMin;
        return this;
    }

    public long getBaseAttackMax() {
        return baseAttackMax;
    }

    public void setBaseAttackMax(long baseAttackMax) {
        this.baseAttackMax = baseAttackMax;
    }

    public Hero withBaseAttackMax(long baseAttackMax) {
        this.baseAttackMax = baseAttackMax;
        return this;
    }

    public long getBaseStr() {
        return baseStr;
    }

    public void setBaseStr(long baseStr) {
        this.baseStr = baseStr;
    }

    public Hero withBaseStr(long baseStr) {
        this.baseStr = baseStr;
        return this;
    }

    public long getBaseAgi() {
        return baseAgi;
    }

    public void setBaseAgi(long baseAgi) {
        this.baseAgi = baseAgi;
    }

    public Hero withBaseAgi(long baseAgi) {
        this.baseAgi = baseAgi;
        return this;
    }

    public long getBaseInt() {
        return baseInt;
    }

    public void setBaseInt(long baseInt) {
        this.baseInt = baseInt;
    }

    public Hero withBaseInt(long baseInt) {
        this.baseInt = baseInt;
        return this;
    }

    public double getStrGain() {
        return strGain;
    }

    public void setStrGain(double strGain) {
        this.strGain = strGain;
    }

    public Hero withStrGain(long strGain) {
        this.strGain = strGain;
        return this;
    }

    public double getAgiGain() {
        return agiGain;
    }

    public void setAgiGain(double agiGain) {
        this.agiGain = agiGain;
    }

    public Hero withAgiGain(long agiGain) {
        this.agiGain = agiGain;
        return this;
    }

    public double getIntGain() {
        return intGain;
    }

    public void setIntGain(double intGain) {
        this.intGain = intGain;
    }

    public Hero withIntGain(double intGain) {
        this.intGain = intGain;
        return this;
    }

    public double getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(double attackRange) {
        this.attackRange = attackRange;
    }

    public Hero withAttackRange(long attackRange) {
        this.attackRange = attackRange;
        return this;
    }

    public long getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(long projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public Hero withProjectileSpeed(long projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
        return this;
    }

    public double getAttackRate() {
        return attackRate;
    }

    public void setAttackRate(double attackRate) {
        this.attackRate = attackRate;
    }

    public Hero withAttackRate(double attackRate) {
        this.attackRate = attackRate;
        return this;
    }

    public long getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(long moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public Hero withMoveSpeed(long moveSpeed) {
        this.moveSpeed = moveSpeed;
        return this;
    }

    public double getTurnRate() {
        return turnRate;
    }

    public void setTurnRate(double turnRate) {
        this.turnRate = turnRate;
    }

    public Hero withTurnRate(double turnRate) {
        this.turnRate = turnRate;
        return this;
    }

    public boolean isCmEnabled() {
        return cmEnabled;
    }

    public void setCmEnabled(boolean cmEnabled) {
        this.cmEnabled = cmEnabled;
    }

    public Hero withCmEnabled(boolean cmEnabled) {
        this.cmEnabled = cmEnabled;
        return this;
    }

    public long getLegs() {
        return legs;
    }

    public void setLegs(long legs) {
        this.legs = legs;
    }

    public Hero withLegs(long legs) {
        this.legs = legs;
        return this;
    }

    public long getProBan() {
        return proBan;
    }

    public void setProBan(long proBan) {
        this.proBan = proBan;
    }

    public Hero withProBan(long proBan) {
        this.proBan = proBan;
        return this;
    }

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public Hero withHeroId(long heroId) {
        this.heroId = heroId;
        return this;
    }

    public long getProWin() {
        return proWin;
    }

    public void setProWin(long proWin) {
        this.proWin = proWin;
    }

    public Hero withProWin(long proWin) {
        this.proWin = proWin;
        return this;
    }

    public long getProPick() {
        return proPick;
    }

    public void setProPick(long proPick) {
        this.proPick = proPick;
    }

    public Hero withProPick(long proPick) {
        this.proPick = proPick;
        return this;
    }

    public long get4Pick() {
        return _4Pick;
    }

    public void set4Pick(long _4Pick) {
        this._4Pick = _4Pick;
    }

    public Hero with4Pick(long _4Pick) {
        this._4Pick = _4Pick;
        return this;
    }

    public long get4Win() {
        return _4Win;
    }

    public void set4Win(long _4Win) {
        this._4Win = _4Win;
    }

    public Hero with4Win(long _4Win) {
        this._4Win = _4Win;
        return this;
    }

    public long get6Pick() {
        return _6Pick;
    }

    public void set6Pick(long _6Pick) {
        this._6Pick = _6Pick;
    }

    public Hero with6Pick(long _6Pick) {
        this._6Pick = _6Pick;
        return this;
    }

    public long get6Win() {
        return _6Win;
    }

    public void set6Win(long _6Win) {
        this._6Win = _6Win;
    }

    public Hero with6Win(long _6Win) {
        this._6Win = _6Win;
        return this;
    }

    public long get2Pick() {
        return _2Pick;
    }

    public void set2Pick(long _2Pick) {
        this._2Pick = _2Pick;
    }

    public Hero with2Pick(long _2Pick) {
        this._2Pick = _2Pick;
        return this;
    }

    public long get2Win() {
        return _2Win;
    }

    public void set2Win(long _2Win) {
        this._2Win = _2Win;
    }

    public Hero with2Win(long _2Win) {
        this._2Win = _2Win;
        return this;
    }

    public long get7Pick() {
        return _7Pick;
    }

    public void set7Pick(long _7Pick) {
        this._7Pick = _7Pick;
    }

    public Hero with7Pick(long _7Pick) {
        this._7Pick = _7Pick;
        return this;
    }

    public long get7Win() {
        return _7Win;
    }

    public void set7Win(long _7Win) {
        this._7Win = _7Win;
    }

    public Hero with7Win(long _7Win) {
        this._7Win = _7Win;
        return this;
    }

    public long get5Pick() {
        return _5Pick;
    }

    public void set5Pick(long _5Pick) {
        this._5Pick = _5Pick;
    }

    public Hero with5Pick(long _5Pick) {
        this._5Pick = _5Pick;
        return this;
    }

    public long get5Win() {
        return _5Win;
    }

    public void set5Win(long _5Win) {
        this._5Win = _5Win;
    }

    public Hero with5Win(long _5Win) {
        this._5Win = _5Win;
        return this;
    }

    public long get3Pick() {
        return _3Pick;
    }

    public void set3Pick(long _3Pick) {
        this._3Pick = _3Pick;
    }

    public Hero with3Pick(long _3Pick) {
        this._3Pick = _3Pick;
        return this;
    }

    public long get3Win() {
        return _3Win;
    }

    public void set3Win(long _3Win) {
        this._3Win = _3Win;
    }

    public Hero with3Win(long _3Win) {
        this._3Win = _3Win;
        return this;
    }

    public long get1Pick() {
        return _1Pick;
    }

    public void set1Pick(long _1Pick) {
        this._1Pick = _1Pick;
    }

    public Hero with1Pick(long _1Pick) {
        this._1Pick = _1Pick;
        return this;
    }

    public long get1Win() {
        return _1Win;
    }

    public void set1Win(long _1Win) {
        this._1Win = _1Win;
    }

    public Hero with1Win(long _1Win) {
        this._1Win = _1Win;
        return this;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", localizedName='" + localizedName + '\'' +
                ", img='" + img + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
