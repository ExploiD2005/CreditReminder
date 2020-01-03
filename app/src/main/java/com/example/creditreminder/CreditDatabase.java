package com.example.creditreminder;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Credit.class}, version = 1)
@TypeConverters({ConverterDataLong.class})
public abstract class CreditDatabase extends RoomDatabase {

    private static CreditDatabase instance;

    public abstract CreditDao creditDao();

    public static synchronized CreditDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CreditDatabase.class, "credit_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynkTask(instance).execute();
        }
    };

    private static class PopulateDbAsynkTask extends AsyncTask<Void, Void, Void> {
        private CreditDao creditDao;

        private PopulateDbAsynkTask(CreditDatabase db) {
            creditDao = db.creditDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //creditDao.insert(new Credit("Тинькофф",  Date(), (2019.09.25), 10-01-2019, 2000, 1000));
            return null;
        }
    }
}
