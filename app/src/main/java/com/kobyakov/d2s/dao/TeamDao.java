package com.kobyakov.d2s.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.Team;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface TeamDao {
    @Query("SELECT * FROM team")
    List<Team> getAllTeam();

    @Query("SELECT * FROM team")
    Single<List<Team>> getAllTeamRx();

    @Query("SELECT * FROM team ORDER BY wins DESC")
    LiveData<List<Team>> getAllTeamLiveData();

    @Query("SELECT * FROM team WHERE teamId == :id LIMIT 1")
    Single<Team> getTeamByIdRx(long id);

    @Insert
    void insertAll(List<Team> teams);

    @Delete
    void delete(Team team);

    @Update
    void updateAll(List<Team> teams);
}
