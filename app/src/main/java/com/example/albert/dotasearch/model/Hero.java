package com.example.albert.dotasearch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Hero {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private long id;
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
    @SerializedName("legs")
    @Expose
    private long legs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Hero withId(long id) {
        this.id = id;
        return this;
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

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", localizedName='" + localizedName + '\'' +
                '}';
    }
}
