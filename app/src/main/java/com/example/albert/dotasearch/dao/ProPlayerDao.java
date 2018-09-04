package com.example.albert.dotasearch.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albert.dotasearch.model.ProPlayer;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ProPlayerDao {
    @Query("SELECT * FROM proplayer")
    List<ProPlayer> getAll();

    @Query("SELECT * FROM proplayer")
    LiveData<List<ProPlayer>> getAllRx();

    @Query("SELECT * FROM proplayer")
    Single<List<ProPlayer>> getAllRx1();

    @Insert
    void insertAll(List<ProPlayer> proPlayer);

    @Delete
    void delete(ProPlayer proPlayer);

    @Update
    void updateAll(List<ProPlayer> proPlayer);
}
