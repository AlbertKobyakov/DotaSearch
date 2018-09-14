package com.example.albert.dotasearch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class UpdateInfoDB {
    @PrimaryKey
    private int idTable;
    private String nameTable;
    private long currentTimeMillis;

    public UpdateInfoDB() {
    }

    @Ignore
    public UpdateInfoDB(int idTable, String nameTable, long currentTimeMillis) {
        this.idTable = idTable;
        this.nameTable = nameTable;
        this.currentTimeMillis = currentTimeMillis;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public void setCurrentTimeMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public String toString() {
        return "UpdateInfoDB{" +
                "idTable=" + idTable +
                ", nameTable='" + nameTable + '\'' +
                ", currentTimeMillis=" + currentTimeMillis +
                '}';
    }
}
