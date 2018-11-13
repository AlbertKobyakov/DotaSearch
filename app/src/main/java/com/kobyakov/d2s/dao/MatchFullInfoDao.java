package com.kobyakov.d2s.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kobyakov.d2s.model.MatchFullInfo;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface MatchFullInfoDao {
    @Query("SELECT * FROM matchfullinfo")
    List<MatchFullInfo> getAll();

    @Query("SELECT * FROM matchfullinfo")
    Single<List<MatchFullInfo>> getAllRx();

    @Query("SELECT * FROM matchfullinfo")
    Maybe<List<MatchFullInfo>> checkMatches();

    @Query("SELECT * FROM matchfullinfo WHERE matchId == :id LIMIT 1")
    Single<MatchFullInfo> getMatchByIdRx(long id);

    @Query("SELECT * FROM matchfullinfo WHERE matchId == :id LIMIT 1")
    LiveData<MatchFullInfo> getMatchByIdRxLive(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MatchFullInfo matchFullInfoDaos);

    @Delete
    void delete(MatchFullInfo matchFullInfoDao);

    @Query("DELETE FROM matchfullinfo")
    void deleteAllMAtches();

    @Update
    void updateAll(List<MatchFullInfo> matchFullInfoDaos);
}
