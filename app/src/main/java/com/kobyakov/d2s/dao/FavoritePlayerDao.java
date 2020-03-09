package com.kobyakov.d2s.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.FavoritePlayer;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface FavoritePlayerDao {
    @Query("SELECT * FROM favoriteplayer")
    List<FavoritePlayer> getAll();

    @Query("SELECT * FROM favoriteplayer")
    LiveData<List<FavoritePlayer>> getAllRx();

    @Query("SELECT * FROM favoriteplayer WHERE accountId == :key LIMIT 1")
    Single<FavoritePlayer> getFavoritePlayerByIdRx(long key);

    @Query("DELETE FROM favoriteplayer WHERE accountId == :key")
    void deleteById(long key);

    @Query("DELETE FROM favoriteplayer")
    void deleteAllFavoritePlayers();

    @Insert
    void insertPlayer (FavoritePlayer favoritePlayer);

    @Insert
    void insertAll(List<FavoritePlayer> favoritePlayers);

    @Delete
    void delete(FavoritePlayer favoritePlayer);

    @Update
    void updateAll(List<FavoritePlayer> favoritePlayers);
}
