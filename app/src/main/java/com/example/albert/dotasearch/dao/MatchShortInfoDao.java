package com.example.albert.dotasearch.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albert.dotasearch.model.MatchShortInfo;

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
