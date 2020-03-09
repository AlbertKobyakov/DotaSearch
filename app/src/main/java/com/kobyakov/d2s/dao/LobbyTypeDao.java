package com.kobyakov.d2s.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
