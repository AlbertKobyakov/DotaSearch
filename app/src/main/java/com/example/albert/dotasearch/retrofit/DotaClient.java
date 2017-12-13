package com.example.albert.dotasearch.retrofit;

import com.example.albert.dotasearch.model.FoundUser;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Match;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.model.TimeRefreshLeaderBoard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DotaClient {
    @GET("/api/proPlayers/")
    Call<List<ProPlayer>> getProPlayer();

    @GET("/api/search")
    Call<ArrayList<FoundUser>> getFoundUsers(
        @Query("q") String query
    );

    @GET("/webapi/ILeaderboard/GetDivisionLeaderboard/v0001")
    Call<TimeRefreshLeaderBoard> getLeaderBorder(
        @Query("division") String division
    );

    @GET("/api/players/{Id}/matches")
    Call<List<Match>> getMatchesPlayer(
        @Path("Id") long playerId,
        @Query("limit") int limit
    );

    @GET("/api/players/{Id}/matches")
    Call<List<Match>> getMatchesPlayer(
        @Path("Id") long playerId,
        @Query("limit") int limit,
        @Query("win") int win
    );

    @GET("/api/players/{Id}/matches")
    Observable<List<Match>> getListMatchesPlayerReact(
        @Path("Id") long playerId,
        @Query("limit") int limit
    );

    @GET("/api/players/{Id}/matches")
    Observable<List<Match>> getListWinMatchesPlayerReact(
            @Path("Id") long playerId,
            @Query("limit") int limit,
            @Query("win") int win
    );

    @GET("/api/heroes")
    Call<List<Hero>> getAllHeroes();

    @GET("/api/heroes")
    Observable<List<Hero>> getAllHeroesRx();
}
