package com.example.creditreminder;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Credit.class}, version = 1)
public abstract class CreditDatabase extends RoomDatabase {

    private static CreditDatabase instance;

    public abstract CreditDao creditDao();

    public static synchronized CreditDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CreditDatabase.class, "credit_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
