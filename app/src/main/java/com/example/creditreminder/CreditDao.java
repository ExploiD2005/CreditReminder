package com.example.creditreminder;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CreditDao {
    @Insert
    void insert(Credit credit);

    @Update
    void update(Credit credit);

    @Delete
    void delete(Credit credit);

    @Query("DELETE FROM credit_table")
    void deleteAllCredits();

    @Query("SELECT * FROM credit_table ORDER BY last_pay_date ASC")
    LiveData<List<Credit>> getAllCredits();

    @Query("SELECT * FROM credit_table ORDER BY last_pay_date ASC")
    List<Credit> getAllCreditsForService();
}
