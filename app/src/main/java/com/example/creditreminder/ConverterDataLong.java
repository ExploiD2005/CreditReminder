package com.example.creditreminder;

import androidx.room.TypeConverter;

import java.util.Date;

public class ConverterDataLong {
    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null: date.getTime();
    }
}