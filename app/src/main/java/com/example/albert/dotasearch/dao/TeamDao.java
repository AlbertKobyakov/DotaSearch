package com.example.albert.dotasearch.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albert.dotasearch.model.Team;

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
