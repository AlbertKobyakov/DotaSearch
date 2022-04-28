package com.kobyakov.d2s.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.ProPlayer;

import java.util.List;

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
