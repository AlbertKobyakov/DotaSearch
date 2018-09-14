package com.example.albert.dotasearch.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albert.dotasearch.model.UpdateInfoDB;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface UpdateInfoDBDao {
    @Query("SELECT * FROM updateinfodb")
    List<UpdateInfoDB> getAllInfoUpdate();

    @Query("SELECT * FROM updateinfodb")
    Single<List<UpdateInfoDB>> getAllInfoUpdateRx();

    @Query("SELECT * FROM updateinfodb WHERE idTable == :id LIMIT 1")
    Maybe<UpdateInfoDB> getInfoUpdateByIdRx(int id);

    @Delete
    void delete(UpdateInfoDB updateInfoDB);

    @Update
    void update(UpdateInfoDB updateInfoDB);

    @Insert
    void insert(UpdateInfoDB updateInfoDB);
}
