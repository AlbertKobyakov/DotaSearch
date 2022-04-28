package com.kobyakov.d2s.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.FoundPlayer;

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
