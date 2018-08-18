package com.example.albert.dotasearch.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.model.Item;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface FoundPlayerDao {
    @Query("SELECT * FROM foundplayer")
    List<FoundPlayer> getAll();

    @Query("SELECT * FROM foundplayer ORDER BY similarity DESC")
    Single<List<FoundPlayer>> getAllRx();

    @Query("SELECT * FROM foundplayer ORDER BY similarity DESC")
    LiveData<List<FoundPlayer>> getAllLiveData();

    @Query("SELECT * FROM foundplayer WHERE accountId == :key LIMIT 1")
    Single<FoundPlayer> getFoundPlayerByIdRx(long key);

    @Query("DELETE FROM foundplayer")
    void deleteAllFoundPlayer();

    @Insert
    void insertAll(List<FoundPlayer> foundPlayers);

    @Delete
    void delete(FoundPlayer foundPlayer);

    @Update
    void updateAll(List<FoundPlayer> foundPlayers);
}
