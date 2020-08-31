package com.abhi.flickr.repository.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.abhi.flickr.repository.entities.FlikrEntity;


@Database(entities = {FlikrEntity.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {

    static private final String DATABASE_NAME = "flikrdb";
    private static RoomDb INSTANCE;

    public abstract FlikrDao flikrDao();

    public static RoomDb getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDb.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

}
