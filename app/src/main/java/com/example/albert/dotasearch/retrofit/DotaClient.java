package com.example.albert.dotasearch.retrofit;

import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.ItemsInfoWithSteam;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.model.PlayerHero;
import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.Record;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.model.Pros;
import com.example.albert.dotasearch.model.Team;
import com.example.albert.dotasearch.model.TimeRefreshLeaderBoard;
import com.example.albert.dotasearch.model.WinLose;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DotaClient {

    @GET("/api/proPlayers/")
    Single<List<ProPlayer>> getAllProPlayerRx();

    @GET("/api/proPlayers/")
    Single<Response<List<ProPlayer>>> getAllProPlayerResponse();

    @GET("/api/teams/")
    Single<List<Team>> getAllProTeam();

    @GET("/api/teams/")
    Single<Response<List<Team>>> getAllProTeamResponse();

    @GET("/api/search")
    Single<List<FoundPlayer>> getFoundPlayersRx(
            @Query("q") String query
    );

    @GET("/api/search")
    Single<Response<List<FoundPlayer>>> getFoundPlayersRxResponse(
            @Query("q") String query
    );

    @GET("/webapi/ILeaderboard/GetDivisionLeaderboard/v0001")
    Single<Response<TimeRefreshLeaderBoard>> getLeaderBorderRx(
            @Query("division") String division
    );

    @GET("/api/players/{Id}")
    Single<PlayerInfo> getPlayerInfoById(
            @Path("Id") long playerId
    );

    @GET("/api/players/{Id}/pros")
    Single<List<Pros>> getPlayerPros(
            @Path("Id") long playerId
    );

    @GET("/api/players/{Id}/pros")
    Single<Response<List<Pros>>> getPlayerProsResponse(
            @Path("Id") long playerId
    );

    @GET("/api/records/{title}")
    Single<List<Record>> getRecordsByTitle(
            @Path("title") String titleRecord
    );

    @GET("/api/records/{title}")
    Single<Response<List<Record>>> getRecordsByTitleResponse(
            @Path("title") String titleRecord
    );

    @GET("/api/players/{Id}/heroes")
    Single<List<PlayerHero>> getPlayerHeroes(
            @Path("Id") long playerId
    );

    @GET("/api/players/{Id}/heroes")
    Single<Response<List<PlayerHero>>> getPlayerHeroesResponse(
            @Path("Id") long playerId
    );

    @GET("/api/players/{Id}/wl")
    Single<WinLose> getPlayerWinLoseById(
            @Path("Id") long playerId
    );

    @GET("/api/players/{Id}/matches")
    Single<List<MatchShortInfo>> getMatchesPlayerRx(
            @Path("Id") long playerId/*,
            @Query("limit") int limit*/
    );

    @GET("/api/players/{Id}/matches")
    Single<Response<List<MatchShortInfo>>> getMatchesPlayerResponse(
            @Path("Id") long playerId
    );

    @GET("/api/heroStats")
    Single<List<Hero>> getAllHeroesRx();

    @GET("/api/heroStats")
    Single<Response<List<Hero>>> getAllHeroesRxResponse();

    @GET("/api/matches/{match_id}")
    Observable<MatchFullInfo> getMatchFullInfoRx(
            @Path("match_id") long matchId
    );

    @GET("/api/matches/{match_id}")
    Single<Response<MatchFullInfo>> getMatchFullInfoRxResponse(
            @Path("match_id") long matchId
    );

    @GET("/IEconDOTA2_570/GetGameItems/v0001/")
    Single<ItemsInfoWithSteam> getItemInfoSteamRx(
            @Query("key") String key
    );

    @GET("/IEconDOTA2_570/GetGameItems/v0001/")
    Single<Response<ItemsInfoWithSteam>> getItemInfoSteamRxResponse(
            @Query("key") String key
    );
}