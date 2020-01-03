package com.example.creditreminder;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CreditRepository {
    private CreditDao creditDao;
    private LiveData<List<Credit>> allCredits;

    public CreditRepository(Application application) {
        CreditDatabase database = CreditDatabase.getInstance(application);
        creditDao = database.creditDao();
        allCredits = creditDao.getAllCredits();
    }

    public void insert(Credit credit) {
        new InsertCreditAsyncTask(creditDao).execute(credit);
    }

    public void update(Credit credit) {
        new UpdateCreditAsyncTask(creditDao).execute(credit);
    }

    public void delete(Credit credit) {
        new DeleteCreditAsyncTask(creditDao).execute(credit);
    }

    public void deleteAllCredits() {
        new DeleteAllCreditsAsyncTask(creditDao).execute();
    }

    public LiveData<List<Credit>> getAllCredits() {
        return allCredits;
    }

    private static class InsertCreditAsyncTask extends AsyncTask<Credit, Void, Void> {
        private CreditDao creditDao;

        private InsertCreditAsyncTask(CreditDao creditDao) {
            this.creditDao = creditDao;
        }

        @Override
        protected Void doInBackground(Credit... credits) {
            creditDao.insert(credits[0]);
            return null;
        }
    }

    private static class UpdateCreditAsyncTask extends AsyncTask<Credit, Void, Void> {
        private CreditDao creditDao;

        private UpdateCreditAsyncTask(CreditDao creditDao) {
            this.creditDao = creditDao;
        }

        @Override
        protected Void doInBackground(Credit... credits) {
            creditDao.update(credits[0]);
            return null;
        }
    }

    private static class DeleteCreditAsyncTask extends AsyncTask<Credit, Void, Void> {
        private CreditDao creditDao;

        private DeleteCreditAsyncTask(CreditDao creditDao) {
            this.creditDao = creditDao;
        }

        @Override
        protected Void doInBackground(Credit... credits) {
            creditDao.delete(credits[0]);
            return null;
        }
    }

    private static class DeleteAllCreditsAsyncTask extends AsyncTask<Void, Void, Void> {
        private CreditDao creditDao;

        private DeleteAllCreditsAsyncTask(CreditDao creditDao) {
            this.creditDao = creditDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            creditDao.deleteAllCredits();
            return null;
        }
    }
}
