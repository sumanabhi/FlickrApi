package com.abhi.flickr.repository.db;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abhi.flickr.repository.entities.FlikrEntity;

import java.util.List;


@Dao
public interface FlikrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFlikrs(List<FlikrEntity> flikrs);

    @Query("SELECT * FROM flikrs")
    LiveData<List<FlikrEntity>> getAllFlikrsLive();

    @Query("SELECT * FROM flikrs")
    List<FlikrEntity> getAllFlikrs();

    @Query("SELECT * FROM flikrs LIMIT :limit")
    LiveData<List<FlikrEntity>> getFlikrs(int limit);

    @Query("SELECT * FROM flikrs WHERE id=:id")
    LiveData<FlikrEntity> getFlikr(String id);

    @Query("DELETE FROM flikrs")
    void deleteAllFlikrs();


}

