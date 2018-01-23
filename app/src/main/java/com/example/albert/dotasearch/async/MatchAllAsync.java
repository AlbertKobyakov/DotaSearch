package com.example.albert.dotasearch.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.retrofit.DotaClient;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchAllAsync extends AsyncTask<Void, Void, List<MatchShortInfo>> {
    public List<MatchShortInfo> matchesWins;

    @Override
    protected List<MatchShortInfo> doInBackground(Void... voids) {
        getMatchesPlayer(354379549, 20);
        /*matchesWins = new ArrayList<>();
        MatchShortInfo match = new MatchShortInfo();
        match.setMatchId(44444444);
        matchesWins.add(match);*/
        return matchesWins;
    }

    public void getMatchesPlayer(long accountId, int limit) {
        DotaClient client = new UtilDota().initRetrofit("https://api.opendota.com");
        Call<List<MatchShortInfo>> call = client.getMatchesPlayer(accountId, limit);
        call.enqueue(new Callback<List<MatchShortInfo>>() {
            @Override
            public void onResponse(Call<List<MatchShortInfo>> call, Response<List<MatchShortInfo>> response) {
                matchesWins = response.body();
                Log.e("MatchAllAsync", "Success");
            }

            @Override
            public void onFailure(Call<List<MatchShortInfo>> call, Throwable t) {
                Log.e("MatchAllAsync", t.getMessage());
            }
        });
    }

    @Override
    protected void onPostExecute(List<MatchShortInfo> matches) {
        super.onPostExecute(matches);
        Log.e("ASync", "End");
    }
}
