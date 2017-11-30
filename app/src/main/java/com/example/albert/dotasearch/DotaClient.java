package com.example.albert.dotasearch;

import com.example.albert.dotasearch.model.FoundUser;
import com.example.albert.dotasearch.model.ProPlayer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DotaClient {
    @GET("/api/proPlayers/")
    Call<List<ProPlayer>> getProPlayer();

    @GET("/api/search")
    Call<ArrayList<FoundUser>> getFoundUsers(
            @Query("q") String query
    );
}
