package com.example.albert.dotasearch.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albert.dotasearch.model.Leaderboard;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface LeaderboardDao {
    @Query("SELECT * FROM leaderboard")
    List<Leaderboard> getAll();

    @Query("SELECT * FROM leaderboard")
    Single<List<Leaderboard>> getAllRx();

    @Insert
    void insertAll(List<Leaderboard> leaderboards);

    @Delete
    void delete(Leaderboard leaderboard);

    @Update
    void updateAll(List<Leaderboard> leaderboards);
}
