package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.util.SparseArray;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Record;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecordRepository {
    private static final String TAG = "RecordRepository";
    /*private static final int TABLE_ID_FOR_UPDATE = Table.RECORD.ordinal();
    private static final String TABLE_NAME_FOR_UPDATE = Table.RECORD.name();
    private static final long MILLIS_IN_WEEK = 604800000;*/

    private AppDatabase db;
    private MutableLiveData<List<Record>> recordsMutable;
    private LiveData<List<Record>> recordListLiveData;
    private String titleRecord;
    private List<Record> emptyRecordList = new ArrayList<>();

    public RecordRepository(String recordTitle) {
        this.titleRecord = recordTitle;
        db = App.get().getDB();
        //recordListLiveData = db.recordDao().getAllRecordsByTitleLiveData(recordTitle);
        recordListLiveData = getRecordsMutable();
    }

    public LiveData<List<Record>> getRecordListLiveData() {
        return recordListLiveData;
    }

    /*private void recordDataAcquisitionAndRecordingToDB(boolean isInsert) {
        Disposable d1 = UtilDota.initRetrofitRx()
                .getRecordsByTitle(titleRecord)
                .flatMap(recordsResponse -> {
                    List<Record> recordListWithRecordTitle = getRecordListWithRecordTitle(recordsResponse);
                    String result;
                    db.recordDao().deleteAllRecordByTitle(titleRecord);
                    db.recordDao().insertAll(recordListWithRecordTitle);

                    if (isInsert) {
                        db.updateInfoDBDao().insert(new UpdateInfoDB(TABLE_ID_FOR_UPDATE, TABLE_NAME_FOR_UPDATE, (System.currentTimeMillis() + MILLIS_IN_WEEK)));
                    } else {
                        db.updateInfoDBDao().update(new UpdateInfoDB(TABLE_ID_FOR_UPDATE, TABLE_NAME_FOR_UPDATE, (System.currentTimeMillis() + MILLIS_IN_WEEK)));
                    }
                    result = "insertData";
                    return Single.just(result);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> Log.d(TAG, "recordDataAcquisitionAndRecordingToDB: " + result),
                        Throwable::printStackTrace
                );
    }*/

    /*public void checkValidRecordData() {
        Disposable disposable = db.updateInfoDBDao().getInfoUpdateByIdRx(TABLE_ID_FOR_UPDATE)
                .defaultIfEmpty(new UpdateInfoDB())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        updateInfoDB -> {
                            if (updateInfoDB.getCurrentTimeMillis() < System.currentTimeMillis()) {
                                if (updateInfoDB.getNameTable() == null) {
                                    recordDataAcquisitionAndRecordingToDB(true);
                                } else {
                                    recordDataAcquisitionAndRecordingToDB(false);
                                }
                                Log.d(TAG, updateInfoDB.toString() + " yyyyyyyy");
                            } else {
                                Log.d(TAG, "update not need " + updateInfoDB.toString());
                            }
                        },
                        error -> Log.e(TAG, error.getLocalizedMessage())
                );
    }*/

    /*private List<Record> getRecordListWithRecordTitle(List<Record> records) {
        for (Record record : records) {
            record.setRecordTitle(titleRecord);
        }

        return records;
    }*/

    private LiveData<List<Record>> getRecordsMutable() {
        Log.d(TAG, "NETWORK REQUEST");
        if (recordsMutable == null) {
            recordsMutable = new MutableLiveData<>();
        }

        Disposable disposable = UtilDota.initRetrofitRx()
                .getRecordsByTitle(titleRecord)
                .flatMap(records -> {
                    if (records.get(0).getHeroId().trim().length() > 0) {
                        SparseArray<Hero> heroes = sortHeroes(db.heroDao().getAll());

                        for (Record record : records) {
                            int heroId = Integer.parseInt(record.getHeroId());
                            record.setHeroImgUrl(heroes.get(heroId).getImg());
                            record.setHeroName(heroes.get(heroId).getLocalizedName());
                        }
                    }
                    return Single.just(records);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        recordsResponse -> recordsMutable.setValue(recordsResponse),
                        err -> recordsMutable.setValue(emptyRecordList)
                );

        return recordsMutable;
    }

    private SparseArray<Hero> sortHeroes(List<Hero> heroes) {
        SparseArray<Hero> sortedHeroes = new SparseArray<>();
        for (int i = 0; i < heroes.size(); i++) {
            sortedHeroes.put(heroes.get(i).getId(), heroes.get(i));
        }
        return sortedHeroes;
    }
}
