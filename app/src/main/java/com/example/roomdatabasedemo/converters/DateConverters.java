package com.example.roomdatabasedemo.converters;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverters {

    /**
     * 必须是public
     */
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * 必须是public
     */
    @TypeConverter
    public Long dateToTimestamp (Date date) {
        return date == null ? null : date.getTime();
    }
}
