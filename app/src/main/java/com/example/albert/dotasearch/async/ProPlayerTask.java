package com.example.albert.dotasearch.async;

import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.albert.dotasearch.adapter.ProPlayerAdapter;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.retrofit.DotaClient;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProPlayerTask extends AsyncTask<Void, Void, List<ProPlayer>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<ProPlayer> doInBackground(Void... voids) {
        final List<ProPlayer> proPlayers;
        DotaClient client = new UtilDota().initRetrofit();
        Call<List<ProPlayer>> call = client.getProPlayer();
        call.enqueue(new Callback<List<ProPlayer>>() {
            @Override
            public void onResponse(Call<List<ProPlayer>> call, Response<List<ProPlayer>> response) {
                //proPlayers = response.body();
                //Toast.makeText(view.getContext(), proPlayers.get(5).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<ProPlayer>> call, Throwable t) {
                Log.e("ERRRO", t.getMessage() + " " + t.getLocalizedMessage());
                //Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return null;
    }
}
