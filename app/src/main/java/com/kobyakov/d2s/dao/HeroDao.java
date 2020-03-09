package com.kobyakov.d2s.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kobyakov.d2s.model.Hero;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface HeroDao {
    @Query("SELECT * FROM hero")
    List<Hero> getAll();

    @Query("SELECT * FROM hero")
    Single<List<Hero>> getAllRx();

    @Query("SELECT * FROM hero")
    Maybe<List<Hero>> checkHeroes();

    @Query("SELECT * FROM hero WHERE id == :id LIMIT 1")
    Single<Hero> getHeroByIdRx(long id);

    @Query("SELECT * FROM hero WHERE id == :id LIMIT 1")
    Hero getHeroById(long id);

    @Insert
    void insertAll(List<Hero> heroes);

    @Delete
    void delete(Hero user);

    @Query("DELETE FROM hero")
    void deleteAllHeroes();

    @Update
    void updateAll(List<Hero> heroes);

    @Query("SELECT * FROM hero WHERE heroId IN(:heroIds)")
    List<Hero> heroFindByIds(List<Integer> heroIds);

    @Query("SELECT * FROM hero WHERE heroId IN(:heroIds)")
    List<Hero> heroFindByIdsArr(Long[] heroIds);
}
