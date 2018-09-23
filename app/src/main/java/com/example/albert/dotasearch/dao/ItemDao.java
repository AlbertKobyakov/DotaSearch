package com.example.albert.dotasearch.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albert.dotasearch.model.Item;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Query("SELECT * FROM item")
    Single<List<Item>> getAllRx();

    @Query("SELECT * FROM item")
    Maybe<List<Item>> checkItems();

    @Query("SELECT * FROM item WHERE id == :key LIMIT 1")
    Single<Item> getItemByIdRx(long key);

    @Insert
    void insertAll(List<Item> items);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM item")
    void deleteAllItems();


    @Update
    void updateAll(List<Item> items);
}
