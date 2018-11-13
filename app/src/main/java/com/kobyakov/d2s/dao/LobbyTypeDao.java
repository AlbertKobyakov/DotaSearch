package com.kobyakov.d2s.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kobyakov.d2s.model.LobbyType;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface LobbyTypeDao {
    @Query("SELECT * FROM lobbytype")
    List<LobbyType> getAll();

    @Query("SELECT * FROM lobbytype")
    Single<List<LobbyType>> getAllRx();

    @Query("SELECT * FROM lobbytype WHERE id == :id LIMIT 1")
    Single<LobbyType> getLobbyTypeByIdRx(long id);

    @Insert
    void insertAll(List<LobbyType> lobbyTypes);

    @Delete
    void delete(LobbyType lobbyType);

    @Update
    void updateAll(List<LobbyType> lobbyTypes);
}
