package com.kobyakov.d2s.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.Record;

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
