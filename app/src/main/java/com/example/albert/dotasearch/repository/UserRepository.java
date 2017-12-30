package com.example.albert.dotasearch.repository;

import android.content.Context;
import android.util.Log;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.dao.ProPlayerDao;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.retrofit.DotaClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {
    DotaClient dotaClient;
    ProPlayerDao proPlayerDao;
    Context context;

    public UserRepository(DotaClient dotaClient, ProPlayerDao proPlayerDao, Context context) {
        this.dotaClient = dotaClient;
        this.proPlayerDao = proPlayerDao;
        this.context = context;
    }

    public Observable<List<ProPlayer>> getUsers() {
        return Observable.concat(
                getUsersFromDb(),
                getUsersFromApi())
                .subscribeOn(Schedulers.io());
    }

    private Observable<List<ProPlayer>> getUsersFromDb() {
        return proPlayerDao.getAllRx()
                .toObservable()
                .subscribeOn(Schedulers.io());
    }

    private Observable<List<ProPlayer>> getUsersFromApi() {
        return dotaClient.getAllProPlayerRx()
                .doOnNext(this::storeProPlayersInDB)
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(new ArrayList<>());
    }

    private void storeProPlayersInDB(List<ProPlayer> proPlayers){
        List<ProPlayer> proPlayersWithDefaultData = setProPlayerDefaultPersonalNameTeamNameAndAvatar(proPlayers);
        proPlayerDao.insertAll(proPlayersWithDefaultData);
        Log.e("ProPlayersInDB", "success");
    }

    public List<ProPlayer> setProPlayerDefaultPersonalNameTeamNameAndAvatar(List<ProPlayer> proPlayers){
        for(ProPlayer proPlayer : proPlayers){
            if(proPlayer.getPersonaname() == null || proPlayer.getPersonaname().trim().length() == 0){
                proPlayer.setPersonaname(context.getString(R.string.unknown));
            }
            if(proPlayer.getTeamName() == null || proPlayer.getTeamName().trim().length() == 0){
                proPlayer.setTeamName(context.getString(R.string.unknown));
            }
            if(proPlayer.getAvatarmedium() == null){
                proPlayer.setAvatarmedium("https://steamcdn-a.akamaihd.net/steamcommunity/public" +
                        "/images/avatars/fe/fef49e7fa7e1997310d705b2a6158ff8dc1cdfeb_medium.jpg");
            }
        }
        return proPlayers;
    }
}
