package com.kobyakov.d2s.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.MatchShortInfo;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MatchShortInfoDao {

    @Query("SELECT * FROM matchshortinfo ORDER BY matchId DESC")
    LiveData<List<MatchShortInfo>> getAllMatch();

    @Query("SELECT * FROM matchshortinfo WHERE matchId == :matchId LIMIT 1")
    Single<MatchShortInfo> getMatchByIdRx(long matchId);

    @Query("DELETE FROM matchshortinfo")
    void deleteAllMatches();

    @Insert
    void insertAll(List<MatchShortInfo> matchshortinfo);

    @Delete
    void delete(MatchShortInfo matchshortinfo);

    @Update
    void updateAll(List<MatchShortInfo> matchshortinfo);
}
