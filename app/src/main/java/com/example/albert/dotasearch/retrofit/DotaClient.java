package com.example.albert.dotasearch.retrofit;

import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.ItemsInfoWithSteam;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.model.TimeRefreshLeaderBoard;
import com.example.albert.dotasearch.model.WinLose;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DotaClient {
    @GET("/api/proPlayers/")
    Call<List<ProPlayer>> getProPlayer();

    @GET("/api/proPlayers/")
    Observable<List<ProPlayer>> getAllProPlayerRx();

    @GET("/api/search")
    Call<ArrayList<FoundPlayer>> getFoundUsers(
        @Query("q") String query
    );

    @GET("/api/search")
    Observable<List<FoundPlayer>> getFoundPlayersRx(
            @Query("q") String query
    );

    @GET("/webapi/ILeaderboard/GetDivisionLeaderboard/v0001")
    Call<TimeRefreshLeaderBoard> getLeaderBorder(
        @Query("division") String division
    );

    @GET("/webapi/ILeaderboard/GetDivisionLeaderboard/v0001")
    Observable<TimeRefreshLeaderBoard> getLeaderBorderRx(
            @Query("division") String division
    );

    @GET("/api/players/{Id}")
    Observable<PlayerInfo> getPlayerInfoById(
            @Path("Id") long playerId
    );

    @GET("/api/players/{Id}/wl")
    Observable<WinLose> getPlayerWinLoseById(
            @Path("Id") long playerId
    );

    @GET("/api/players/{Id}/matches")
    Call<List<MatchShortInfo>> getMatchesPlayer(
        @Path("Id") long playerId,
        @Query("limit") int limit
    );

    @GET("/api/players/{Id}/matches")
    Single<List<MatchShortInfo>> getMatchesPlayerRx(
            @Path("Id") long playerId,
            @Query("limit") int limit
    );

    @GET("/api/players/{Id}/matches")
    Call<List<MatchShortInfo>> getMatchesPlayer(
        @Path("Id") long playerId,
        @Query("limit") int limit,
        @Query("win") int win
    );

    @GET("/api/players/{Id}/matches")
    Observable<List<MatchShortInfo>> getListMatchesPlayerReact(
        @Path("Id") long playerId,
        @Query("limit") int limit
    );

    @GET("/api/players/{Id}/matches")
    Observable<List<MatchShortInfo>> getListWinMatchesPlayerReact(
            @Path("Id") long playerId,
            @Query("limit") int limit,
            @Query("win") int win
    );

    @GET("/api/heroStats")
    Single<List<Hero>> getAllHeroes();

    @GET("/api/heroStats")
    Observable<List<Hero>> getAllHeroesRx();

    @GET("/api/matches/{match_id}")
    Observable<MatchFullInfo> getMatchFullInfoRx(
            @Path("match_id") long matchId
    );

    @GET("/IEconDOTA2_570/GetGameItems/v0001/")
    Observable<ItemsInfoWithSteam> getItemInfoSteamRx(
            @Query("key") String key
    );


}
