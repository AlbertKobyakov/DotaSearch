package com.example.albert.dotasearch.async;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.albert.dotasearch.model.Match;
import com.example.albert.dotasearch.retrofit.DotaClient;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchAllAsync extends AsyncTask<Void, Void, List<Match>> {
    public List<Match> matchesWins;

    @Override
    protected List<Match> doInBackground(Void... voids) {
        getMatchesPlayer(354379549, 20);
        /*matchesWins = new ArrayList<>();
        Match match = new Match();
        match.setMatchId(44444444);
        matchesWins.add(match);*/
        return matchesWins;
    }

    public void getMatchesPlayer(long accountId, int limit) {
        DotaClient client = new UtilDota().initRetrofit("https://api.opendota.com");
        Call<List<Match>> call = client.getMatchesPlayer(accountId, limit);
        call.enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                matchesWins = response.body();
                Log.e("MatchAllAsync", "Success");
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                Log.e("MatchAllAsync", t.getMessage());
            }
        });
    }

    @Override
    protected void onPostExecute(List<Match> matches) {
        super.onPostExecute(matches);
        Log.e("ASync", "End");
    }
}
