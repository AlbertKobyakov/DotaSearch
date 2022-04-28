package com.kobyakov.d2s.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.Item;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Query("SELECT * FROM item")
    LiveData<List<Item>> getAllLive();

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

    @Query("SELECT * FROM item WHERE id IN(:itemIds)")
    List<Item> itemFindByIds(List<Long> itemIds);
}
