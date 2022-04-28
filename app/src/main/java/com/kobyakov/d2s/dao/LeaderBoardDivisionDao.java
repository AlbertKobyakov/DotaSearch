package com.kobyakov.d2s.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.TimeRefreshLeaderBoard;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface LeaderBoardDivisionDao {
    @Query("SELECT * FROM timerefreshleaderboard")
    TimeRefreshLeaderBoard getAll();

    @Query("SELECT * FROM timerefreshleaderboard")
    Single<List<TimeRefreshLeaderBoard>> getAllRx();

    @Query("SELECT * FROM timerefreshleaderboard WHERE division == :division LIMIT 1")
    TimeRefreshLeaderBoard getConcreteDivision(String division);

    @Query("SELECT * FROM timerefreshleaderboard WHERE division == :division LIMIT 1")
    LiveData<TimeRefreshLeaderBoard> getConcreteDivisionLive(String division);

    @Query("SELECT * FROM timerefreshleaderboard WHERE division == :division LIMIT 1")
    Maybe<TimeRefreshLeaderBoard> getConcreteDivisionRx(String division);

    @Insert
    void insertAll(TimeRefreshLeaderBoard timeRefreshLeaderBoard);

    @Delete
    void delete(TimeRefreshLeaderBoard timeRefreshLeaderBoard);

    @Update
    void updateAll(TimeRefreshLeaderBoard timeRefreshLeaderBoard);
}
