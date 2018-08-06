package com.example.albert.dotasearch.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albert.dotasearch.model.HeaderPlayerContainsWinLoseAndPlayerInfo;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface HeaderPlayerContainsWinLoseAndPlayerInfoDao {
    @Query("SELECT * FROM headerplayercontainswinloseandplayerinfo")
    List<HeaderPlayerContainsWinLoseAndPlayerInfo> getAll();

    @Query("SELECT * FROM headerplayercontainswinloseandplayerinfo")
    Single<List<HeaderPlayerContainsWinLoseAndPlayerInfo>> getAllRx();

    @Query("SELECT * FROM headerplayercontainswinloseandplayerinfo WHERE playerId == :id LIMIT 1")
    HeaderPlayerContainsWinLoseAndPlayerInfo getHeaderPlayerInfoByIdRx(long id);

    @Insert
    void insertAll(List<HeaderPlayerContainsWinLoseAndPlayerInfo> headerPlayerContainsWinLoseAndPlayerInfoList);

    @Insert
    void insert(HeaderPlayerContainsWinLoseAndPlayerInfo headerPlayerContainsWinLoseAndPlayerInfoList);

    @Delete
    void delete(HeaderPlayerContainsWinLoseAndPlayerInfo headerPlayerInfo);

    @Query("DELETE FROM headerplayercontainswinloseandplayerinfo")
    void deleteAll();

    @Update
    void updateAll(List<HeaderPlayerContainsWinLoseAndPlayerInfo> headerPlayerInfo);
}
