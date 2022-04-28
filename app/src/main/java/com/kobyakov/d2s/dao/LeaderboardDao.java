package com.kobyakov.d2s.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.Leaderboard;

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
