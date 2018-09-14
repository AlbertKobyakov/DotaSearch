package com.example.albert.dotasearch.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albert.dotasearch.model.Record;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface RecordDao {
    @Query("SELECT * FROM record")
    List<Record> getAll();

    @Query("SELECT * FROM record WHERE recordTitle == :recordTitle")
    LiveData<List<Record>> getAllRecordsByTitleLiveData(String recordTitle);

    @Query("SELECT * FROM record WHERE recordTitle == :recordTitle")
    Single<List<Record>> getAllRecordsByTitle(String recordTitle);

    @Query("SELECT * FROM record WHERE recordTitle == :recordTitle")
    Maybe<List<Record>> getConcreteRecordRx(String recordTitle);

    @Insert
    void insertAll(List<Record> records);

    @Delete
    void delete(Record record);

    @Update
    void updateAll(List<Record> records);

    @Query("DELETE FROM record WHERE recordTitle == :recordTitle")
    void deleteAllRecordByTitle(String recordTitle);
}
