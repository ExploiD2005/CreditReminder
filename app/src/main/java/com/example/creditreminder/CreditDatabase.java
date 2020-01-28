package com.example.creditreminder;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.Date;

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
                    .addCallback(roomCallback) //Заполнение таблицы записями для примера
                    .build();
        }
        return instance;
    }

    //
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
            Date lpd = new Date();
            //Date date = new SimpleDateFormat("dd.MM.yyyy").parse("28.12.2019");
            Calendar date1 = Calendar.getInstance();
            date1.set(2019,11,10);
            Date date = date1.getTime();
            creditDao.insert(new Credit("Тинькофф",  lpd, 2000, 500, 0));
            creditDao.insert(new Credit("Сбербанк",  date, 5000, 0, 0));
            creditDao.insert(new Credit("Альфа-банк",  lpd, 6000, 0, 0));
            return null;
        }
    }
}
