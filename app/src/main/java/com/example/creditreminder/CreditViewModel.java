package com.example.creditreminder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CreditViewModel extends AndroidViewModel {
    private CreditRepository repository;
    private LiveData<List<Credit>> allCredits;

    public CreditViewModel(@NonNull Application application) {
        super(application);
        repository = new CreditRepository(application);
        allCredits = repository.getAllCredits();
    }

    public void insert(Credit credit) {
        repository.insert(credit);
    }

    public void update(Credit credit) {
        repository.update(credit);
    }

    public void delete(Credit credit) {
        repository.delete(credit);
    }

    public void deleteAllCredits() {
        repository.deleteAllCredits();
    }

    public LiveData<List<Credit>> getAllCredits() {
        return allCredits;
    }
}
