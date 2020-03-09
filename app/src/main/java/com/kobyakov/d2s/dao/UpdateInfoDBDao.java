package com.kobyakov.d2s.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.UpdateInfoDB;

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
