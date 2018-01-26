package com.example.albert.dotasearch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Item {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cost")
    @Expose
    private long cost;
    @SerializedName("secret_shop")
    @Expose
    private long secretShop;
    @SerializedName("side_shop")
    @Expose
    private long sideShop;
    @SerializedName("recipe")
    @Expose
    private long recipe;

    private String itemUrl;

    public Item(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getSecretShop() {
        return secretShop;
    }

    public void setSecretShop(long secretShop) {
        this.secretShop = secretShop;
    }

    public long getSideShop() {
        return sideShop;
    }

    public void setSideShop(long sideShop) {
        this.sideShop = sideShop;
    }

    public long getRecipe() {
        return recipe;
    }

    public void setRecipe(long recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                '}';
    }
}